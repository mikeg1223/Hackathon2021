import numpy as np
from PIL import Image
import tensorflow as tf
import sys

# Enter model filepath
path_to_model = '../python/resources/fire_detector.h5'

fire_detector = tf.keras.models.load_model(path_to_model)

image_path = sys.argv[1]

image = tf.expand_dims(
    tf.keras.preprocessing.image.smart_resize(
        tf.convert_to_tensor(
            np.array(
                Image.open(image_path)
            )
        ), size=(224, 224)
    ), axis=0)

prediction = fire_detector.predict(image)



if int(prediction) == 1:
    print("FIRE")
else:
    print("NO FIRE")
