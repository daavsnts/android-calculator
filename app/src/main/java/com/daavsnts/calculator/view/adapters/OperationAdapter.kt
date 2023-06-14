package com.daavsnts.calculator.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daavsnts.calculator.R
import com.daavsnts.calculator.databinding.HistoryOperationItemBinding
import com.daavsnts.calculator.model.Operation
import kotlin.properties.Delegates

class OperationAdapter(
    private val operationsList: List<Operation>,
    private val onOperationClickListener: (operation: Operation, clickChoice: String) -> Unit
) : RecyclerView.Adapter<OperationAdapter.OperationViewHolder>() {
    override fun getItemCount() = operationsList.size

    class OperationViewHolder(
        itemLayout: HistoryOperationItemBinding,
        private val onOperationClickListener: (operation: Operation, clickChoice: String) -> Unit
    ) : RecyclerView.ViewHolder(itemLayout.root), View.OnClickListener {
        var id by Delegates.notNull<Int>()
        val firstOperand = itemLayout.textFirstOperandItem
        val operator = itemLayout.textOperatorItem
        val secondOperand = itemLayout.textSecondOperandItem
        val operationResult = itemLayout.textOperationResultItem

        init {
            itemLayout.itemContainerConstraintLayout.setOnClickListener(this)
            itemLayout.buttonDeleteOperation.setOnClickListener(this)
            itemLayout.buttonCopyOperation.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val operation = Operation(
                id,
                firstOperand.text.toString(),
                operator.text.toString(),
                secondOperand.text.toString(),
                operationResult.text.toString()
            )

            when (view?.id) {
                R.id.item_container_constraint_layout -> onOperationClickListener(
                    operation,
                    "getOperation"
                )

                R.id.button_delete_operation -> onOperationClickListener(
                    operation,
                    "deleteOperation"
                )

                R.id.button_copy_operation -> onOperationClickListener(
                    operation,
                    "copyOperation"
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val itemLayout =
            HistoryOperationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OperationViewHolder(itemLayout, onOperationClickListener)
    }

    override fun onBindViewHolder(operationViewHolder: OperationViewHolder, position: Int) {
        val operationItem = operationsList[position]
        operationViewHolder.apply {
            id = operationItem.id
            firstOperand.text = operationItem.firstOperand
            operator.text = operationItem.operator
            secondOperand.text = operationItem.secondOperand
            operationResult.text = operationItem.operationResult
        }
    }
}