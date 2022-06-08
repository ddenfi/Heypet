package com.c22ps104.heypetanimalwelfare.view.upload

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_TOKEN
import com.c22ps104.heypetanimalwelfare.databinding.ActivityUploadBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlin.math.min

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private val viewModel:UploadViewModel by viewModels()
    private lateinit var preferencesHelper: PreferencesHelper

    private lateinit var tempFile:File

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == -1) {
                val imageBitmap = it.data?.extras?.get("data") as Bitmap

                Log.d("Upload Image","${imageBitmap.width} ${imageBitmap.height} ")

                binding.ivUpload.setImageBitmap(cropBmp(imageBitmap))

                tempFile = createTempFile(imageBitmap)
            }
        }

    private val gallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == -1) {
                try {
                    val imageUri = it.data?.data
                    val imageStream = contentResolver.openInputStream(imageUri!!)
                    val bitmap = BitmapFactory.decodeStream(imageStream)
                    binding.ivUpload.setImageBitmap(cropBmp(bitmap))
                    tempFile = createTempFile(bitmap)

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePicture.launch(intent)
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val galleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                gallery.launch(chooser)
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesHelper = PreferencesHelper(this)

        val category = resources.getStringArray(R.array.post_category)
        val arrayAdapter = ArrayAdapter(this, R.layout.item_dropdown,category)

        binding.tvAutocompleteUpload.setAdapter(arrayAdapter)

        binding.ivUploadCamera.setOnClickListener{
            cameraPermission.launch(android.Manifest.permission.CAMERA)
        }

        binding.ivUploadGallery.setOnClickListener {
            galleryPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        binding.btnUpload.setOnClickListener {
            val desc = binding.etUploadCaption.text.toString().toRequestBody("text/plain".toMediaType())
            val categoryId = when (binding.tvAutocompleteUpload.text.toString()) {
                "Story" -> 1
                "Breeding" -> 2
                "Adoption" -> 3
                "Tips & Trick" -> 4
                else -> 1
            }
            val category = categoryId.toString().toRequestBody("text/plain".toMediaType())
            val file = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                tempFile.name,
                file
            )

            val token = preferencesHelper.getString(PREF_TOKEN)
            if (token != null) {
                viewModel.upload(token,category,imageMultipart,desc).observe(this){
                    Toast.makeText(this,"Upload $it",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createTempFile(bitmap: Bitmap): File {
        val file = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            System.currentTimeMillis().toString() + "_image.jpg"
        )
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bytes: ByteArray = bos.toByteArray()
        try {
            val fos = FileOutputStream(file)
            fos.write(bytes)
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }


    private fun cropBmp(bitmap: Bitmap): Bitmap {
        val dimension = min(bitmap.width, bitmap.height)
        val cropBmp = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension)

        return Bitmap.createScaledBitmap(cropBmp, dimension, dimension, false)
    }
}