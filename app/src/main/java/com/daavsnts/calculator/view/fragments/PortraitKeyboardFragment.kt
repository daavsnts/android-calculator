package com.daavsnts.calculator.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.daavsnts.calculator.databinding.FragmentPortraitKeyboardBinding
import com.daavsnts.calculator.view.setKeyListeners
import com.daavsnts.calculator.viewmodel.OperationViewModel

class PortraitKeyboardFragment : Fragment(), View.OnClickListener {
    private lateinit var keyboardButtons: List<Button>
    lateinit var binding: FragmentPortraitKeyboardBinding
    private val operationViewModel: OperationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortraitKeyboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setKeyboardButtonsListeners()
    }

    private fun setKeyboardButtonsListeners() {
        binding.apply {
            keyboardButtons = listOf(
                buttonN7, buttonN8, buttonN9, buttonN4, buttonN5, buttonN6,
                buttonN1, buttonN2, buttonN3, buttonN0, buttonEqual, buttonDot,
                buttonBackspace, buttonAc, buttonPow, buttonPolarity, buttonDivisor,
                buttonMultiply, buttonMinus, buttonPlus
            )
        }

        keyboardButtons.forEach { it.setOnClickListener(this) }
    }

    override fun onClick(view: View?) = setKeyListeners(view, operationViewModel)
}