from urllib import response
import requests
import json
import numpy as np
import cv2 as cv
import tensorflow as tf
import time

img = cv.imread('image.jpg')
img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
img = cv.resize(img, (150,150))
img = np.expand_dims(img, axis=0)

url = ''
data = json.dumps({"signature_name": "", "instances": })
headers = {"content-type": "application/json"}
response = requests.post(url, data = data)
prediction = json.loads(response.text)["prediction"]
print(prediction)