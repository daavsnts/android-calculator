package com.daavsnts.calculator.view.activities

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import com.daavsnts.calculator.CalculatorApplication
import com.daavsnts.calculator.R
import com.daavsnts.calculator.databinding.ActivityMainBinding
import com.daavsnts.calculator.model.Operation
import com.daavsnts.calculator.view.copyToClipboard
import com.daavsnts.calculator.view.setKeyListeners
import com.daavsnts.calculator.view.setResultTextSize
import com.daavsnts.calculator.viewmodel.OperationViewModel
import com.daavsnts.calculator.viewmodel.OperationViewModelFactory

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var scientificContainerNavOpened = false
    private lateinit var landKeyboardButtons: List<Button?>
    private lateinit var landScientificKeyboardButtons: List<Button?>
    private val operationViewModel: OperationViewModel by viewModels {
        OperationViewModelFactory((application as CalculatorApplication).repository)
    }
    private val getOperationFromHistory = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { tryToGetOperationFromHistory(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initiateOperationObservers()
        setDisplayListeners()
        setTopButtonsListeners()
        setLandKeyboardButtonsListeners()
        setPortraitScientificNavButtonsListeners()
        setLandScientificKeyboardButtonsListeners()
    }

    private fun initiateOperationObservers() {
        binding.apply {
            operationViewModel.apply {
                firstOperand.observe(this@MainActivity) {
                    textFirstOperand.text = it
                }
                operator.observe(this@MainActivity) {
                    textOperator.text = it
                }
                secondOperand.observe(this@MainActivity) {
                    textSecondOperand.text = it
                }
                equal.observe(this@MainActivity) {
                    textEqual.text = it
                }
                operationResult.observe(this@MainActivity) {
                    textOperationResult.text = it
                    setResultTextSize(it.length, textOperationResult, textEqual)
                }
            }
        }
    }

    private fun setDisplayListeners() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        binding.apply {
            textFirstOperand.setOnLongClickListener {
                copyToClipboard(this@MainActivity, clipboard, textFirstOperand.text)
            }
            textSecondOperand.setOnLongClickListener {
                copyToClipboard(this@MainActivity, clipboard, textSecondOperand.text)
            }
            textOperationResult.setOnLongClickListener {
                copyToClipboard(this@MainActivity, clipboard, textOperationResult.text)
            }
        }
    }

    private fun setTopButtonsListeners() {
        binding.apply {
            mainTopAppBar?.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_history -> openHistory()
                    else -> false
                }
            }
            buttonHistory?.setOnClickListener(this@MainActivity)
        }
    }

    private fun setPortraitScientificNavButtonsListeners() {
        binding.apply {
            buttonOpenCloseScientificContainer?.setOnClickListener(this@MainActivity)
            scientificContainerOpenedOverlay?.setOnClickListener(this@MainActivity)
        }
    }

    private fun setLandKeyboardButtonsListeners() {
        binding.apply {
            landKeyboardButtons = listOf(
                buttonN7, buttonN8, buttonN9, buttonN4, buttonN5, buttonN6,
                buttonN1, buttonN2, buttonN3, buttonN0, buttonEqual, buttonDot,
                buttonBackspace, buttonAc, buttonPow, buttonPolarity, buttonDivisor,
                buttonMultiply, buttonMinus, buttonPlus
            )
        }
        landKeyboardButtons.forEach { it?.setOnClickListener(this) }
    }

    private fun setLandScientificKeyboardButtonsListeners() {
        binding.apply {
            landScientificKeyboardButtons = listOf(
                buttonSquare, buttonCube, buttonTenPow, buttonCos, buttonTan, buttonSine,
                buttonLogTen, buttonLn, buttonSqrt, buttonPi, buttonEuler, buttonRound,
                buttonPercent, buttonFactorial, buttonMod, buttonOneDivX
            )
        }
        landScientificKeyboardButtons.forEach { it?.setOnClickListener(this) }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            // Top buttons
            R.id.button_history -> openHistory()

            // Open/close portrait scientific keyboard
            R.id.button_open_close_scientific_container -> openClosePortraitScientificKeyboard()
            R.id.scientific_container_opened_overlay -> openClosePortraitScientificKeyboard()
        }
        // Keyboard listeners
        setKeyListeners(view, operationViewModel)
    }

    private fun openClosePortraitScientificKeyboard() {
        if (!scientificContainerNavOpened) {
            openPortraitScientificKeyboard()
            return
        }
        closePortraitScientificKeyboard()
    }

    private fun openPortraitScientificKeyboard() {
        binding.apply {
            scientificContainerNav?.updateLayoutParams { width = MATCH_CONSTRAINT }
            scientificFragmentContainerView?.visibility = VISIBLE
            scientificContainerOpenedOverlay?.updateLayoutParams { width = 250 }
            scientificContainerOpenedOverlay?.visibility = VISIBLE
            buttonOpenCloseScientificContainer?.setIconResource(R.drawable.ic_nav_scientific_keyboard_arrow_close)
        }
        scientificContainerNavOpened = true
    }

    private fun closePortraitScientificKeyboard() {
        binding.apply {
            scientificContainerNav?.updateLayoutParams { width = 75 }
            scientificFragmentContainerView?.visibility = GONE
            scientificContainerOpenedOverlay?.updateLayoutParams { width = MATCH_CONSTRAINT }
            scientificContainerOpenedOverlay?.visibility = INVISIBLE
            buttonOpenCloseScientificContainer?.setIconResource(R.drawable.ic_nav_scientific_keyboard_arrow_open)
        }
        scientificContainerNavOpened = false
    }

    private fun openHistory(): Boolean {
        val intent = Intent(this, HistoryActivity::class.java)
        getOperationFromHistory.launch(intent)
        return true
    }

    private fun tryToGetOperationFromHistory(activityResult: ActivityResult) {
        if (activityResult.resultCode == Activity.RESULT_OK) {
            val historyOperation =
                @Suppress("DEPRECATION")
                activityResult.data?.getParcelableExtra("operation") as Operation?
            binding.apply {
                operationViewModel.apply {
                    if (historyOperation != null) {
                        firstOperand.value = historyOperation.firstOperand
                        operator.value = historyOperation.operator
                        secondOperand.value = historyOperation.secondOperand
                        equal.value = "="
                        operationResult.value = historyOperation.operationResult
                    }
                }
            }
        }
    }
}
