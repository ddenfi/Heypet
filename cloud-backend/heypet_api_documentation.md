# HEYPET API Documentation

# Auth API

## Register

URL: `http://34.121.253.2:5001/api/v1/auth/register`

Method: `POST`

Auth requered: YES

Example Request (All fields must be filled):

```
curl --location --request POST 'http://34.121.253.2:5001/api/v1/auth/register' \
--form 'name="heypet"' \
--form 'bio="hi this is heypet"' \
--form 'email="heypet@gmail.com"' \
--form 'phoneNumber="082324252627"' \
--form 'password="heypet123"' \
--form 'photo=@"/C:/Users/Milla/Downloads/Capstone Project/image/image.jpg"'
```

- Email and phoneNumber must unique

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "token": {
            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjI2LCJpYXQiOjE2NTQ1MDU1NTEsImV4cCI6MTY1NDU2NTU1MSwidHlwZSI6ImFjY2VzcyJ9.JXFAQh8JphSZmOIUW5cVrTFfyUaksLB5fn9oAtU0i68",
            "accessTokenExpires": "2022-06-07 01:32:31"
        },
        "user": {
            "role": "user",
            "id": 26,
            "name": "heypet",
            "bio": "hi this is heypet",
            "email": "heypet@gmail.com",
            "phoneNumber": "082324252627",
            "photo": "http://34.121.253.2:5001/user/photo-1654505551014.jpeg",
            "updatedAt": "2022-06-06T08:52:31.895Z",
            "createdAt": "2022-06-06T08:52:31.895Z"
        }
    }
}
```

## Login

URL: `http://34.121.253.2:5001/api/v1/auth/login`

Method: `POST`

Auth requered: YES

Example Request (All fields must be filled):

```
curl --location --request POST 'http://34.121.253.2:5001/api/v1/auth/login' \
--data-raw '{
    "email" : "heypet@gmail.com",
    "password" : "heypet123"
}'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "token": {
            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjI2LCJpYXQiOjE2NTQ1MDU3NzEsImV4cCI6MTY1NDU2NTc3MSwidHlwZSI6ImFjY2VzcyJ9.E45aDUG07q4DINbVMpTG6wcyFIg3Jwi6W5yPM2h0AMQ",
            "accessTokenExpires": "2022-06-07 01:36:11"
        },
        "user": {
            "id": 26,
            "name": "heypet",
            "bio": "hi this is heypet",
            "email": "heypet@gmail.com",
            "phoneNumber": "082324252627",
            "photo": "http://34.121.253.2:5001/user/photo-1654505551014.jpeg",
            "role": "user"
        }
    }
}
```

# User

## Get All Users

URL: `http://34.121.253.2:5001/api/v1/users`

Method: `GET`

Auth requered: YES (Bearer Token)

**Must Input Token**

Example Token: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjI2LCJpYXQiOjE2NTQ1MDU3NzEsImV4cCI6MTY1NDU2NTc3MSwidHlwZSI6ImFjY2VzcyJ9.E45aDUG07q4DINbVMpTG6wcyFIg3Jwi6W5yPM2h0AMQ`

Example Request:

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/users'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "count": 22,
    "results": 22,
    "data": {
        "users": [
            {
                "id": 2,
                "name": "heypet",
                "bio": "hi this is heypet",
                "email": "heypet@gmail.com",
                "phoneNumber": "082324252627",
                "password": "$2a$08$Lko2b1wsLdo2SXug3aWwguJo9AC/.k6CHgEtdQw84oh7KuTbdXfei",
                "photo": "http://34.121.253.2:5001/user/photo-1654505551014.jpeg",
                "role": "user",
                "createdAt": "2022-06-06T08:52:31.000Z",
                "updatedAt": "2022-06-06T08:52:31.000Z"
            },
            {
                "id": 1,
                "name": "zayn",
                "bio": "halooo semua",
                "email": "zayn@gmail.com",
                "phoneNumber": "0823724234586",
                "password": "$2a$08$WVYhgyyH4OhNbWlSlE91O.TA7iTxMM8QZjxGHBaJSG8jIa/4iW7Ay",
                "photo": "http://34.121.253.2:5001/user/photo-1654446204064.jpeg",
                "role": "user",
                "createdAt": "2022-06-05T16:23:24.000Z",
                "updatedAt": "2022-06-05T16:23:24.000Z"
            }
        ]
    }
}
```

