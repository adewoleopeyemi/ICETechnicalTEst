package com.iceapk.presentation.signup

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentCredentialBinding
import com.iceapk.presentation.data.models.Name
import com.iceapk.presentation.data.models.User
import com.iceapk.presentation.signup.intent.SignupIntent
import com.iceapk.presentation.signup.viewstate.SignupViewState
import com.iceapk.utils.Constants
import com.iceapk.utils.ICENavigator
import com.iceapk.utils.UIController
import com.iceapk.utils.saveInSharedPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CredentialFragment : Fragment(R.layout.fragment_credential) {
    private var _binding: FragmentCredentialBinding? = null
    private val binding get() = _binding
    lateinit var uiController: UIController
    lateinit var navController: NavController
    private val viewModel by viewModels<SignUpViewModel>()
    private var conditons1 =false
    private var conditons2 =false
    private var conditons3 =false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCredentialBinding.inflate(inflater, container, false)
        uiController.statusBarColor(R.color.white, false)
        val view = binding!!.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpTextWatchers()
        setUpListeners()
        navController = Navigation.findNavController(view)
        setUpObservers()
    }


    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.signUpState.collect{
                when (it){
                    is SignupViewState.Idle->{}
                    is SignupViewState.Loading->{
                        uiController.showToast(R.color.light_purple, R.color.white, R.color.white, "Registering user...")
                    }
                    is SignupViewState.Success->{
                        try {

                            ICENavigator.navigate(binding!!.root,  R.id.action_credentialFragment_to_loginFragment)
                            it.resp.id.saveInSharedPreference(requireContext(), Constants.USER_ID)
                            requireArguments().getString("email")!!.saveInSharedPreference(requireContext(), Constants.EMAIL)
                            uiController.showToast(R.color.light_purple, R.color.white, R.color.white, "Signup sucessful")
                        }
                        catch (e: Exception){

                        }
                    }
                    is SignupViewState.Error->{
                        uiController.showToast(R.color.red, R.color.white, R.color.white, it.error)
                    }
                }
            }
        }
    }

    private fun setUpListeners() {
        binding!!.next.root.setOnClickListener {
            if (conditons1 && conditons2 && conditons3){
                lifecycleScope.launch {
                    viewModel.signUpIntent.send(
                        SignupIntent.signUp(User(email = requireArguments().getString("email")!!,
                            username =   requireArguments().getString("phoneNumber")!!.filterNot { it.isWhitespace() },
                            password = binding!!.password.text.text.toString(),
                            name = Name(firstname = requireArguments().getString("firstName")!!, lastname = requireArguments().getString("lastName")!!),
                            phone= requireArguments().getString("phoneNumber")!!.filterNot { it.isWhitespace() },
                    )))
                }
            }
        }
    }

    private fun setUpTextWatchers() {
        binding!!.password.text.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                conditons1 = if (binding!!.password.text.text.length >= 8){
                    setConditionsMet(1)
                    true
                } else{
                    unSetConditionsMet(1)
                    false
                }
                for (i in binding!!.password.text.text.toString()){
                    if (i.isUpperCase()){
                        for (j in binding!!.password.text.text.toString()){
                            conditons2 = if (j.isLowerCase()){
                                setConditionsMet(2)
                                true
                            } else{
                                unSetConditionsMet(2)
                                false
                            }
                        }
                    }
                }
                conditons3 = if (binding!!.password.text.text.isNotEmpty() && binding!!.password.text.text.toString() == binding!!.confirmPassword.text.text.toString()){
                    setConditionsMet(3)
                    true
                } else{
                    unSetConditionsMet(3)
                    false
                }
            }


            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding!!.confirmPassword.text.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding!!.confirmPassword.text.text.length >= 8){
                    setConditionsMet(1)
                }

                if (binding!!.confirmPassword.text.text.isNotEmpty() && binding!!.confirmPassword.text.text.toString() == binding!!.password.text.text.toString()){
                    setConditionsMet(3)
                }
                if (conditons1 && conditons2 && conditons3){
                    binding!!.next.root.background = resources.getDrawable(R.drawable.light_purple_button)
                    binding!!.next.continueTxt.setTextColor(R.color.black)
                }
                else{
                    binding!!.next.root.background = resources.getDrawable(R.drawable.deactivated_button)
                    binding!!.next.continueTxt.setTextColor(R.color.grey)
                }
            }


            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun setUpView() {
        binding!!.checkTwo.text.text = "Must contain upper and lower case"
        binding!!.checkThree.text.text = "Passwords must Match"
        binding!!.confirmPassword.label.text = "Confirm Password"
        binding!!.password.root.setOnClickListener {
            binding!!.password.root.background = resources.getDrawable(R.drawable.edit_bg_outline)
        }
        binding!!.confirmPassword.root.setOnClickListener {
            binding!!.confirmPassword.root.background = resources.getDrawable(R.drawable.edit_bg_outline)
        }
    }

    private fun setConditionsMet(condition: Int){
        when (condition){
            1-> {
                binding!!.checkOne.icon.setImageResource(R.drawable.check_icon)
                binding!!.checkOne.text.setTextColor(resources.getColor(R.color.purple))
                conditons1 = true
            }
            2->{
                binding!!.checkTwo.icon.setImageResource(R.drawable.check_icon)
                binding!!.checkTwo.text.setTextColor(resources.getColor(R.color.purple))
                conditons2 = true
            }
            3->{
                binding!!.checkThree.icon.setImageResource(R.drawable.check_icon)
                binding!!.checkThree.text.setTextColor(resources.getColor(R.color.purple))
                conditons3 = true
            }
        }
    }

    private fun unSetConditionsMet(condition: Int){
        when (condition){
            1-> {
                binding!!.checkOne.icon.setImageResource(R.drawable.unmet)
                binding!!.checkOne.text.setTextColor(resources.getColor(R.color.black))
                conditons1 = false
            }
            2->{
                binding!!.checkTwo.icon.setImageResource(R.drawable.unmet)
                binding!!.checkTwo.text.setTextColor(resources.getColor(R.color.black))
                conditons2 = false
            }
            3->{
                binding!!.checkThree.icon.setImageResource(R.drawable.unmet)
                binding!!.checkThree.text.setTextColor(resources.getColor(R.color.black))
                conditons3 = false
            }
        }
        if (conditons1 && conditons2 && conditons3){
            binding!!.next.root.background = resources.getDrawable(R.drawable.light_purple_button)
            binding!!.next.continueTxt.setTextColor(R.color.black)
        }
        else{
            binding!!.next.root.background = resources.getDrawable(R.drawable.deactivated_button)
            binding!!.next.continueTxt.setTextColor(R.color.grey)
        }
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