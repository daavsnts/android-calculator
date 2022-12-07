package com.daavsnts.calculator.viewmodel


import java.text.DecimalFormat

private const val maxOperationSize: Int = 17
private const val maxSizeAfterDot: Int = 4
private const val maxSizeBeforeE: Int = 13

fun formatOperand(operand: String, key: Char): String {
    val firstKeyIsADot = operand.isEmpty() && key == '.'
    if (firstKeyIsADot) return ""

    if (operand.length < maxOperationSize && maxSizeAfterDotNotReached(operand)) {
        val operandWithNewKey = operand + key
        val cleanOperand = removeAllCommas(operandWithNewKey)
        val lastKeyIsADot = cleanOperand.last() == '.'
        return if (lastKeyIsADot) {
            formatValueWithoutDotSpamming(cleanOperand)
        } else {
            formatValue(cleanOperand.toDouble())
        }
    }
    return operand
}

fun removeAllCommas(operand: String): String {
    return operand.toCharArray().filter { it != ',' }.joinToString(separator = "")
}

private fun maxSizeAfterDotNotReached(operand: String): Boolean {
    if (operand.contains('.')) {
        val indexOfDot = operand.indexOf('.')
        if (operand.length - maxSizeAfterDot == indexOfDot)
            return false
    }
    return true
}

private fun formatValueWithoutDotSpamming(operand: String): String {
    val haveMoreThanOneDot = operand.count { it == '.' } > 1
    return if (haveMoreThanOneDot) {
        val operandWithoutDotSpamming = operand.slice(0..operand.length - 2)
        formatValue(operandWithoutDotSpamming.toDouble())
    } else {
        formatValue(operand.toDouble()) + '.'
    }
}

fun formatValue(value: Double): String {
    val valueString = value.toString()
    val formatPattern = "#,###,###,###,###.###"

    val valueIsExtensive = valueString.contains('E') && valueString.length > maxOperationSize
    if (valueIsExtensive)
        return formatExtensiveValue(valueString)

    val normalFormattedValue = DecimalFormat(formatPattern).format(value)
    val valueBecameExtensive = normalFormattedValue.length > maxOperationSize
    if (valueBecameExtensive)
        return removeAllCommas(normalFormattedValue).toDouble().toString()

    return normalFormattedValue
}

private fun formatExtensiveValue(valueString: String): String {
    val formatPattern = "#,###,###,###,###.###"
    val indexOfE = valueString.indexOf('E')
    val afterE = valueString.slice(indexOfE until valueString.length)
    val beforeE = valueString.slice(0 until indexOfE)

    return if (beforeE.length > maxSizeBeforeE) {
        val beforeEWithRightSize = beforeE.slice(0..maxSizeBeforeE)
        val beforeEFormatted = DecimalFormat(formatPattern).format(beforeEWithRightSize.toDouble())
        beforeEFormatted + afterE
    } else {
        val beforeEFormatted = DecimalFormat(formatPattern).format(beforeE.toDouble())
        beforeEFormatted + afterE
    }
}