## Get Users by Token

URL: `http://34.121.253.2:5001/api/v1/users/session`

Method: `GET`

Auth requered: YES (Bearer Token)

**Must Input Token**

Example Token: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjI2LCJpYXQiOjE2NTQ1MDU3NzEsImV4cCI6MTY1NDU2NTc3MSwidHlwZSI6ImFjY2VzcyJ9.E45aDUG07q4DINbVMpTG6wcyFIg3Jwi6W5yPM2h0AMQ`

Example Request:

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/users/session'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "users": {
            "id": 26,
            "name": "heypet",
            "bio": "hi this is heypet",
            "email": "heypet@gmail.com",
            "phoneNumber": "082324252627",
            "password": "$2a$08$Lko2b1wsLdo2SXug3aWwguJo9AC/.k6CHgEtdQw84oh7KuTbdXfei",
            "photo": "http://34.121.253.2:5001/user/photo-1654505551014.jpeg",
            "role": "user",
            "createdAt": "2022-06-06T08:52:31.000Z",
            "updatedAt": "2022-06-06T08:52:31.000Z"
        }
    }
}
```

## Update Bio

URL: `http://34.121.253.2:5001/api/v1/users/session`

Method: `PUT`

Auth requered: YES (Bearer Token)

**Must Input Token**

Example Token: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjI2LCJpYXQiOjE2NTQ1MDU3NzEsImV4cCI6MTY1NDU2NTc3MSwidHlwZSI6ImFjY2VzcyJ9.E45aDUG07q4DINbVMpTG6wcyFIg3Jwi6W5yPM2h0AMQ`

Example Request:

```
curl --location --request PUT 'http://localhost:5000/api/v1/users/session' \
--data-raw '{
    "bio": "haii guys')"
}'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success"
}
```

## Update Photo Profile

URL: `http://34.121.253.2:5001/api/v1/users/session/photo`

Method: `PUT`

Auth requered: YES (Bearer Token)

**Must Input Token**

Example Token: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjI2LCJpYXQiOjE2NTQ1MDU3NzEsImV4cCI6MTY1NDU2NTc3MSwidHlwZSI6ImFjY2VzcyJ9.E45aDUG07q4DINbVMpTG6wcyFIg3Jwi6W5yPM2h0AMQ`

Example Request:

```
curl --location --request PUT 'http://localhost:5000/api/v1/users/session/photo' \
--form 'photo=@"/C:/Users/Milla/Downloads/Capstone Project/image/puppy.jpg"'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success"
}
```

# Category

## Create Category

URL: `http://34.121.253.2:5001/api/v1/categories`

Method: `POST`

Auth requered: NO

Example Request:

```
curl --location --request POST 'http://34.121.253.2:5001/api/v1/categories' \
--data-raw '{
    "name" : "Reels"
}'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "categories": {
            "id": 5,
            "name": "Reels",
            "updatedAt": "2022-06-06T13:56:53.333Z",
            "createdAt": "2022-06-06T13:56:53.333Z"
        }
    }
}
```

## Get All Categories

URL: `http://34.121.253.2:5001/api/v1/categories`

Method: `GET`

Auth requered: NO

Example Request:

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/categories' \
--data-raw ''
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "results": 5,
    "data": {
        "categories": [
            {
                "id": 1,
                "name": "Story",
                "createdAt": null,
                "updatedAt": null
            },
            {
                "id": 2,
                "name": "breeding",
                "createdAt": null,
                "updatedAt": null
            },
            {
                "id": 3,
                "name": "Adopsi",
                "createdAt": null,
                "updatedAt": null
            },
            {
                "id": 4,
                "name": "Tips",
                "createdAt": null,
                "updatedAt": null
            }
        ]
    }
}
```

## Get Category By Id

URL: `http://34.121.253.2:5001/api/v1/categories/:id`

