# Machine Learning with Tensorflow Serving

TensorFlow Serving is a flexible, high-performance serving system for machine learning models, designed for production environments. TensorFlow Serving makes it easy to deploy new algorithms and experiments, while keeping the same server architecture and APIs. TensorFlow Serving provides out-of-the-box integration with TensorFlow models, but can be easily extended to serve other types of models and data. 

The reason our team use this method because our target market are the peoples who lived in urban cities that have a fully access to the internet. We also don't want to make the mobile application to use a large size of storage and memory so we load most of it's data and processing to the internet.

## Installation

The main package that required to deploy the model is tensorflow/serving docker image:

```bash
sudo docker pull tensorflow/serving:latest
```

## Deployment 

To deploy the model to local server is by using this bash

```bash
sudo docker run --name {MODEL_NAME} -p 8501:8501 -p 8500:8500 --mount type=bind,source={MODEL_PATH},target=/models/{MODEL_NAME} -e MODEL_NAME={MODEL_NAME} -t tensorflow/serving:latest
```

For example:

```bash
sudo docker run --name Banana -p 8501:8501 -p 8500:8500 --mount type=bind,source=$HOME/Ripe-AI/Machine\ Learning/Model/Banana,target=/models/Banana -e MODEL_NAME=Banana -t tensorflow/serving:latest
```
## Usage

### Python script example:

You can find this code on Script/Inference.py

```python
from urllib import response
import requests
import json
import numpy as np
import cv2 as cv

img = cv.imread('/home/rayhan/Ripe-AI/Machine Learning/Script/Banana/Ripe 1.jpeg')
img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
img = cv.resize(img, (150,150))
img = np.array(img)
img = np.expand_dims(img, axis=0)
img = img / 255

url = 'http://34.70.38.194:8501/v1/models/Banana:predict'
data = json.dumps({"signature_name": "serving_default", "instances": img.tolist()})
headers = {"Content-Type": "application/json"}
response = requests.post(url, data = data)
prediction = json.loads(response.text)["predictions"]
predict_dict = {"Overripe": prediction[0][0], "Ripe": prediction[0][1], "Unripe": prediction[0][2]}
predict_dict = sorted(predict_dict.items(), key=lambda x: x[1], reverse=True)

for i, j in predict_dict:
    print("{:>8} = {:>13.10f} %" .format(i, j*100))
```

Output:

```python
    Ripe = 99.9947786000 %
  Unripe =  0.0052236723 %
Overripe =  0.0000031387 %
```

### Example on API Testing:

Endpoint:

```bash
http://34.70.38.194:8501/v1/models/Banana:predict
```

Content-Type:

```bash
application/json
```

Body:

```bash
{"signature_name": "serving_default", "instances": img.tolist()}
```

We cannot preview the image data list (img.tolist()) here because the content is too long. You can check it out on data.json.

Example:

![Screenshot from 2022-06-11 18-04-38](https://user-images.githubusercontent.com/62177002/173215140-58cc25b5-f99b-48fe-a086-47e865b4d8cb.png)

Return:

```bash
{
    "predictions": [
        [
            3.13874402e-08,
            0.999947786,
            5.22367227e-05
        ]
    ]
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
