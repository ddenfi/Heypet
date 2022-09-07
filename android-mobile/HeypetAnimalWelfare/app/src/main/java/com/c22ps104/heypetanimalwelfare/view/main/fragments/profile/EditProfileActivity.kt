package com.c22ps104.heypetanimalwelfare.view.main.fragments.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_TOKEN
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_USER_NAME
import com.c22ps104.heypetanimalwelfare.databinding.ActivityEditProfileBinding
import com.c22ps104.heypetanimalwelfare.utils.bitmapToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlin.math.min

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private lateinit var preferencesHelper: PreferencesHelper

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
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesHelper = PreferencesHelper(this)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        val username = preferencesHelper.getString(PREF_USER_NAME)
        val token = preferencesHelper.getString(PREF_TOKEN)!!

        binding.tvEditProfileUsername.text = username

        /*
            TODO("EditProfileActivity: require fix to enable profile picture update")

            Programnya gagal meng-update profile picture.

            Method dari ViewModel-nya tidak berjalan semestinya. Dia gagal menampilkan
                profile picture yang ada di API walaupun udah di-request sama persis
                kayak yang ada di halaman HomeFragment.kt untuk foto Feeds.

            Karena itu, untuk sementara fitur untuk meng-update profile picture
                di-disable.

            Untuk mengerjakan bagian ini, ubah visibility 3 View di bawah menjadi
                VISIBLE dan un-comment kode setOnClickListener di bawahnya.

         */

        binding.tvChangephoto.visibility = View.GONE
        binding.ivUploadCamera.visibility = View.GONE
        binding.ivUploadGallery.visibility = View.GONE

        binding.ivPhotoProfile.setImageResource(R.drawable.default_photo_profile)

        editProfileViewModel.getUserDetail(token)
        editProfileViewModel.userDetails.observe(this) {
            Glide.with(this)
                .load(it.photo)
                .placeholder(R.drawable.default_photo_profile)
                .into(binding.ivPhotoProfile)
        }

//        binding.ivUploadCamera.setOnClickListener {
//            cameraPermission.launch(android.Manifest.permission.CAMERA)
//        }
//
//        binding.ivUploadGallery.setOnClickListener {
//            galleryPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.menu_save) {
            saveProfile()
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveProfile() {
        val token = preferencesHelper.getString(PREF_TOKEN)
        val username =
            preferencesHelper.getString(PREF_USER_NAME)?.toRequestBody("text/plain".toMediaType())
        val bio = binding.etBio.text.toString().toRequestBody("text/plain".toMediaType())
        val pet = binding.etBuddy.text.toString().toRequestBody("text/plain".toMediaType())
        val photo = bitmapToFile(binding.ivPhotoProfile.drawable.toBitmap(), this)

        if (token != null && username != null) {
            editProfileViewModel.updateProfile(token, pet, bio, photo).observe(this) {
                if (it == "Success") finish()
                Toast.makeText(this, "Profile Edit $it", Toast.LENGTH_SHORT).show()
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