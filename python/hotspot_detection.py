
import numpy as np
import pandas as pd
from sklearn.neighbors import NearestNeighbors
from sklearn.preprocessing import StandardScaler
import requests
import datetime
import sys
import tensorflow

API_KEY = 'KLVKNlEgezMFZUhLNCrgSxFJfTgGbOAx'

us_stations = pd.read_csv('./csv/us_stations.csv')

def get_stations(stations, knn, lat, lng):
    neighbors = knn.kneighbors([[lat, lng]])[1]
    neighbor_data = stations.iloc[neighbors[0]]
    return list(neighbor_data['STATIONID'].values)

def get_query_string(stations):
    query_string = ''
    for station in stations:
        query_string += '&stationid=GHCND:' + station
    return query_string

def query_weather_data(stations, start, end, token, limit=100):
    
    url = 'https://www.ncdc.noaa.gov/cdo-web/api/v2/data?datasetid=GHCND&startdate=' + start + '&enddate=' + end + '&limit=' + str(limit)
    url += get_query_string(stations)

    headers = {'token': token}

    response = requests.get(url=url, headers=headers)
    
    return response.json()

def parse_weather_data(response):
    # [count, value]
    weather_data = {
        'TMAX': [0, 0],
        'TMIN': [0, 0],
        'PRCP': [0, 0],
        'SNOW': [0, 0]
    }
    
    try:
        for result in response['results']:
            try:
                weather_data[result['datatype']][1] += result['value']
                weather_data[result['datatype']][0] += 1
            except:
                pass
    except:
        pass
    
    tmax = weather_data['TMAX'][1] / weather_data['TMAX'][0] if weather_data['TMAX'][0] > 0 else 0
    tmin = weather_data['TMIN'][1] / weather_data['TMIN'][0] if weather_data['TMIN'][0] > 0 else 0
    prcp = weather_data['PRCP'][1] / weather_data['PRCP'][0] if weather_data['PRCP'][0] > 0 else 0
    snow = weather_data['SNOW'][1] / weather_data['SNOW'][0] if weather_data['SNOW'][0] > 0 else 0
    
    return tmax, tmin, prcp, snow

def get_start_date(end_date):
    return str(datetime.date.fromisoformat(end_date) - datetime.timedelta(days=14))

def preprocess_inputs(lat, lng, date, tmax, tmin, prcp, snow, scaler):
    # Process date
    date = datetime.date.fromisoformat(date)
    year = date.year
    month = date.month
    day = date.day
    
    # Create NumPy array and scale
    x = np.array([lat, lng, year, month, day, tmax, tmin, prcp, snow])
    x = scaler.transform(x)
    
    return x

#comman line calls
latitude = sys.argv[1]
longitude = sys.argv[2]
date = sys.argv[3]

knn = NearestNeighbors(n_neighbors=10)
knn.fit(us_stations.loc[:, ['LATITUDE', 'LONGITUDE']])

stations = get_stations(us_stations, knn, lat=np.float(latitude), lng=np.float(longitude))

response = query_weather_data(
    stations=stations,
    start=get_start_date(end_date=date),
    end=date,
    token=API_KEY,
    limit=100
)

tmax, tmin, prcp, snow = parse_weather_data(response)

model_input = preprocess_inputs(
    lat=latitude,
    lng=longitude,
    date=date,
    tmax=tmax,
    tmin=tmin,
    prcp=prcp,
    snow=snow
)

model = tf.keras.Model()
model.load('./models/hotspot_detector.h5')

pred = np.squeeze(model.predict(model_input))
pred = pred >= 0.5

if pred == True:
    print("HOTSPOT")
else:
    print("SAFE")
