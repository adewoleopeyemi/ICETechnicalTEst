package com.iceapk.presentation.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentEditProfileBinding
import com.iceapk.utils.UIController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
        private var _binding: FragmentEditProfileBinding? = null
        private val binding get() = _binding
        lateinit var uiController: UIController
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
            setUpView()
            return binding!!.root
        }

        private fun setUpView() {

            //Set up toolbar
            binding!!.toolbar.textView16.text = "Edit Profile"

            binding!!.btnSave.continueTxt.text = "Save Changes"
            binding!!.btnSave.imageViewCard.visibility = View.GONE
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

