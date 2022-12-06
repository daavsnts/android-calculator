package com.example.mycalc.viewmodel

import androidx.lifecycle.*
import com.example.mycalc.data.repository.OperationRepository
import com.example.mycalc.model.Operation
import kotlinx.coroutines.launch
import org.apache.commons.math3.util.CombinatoricsUtils.factorialDouble
import kotlin.math.*

class OperationViewModel(private val operationRepository: OperationRepository) : ViewModel() {
    val operations: LiveData<List<Operation>> = operationRepository.allOperations.asLiveData()

    private var _firstOperand: MutableLiveData<String> = MutableLiveData("")
    val firstOperand: MutableLiveData<String>
        get() = _firstOperand

    private var _operator: MutableLiveData<String> = MutableLiveData("")
    val operator: MutableLiveData<String>
        get() = _operator

    private var _secondOperand: MutableLiveData<String> = MutableLiveData("")
    val secondOperand: MutableLiveData<String>
        get() = _secondOperand

    private var _equal: MutableLiveData<String> = MutableLiveData("")
    val equal: MutableLiveData<String>
        get() = _equal

    private var _operationResult: MutableLiveData<String> = MutableLiveData("")
    val operationResult: MutableLiveData<String>
        get() = _operationResult

    // Database functions
    private fun insertOperation(operation: Operation) =
        viewModelScope.launch { operationRepository.insert(operation) }

    fun deleteOperation(operation: Operation) =
        viewModelScope.launch { operationRepository.delete(operation) }

    fun deleteAllOperations() = viewModelScope.launch { operationRepository.deleteAll() }

    // Functions to check conditions
    private fun firstOperandIsEmpty() = firstOperand.value.toString().isEmpty()
    private fun secondOperandIsEmpty() = secondOperand.value.toString().isEmpty()
    private fun operationResultIsEmpty() = operationResult.value.toString().isEmpty()
    private fun operatorIsEmpty() = operator.value.toString().isEmpty()
    private fun operandIsExtensive(operand: String) = operand.contains('E')
    private fun isInfinity(value: String) = value.contains('∞')
    private fun isNaN(value: String) = value.contains("NaN")
    private fun someOperandIsInvalid(): Boolean {
        val firstOperand = firstOperand.value.toString()
        val secondOperand = secondOperand.value.toString()
        return !operandIsValid(firstOperand) || !operandIsValid(secondOperand)
    }

    // Operations functions
    fun fillOperands(key: Char) {
        if (someOperandIsInvalid()) clearOperation()

        if (operatorIsEmpty()) {
            firstOperand.value =
                formatOperand(firstOperand.value.toString(), key)
            return
        }

        secondOperand.value =
            formatOperand(secondOperand.value.toString(), key)
        operationResult.value = ""
        equal.value = ""
    }

    fun selectOperator(selected: String) {
        if (!firstOperandIsEmpty() && secondOperandIsEmpty())
            operator.value = selected

        if (!firstOperandIsEmpty() && !secondOperandIsEmpty())
            evalUsingOperator(selected)
    }

    private fun evalUsingOperator(selected: String) {
        val resultIsInfinity = isInfinity(operationResult.value.toString())
        val resultIsNaN = isNaN(operationResult.value.toString())
        if (resultIsInfinity || resultIsNaN || someOperandIsInvalid()) {
            clearOperation()
            return
        }

        val nextResultIsInfinity = isInfinity(evaluate())
        if (nextResultIsInfinity) {
            equal.value = "="
            operationResult.value = evaluate()
            return
        }

        val nextResultIsNaN = isNaN(evaluate())
        if (nextResultIsNaN) {
            equal.value = "="
            operationResult.value = evaluate()
            return
        }

        val fOperand = removeAllCommas(firstOperand.value.toString()).toDouble()
        val op = operator.value.toString()
        val sOperand = removeAllCommas(secondOperand.value.toString()).toDouble()
        val resultPreview = removeAllCommas(eval(fOperand, op, sOperand)).toDouble() * (-1)

        val resultWasEvaluatedAndPolarized =
            operationResult.value == formatValue(resultPreview)
        if (resultWasEvaluatedAndPolarized) {
            firstOperand.value = operationResult.value
            secondOperand.value = ""
            operator.value = selected
            return
        }

        evalOperation()
        firstOperand.value = operationResult.value
        secondOperand.value = ""
        operator.value = selected
    }

    fun evalOperation() {
        if (someOperandIsInvalid()) clearOperation()

        val result = evaluate()
        operationResult.value = result
        if (!operationResultIsEmpty()) {
            equal.value = "="
            insertOperation(makeOperation())
        } else equal.value = ""
    }

    private fun makeOperation(): Operation {
        return Operation(
            0,
            firstOperand.value,
            operator.value,
            secondOperand.value,
            operationResult.value
        )
    }

    private fun evaluate(): String {
        if (someOperandIsInvalid() || firstOperandIsEmpty() || secondOperandIsEmpty())
            return ""

        val firstOperand = removeAllCommas(firstOperand.value.toString()).toDouble()
        val secondOperand = removeAllCommas(secondOperand.value.toString()).toDouble()
        return eval(firstOperand, operator.value.toString(), secondOperand)
    }

    private fun eval(firstOperand: Double, operator: String, secondOperand: Double): String {
        return when (operator) {
            "+" -> formatValue((firstOperand) + (secondOperand))
            "-" -> formatValue((firstOperand) - (secondOperand))
            "/" -> formatValue((firstOperand) / (secondOperand))
            "x" -> formatValue((firstOperand) * (secondOperand))
            "^" -> formatValue((firstOperand).pow(secondOperand))
            "mod" -> formatValue((firstOperand).mod(secondOperand))
            else -> ""
        }
    }

