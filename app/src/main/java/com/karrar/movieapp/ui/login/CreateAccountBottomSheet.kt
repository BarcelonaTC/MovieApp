package com.karrar.movieapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.karrar.movieapp.BuildConfig
import com.karrar.movieapp.databinding.CreateAccountBottomSheetBinding

class CreateAccountBottomSheet : DialogFragment() {

    lateinit var binding: CreateAccountBottomSheetBinding
    private val viewModel: LoginViewModel by activityViewModels()

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
        binding = CreateAccountBottomSheetBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancle.setOnClickListener { dismiss() }
        binding.gotoWebsite.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, BuildConfig.TMDB_SIGNUP_URL.toUri())
            startActivity(browserIntent)

        }
    }

}
