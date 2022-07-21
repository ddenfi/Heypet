##  Description
----------------
In building the ML model, we used TensorFlow as a framework for building deep learning models and Google Collab as an editor. For the dataset, we took data from the Kaggle [23 pet breeds image classification](https://www.kaggle.com/datasets/aseemdandgaval/23-pet-breeds-image-classification) with 3881 images divided into 23 classes. We used the fine-tuning method using the pretrained model [InceptionV3](https://keras.io/api/applications/inceptionv3), with an output class of 10 animal races: 
1. Abyssinian 
2. American shorthair 
3. Bulldog 
4. Chihuahua
5. Goldenretriever
6. Husky
7. Labrador
8. Persian
9. Siamese
10. Sphynx


##  Running Flask API
----------------
This project files requires Python 3 and the following Python libraries installed:
- Tensorflow
- Flask
- Numpy
- PIL

### Running
1. Download model.h5 [here](https://drive.google.com/file/d/1Z7WWnsSKB3tPdx_-dD5yPizZFtcxdN91/view?usp=sharing)
2. put model it in the same place as app.py
3. run app.py using this command `python app.py`


## Test model using this method

----------------

### Request:
```
POST https://breed-pet-classification.herokuapp.com/predict
```

### Examples:

> 
> **Body**
> ```form-data```
>
> key: image | type: file | value: {imagepath}.jpg
> 
> 

### Response
```
{
    "breed": "siamese",
    "confidence": "0.96",
    "status": "200"
}
```