Method: `GET`

Auth requered: NO

Example Request:

#### HEADERS

id: 1

#### PATH VARIABLES

id: 5

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/categories/5'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "categories": {
            "id": 5,
            "name": "Reels",
            "createdAt": "2022-06-06T13:56:53.000Z",
            "updatedAt": "2022-06-06T13:56:53.000Z"
        }
    }
}
```

## Update Category

URL: `http://34.121.253.2:5001/api/v1/categories/:id`

Method: `PUT`

Auth requered: NO

Example Request:

#### PATH VARIABLES

id: 5

```
curl --location --request PUT 'http://34.121.253.2:5001/api/v1/categories/5' \
--data-raw '{
    "name" : "Charity"
}'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "categories": [
            1
        ]
    }
}
```

## Delete Category

URL: `http://34.121.253.2:5001/api/v1/categories/:id`

Method: `DELETE`

Auth requered: NO

Example Request:

#### PATH VARIABLES

id: 5

```
curl --location --request DELETE 'http://34.121.253.2:5001/api/v1/categories/5'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "categories": 1
    }
}
```

# Animal

## Create Animal

URL: `http://34.121.253.2:5001/api/v1/animals`

Method: `POST`

Auth requered: NO

Example Request:

```
curl --location --request POST 'http://34.121.253.2:5001/api/v1/animals' \
--data-raw '{
    "name" : "Abyssinian 2",
    "animalType":"Cat",
    "personality" : "Abyssinian adalah kucing cerdas yang suka bermain dan berinteraksi dengan manusia. Keahliannya dalam mengamati manusia sangat baik dan kucing ini paling cocok untuk orang-orang dari segala usia yang menghabiskan banyak waktu di rumah.",
    "health":"Abyssinians dapat mengalami penyakit periodontal dan kondisi keturunan yang disebut defisiensi piruvat kinase. Kucing yang kekurangan piruvat kinase mengalami anemia intermiten.",
    "grooming": "Mantel pendek Abyssinian mudah dirawat—rawat dia setiap minggu dengan sisir baja tahan karat untuk menghilangkan rambut mati dan menjaga mantelnya tetap berkilau. Potong kuku mereka sesuai kebutuhan, biasanya setiap 10 hingga 14 hari. Abyssinians dapat mengalami penyakit periodontal, jadi sikat gigi mereka di rumah dengan pasta gigi hewan peliharaan yang disetujui dokter hewan dan jadwalkan pembersihan hewan secara teratur."
}'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "categories": {
            "id": 4,
            "name": "Abyssinian 2",
            "animalType": "Cat",
            "health": "Abyssinians dapat mengalami penyakit periodontal dan kondisi keturunan yang disebut defisiensi piruvat kinase. Kucing yang kekurangan piruvat kinase mengalami anemia intermiten.",
            "personality": "Abyssinian adalah kucing cerdas yang suka bermain dan berinteraksi dengan manusia. Keahliannya dalam mengamati manusia sangat baik dan kucing ini paling cocok untuk orang-orang dari segala usia yang menghabiskan banyak waktu di rumah.",
            "grooming": "Mantel pendek Abyssinian mudah dirawat—rawat dia setiap minggu dengan sisir baja tahan karat untuk menghilangkan rambut mati dan menjaga mantelnya tetap berkilau. Potong kuku mereka sesuai kebutuhan, biasanya setiap 10 hingga 14 hari. Abyssinians dapat mengalami penyakit periodontal, jadi sikat gigi mereka di rumah dengan pasta gigi hewan peliharaan yang disetujui dokter hewan dan jadwalkan pembersihan hewan secara teratur.",
            "updatedAt": "2022-06-07T08:16:15.315Z",
            "createdAt": "2022-06-07T08:16:15.315Z"
        }
    }
}
```

