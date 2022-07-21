from flask import Flask, render_template, request, jsonify
from keras.models import load_model
from keras.preprocessing import image
import numpy as np
import base64
from PIL import Image
import io

app = Flask(__name__)

model = load_model('weights-fine1-48-0.92.h5')

label = ["abyssinian", "americanshorthair", "bulldog", "chihuahua", "goldenretriever", "husky", "labrador", "persian", "siamese", "sphynx"]

# Without Save image to directory
def predict_label(img):
    i = image.img_to_array(img) / 255.0
    i = i.reshape(1, 299, 299, 3)
    p = model.predict(i)
    
    return p

# Load Save image to directory
# def predict_label(img_path):
#     i = image.load_img(img_path, target_size=(299, 299))
#     i = image.img_to_array(img_path) / 255.0
#     i = i.reshape(1, 299, 299, 3)
#     p = model.predict(i)
#     result = label[np.argmax(p)]
#     return result


# routes
@app.route("/")
def index():
    return jsonify({'status': '200', 'message': 'hello anggiat'})

@app.route("/test")
def about_page():
    # return "This is about page"
    return jsonify({'status': '200', 'message': 'hello world'})

@app.route("/predict", methods=['GET', 'POST'])
# Without Save image to directory
def get_output():
    if request.method == 'POST':
        image = request.files['image']

        img = Image.open(image)
        img = img.resize((299, 299), Image.NEAREST)
        # img_path = "static/" + img.filename
        # img.save(img_path)
        p = predict_label(img)
        breed = label[np.argmax(p)]
        print(p)
        print("confidence: ", p[0][np.argmax(p)])
        conf = "%.2f" % p[0][np.argmax(p)]
    return jsonify({'status': '200', 'breed': breed, 'confidence': conf})

# Load Save image to directory
# def get_output():
#     if request.method == 'POST':
#         image = request.files['my_image']

#         img_path = "static/" + img.filename
#         img.save(img_path)
        
#         p = predict_label(img_path)
#     return jsonify({'status': '200', 'message': p})

if __name__ == '__main__':
    # app.debug = True
    app.run()