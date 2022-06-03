package com.c22ps104.heypetanimalwelfare.view.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = resources.getStringArray(R.array.post_category)
        val arrayAdapter = ArrayAdapter(this, R.layout.item_dropdown,category)

        binding.tvAutocompleteUpload.setAdapter(arrayAdapter)
    }
}