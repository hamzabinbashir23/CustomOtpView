package com.example.customotpview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.customotpview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityMainBinding.root)


        mainActivityMainBinding.otp.setOtpFields(4)
        mainActivityMainBinding.otp.setDashAtPosition(2)
        mainActivityMainBinding.otp.onChange {
            mainActivityMainBinding.otp.setActiveFieldBackground()
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
        }
    }
}