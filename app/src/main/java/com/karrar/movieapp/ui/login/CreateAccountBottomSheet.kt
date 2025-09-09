package com.karrar.movieapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karrar.movieapp.BuildConfig
import com.karrar.movieapp.databinding.CreateAccountBottomSheetBinding

class CreateAccountBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: CreateAccountBottomSheetBinding
    private val viewModel: LoginViewModel by activityViewModels()

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
