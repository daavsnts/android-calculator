package com.daavsnts.calculator.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.daavsnts.calculator.R
import com.daavsnts.calculator.viewmodel.OperationViewModel

fun setKeyListeners(view: View?, operationViewModel: OperationViewModel) {
    when (view?.id) {
        // Keyboard
        R.id.button_n7 -> operationViewModel.fillOperands('7')
        R.id.button_n8 -> operationViewModel.fillOperands('8')
        R.id.button_n9 -> operationViewModel.fillOperands('9')
        R.id.button_n4 -> operationViewModel.fillOperands('4')
        R.id.button_n5 -> operationViewModel.fillOperands('5')
        R.id.button_n6 -> operationViewModel.fillOperands('6')
        R.id.button_n1 -> operationViewModel.fillOperands('1')
        R.id.button_n2 -> operationViewModel.fillOperands('2')
        R.id.button_n3 -> operationViewModel.fillOperands('3')
        R.id.button_n0 -> operationViewModel.fillOperands('0')
        R.id.button_dot -> operationViewModel.fillOperands('.')
        R.id.button_pow -> operationViewModel.selectOperator("^")
        R.id.button_divisor -> operationViewModel.selectOperator("/")
        R.id.button_multiply -> operationViewModel.selectOperator("x")
        R.id.button_minus -> operationViewModel.selectOperator("-")
        R.id.button_plus -> operationViewModel.selectOperator("+")
        R.id.button_polarity -> operationViewModel.reverseOperandPolarity()
        R.id.button_equal -> operationViewModel.evalOperation()
        R.id.button_ac -> operationViewModel.clearOperation()
        R.id.button_backspace -> operationViewModel.clearLastKey()

        // Scientific keyboard
        R.id.button_square -> operationViewModel.squareOperand()
        R.id.button_cube -> operationViewModel.cubeOperand()
        R.id.button_ten_pow -> operationViewModel.tenPowOperand()
        R.id.button_cos -> operationViewModel.cosOperand()
        R.id.button_tan -> operationViewModel.tanOperand()
        R.id.button_sine -> operationViewModel.sineOperand()
        R.id.button_log_ten -> operationViewModel.logBaseTenOperand()
        R.id.button_ln -> operationViewModel.naturalLogOperand()
        R.id.button_sqrt -> operationViewModel.sqrtOperand()
        R.id.button_pi -> operationViewModel.piOperand()
        R.id.button_euler -> operationViewModel.eulerOperand()
        R.id.button_round -> operationViewModel.roundOperand()
        R.id.button_percent -> operationViewModel.percentOperand()
        R.id.button_factorial -> operationViewModel.factorialOperand()
        R.id.button_mod -> operationViewModel.selectOperator("mod")
        R.id.button_one_div_x -> operationViewModel.oneDividedByOperand()
    }
}

fun setResultTextSize(resultSize: Int, textOperationResult: TextView, textEqual: TextView) {
        textOperationResult.textSize = getSizeByResultLength(resultSize)
        textEqual.textSize = getSizeByResultLength(resultSize)
}

private fun getSizeByResultLength(resultLength: Int): Float {
    return when (resultLength) {
        in 0..7 -> 45f
        in 8..14 -> 40f
        in 14..17 -> 35f
        else -> 30f
    }
}

fun copyToClipboard(context: Context, clipboard: ClipboardManager, value: CharSequence): Boolean {
    val clip: ClipData = ClipData.newPlainText("", value)
    clipboard.setPrimaryClip(clip)

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
        Toast.makeText(context, R.string.clipboard_copied_message, Toast.LENGTH_SHORT).show()

    return true
}

fun callToast(context: Context, @StringRes text: Int) =
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()