    fun clearOperation() {
        firstOperand.value = ""
        operator.value = ""
        secondOperand.value = ""
        equal.value = ""
        operationResult.value = ""
    }

    fun clearLastKey() {
        if (!operationResultIsEmpty()) {
            operationResult.value = ""
            equal.value = ""
            return
        }
        if (operatorIsEmpty()) {
            firstOperand.value = removeLastKey(firstOperand.value.toString())
            return
        }
        if (secondOperandIsEmpty() && !operatorIsEmpty()) {
            operator.value = ""
            return
        }

        secondOperand.value = removeLastKey(secondOperand.value.toString())
        operationResult.value = ""
    }

    private fun removeLastKey(operand: String): String {
        if (operand.length == 2 && operand[0] == '-') return ""
        return operand.slice(0 until operand.length - 1)
    }


    // Scientific keyboard
    fun squareOperand() = selectOperandAndScientificOperator(square)
    fun cubeOperand() = selectOperandAndScientificOperator(cube)
    fun tenPowOperand() = selectOperandAndScientificOperator(powTen)
    fun cosOperand() = selectOperandAndScientificOperator(cosine)
    fun tanOperand() = selectOperandAndScientificOperator(tangent)
    fun sineOperand() = selectOperandAndScientificOperator(sine)
    fun logBaseTenOperand() = selectOperandAndScientificOperator(logarithmBaseTen)
    fun naturalLogOperand() = selectOperandAndScientificOperator(naturalLogarithm)
    fun sqrtOperand() = selectOperandAndScientificOperator(sqrtValue)
    fun roundOperand() = selectOperandAndScientificOperator(roundValue)
    fun percentOperand() = selectOperandAndScientificOperator(percentValue)
    fun factorialOperand() = selectOperandAndScientificOperator(factorialValue)
    fun reverseOperandPolarity() = selectOperandAndScientificOperator(reverse)
    fun oneDividedByOperand() = selectOperandAndScientificOperator(oneDividedByOperand)
    fun piOperand() = fillOperandWithScientificOperator(pi)
    fun eulerOperand() = fillOperandWithScientificOperator(euler)

    private fun selectOperandAndScientificOperator(operationType: (Double) -> Double) {
        if (!operationResultIsEmpty()) {
            val operandValue = removeAllCommas(operationResult.value.toString())
            operationResult.value = calculate(operationType, operandValue)

            val firstOperandAndOperationResultAreEqual =
                !firstOperandIsEmpty() && secondOperandIsEmpty() && !operationResultIsEmpty()
            if (firstOperandAndOperationResultAreEqual)
                firstOperand.value = operationResult.value.toString()

            return
        }

        val selectedOperator = selectOperator()
        val operandValue = removeAllCommas(selectedOperator.value.toString())
        selectedOperator.value = calculate(operationType, operandValue)
        operationResult.value = ""
    }

    private fun selectOperator(): MutableLiveData<String> {
        if (!firstOperandIsEmpty() && !secondOperandIsEmpty())
            return secondOperand
        return firstOperand
    }

    private fun fillOperandWithScientificOperator(operationType: () -> Double) {
        if (operatorIsEmpty()) {
            firstOperand.value = formatValue(operationType())
            return
        }

        secondOperand.value = formatValue(operationType())
        operationResult.value = ""
        equal.value = ""
    }

    private fun calculate(operationType: (Double) -> Double, operandValue: String): String {
        if (operandIsValid(operandValue) && operandValue.isNotEmpty())
            return formatValue(operate(operationType, operandValue))

        return operandValue
    }

    private fun operandIsValid(operand: String): Boolean {
        if (isInfinity(operand)) return false
        if (isNaN(operand)) return false

        return true
    }

    private fun operate(OperationType: (Double) -> Double, operand: String): Double =
        OperationType(operand.toDouble())

    private val square: (Double) -> Double = { operand: Double -> operand * operand }
    private val cube: (Double) -> Double = { operand: Double -> operand * operand * operand }
    private val powTen: (Double) -> Double = { operand: Double -> 10.0.pow(operand) }
    private val cosine: (Double) -> Double = { operand: Double -> cos(operand) }
    private val tangent: (Double) -> Double = { operand: Double -> tan(operand) }
    private val sine: (Double) -> Double = { operand: Double -> sin(operand) }
    private val logarithmBaseTen: (Double) -> Double = { operand: Double -> log(operand, 10.0) }
    private val naturalLogarithm: (Double) -> Double = { operand: Double -> ln(operand) }
    private val sqrtValue: (Double) -> Double = { operand: Double -> sqrt(operand) }
    private val euler: () -> Double = { E }
    private val pi: () -> Double = { PI }
    private val roundValue: (Double) -> Double = { operand: Double -> round(operand) }
    private val percentValue: (Double) -> Double = { operand: Double -> operand / 100 }
    private val oneDividedByOperand: (Double) -> Double = { operand: Double -> 1 / operand }
    private val factorialValue: (Double) -> Double =
        factorialValue@{ operand: Double ->
            if (formatValue(operand).contains('.') || operand < 0)
                return@factorialValue operand

            if (operand > 170)
                return@factorialValue Double.POSITIVE_INFINITY

            return@factorialValue factorialDouble(operand.toInt())
        }

    private val reverse: (Double) -> Double = reverse@{ operand: Double ->
        val operandString = operand.toString()
        if (operandIsExtensive(operandString)) {
            if (operand > 0)
                return@reverse "-${operand}".toDouble()

            val operandLength = operandString.length
            return@reverse operandString.slice(1 until operandLength).toDouble()
        }

        return@reverse operand * (-1)
    }
}

class OperationViewModelFactory(
    private val operationRepository: OperationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OperationViewModel(operationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}