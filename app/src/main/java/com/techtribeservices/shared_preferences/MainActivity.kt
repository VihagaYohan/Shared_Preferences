package com.techtribeservices.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var userName: EditText
    lateinit var userMessage: EditText
    lateinit var counter: Button
    lateinit var remember: CheckBox

    var count: Int = 0

    var name:String? = null
    var message:String? = null
    var isCheck: Boolean? = null

    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userName = findViewById(R.id.txt_name)
        userMessage = findViewById(R.id.txt_multiline)
        counter = findViewById(R.id.btn_submit)
        remember = findViewById(R.id.btn_checkbox)

        counter.setOnClickListener {
            count++
            counter.setText(count.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        // call save  method onPause method
        save()
    }

    override fun onResume() {
        super.onResume()
        // call retrieve data method onResume method
        retrieveData()
    }

    // handle saving data
    private fun save() {
        sharedPreference = this.getSharedPreferences("saveData",Context.MODE_PRIVATE)
        name = userName.text.toString()
        message = userMessage.text.toString()
        isCheck = remember.isChecked

        val editor = sharedPreference.edit()
        editor.putString("key name", name)
        editor.putString("key message", message)
        editor.putInt("key count", count)
        editor.putBoolean("key remember", isCheck!!)

        editor.apply() //  store data

        // show a toast message
        Toast.makeText(applicationContext, "Your data is saved", Toast.LENGTH_LONG).show()
    }

    // handle retrieve data
    fun retrieveData() {
        sharedPreference = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)

        name = sharedPreference.getString("key name", null)
        message = sharedPreference.getString("key message", null)
        count = sharedPreference.getInt("key count", 0)
        isCheck = sharedPreference.getBoolean("key remember", false)

        userName.setText(name)
        userMessage.setText(message)
        counter.setText(isCheck.toString())
        remember.isChecked = isCheck!!
    }
}