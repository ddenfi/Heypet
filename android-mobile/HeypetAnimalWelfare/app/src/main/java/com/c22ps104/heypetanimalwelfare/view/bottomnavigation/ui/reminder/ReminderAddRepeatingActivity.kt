package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity
import com.c22ps104.heypetanimalwelfare.databinding.ActivityReminderAddRepeatingBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ReminderAddRepeatingActivity : AppCompatActivity(),TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityReminderAddRepeatingBinding
    private val viewModel:ReminderAddOneTimeViewModel by viewModels()
    private val reminderSetCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderAddRepeatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        binding.btnReminderRepeatingChooseTime.setOnClickListener {
            val dialogTime = TimePickerFragment()
            dialogTime.show(supportFragmentManager,"RepeatingAlarmTime")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_save_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_save){
            saveReminder()
            finish()
            Toast.makeText(applicationContext,"Reminder Saved", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveReminder(){
        val name = binding.etReminderRepeatingName.text.toString()
        val date = reminderSetCalendar.time
        val reminder = ReminderEntity(reminderDate = date, reminderName = name, reminderType = 1 )
        lifecycleScope.launch {
            viewModel.addReminder(reminder)
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        // Ready time formatter
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.tvReminderRepeatingChooseTime.text = dateFormat.format(calendar.time)
    }
}