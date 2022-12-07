package com.daavsnts.calculator.view.activities

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.daavsnts.calculator.MyCalcApplication
import com.daavsnts.calculator.R
import com.daavsnts.calculator.databinding.ActivityHistoryBinding
import com.daavsnts.calculator.model.Operation
import com.daavsnts.calculator.view.adapters.OperationAdapter
import com.daavsnts.calculator.view.callToast
import com.daavsnts.calculator.view.copyToClipboard
import com.daavsnts.calculator.viewmodel.OperationViewModel
import com.daavsnts.calculator.viewmodel.OperationViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val operationViewModel: OperationViewModel by viewModels {
        OperationViewModelFactory((application as MyCalcApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        setRecycleViewAdapter()
        setButtonsListeners()
    }

    private fun setRecycleViewAdapter() {
        val operationsList = operationViewModel.operations
        binding.apply {
            operationsList.observe(this@HistoryActivity) {
                rvOperations.adapter = OperationAdapter(it) { operation, clickChoice ->
                    onOperationClickListener(operation, clickChoice)
                }
                rvOperations.setHasFixedSize(true)
            }
        }
    }

    private fun setButtonsListeners() {
        binding.apply {
            historyTopAppBar.setNavigationOnClickListener { finish() }
            historyTopAppBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.clear_history -> clearAllOperationsFromHistory()
                    else -> false
                }
            }
        }
    }

    private fun onOperationClickListener(operation: Operation, clickChoice: String) {
        when (clickChoice) {
            "getOperation" -> getOperationFromHistory(operation)
            "deleteOperation" -> deleteOperationFromHistory(operation)
            "copyOperation" -> copyOperationToClipboard(operation)
        }
    }

    private fun getOperationFromHistory(operation: Operation) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("operation", operation)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun deleteOperationFromHistory(operation: Operation) {
        val builder = MaterialAlertDialogBuilder(this)
        builder.apply {
            setTitle(R.string.history_dialog_delete_operation_title)
            setPositiveButton(R.string.history_dialog_confirm) { _, _ ->
                callToast(this@HistoryActivity, R.string.history_dialog_operation_deleted_confirmation_message)
                operationViewModel.deleteOperation(operation)
            }
            setNegativeButton(R.string.history_dialog_deny) { _, _ -> }
            show()
        }
    }

    private fun clearAllOperationsFromHistory(): Boolean {
        val operationIsEmpty = operationViewModel.operations.value?.isEmpty() == true
        if (operationIsEmpty) {
            callToast(this@HistoryActivity, R.string.history_toast_empty_message)
            return false
        }

        val builder = MaterialAlertDialogBuilder(this)
        builder.apply {
            setTitle(R.string.history_dialog_clear_history_title)
            setPositiveButton(R.string.history_dialog_confirm) { _, _ ->
                operationViewModel.deleteAllOperations()
                callToast(this@HistoryActivity, R.string.history_toast_cleared_message)
            }
            setNegativeButton(R.string.history_dialog_deny) { _, _ -> }
            show()
        }
        return true
    }

    private fun copyOperationToClipboard(operation: Operation) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val operationString = operation.let {
            "${it.firstOperand} ${it.operator} ${it.secondOperand} = ${it.operationResult}"
        }
        copyToClipboard(this, clipboard, operationString)
    }
}