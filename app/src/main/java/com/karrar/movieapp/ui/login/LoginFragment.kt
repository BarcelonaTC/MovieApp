package com.karrar.movieapp.ui.login

import android.content.Intent
import android.text.InputType
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.karrar.movieapp.BuildConfig
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentLoginBinding
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val layoutIdFragment = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()
    private var isPasswordVisible = true


    override fun onStart() {
        super.onStart()
        setTitle(false)
        collectLast(viewModel.loginEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }

        collectLast(viewModel.loginUIState) { state ->
            showLoading(state.isLoading)
            userNameErrorIcon(state.userNameHelperText)
        }

        passwordToggle()
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

            LoginUIEvent.ContinueAsGuestEvent -> {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.LoginButton.text = ""
            binding.ProgressIndicator.visibility = View.VISIBLE
            binding.LoginButton.isEnabled = false
        } else {
            binding.LoginButton.text = getString(R.string.login)
            binding.ProgressIndicator.visibility = View.GONE
            binding.LoginButton.isEnabled = true
        }
    }

    private fun passwordToggle() {
        binding.passwordInput.setEndIconOnClickListener {
            isPasswordVisible = !isPasswordVisible
            val editText = binding.passwordInput.editText

            if (isPasswordVisible) {
                editText?.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordInput.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.eye_opened)
            } else {
                editText?.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordInput.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.eye_closed)
            }

            editText?.setSelection(editText.text?.length ?: 0)
        }
    }

    private fun userNameErrorIcon(errorText: String){
        binding.userNameInput.apply {
            if(errorText.isNotEmpty()) {
                endIconMode = TextInputLayout.END_ICON_CUSTOM
                binding.userNameInput.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.danger_triangle)
            }
            else{
                endIconMode = TextInputLayout.END_ICON_NONE
            }


        }

    }


}