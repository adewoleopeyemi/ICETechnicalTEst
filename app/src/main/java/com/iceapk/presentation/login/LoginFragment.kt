package com.iceapk.presentation.login
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentLoginBinding
import com.iceapk.utils.saveInSharedPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var uiController: UIController
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpListeners()
        setUpTextWatchers()
        uiController.statusBarColor(R.color.white, false)
        return binding!!.root
    }

    private fun setUpTextWatchers() {
        binding!!.phone.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding!!.password.text.text.toString().isNotEmpty()){
                    binding!!.next.background = resources.getDrawable(R.drawable.light_purple_button)
                    binding!!.continueTxt.setTextColor(R.color.black)
                }
                else{
                    binding!!.next.background = resources.getDrawable(R.drawable.deactivated_button)
                    binding!!.continueTxt.setTextColor(R.color.text_gray)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding!!.password.text.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding!!.phone.text.toString().isNotEmpty()){
                    binding!!.next.background = resources.getDrawable(R.drawable.light_purple_button)
                    binding!!.continueTxt.setTextColor(R.color.black)
                }
                else{
                    binding!!.next.background = resources.getDrawable(R.drawable.deactivated_button)
                    binding!!.continueTxt.setTextColor(R.color.text_gray)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun observeViewModel(){
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                when (it){
                    is LoginViewState.Idle ->{

                    }
                    is LoginViewState.Loading -> {
                        uiController.showToast(R.color.light_purple, R.color.white, R.color.light_red, "Logging in please wait...")
                    }
                    is LoginViewState.Success -> {
                        it.items.tokens.accessToken.saveInSharedPreference(requireContext(), Constants.ACCESS_TOKEN)
                        binding!!.phone.text.toString().saveInSharedPreference(requireContext(), Constants.PHONE)
                        val bundle = Bundle()
                        AvivNavigator.navigate(binding!!.root, R.id.action_loginFragment_to_homeFragment, bundle )
                    }

                    is LoginViewState.Error ->{
                        uiController.showToast(R.color.red, R.color.white, R.color.light_red, it.error!!)
                    }
                }
            }
        }
    }

    private fun setUpListeners() {
        binding!!.forgotPassword.setOnClickListener {
            AvivNavigator.navigate(it, R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding!!.next.setOnClickListener {
            if (binding!!.password.text.text.toString().isNotEmpty() && binding!!.phone.text.isNotEmpty()){
                lifecycleScope.launch {
                    viewModel.intent.send(Intent.Login(resources.getString(R.string.api_key), LoginDTO("0${binding!!.phone.text.toString()}", binding!!.password.text.text.toString())))
                }
            }
            else{
                uiController.showToast(R.color.red, R.color.white, R.color.white, "Please enter the required fields")
            }
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
            uiController.statusBarColor(R.color.white, false)
        } else {
            throw (ClassCastException (
                requireContext().toString()
                        + "Must imaplement UIController in activity"
            ))
        }
    }
}