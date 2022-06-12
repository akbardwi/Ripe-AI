#!/bin/bash

sudo docker pull tensorflow/serving:latest
sudo docker run -d --name serving_base tensorflow/serving
sudo docker cp {MODEL_PATH} serving_base:/models/{MODEL_NAME}
sudo docker commit --change "ENV MODEL_NAME {MODEL_NAME}" serving_base {IMAGE_NAME}
sudo docker tag {IMAGE_NAME}:latest rayhanhaqi/predict_banana:latest
sudo docker push rayhanhaqi/predict_banana:latest