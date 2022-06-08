from urllib import response
from matplotlib import image
import requests
import json
import numpy as np
import cv2 as cv
import tensorflow as tf
import time

img = cv.imread('/home/rayhan/Ripe-AI/Machine Learning/Script/image.jpeg')
img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
img = cv.resize(img, (150,150))
img = np.array(img)
img = np.expand_dims(img, axis=0)
img = img / 255


url = 'http://localhost:8501/v1/models/Banana:predict'
data = json.dumps({"signature_name": "", "instances": img.tolist()})
headers = {"content-type": "application/json"}
response = requests.post(url, data = data)
prediction = json.loads(response.text)["predictions"]
print(prediction)