#!/bin/bash

sudo docker run --name Banana -p 8501:8501 -p 8500:8500 --mount type=bind,source=$PWD/Ripe-AI/Machine\ Learning/Model/Banana,target=/models/Banana -e MODEL_NAME=Banana -t tensorflow/serving:latest