package com.iceapk.presentation.Onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentBoardBinding
import com.iceapk.utils.ICENavigator
import com.iceapk.utils.ICENavigator.navigate
import com.iceapk.utils.UIController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BoardFragment
@Inject
constructor(
): Fragment(R.layout.fragment_board) {

    lateinit var uiController: UIController
    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiController.hideBottomNav(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.createAccount.setOnClickListener {
            ICENavigator.navigate(view, R.id.action_boardFragment_to_detailsFragment, null)
        }
        binding!!.loginBtn.setOnClickListener {
            ICENavigator.navigate(it, R.id.action_boardFragment_to_loginFragment2)
        }
    }

    override fun onResume() {
        uiController.statusBarColor(R.color.purple, true)
        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is UIController) {
            uiController = context
            uiController.statusBarColor(R.color.purple, true)
        } else {
            throw (ClassCastException(
                requireContext().toString()
                        + "Must imaplement UIController in activity"
            ))
        }
    }
}