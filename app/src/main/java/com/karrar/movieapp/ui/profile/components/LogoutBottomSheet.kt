package com.karrar.movieapp.ui.profile.components

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.karrar.movieapp.databinding.LogoutAccountBottomSheetBinding
import com.karrar.movieapp.ui.profile.ProfileViewModel

class LogoutBottomSheet : DialogFragment() {

    lateinit var binding: LogoutAccountBottomSheetBinding
    private val viewModel: ProfileViewModel by viewModels({ requireParentFragment() })

    override fun onStart() {
        super.onStart()

        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

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
        binding = LogoutAccountBottomSheetBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancel.setOnClickListener { dismiss() }
    }

}