## Get All Animals

URL: `http://34.121.253.2:5001/api/v1/animals`

Method: `GET`

Auth requered: NO

Example Request:

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/animals'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "results": 4,
    "data": {
        "categories": [
            {
                "id": 4,
                "name": "Abyssinian 2",
                "animalType": "Cat",
                "personality": "Abyssinian adalah kucing cerdas yang suka bermain dan berinteraksi dengan manusia. Keahliannya dalam mengamati manusia sangat baik dan kucing ini paling cocok untuk orang-orang dari segala usia yang menghabiskan banyak waktu di rumah.",
                "health": "Abyssinians dapat mengalami penyakit periodontal dan kondisi keturunan yang disebut defisiensi piruvat kinase. Kucing yang kekurangan piruvat kinase mengalami anemia intermiten.",
                "grooming": "Mantel pendek Abyssinian mudah dirawat—rawat dia setiap minggu dengan sisir baja tahan karat untuk menghilangkan rambut mati dan menjaga mantelnya tetap berkilau. Potong kuku mereka sesuai kebutuhan, biasanya setiap 10 hingga 14 hari. Abyssinians dapat mengalami penyakit periodontal, jadi sikat gigi mereka di rumah dengan pasta gigi hewan peliharaan yang disetujui dokter hewan dan jadwalkan pembersihan hewan secara teratur.",
                "createdAt": "2022-06-07T08:16:15.000Z",
                "updatedAt": "2022-06-07T08:16:15.000Z"
            },
            {
                "id": 3,
                "name": "Golden Retriever",
                "animalType": "Dog",
                "personality": "Ciri khas Golden Retriever adalah sifatnya yang baik hati, lembut, dan penuh gairah. Golden Retrievers mudah beradaptasi dan berorientasi pada orang, dan karakteristik itu ada di urutan teratas daftar alasan orang-orang menyukainya.",
                "health": "Di bagian atas daftar masalah kesehatan Golden Retriever adalah kanker, termasuk hemangiosarcoma, lymphosarcoma, tumor sel mast, dan kanker tulang. Golden Retrievers juga menderita insiden tinggi kelainan bentuk pinggul genetik yang menyakitkan yang dikenal sebagai displasia pinggul. Golden Retriever juga dapat memiliki kelainan bentuk siku genetik. Penyakit jantung juga lazim di Golden Retriever, terutama dikenal sebagai stenosis subaortik. Epilepsi, infeksi telinga, alergi, gatal-gatal dan infeksi kulit, hipotiroidisme.",
                "grooming": "Golden Retriever harus disikat dan dimandikan secara teratur untuk menghilangkan kotoran. Seperti kebanyakan anjing retriever, Golden menyukai air. Saat Golden Anda basah, bilas dia di air tawar untuk menghilangkan klorin, garam, atau kotoran danau dari mantelnya, yang semuanya dapat mengeringkan atau merusak mantelnya. Jaga agar telinganya tetap kering untuk mencegah infeksi, dan gunakan pembersih telinga yang direkomendasikan oleh dokter hewan Anda setelah ia berenang. Sisanya adalah perawatan penting. Potong kukunya sesuai kebutuhan, biasanya setiap beberapa minggu, dan gosok giginya untuk kesehatan yang baik dan nafas yang segar.",
                "createdAt": "2022-05-28T08:16:15.000Z",
                "updatedAt": "2022-05-28T08:16:15.000Z"
            },
            {
                "id": 2,
                "name": "Labrador",
                "animalType": "Dog",
                "personality": "Labrador ramah dan penurut, bersemangat untuk menyenangkan, dan cenderung tidak agresif terhadap orang atau hewan lain. Labrador aktif, Labrador suka basah, dan Labrador suka makan.",
                "health": "Masalah kesehatan yang paling terkenal terkait dengan malformasi pinggul dan siku ( masing-masing displasia pinggul dan displasia siku). Penyakit mata seperti atrofi retina progresif dan katarak merupakan masalah potensial. Begitu juga keruntuhan akibat olahraga, kelainan otot yang memengaruhi kekuatan, stamina, dan gerakan anjing. Masalah kesehatan lain yang dapat mempengaruhi breed termasuk penyakit jantung, masalah ortopedi yang disebut osteochondrosis, panosteitis (nyeri tumbuh), epilepsi, dan penyakit kulit alergi.",
                "grooming": "Labrador adalah anjing yang mudah dirawat yang tidak membutuhkan banyak perawatan mewah, tetapi ada beberapa hal penting yang perlu diketahui tentang perawatan mereka. Sikat Labrador Anda sekali atau dua kali seminggu menggunakan sikat kari karet atau pisau penumpah logam atau sikat jas hujan kawat sehingga rambut menempel pada sikat, bukan ke furnitur dan pakaian Anda. Saat Labrador Anda basah, bilas dengan air bersih untuk menghilangkan klorin, garam, atau kotoran danau dari bulunya, yang dapat mengeringkan atau merusak bulunya. Kelembaban di telinga dapat meningkatkan risiko infeksi telinga -- terutama pada ras yang sudah rentan terhadapnya (terutama karena alergi). Keringkan telinga secara menyeluruh setelah berenang, dan gunakan pembersih telinga yang direkomendasikan oleh dokter hewan Anda. Sisanya adalah perawatan penting. Potong kuku setiap satu atau dua minggu, sesuai kebutuhan. Kuku yang panjang dapat membuat Lab tidak nyaman untuk berjalan, dan dapat tersangkut pada benda-benda dan robek. Itu menyakitkan, dan akan mengeluarkan banyak darah. Sikat gigi sesering mungkin dengan pasta gigi hewan peliharaan yang disetujui dokter hewan untuk kesehatan gigi yang baik dan napas segar.",
               "createdAt": "2022-05-28T08:16:15.000Z",
                "updatedAt": "2022-05-28T08:16:15.000Z"
            },
            {
                "id": 1,
                "name": "Abyssinian",
                "animalType": "Cat",
                "personality": "Abyssinian adalah kucing cerdas yang suka bermain dan berinteraksi dengan manusia. Keahliannya dalam mengamati manusia sangat baik dan kucing ini paling cocok untuk orang-orang dari segala usia yang menghabiskan banyak waktu di rumah.",
                "health": "Abyssinians dapat mengalami penyakit periodontal dan kondisi keturunan yang disebut defisiensi piruvat kinase. Kucing yang kekurangan piruvat kinase mengalami anemia intermiten.",
                "grooming": "Mantel pendek Abyssinian mudah dirawat—rawat dia setiap minggu dengan sisir baja tahan karat untuk menghilangkan rambut mati dan menjaga mantelnya tetap berkilau. Potong kuku mereka sesuai kebutuhan, biasanya setiap 10 hingga 14 hari. Abyssinians dapat mengalami penyakit periodontal, jadi sikat gigi mereka di rumah dengan pasta gigi hewan peliharaan yang disetujui dokter hewan dan jadwalkan pembersihan hewan secara teratur.",
                "createdAt": "2022-05-28T08:16:15.000Z",
                "updatedAt": "2022-05-28T08:16:15.000Z"
            }
        ]
    }
}
```

## Get Animal By Id

URL: `http://34.121.253.2:5001/api/v1/animals`

