package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2.databinding.ActivityMainBinding

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

            when (state.actionState) {
                ActionState.DEFAULT -> {
                    binding.tvFirstNumber.text = state.firstNumberOfExpression.toString()
                    binding.tvAction.text = state.action.toString()
                    binding.tvSecondNumber.text = state.secondNumberOfExpression.toString()
                }
                ActionState.OPEN_ACTIVITY_TRUE -> openResultActivity(R.string.answer_is_true_text)
                ActionState.OPEN_ACTIVITY_FALSE -> openResultActivity(R.string.answer_is_false_text)
                ActionState.ERROR -> Toast.makeText(this@MainActivity, R.string.answer_error_text, Toast.LENGTH_SHORT).show()
            }

        }

        binding.bCheckResult.setOnClickListener {

            viewModel.sendResult(binding.etResult.text.toString())

        }

    }

    private fun openResultActivity(resultMessageId: Int) {
        val intent = Intent(this@MainActivity, SecondActivity::class.java)

        intent.putExtra("RESULT_KEY", resultMessageId)

        startActivity(intent)
    }

}