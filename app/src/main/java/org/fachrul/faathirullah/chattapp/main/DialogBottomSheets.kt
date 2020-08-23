package org.fachrul.faathirullah.chattapp.main

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.fachrul.faathirullah.chattapp.databinding.BottomsheetsBinding
import org.fachrul.faathirullah.chattapp.model.Chat

class UpdateBottomDialog(
    private val chat: Chat,
    private val viewModel: MainViewModel
): BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = BottomsheetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etEdit.setText(chat.message.orEmpty())
        viewClicked()
    }

    private fun viewClicked(){
        binding.tvUpdate.setOnClickListener {
            chat.message = binding.etEdit.text.toString()
            viewModel.updateChat(chat)
            dismiss()
        }

        binding.tvDelete.setOnClickListener {
//            viewModel.deleteChat(chat)
            dismiss()
        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }


}