Method: `GET`

Auth requered: NO

Example Request:

#### PATH VARIABLES

id: 4

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/animals/4'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "categories": {
            "id": 4,
            "name": "Abyssinian 2",
            "animalType": "Cat",
            "personality": "Abyssinian adalah kucing cerdas yang suka bermain dan berinteraksi dengan manusia. Keahliannya dalam mengamati manusia sangat baik dan kucing ini paling cocok untuk orang-orang dari segala usia yang menghabiskan banyak waktu di rumah.",
            "health": "Abyssinians dapat mengalami penyakit periodontal dan kondisi keturunan yang disebut defisiensi piruvat kinase. Kucing yang kekurangan piruvat kinase mengalami anemia intermiten.",
            "grooming": "Mantel pendek Abyssinian mudah dirawat—rawat dia setiap minggu dengan sisir baja tahan karat untuk menghilangkan rambut mati dan menjaga mantelnya tetap berkilau. Potong kuku mereka sesuai kebutuhan, biasanya setiap 10 hingga 14 hari. Abyssinians dapat mengalami penyakit periodontal, jadi sikat gigi mereka di rumah dengan pasta gigi hewan peliharaan yang disetujui dokter hewan dan jadwalkan pembersihan hewan secara teratur.",
            "createdAt": "2022-06-07T08:16:15.000Z",
            "updatedAt": "2022-06-07T08:16:15.000Z"
        }
    }
}
```

## Get Animal By Name

URL: `http://34.121.253.2:5001/api/v1/animals/key/name?name=Labrador`

