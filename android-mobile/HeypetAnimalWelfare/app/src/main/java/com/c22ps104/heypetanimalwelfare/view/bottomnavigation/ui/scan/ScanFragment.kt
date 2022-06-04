package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.scan

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.c22ps104.heypetanimalwelfare.databinding.FragmentScanBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private lateinit var scanViewModel: ScanViewModel

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                binding.imageView.setImageBitmap(it)
                val file = createTempFile(it)
                if (file != null) {
                    scanViewModel.classify(file)
                } else Toast.makeText(activity, "Filed to classify", Toast.LENGTH_SHORT)
            }
        }

    private val gallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == -1) {
                try {
                    val imageUri = it.data?.data
                    val imageStream = context?.contentResolver?.openInputStream(imageUri!!)
                    val bitmap = BitmapFactory.decodeStream(imageStream)
                    binding.imageView.setImageBitmap(bitmap)
                    val file = createTempFile(bitmap)

                    if (file != null) {
                        scanViewModel.classify(file)
                    } else Toast.makeText(activity, "Filed to classify", Toast.LENGTH_SHORT)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                takePicture.launch(null)
            } else {
                Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scanViewModel = ViewModelProvider(this)[ScanViewModel::class.java]

        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnScanCamera.setOnClickListener {
            cameraPermission.launch(android.Manifest.permission.CAMERA)
            scanViewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbScan.visibility = View.VISIBLE
                } else {
                    binding.pbScan.visibility = View.GONE
                }
            }
            scanViewModel.classifyResult.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.tvScanClassifyResult.text = it.name
                }
            }


//            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                dispatchTakePictureIntent()
//            } else {
//                requestPermissions(
//                    this,
//                    arrayOf(android.Manifest.permission.CAMERA),
//                    REQUEST_IMAGE_CAPTURE
//                )
//            }
        }

        binding.btnScanGallery.setOnClickListener {
            galleryPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            scanViewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbScan.visibility = View.VISIBLE
                } else {
                    binding.pbScan.visibility = View.GONE
                }
            }
            scanViewModel.classifyResult.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.tvScanClassifyResult.text = it.name
                }
            }

        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createTempFile(bitmap: Bitmap): File? {
        val file = File(
            context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
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
}