from urllib import response
from matplotlib import image
import requests
import json
import numpy as np
import cv2 as cv
import time

img = cv.imread('/home/rayhan/Ripe-AI/Machine Learning/Script/Apple/download (3).jpeg')
img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
img = cv.resize(img, (150,150))
img = np.array(img)
img = np.expand_dims(img, axis=0)
img = img / 255

# URL Pisang = http://34.70.38.194:8501/v1/models/Banana:predict


url = 'http://localhost:8501/v1/models/Apple:predict'
data = json.dumps({"signature_name": "serving_default", "instances": img.tolist()})
# with open('data.json', 'w') as f:
#     json.dump(data, f)
headers = {"Content-Type": "application/json"}
response = requests.post(url, data = data)
prediction = json.loads(response.text)["predictions"]
predict_dict = {"Overipe": prediction[0][0], "Ripe": prediction[0][1], "Unripe": prediction[0][2]}
predict_dict = sorted(predict_dict.items(), key=lambda x: x[1], reverse=True)

# print(prediction)

for i, j in predict_dict:
    print("{:>8} = {:>13.10f} %" .format(i, j*100))


#    Prediction Pisang:
#       Index 0 = Overipe
#       Index 1 = Ripe 
#       Index 2 = Unripe 

#    Prediction Apple:
#       Index 0 = Overipe
#       Index 1 = Ripe 
#       Index 2 = Unripe 
