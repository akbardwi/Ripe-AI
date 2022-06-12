#!/bin/bash

sudo docker run --name Apple -p 8501:8501 -p 8500:8500 --mount type=bind,source=$HOME/Ripe-AI/Machine\ Learning/Model/Banana,target=/models/Apple -e MODEL_NAME=Apple -t tensorflow/serving:latest