Method: `GET`

Auth requered: NO

Example Request:

#### PARAMS

name: Labrador

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/animals/key/name?name=Labrador'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "id": 2,
    "name": "Labrador",
    "animalType": "Dog",
    "personality": "Labrador ramah dan penurut, bersemangat untuk menyenangkan, dan cenderung tidak agresif terhadap orang atau hewan lain. Labrador aktif, Labrador suka basah, dan Labrador suka makan.",
    "health": "Masalah kesehatan yang paling terkenal terkait dengan malformasi pinggul dan siku ( masing-masing displasia pinggul dan displasia siku). Penyakit mata seperti atrofi retina progresif dan katarak merupakan masalah potensial. Begitu juga keruntuhan akibat olahraga, kelainan otot yang memengaruhi kekuatan, stamina, dan gerakan anjing. Masalah kesehatan lain yang dapat mempengaruhi breed termasuk penyakit jantung, masalah ortopedi yang disebut osteochondrosis, panosteitis (nyeri tumbuh), epilepsi, dan penyakit kulit alergi.",
    "grooming": "Labrador adalah anjing yang mudah dirawat yang tidak membutuhkan banyak perawatan mewah, tetapi ada beberapa hal penting yang perlu diketahui tentang perawatan mereka. Sikat Labrador Anda sekali atau dua kali seminggu menggunakan sikat kari karet atau pisau penumpah logam atau sikat jas hujan kawat sehingga rambut menempel pada sikat, bukan ke furnitur dan pakaian Anda. Saat Labrador Anda basah, bilas dengan air bersih untuk menghilangkan klorin, garam, atau kotoran danau dari bulunya, yang dapat mengeringkan atau merusak bulunya. Kelembaban di telinga dapat meningkatkan risiko infeksi telinga -- terutama pada ras yang sudah rentan terhadapnya (terutama karena alergi). Keringkan telinga secara menyeluruh setelah berenang, dan gunakan pembersih telinga yang direkomendasikan oleh dokter hewan Anda. Sisanya adalah perawatan penting. Potong kuku setiap satu atau dua minggu, sesuai kebutuhan. Kuku yang panjang dapat membuat Lab tidak nyaman untuk berjalan, dan dapat tersangkut pada benda-benda dan robek. Itu menyakitkan, dan akan mengeluarkan banyak darah. Sikat gigi sesering mungkin dengan pasta gigi hewan peliharaan yang disetujui dokter hewan untuk kesehatan gigi yang baik dan napas segar.",
    "createdAt": "2022-05-28T08:16:15.000Z",
    "updatedAt": "2022-05-28T08:16:15.000Z"
}
```

## Update Animal

URL: `http://34.121.253.2:5001/api/v1/animals/:id`

Method: `PUT`

Auth requered: NO

Example Request:

#### PATH VARIABLES

id: 4

