import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import io
import tensorflow as tf
from tensorflow import keras
from keras.models import load_model
import numpy as np
from PIL import Image
from datetime import datetime
from flask import Flask, request, jsonify
import urllib.request
from keras.preprocessing import image
import requests

print(os.getcwd())
model = keras.models.load_model("model.h5")
label = ["Abyssinian", "American Shorthair", "Bulldog", "Chihuahua", "Golden Retriever", "Husky", "Labrador", "Persian", "Siamese", "Sphynx"]

app = Flask(__name__)

def predict_label(img):
    i = np.asarray(img) / 255.0
    i = i.reshape(1, 299, 299, 3)
    p = model.predict(i)
    result = label[np.argmax(p)]
    return result


@app.route("/predict", methods=["GET", "POST"])
def index():
    file = request.files.get('file')
    if file is None or file.filename == "":
            return jsonify({"error": "no file"})
    
    if request.method == "POST":
        image_bytes = file.read()
        img = Image.open(io.BytesIO(image_bytes))
        img = img.resize((299, 299), Image.NEAREST)
        p = predict_label(img)
        res = requests.get("https://heypetpetpet-2326mg2rrq-uc.a.run.app/api/v1/animals/key/name?name={}".format(p))
    return res.json()


if __name__ == "__main__":
    app.run(debug=True)