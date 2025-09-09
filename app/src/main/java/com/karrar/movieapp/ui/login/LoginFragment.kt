package com.karrar.movieapp.ui.login

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentLoginBinding
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint
import com.karrar.movieapp.BuildConfig
import androidx.core.net.toUri


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val layoutIdFragment = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        setTitle(false)
        collectLast(viewModel.loginEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }


    private fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.LoginEvent -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
            }
            LoginUIEvent.CreateAccountEvent -> {
                CreateAccountBottomSheet().show(childFragmentManager, "TAG")
            }

            LoginUIEvent.ForgotPasswordEvent -> {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, BuildConfig.RESET_PASSWORD_URL.toUri())
                startActivity(browserIntent)
            }
        }
    }
}