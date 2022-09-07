package com.c22ps104.heypetanimalwelfare.view.onboarding

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.c22ps104.heypetanimalwelfare.databinding.ActivityOnBoardingBinding
import com.c22ps104.heypetanimalwelfare.utils.bitmapToFile
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity
import com.c22ps104.heypetanimalwelfare.view.main.MainActivity
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity.Companion.EXTRA_TOKEN
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity.Companion.EXTRA_USERNAME
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlin.math.min

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    private lateinit var tempFile: File

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == -1) {
                val imageBitmap = it.data?.extras?.get("data") as Bitmap

                Log.d("Upload Image Size", "${imageBitmap.width} ${imageBitmap.height} ")

                binding.ivPhotoProfile.setImageBitmap(cropBmp(imageBitmap))

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

                    binding.ivPhotoProfile.setImageBitmap(cropBmp(bitmap))
                    tempFile = createTempFile(bitmap)

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePicture.launch(intentCamera)
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val galleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                val intentGallery = Intent()
                intentGallery.action = Intent.ACTION_GET_CONTENT
                intentGallery.type = "image/*"

                val chooser = Intent.createChooser(intentGallery, "Choose a Picture")
                gallery.launch(chooser)
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        val token = intent.getStringExtra(EXTRA_TOKEN)
        val name = intent.getStringExtra(EXTRA_USERNAME)

        binding.tvOnBoardingUsername.text = name

        /*
            TODO("OnBoardingActivity: may require fix to enable profile picture update")

            Fungsi untuk meng-update profile picture di-disable sementara.

            Baca lebih lanjut di halaman EditProfileActivity.kt.

            Untuk mengerjakan bagian ini, ubah visibility 3 View di bawah menjadi
                VISIBLE dan un-comment kode setOnClickListener di bawahnya.

         */

        binding.tvChangephoto.visibility = View.GONE
        binding.ivUploadCamera.visibility = View.GONE
        binding.ivUploadGallery.visibility = View.GONE

//        binding.ivUploadCamera.setOnClickListener {
//            cameraPermission.launch(android.Manifest.permission.CAMERA)
//        }
//
//        binding.ivUploadGallery.setOnClickListener {
//            galleryPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//        }

        binding.tvSkip.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToMain)
            finish()
        }

        binding.btnFinish.setOnClickListener {
            val bio = binding.etBio.text.toString().toRequestBody("text/plain".toMediaType())
            val pet = binding.etBuddy.text.toString().toRequestBody("text/plain".toMediaType())
            val photo = bitmapToFile(binding.ivPhotoProfile.drawable.toBitmap(), this)

            if (token != null && name != null) {
                onBoardingViewModel.updateProfile(token, pet, bio, photo).observe(this) {
                    val intentToMain = Intent(this, MainActivity::class.java)
                    intentToMain.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentToMain)
                    finish()
                    Toast.makeText(this, "Profile Setup $it", Toast.LENGTH_SHORT).show()
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