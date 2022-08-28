package com.iceapk.presentation.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentEditProfileBinding
import com.iceapk.data.models.Name
import com.iceapk.data.models.User
import com.iceapk.presentation.home.intent.HomeIntent
import com.iceapk.presentation.profile.intent.ProfileIntent
import com.iceapk.presentation.profile.viewstate.ProfileViewState
import com.iceapk.utils.UIController
import com.iceapk.utils.getStringInSharedPreference
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
        private var _binding: FragmentEditProfileBinding? = null
        private val binding get() = _binding
        lateinit var uiController: UIController
        private val viewModel by viewModels<ProfileViewModel>()
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
            setUpView()
            setUpListeners()
            setUpObservers()
            return binding!!.root
        }

    private fun setUpListeners() {
        binding!!.btnSave.root.setOnClickListener {
            if (binding!!.firstName.text.isNotEmpty() && binding!!.lastName.text.isNotEmpty() && binding!!.phone.text.isNotEmpty() && binding!!.email
                    .text.isNotEmpty()){
                lifecycleScope.launch { viewModel.intent.send(ProfileIntent.updateUserProfile(User(email = binding!!.email.text.toString(), username = binding!!.phone.text.toString(), name = Name(firstname = binding!!.firstName.text.toString(), lastname = binding!!.lastName.text.toString()),
                phone = binding!!.phone.text.toString(), password = getStringInSharedPreference(requireContext(), "password")))) }
            }
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewModel.viewState.collect{
                when (it){
                    is ProfileViewState.Idle-> {}
                    is ProfileViewState.Loading->{uiController.showToast(R.color.light_purple, R.color.white, R.color.white, "Updating profile..")}
                    is ProfileViewState.Success->{uiController.showToast(R.color.light_purple, R.color.white, R.color.white, "Profile updated")}
                    is ProfileViewState.Error -> {uiController.showToast(R.color.red, R.color.white, R.color.white, it.error!!)}
                }
            }
        }
    }

    private fun setUpView() {
            //Set up toolbar
            binding!!.toolbar.textView16.text = "Edit Profile"

            binding!!.btnSave.continueTxt.text = "Save Changes"
            binding!!.btnSave.continueTxt.setTextColor(R.color.white)
            binding!!.btnSave.imageViewCard.visibility = View.GONE


            binding!!.btnSave.root.background= resources.getDrawable(R.drawable.light_purple_button)


        }

        override fun onAttach(context: Context) {
            super.onAttach(context)

            if (context is UIController) {
                uiController = context
            } else {
                throw (ClassCastException(
                    requireContext().toString()
                            + "Must imaplement UIController in activity"
                ))
            }
        }
    }

