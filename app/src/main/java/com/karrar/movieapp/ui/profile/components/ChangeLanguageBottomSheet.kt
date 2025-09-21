package com.karrar.movieapp.ui.profile.components

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.karrar.movieapp.BuildConfig
import com.karrar.movieapp.databinding.ChangeLanguageBottomSheetBinding
import com.karrar.movieapp.ui.profile.ProfileViewModel

class ChangeLanguageBottomSheet : DialogFragment() {

    lateinit var binding: ChangeLanguageBottomSheetBinding
    private val viewModel: ProfileViewModel by viewModels({ requireParentFragment() })

    override fun onStart() {
        super.onStart()

        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)

        val params = dialog?.window?.attributes
        val marginBottomInDp = 28
        val marginBottomInPx = (marginBottomInDp * resources.displayMetrics.density).toInt()
        params?.y = marginBottomInPx
        dialog?.window?.attributes = params

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChangeLanguageBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.englishCard.isChecked = false
        binding.arabicCard.isChecked = false

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.englishCard.setOnClickListener {
            binding.englishCard.isChecked = true
            binding.arabicCard.isChecked = false
            viewModel.onLanguageChanged(Language.English.code)
        }

        binding.arabicCard.setOnClickListener {
            binding.englishCard.isChecked = false
            binding.arabicCard.isChecked = true
            viewModel.onLanguageChanged(Language.Arabic.code)
        }

    }

    enum class Language(val code: String) {
        Arabic("ar"),
        English("en")
    }

}

