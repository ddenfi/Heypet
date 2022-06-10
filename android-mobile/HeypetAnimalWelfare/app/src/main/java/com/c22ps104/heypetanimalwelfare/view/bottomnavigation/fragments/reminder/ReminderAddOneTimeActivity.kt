package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.fragments.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity
import com.c22ps104.heypetanimalwelfare.databinding.ActivityReminderAddOneTimeBinding
import com.c22ps104.heypetanimalwelfare.utils.AlarmReceiver
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ReminderAddOneTimeActivity : AppCompatActivity(),DatePickerFragment.DialogDateListener,TimePickerFragment.DialogTimeListener {

    private lateinit var binding:ActivityReminderAddOneTimeBinding
    private val viewModel:ReminderAddOneTimeViewModel by viewModels()
    private val reminderSetCalendar = Calendar.getInstance()
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderAddOneTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmReceiver = AlarmReceiver()

        setupView()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        binding.btnReminderRepeatingChooseDate.setOnClickListener {
            val dialogDate = DatePickerFragment()
            dialogDate.show(supportFragmentManager,"RepeatingAlarmDate")
        }

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
        menuInflater.inflate(R.menu.simple_save_menu, menu)
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

    private fun saveReminder() {
        val name = binding.etReminderRepeatingName.text.toString()
        val date = reminderSetCalendar.time
        val reminder = ReminderEntity(reminderDate = date, reminderName = name, reminderType = 1)
        lifecycleScope.launch {
            viewModel.addReminder(reminder)
            viewModel.getLatestReminder().observe(this@ReminderAddOneTimeActivity) {
                alarmReceiver.setOneTimeAlarm(this@ReminderAddOneTimeActivity,1, reminderSetCalendar, name, it.id)
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        reminderSetCalendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Set text of TextView
        binding.tvReminderRepeatingChooseDate.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        // Ready time formatter
        reminderSetCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        reminderSetCalendar.set(Calendar.MINUTE, minute)
        reminderSetCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        reminderSetCalendar.set(Calendar.MINUTE, minute)
        reminderSetCalendar.set(Calendar.SECOND, 0)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.tvReminderRepeatingChooseTime.text = dateFormat.format(reminderSetCalendar.time)
    }
}