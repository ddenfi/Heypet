package com.c22ps104.heypetanimalwelfare.view.main.fragments.scan

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.api.ClassifyResponse
import com.c22ps104.heypetanimalwelfare.databinding.FragmentScanBinding
import com.c22ps104.heypetanimalwelfare.view.caretips.CaretipsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private lateinit var scanViewModel: ScanViewModel
    private var byteArray:ByteArray? = null

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == -1) {
                val imageBitmap = it.data?.extras?.get("data") as Bitmap
                binding.ivPictureTaken.setImageBitmap(imageBitmap)
                val file = createTempFile(imageBitmap)

                if (file != null) {
                    byteArray = bitmapToByteArray(imageBitmap)
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
                    binding.ivPictureTaken.setImageBitmap(bitmap)
                    val file = createTempFile(bitmap)

                    if (file != null) {
                        byteArray = bitmapToByteArray(bitmap)
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
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePicture.launch(intent)
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
        binding.ivPictureTaken.setImageResource(R.drawable.default_scan)

        setupView()

        return root
    }

    private fun setupView() {
        val intentToCareTips = Intent(requireContext(),CaretipsActivity::class.java)
        val defaultValue = "You Must Scan First"
        var extraData = ClassifyResponse("","","","",defaultValue,"",0,"")

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
                    extraData = it.copy()
                }
            }
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
                    extraData = it.copy()
                }
            }
        }

        binding.btnDetail.setOnClickListener {
                intentToCareTips.putExtra(EXTRA_CLASSIFY_PHOTO,byteArray)
                intentToCareTips.putExtra(EXTRA_CLASSIFY_RESULT,extraData)
                startActivity(intentToCareTips)
        }
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

    private fun bitmapToByteArray(bmp: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val EXTRA_CLASSIFY_RESULT = "EXTRA_CLASSIFY_RESULT"
        const val EXTRA_CLASSIFY_PHOTO = "EXTRA_CLASSIFY_PHOTO"
    }
}