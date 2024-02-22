package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import net.objecthunter.exp4j.ExpressionBuilder
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private lateinit var mathOperation: TextView
    private lateinit var resultText: TextView
    private lateinit var buttonClear: TextView
    private lateinit var buttonBraceOne: TextView
    private lateinit var buttonBraceTwo: TextView
    private lateinit var buttonErase: ImageView
    private lateinit var buttonDivision: TextView
    private lateinit var buttonSeven: TextView
    private lateinit var buttonEight: TextView
    private lateinit var buttonNine: TextView
    private lateinit var buttonMultiply: TextView
    private lateinit var buttonFour: TextView
    private lateinit var buttonFive: TextView
    private lateinit var buttonSix: TextView
    private lateinit var buttonMinus: TextView
    private lateinit var buttonOne: TextView
    private lateinit var buttonTwo: TextView
    private lateinit var buttonThree: TextView
    private lateinit var buttonPlus: TextView
    private lateinit var buttonZero: TextView
    private lateinit var buttonPoint: TextView
    private lateinit var buttonEqual: TextView

    private var dotAllowed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mathOperation = findViewById(R.id.math_operation)
        resultText = findViewById(R.id.result_text)
        buttonClear = findViewById(R.id.btn_clear)
        buttonBraceOne = findViewById(R.id.btn_brace_one)
        buttonBraceTwo = findViewById(R.id.btn_brace_two)
        buttonErase = findViewById(R.id.btn_erase)
        buttonDivision = findViewById(R.id.btn_division)
        buttonSeven = findViewById(R.id.btn_seven)
        buttonEight = findViewById(R.id.btn_eight)
        buttonNine = findViewById(R.id.btn_nine)
        buttonMultiply = findViewById(R.id.btn_multiply)
        buttonFour = findViewById(R.id.btn_four)
        buttonFive = findViewById(R.id.btn_five)
        buttonSix = findViewById(R.id.btn_six)
        buttonMinus = findViewById(R.id.btn_minus)
        buttonOne = findViewById(R.id.btn_one)
        buttonTwo = findViewById(R.id.btn_two)
        buttonThree = findViewById(R.id.btn_three)
        buttonPlus = findViewById(R.id.btn_plus)
        buttonZero = findViewById(R.id.btn_zero)
        buttonPoint = findViewById(R.id.btn_point)
        buttonEqual = findViewById(R.id.btn_equal)

        buttonBraceOne.setOnClickListener { SetTextFields("(") }
        buttonBraceTwo.setOnClickListener { SetTextFields(")") }
        buttonDivision.setOnClickListener { SetTextFields("÷") }
        buttonSeven.setOnClickListener { SetTextFields("7") }
        buttonEight.setOnClickListener { SetTextFields("8") }
        buttonNine.setOnClickListener { SetTextFields("9") }
        buttonMultiply.setOnClickListener { SetTextFields("×") }
        buttonFour.setOnClickListener { SetTextFields("4") }
        buttonFive.setOnClickListener { SetTextFields("5") }
        buttonSix.setOnClickListener { SetTextFields("6") }
        buttonMinus.setOnClickListener { SetTextFields("−") }
        buttonOne.setOnClickListener { SetTextFields("1") }
        buttonTwo.setOnClickListener { SetTextFields("2") }
        buttonThree.setOnClickListener { SetTextFields("3") }
        buttonPlus.setOnClickListener { SetTextFields("+") }
        buttonPoint.setOnClickListener { SetTextFields(".") }
        buttonZero.setOnClickListener { SetTextFields("0") }

        //кнопка очистить
        buttonClear.setOnClickListener {
            mathOperation.text = ""
            resultText.text = ""
            resetDotCondition()
        }

        //кнопка стереть
        buttonErase.setOnClickListener {
            val str = mathOperation.text.toString()
            if (str.isNotEmpty()) {
                mathOperation.text = str.substring(0, str.length - 1)
                resultText.text = ""
            }
        }

        //кнопка равно
        buttonEqual.setOnClickListener {

            try {
                val inputExpression = mathOperation.text.toString()

                //замена символов для вычислений
                val expression = inputExpression.replace("÷", "/").replace("×", "*").replace("−", "-")

                //если деление на 0
                if ("/0(?:\\.0*|(?![1-9]))$".toRegex() in expression) {
                    showToast("Деление на ноль невозможно")
                    return@setOnClickListener
                }

                //если последний символ знак
                if (inputExpression.matches(Regex(".*[+\\−×÷]$"))) {
                    showToast("Использован недопустимый формат")
                    return@setOnClickListener
                }

                //создаем выражение и вычисляем результат
                val ex = ExpressionBuilder(expression).build()
                val result = ex.evaluate()
                if(result.isInfinite() or result.isNaN() or result.isFinite()){
                    showToast("Деление на ноль невозможно")
                    return@setOnClickListener
                }
                //округляем результат и убираем лишние нули
                val decimalResult = BigDecimal(result.toString()).setScale(5, RoundingMode.HALF_UP)
                val strippedResult = decimalResult.stripTrailingZeros().toPlainString()

                //отображаем результат
                resultText.text = strippedResult

            } catch (e:Exception) {
                showToast("Деление на ноль невозможно")
                Log.d("Ошибка", "${e.message}")
            }
        }
    }

    //метод сохранения данных необходимых для восстановления
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("mathOperation", mathOperation.text.toString())
        outState.putString("resultText", resultText.text.toString())
    }

    //метод восстановления сохраненных данных
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mathOperation.text = savedInstanceState.getString("mathOperation", "")
        resultText.text = savedInstanceState.getString("resultText", "")
    }

    //метод обновления текстовых полей
    private fun SetTextFields(str: String) {

        //переместить результат в строку вычислений
        if (resultText.text != "") {
            mathOperation.text = resultText.text
            resultText.text = ""
            resetDotCondition()
        }

        //проверка на пустую строку и ввод символов
        if (mathOperation.text.isEmpty() && (str == "÷" || str == "×" || str == "−" || str == "+" || str == ".")) {
            return
        }

        //проверка на повторение символов
        val lastChar = mathOperation.text.lastOrNull()?.toString()
        if ((lastChar == "÷" || lastChar == "×" || lastChar == "−" || lastChar == "+" || lastChar == ".") && (str == "÷" || str == "×" || str == "−" || str == "+" || str == ".")) {
            return
        }

        //проверка на повторение символов
        val lastChar1 = mathOperation.text.lastOrNull()?.toString()
        if ((lastChar == "(") && (str == "÷" || str == "×" || str == "+" || str == ".")) {
            return
        }

        //проверка на число символов
        if (mathOperation.text.length >= 18) {
            showToast("Достигнут лимит ввода символов")
            return
        }

        //проверка для точки, может быть только цифра
        if (lastChar == "." && !str.matches(Regex("\\d"))) {
            return
        }

        //проверка на ввод точек
        if (str == "." && !dotAllowed) {
            return
        }

        //сброс условия после ввода знаков операции
        if (str.matches(Regex("[+\\-×÷]"))) {
            resetDotCondition()
        }

        //запрет на начало строки с закрывающей скобкой
        if (str == ")" && mathOperation.text.isEmpty()) {
            showToast("Ошибка в расстановке скобок")
            return
        }

        //проверка, что количество закрывающих скобок не превышает количество открывающих
        if (str == ")" && mathOperation.text.matches(Regex(".*\\d$"))) {
            val openBrackets = mathOperation.text.count { it == '(' }
            val closeBrackets = mathOperation.text.count { it == ')' }

            if (openBrackets <= closeBrackets) {
                showToast("Ошибка в расстановке скобок")
                return
            }
        }
        //запрет на добавление закрывающей скобки, если нет цифр перед ней
        else if (str == ")" && mathOperation.text.isNotEmpty()) {
            showToast("Ошибка в расстановке скобок")
            return
        }

        //добавление символа к текущему выражению
        mathOperation.append(str)

        //если точка добавлена, изменить условие
        if (str == ".") {
            dotAllowed = false
        }
    }

    //метод для сброса условия
    private fun resetDotCondition() {
        dotAllowed = true
    }

    //метод для отображения сообщения
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
