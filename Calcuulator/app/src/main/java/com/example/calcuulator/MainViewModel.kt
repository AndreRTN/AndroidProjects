package com.example.calcuulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class MainViewModel : ViewModel() {

    class Formatter() {
        companion object {
            var decimalFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.US)
        }

    }

    val resultText: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = ""
    }

    private val _result: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = ""
    }
    private val lastNumeric: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val stateError: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val lastOperator: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }


    private val lastDot: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val isEquals: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }


    val result: LiveData<String> = _result

    fun onDigit(value: String) {

        if (result.value.equals("0") && value == "0") return;

        when {
            stateError.value == true -> _result.value = "Error"
            value.isEmpty() -> _result.value = ""
            value.isNotEmpty() -> {
                _result.value += value
                resultText.value = _result.value

            }
            else -> _result.value += value
        }

        _result.postValue(_result.value)
        lastNumeric.value = true
        lastOperator.value = false
        isEquals.value = false

    }

    fun onOperator(operator: Operators) {

        val operators = listOf('*','-','+', '/')
        val existsOperator = result.value!!.count { a -> operators.contains(a) }
        if (operator == Operators.EQUALS) {
            onEqual()
            return
        }
        else if (isEquals.value == false && lastOperator.value == true && operator != Operators.EQUALS) {
            val index = result.value!!.lastIndex
            val swapOperator = result.value!!.toCharArray()
            swapOperator[index] = operator.operatorChar
            if (operator != Operators.EQUALS) _result.value = String(swapOperator)
            return
        }
            when (operator) {
                Operators.SUBTRACT -> {
                    if(existsOperator > 1) {  _result.value += operator.operatorChar }
                    else _result.value = resultText.value + operator.operatorChar
                }
                Operators.SUM -> {
                    resultText.value +=operator.operatorChar
                   _result.value = resultText.value
                }
                Operators.MULTIPLY -> {
                    if(existsOperator > 1) _result.value += operator.operatorChar
                    else _result.value = resultText.value + operator.operatorChar
                }
                Operators.DIVIDE -> {
                    if(existsOperator > 1) _result.value += operator.operatorChar
                    else  _result.value = resultText.value + operator.operatorChar
                }
            }


        lastNumeric.value = false
        lastDot.value = false
        lastOperator.value = true
        if (operator != Operators.EQUALS) {
            isEquals.value = false
            _result.postValue(_result.value)
        }
    }

    fun onClear() {
        _result.value = ""
        resultText.value = "0"
        lastNumeric.value = false
        stateError.value = false
        lastDot.value = false
        lastOperator.value = false
        isEquals.value = false
    }

    fun onDecimalPoint() {
        if (lastNumeric.value == true && !stateError.value!!) {
            _result.value += '.'
            lastNumeric.value = false
            lastDot.value = true
            lastOperator.value = false
            isEquals.value = false
        }
    }

    fun returnDigit() {
        if (_result.value!!.length == 1) _result.value = ""
        if (_result.value!!.isNotEmpty()) {

            _result.value = _result.value!!.dropLast(1)

            _result.postValue(_result.value)
            lastOperator.value = false
        }

    }

    private fun onEqual() {

        if(result.value!!.isEmpty()) return
        if (lastNumeric.value == true && !stateError.value!!) {

            val txt = result.value.toString()
            val expression = ExpressionBuilder(txt).build()

            try {
                val value = expression.evaluate()
                val totalValue = Formatter.decimalFormat.format(value).replace(",",".")
                resultText.value = totalValue
                _result.value = ""
                lastDot.value = true
                isEquals.value = true
                lastOperator.value = true
                _result.postValue("")
                resultText.postValue(totalValue)

            } catch (ex: ArithmeticException) {

                _result.postValue("Error")
                stateError.value = true
                lastNumeric.value = false
            }

        }

    }
}