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
