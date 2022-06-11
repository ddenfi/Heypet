package com.c22ps104.heypetanimalwelfare.view.caretips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.databinding.ActivityCaretipsBinding

class CaretipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCaretipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaretipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        
    }
}