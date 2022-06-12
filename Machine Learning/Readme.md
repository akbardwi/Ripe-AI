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
