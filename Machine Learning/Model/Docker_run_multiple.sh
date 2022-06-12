docker run \
-p 8501:8501 \
-p 8500:8500 \
--mount type=bind,source=$HOME/Ripe-AI/Machine\ Learning/Model/Apple,target=/models/Apple/ \
--mount type=bind,source=$HOME/Ripe-AI/Machine\ Learning/Model/Banana,target=/models/Banana/ \
--mount type=bind,source=$HOME/Ripe-AI/Machine\ Learning/Model/model_config.conf,target=/config/model_config.conf \
-t tensorflow/serving:latest --model_config_file=/config/model_config.conf