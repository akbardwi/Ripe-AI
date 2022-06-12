import shutil
import os

from matplotlib import image

FRUIT = "Apple"
CATEGORIES = ["Overipe", "Ripe", "Unripe"]
HOME_PATH = os.getenv("HOME")


for category in CATEGORIES:
    os.makedirs(os.path.join(HOME_PATH, "Ripe-AI/Machine Learning/Dataset/", FRUIT, "Training", category+" "+FRUIT), exist_ok=True)
    os.makedirs(os.path.join(HOME_PATH, "Ripe-AI/Machine Learning/Dataset/", FRUIT, "Test", category+" "+FRUIT), exist_ok=True)
    os.makedirs(os.path.join(HOME_PATH, "Ripe-AI/Machine Learning/Dataset/", FRUIT, "Validation", category+" "+FRUIT), exist_ok=True)

for category in CATEGORIES:
    CATEGORY_PATH   = os.path.join(HOME_PATH, "Ripe-AI/Machine Learning/Dataset/", FRUIT, category+" "+FRUIT)
    images          = os.listdir(CATEGORY_PATH)

    train_images = images[:int(0.7*len(images))]
    val_images   = images[int(0.7*len(images)):int(0.9*len(images))]
    test_images  = images[int(0.9*len(images)):]

    for file in train_images:
        shutil.copy(os.path.join(CATEGORY_PATH, file), os.path.join(HOME_PATH, "Ripe-AI/Machine Learning/Dataset/", FRUIT, "Training", category+" "+FRUIT, file))

    for file in val_images:
        shutil.copy(os.path.join(CATEGORY_PATH, file), os.path.join(HOME_PATH, "Ripe-AI/Machine Learning/Dataset/", FRUIT, "Validation", category+" "+FRUIT, file))

    for file in test_images:
        shutil.copy(os.path.join(CATEGORY_PATH, file), os.path.join(HOME_PATH, "Ripe-AI/Machine Learning/Dataset/", FRUIT, "Test", category+" "+FRUIT, file))