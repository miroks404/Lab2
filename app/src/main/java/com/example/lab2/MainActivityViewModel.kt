package com.example.lab2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.RoundingMode

class MainActivityViewModel : ViewModel() {

    data class MainActivityUIState(
        val firstNumberOfExpression: Int? = null,
        val action: Char? = null,
        val secondNumberOfExpression: Int? = null,
        val result: String? = null,
    )

    private val _uiState = MutableLiveData(MainActivityUIState())
    val uiState: LiveData<MainActivityUIState>
        get() = _uiState

    fun loadExpression() {
        if (_uiState.value == MainActivityUIState())
            _uiState.value = _uiState.value?.copy(
                firstNumberOfExpression = (0..99).random(),
                action = listOf('+', '-', '*', '/').random(),
                secondNumberOfExpression = (0..99).random()
            )
    }

    fun checkResult() {
        _uiState.value = _uiState.value?.copy(
            result = _uiState.value?.let {
                getResult(
                    it.firstNumberOfExpression ?: 0,
                    it.secondNumberOfExpression ?: 0,
                    it.action ?: '+'
                )
            }
        )
    }

    private fun getResult(firstNumber: Int, secondNumber: Int, operator: Char): String {
        var result = when (operator) {
            '+' ->
                (firstNumber + secondNumber).toBigDecimal().setScale(1, RoundingMode.HALF_UP)
                    .toString()

            '-' -> (firstNumber - secondNumber).toBigDecimal().setScale(1, RoundingMode.HALF_UP)
                .toString()

            '*' -> (firstNumber * secondNumber).toBigDecimal().setScale(1, RoundingMode.HALF_UP)
                .toString()

            '/' -> (firstNumber / secondNumber).toBigDecimal().setScale(1, RoundingMode.HALF_UP)
                .toString()

            else -> "0.0"
        }
        result = if (result.substringAfter(".") == "0") {
            result.substringBefore(".")
        } else {
            result
        }
        return result
    }
}