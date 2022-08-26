package com.iceapk.presentation.signup

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentDetailsBinding
import com.iceapk.utils.ICENavigator
import com.iceapk.utils.UIController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class DetailsFragment
@Inject constructor(): Fragment(R.layout.fragment_details), AdapterView.OnItemSelectedListener {

    lateinit var uiController: UIController
    lateinit var phone: String
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding
    private var email: String = ""
    private var password: String = ""
    private var confirmPassword: String = ""
    private var gender: String = "Male"
    private val intentType = "image/*"
    private val intent: Intent = Intent( "android.intent.action.GET_CONTENT")
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        uiController.statusBarColor(R.color.white, false)
        val view = binding!!.root
        setUpTextWatchers()
        return view
    }

    private fun setUpTextWatchers() {
        binding!!.firstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding!!.firstName.background = AppCompatResources.getDrawable(requireContext(), R.drawable.edit_bg_outline)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding!!.lastName.text.isNotEmpty() && binding!!.email.text.isNotEmpty()){
                    toggleButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding!!.lastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding!!.lastName.background = AppCompatResources.getDrawable(requireContext(), R.drawable.edit_bg_outline)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding!!.firstName.text.isNotEmpty() && binding!!.email.text.isNotEmpty()){
                    toggleButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding!!.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding!!.email.background = AppCompatResources.getDrawable(requireContext(), R.drawable.edit_bg_outline)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding!!.firstName.text.isNotEmpty() && binding!!.lastName.text.isNotEmpty()){
                    toggleButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun toggleButton() {
        binding!!.next.root.background = AppCompatResources.getDrawable(requireContext(), R.drawable.light_purple_button)
        binding!!.next.continueTxt.setTextColor(resources.getColor(R.color.black))
        binding!!.next.imageViewCard.setImageResource(R.drawable.ic_forward_black)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers(view)
    }

    private fun subscribeObservers(view: View){
        binding!!.next.root.setOnClickListener {
            if (binding!!.email.text.isNotEmpty() && binding!!.lastName.text.isNotEmpty() && binding!!.email.text.isNotEmpty() && binding!!.phone.text.isNotEmpty()){
                val bundle = Bundle()
                bundle.putString("firstName", binding!!.firstName.text.toString())
                bundle.putString("lastName", binding!!.lastName.text.toString())
                bundle.putString("email", binding!!.email.text.toString())
                bundle.putString("phoneNumber", binding!!.phone.text.toString())
                ICENavigator.navigate(it, R.id.action_detailsFragment_to_credentialFragment, bundle)
            }
            else{
                uiController.showToast(R.color.red, R.color.white, R.color.white, "Please enter all the required fields")
            }
        }
        binding!!.email.setOnClickListener {
            binding!!.email.background = resources.getDrawable(R.drawable.edit_bg_outline)
        }
        binding!!.firstName.setOnClickListener {
            binding!!.firstName.background = resources.getDrawable(R.drawable.edit_bg_outline)
        }
        binding!!.lastName.setOnClickListener {
            binding!!.lastName.background = resources.getDrawable(R.drawable.edit_bg_outline)
        }
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        gender = parent?.getItemAtPosition(position) as String
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
        if (uri != null){
            imageUri = uri
            //binding!!.avatar.setImageURI(uri)
        }
    }

    private fun pickImage(){
        getContent.launch(intentType)
    }

    override fun onResume() {
        uiController.statusBarColor(R.color.white, false)
        super.onResume()
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