```
curl --location --request PUT 'http://34.121.253.2:5001/api/v1/animals/4' \
--data-raw '{
    "name": "Abyssinian 3",
    "personality": "Abyssinian adalah kucing cerdas yang suka bermain dan berinteraksi dengan manusia.",
    "health": "Abyssinians dapat mengalami penyakit periodontal dan kondisi keturunan yang disebut defisiensi piruvat kinase. Kucing yang kekurangan piruvat kinase mengalami anemia intermiten.",
    "grooming": "Mantel pendek Abyssinian mudah dirawat—rawat dia setiap minggu dengan sisir baja tahan karat untuk menghilangkan rambut mati dan menjaga mantelnya tetap berkilau. Potong kuku mereka sesuai kebutuhan, biasanya setiap 10 hingga 14 hari."
}'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success"
}
```

## Delete Animal

URL: `http://34.121.253.2:5001/api/v1/animals/:id`

Method: `DELETE`

Auth requered: NO

Example Request:

#### PATH VARIABLES

id: 4

```
curl --location --request DELETE 'http://34.121.253.2:5001/api/v1/animals/4'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success"
}
```

# Feeds

## Create Posts

URL: `http://34.121.253.2:5001/api/v1/posts/feed`

Method: `POST`

Auth requered: YES (Bearer Token)

**Must Input Token**

Example Token: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjI2LCJpYXQiOjE2NTQ1MDU3NzEsImV4cCI6MTY1NDU2NTc3MSwidHlwZSI6ImFjY2VzcyJ9.E45aDUG07q4DINbVMpTG6wcyFIg3Jwi6W5yPM2h0AMQ`

Example Request:

```
curl --location --request POST 'http://localhost:5000/api/v1/posts/feed' \
--form 'categoryId="1"' \
--form 'photo=@"/C:/Users/Milla/Downloads/Capstone Project/image/image.jpg"' \
--form 'description="Hi guys. This is my pet. His name is Dogy"'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "data": {
        "categories": {
            "id": 2,
            "categoryId": "1",
            "description": "Hi guys. This is my pet. His name is Dogy",
            "photo": "http://34.121.253.2:5001/feeds/photo-1654609806398.jpeg",
            "idFeeds": "fVuUJ7NkvJnssrIVVij0",
            "userName": "heypet",
            "updatedAt": "2022-06-07T13:50:09.660Z",
            "createdAt": "2022-06-07T13:50:09.660Z"
        }
    }
}
```

## Get All Posts

URL: `http://34.121.253.2:5001/api/v1/posts`

Method: `GET`

Auth requered: NO

Example Request:

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/posts'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "results": 2,
    "data": {
        "categories": [
            {
                "id": 2,
                "categoryId": "1",
                "description": "Hi guys. This is my pet. His name is Dogy",
                "photo": "http://34.121.253.2:5001/feeds/photo-1654609806398.jpeg",
                "idFeeds": "fVuUJ7NkvJnssrIVVij0",
                "userName": "heypet",
                "updatedAt": "2022-06-07T13:50:09.660Z",
                "createdAt": "2022-06-07T13:50:09.660Z",
                "CategoryId": "1",
            },
            {
                "id": 1,
                "categoryId": "1",
                "description": "Hallo",
                "photo": "http://34.121.253.2:5001/feeds/photo-1654445986057.jpeg",
                "idFeeds": "u29yg0aF2kw6gqxzWhIQ",
                "userName": "heylovers"
                "createdAt": "2022-06-05T16:19:48.000Z",
                "updatedAt": "2022-06-05T16:19:48.000Z",
                "CategoryId": "1"
            }
        ]
    }
}
```

## Get Post By CategoryId

URL: `http://34.121.253.2:5001/api/v1/posts/category/:categoryId`

Method: `GET`

Auth requered: NO

Example Request:

#### PATH VARIABLES

categoryId: 1

