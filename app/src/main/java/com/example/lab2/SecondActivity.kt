package com.example.lab2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.extras?.getInt("RESULT_KEY")

        binding.tvResult.text = result?.let { getString(it) }

        binding.bBack.setOnClickListener {
            val intent = Intent(this@SecondActivity, MainActivity::class.java)

            startActivity(intent)
        }
    }
}