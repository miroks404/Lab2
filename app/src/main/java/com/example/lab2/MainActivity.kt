package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2.databinding.ActivityMainBinding
import kotlin.math.sign

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loadExpression()
        viewModel.checkResult()

        viewModel.uiState.observe(this) { state ->

            binding.tvFirstNumber.text = state.firstNumberOfExpression.toString()
            binding.tvAction.text = state.action.toString()
            binding.tvSecondNumber.text = state.secondNumberOfExpression.toString()

        }

        binding.bCheckResult.setOnClickListener {

            if (binding.etResult.text.isEmpty()) {
                Toast.makeText(this@MainActivity, R.string.answer_error_text, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userAnswer = try {
                binding.etResult.text.toString()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, R.string.answer_error_text, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this@MainActivity, SecondActivity::class.java)

            val result = if(viewModel.uiState.value?.result == userAnswer) {
                R.string.answer_is_true_text
            } else {
                R.string.answer_is_false_text
            }

            intent.putExtra("RESULT_KEY", result)

            startActivity(intent)

        }

    }

}