#!/bin/bash

sudo docker run -p 8501:8501 -p 8500:8500 --mount type=bind,source=/home/rayhan/Ripe-AI/Machine\ Learning/Model/Banana,target=/models/Banana -e MODEL_NAME=Banana -t tensorflow/serving:latest