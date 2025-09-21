package com.karrar.movieapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentProfileBinding
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.profile.components.ChangeLanguageBottomSheet
import com.karrar.movieapp.ui.profile.components.EditAccountBottomSheet
import com.karrar.movieapp.ui.profile.components.LogoutBottomSheet
import com.karrar.movieapp.utilities.Constants
import com.karrar.movieapp.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, getString(R.string.profile))

        collectLast(viewModel.profileUIEvent) { event ->
            event.getContentIfNotHandled()?.let { onEvent(it) }
        }

        collectLast(viewModel.isDarkMode) { isDark ->
            binding.mySwitch.isChecked = isDark
        }

    }

    private fun onEvent(event: ProfileUIEvent) {
        when (event) {

            ProfileUIEvent.LogOutBottomSheetEvent -> {
                LogoutBottomSheet().show(childFragmentManager, "TAG")
            }

            ProfileUIEvent.LogoutEvent -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment(Constants.PROFILE)
                findNavController().navigate(action)
            }

            ProfileUIEvent.LoginEvent -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment(Constants.PROFILE)
                findNavController().navigate(action)
            }

            ProfileUIEvent.RatedMoviesEvent -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToRatedMoviesFragment()
                findNavController().navigate(action)
            }

            ProfileUIEvent.WatchHistoryEvent -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToWatchHistoryFragment()
                findNavController().navigate(action)
            }

            ProfileUIEvent.EditProfileEvent -> {
                EditAccountBottomSheet().show(childFragmentManager, "TAG")
            }

            ProfileUIEvent.LanguageBottomSheetEvent -> {
                ChangeLanguageBottomSheet().show(childFragmentManager, "TAG")
            }

            is ProfileUIEvent.LanguageEvent -> {
                changeLanguage(event.languageCode)
            }
        }
    }

    private fun changeLanguage(language: String?) {
        language?.let {
            val localeList = LocaleListCompat.forLanguageTags(it)
            AppCompatDelegate.setApplicationLocales(localeList)
        }
    }
}

