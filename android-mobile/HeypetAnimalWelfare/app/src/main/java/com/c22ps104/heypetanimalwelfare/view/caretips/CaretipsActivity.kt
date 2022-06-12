package com.c22ps104.heypetanimalwelfare.view.caretips

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c22ps104.heypetanimalwelfare.api.ClassifyResponse
import com.c22ps104.heypetanimalwelfare.databinding.ActivityCaretipsBinding
import com.c22ps104.heypetanimalwelfare.view.main.fragments.scan.ScanFragment.Companion.EXTRA_CLASSIFY_PHOTO
import com.c22ps104.heypetanimalwelfare.view.main.fragments.scan.ScanFragment.Companion.EXTRA_CLASSIFY_RESULT

class CaretipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaretipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaretipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraPhoto = intent.getByteArrayExtra(EXTRA_CLASSIFY_PHOTO)
        val extraData = intent.getParcelableExtra<ClassifyResponse>(EXTRA_CLASSIFY_RESULT)

        setupView(extraPhoto, extraData)
    }

    private fun setupView(extraPhoto: ByteArray?, extraData: ClassifyResponse?) {
        if (extraPhoto != null) binding.ivPictureTaken.setImageBitmap(
            BitmapFactory.decodeByteArray(
                extraPhoto,
                0,
                extraPhoto.size
            )
        )

        if (extraData != null) {
            with(binding) {
                tvBreed.text = extraData.name
                tvGrooming.text = extraData.grooming
                tvHealth.text = extraData.health
                tvPersonality.text = extraData.personality
            }
        }

        binding.btnMoretips.setOnClickListener {
            extraData?.let { it1 -> searchGoogle(it1.name) }
        }
    }

    private fun searchGoogle(pet: String) {
        val url = "https://www.google.com/search?q=how+to+take+care+$pet"
        val intentGoogleSearch = Intent(Intent.ACTION_VIEW)
        intentGoogleSearch.data = Uri.parse(url)
        startActivity(intentGoogleSearch)
    }
}