```
curl --location --request GET 'http://34.121.253.2:5001/api/v1/posts/category/1'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success",
    "results": 2,
    "data": {
        "categories": [
            {
                "id": 2,
                "categoryId": 1,
                "description": "Hi guys. This is my pet. His name is Dogy",
                "photo": "http://34.121.253.2:5001/feeds/photo-1654609806398.jpeg",
                "idFeeds": "fVuUJ7NkvJnssrIVVij0",
                "userName": "heypet",
                "createdAt": "2022-06-07T13:50:09.000Z",
                "updatedAt": "2022-06-07T13:50:09.000Z",
                "CategoryId": 1,
                "Category": {
                    "id": 1,
                    "name": "Story",
                    "createdAt": null,
                    "updatedAt": null
                }
            },
            {
                "id": 1,
                "categoryId": "1",
                "description": "Hallo",
                "photo": "http://34.121.253.2:5001/feeds/photo-1654445986057.jpeg",
                "idFeeds": "u29yg0aF2kw6gqxzWhIQ",
                "userName": "heylovers"
                "createdAt": "2022-06-05T16:19:48.000Z",
                "updatedAt": "2022-06-05T16:19:48.000Z",
                "CategoryId": "1"
                "Category": {
                    "id": 1,
                    "name": "Story",
                    "createdAt": null,
                    "updatedAt": null
                }
            }
        ]
    }
}
```

## Delete Post

URL: `http://34.121.253.2:5001/api/v1/posts/:id`

Method: `DELETE`

Auth requered: NO

Example Request:

#### PATH VARIABLES

id: 2

```
curl --location --request DELETE 'http://34.121.253.2:5001/api/v1/posts/2'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "status": "success"
}
```

# Predict

## Predict Animal

URL: `https://heypet-predict-api-2326mg2rrq-uc.a.run.app/predict`

Method: `POST`

Auth requered: NO

Example Request:

```
curl --location --request POST 'https://heypet-predict-api-2326mg2rrq-uc.a.run.app/predict' \
--form 'file=@"/C:/Users/Milla/Downloads/image.jpg"'
```

### Success Response

Condition: If everything is OK

Code: 200

Example Response:

```
{
    "animalType": "Dog",
    "createdAt": "2022-06-07T08:16:15.315Z",
    "grooming": "Golden Retriever harus disikat dan dimandikan secara teratur untuk menghilangkan kotoran. Seperti kebanyakan anjing retriever, Golden menyukai air. Saat Golden Anda basah, bilas dia di air tawar untuk menghilangkan klorin, garam, atau kotoran danau dari mantelnya, yang semuanya dapat mengeringkan atau merusak mantelnya. Jaga agar telinganya tetap kering untuk mencegah infeksi, dan gunakan pembersih telinga yang direkomendasikan oleh dokter hewan Anda setelah ia berenang. Sisanya adalah perawatan penting. Potong kukunya sesuai kebutuhan, biasanya setiap beberapa minggu, dan gosok giginya untuk kesehatan yang baik dan nafas yang segar.",
    "health": "Di bagian atas daftar masalah kesehatan Golden Retriever adalah kanker, termasuk hemangiosarcoma, lymphosarcoma, tumor sel mast, dan kanker tulang. Golden Retrievers juga menderita insiden tinggi kelainan bentuk pinggul genetik yang menyakitkan yang dikenal sebagai displasia pinggul. Golden Retriever juga dapat memiliki kelainan bentuk siku genetik. Penyakit jantung juga lazim di Golden Retriever, terutama dikenal sebagai stenosis subaortik. Epilepsi, infeksi telinga, alergi, gatal-gatal dan infeksi kulit, hipotiroidisme.",
    "id": 5,
    "name": "Golden Retriever",
    "personality": "Ciri khas Golden Retriever adalah sifatnya yang baik hati, lembut, dan penuh gairah. Golden Retrievers mudah beradaptasi dan berorientasi pada orang, dan karakteristik itu ada di urutan teratas daftar alasan orang-orang menyukainya.",
    "updatedAt": "2022-06-07T08:16:15.315Z"
}
```
