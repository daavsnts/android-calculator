package com.daavsnts.calculator.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.daavsnts.calculator.databinding.FragmentPortraitScientificKeyboardBinding
import com.daavsnts.calculator.view.setKeyListeners
import com.daavsnts.calculator.viewmodel.OperationViewModel

class PortraitScientificKeyboardFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentPortraitScientificKeyboardBinding
    private lateinit var scientificKeyboardButtons: List<Button>
    private val operationViewModel: OperationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortraitScientificKeyboardBinding.inflate(inflater, container, false)
        setScientificKeyListeners()
        return binding.root
    }

    private fun setScientificKeyListeners() {
        binding.apply {
            scientificKeyboardButtons = listOf(
                buttonSquare, buttonCube, buttonTenPow, buttonCos, buttonTan, buttonSine,
                buttonLogTen, buttonLn, buttonSqrt, buttonPi, buttonEuler, buttonRound,
                buttonPercent, buttonFactorial, buttonMod
            )
        }

        scientificKeyboardButtons.forEach { it.setOnClickListener(this) }
    }

    override fun onClick(view: View?) = setKeyListeners(view, operationViewModel)
}