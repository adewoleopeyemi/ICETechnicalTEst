package com.iceapk.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentHomeBinding
import com.iceapk.presentation.home.intent.HomeIntent
import com.iceapk.presentation.home.viewstates.HomeViewState
import com.iceapk.utils.UIController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var uiController: UIController
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
       setUpView()
        setUpObservers()
        setUpListeners()
        uiController.statusBarColor(R.color.white, false)
        return binding!!.root
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewModel.viewState.collect{
                when (it){
                    is HomeViewState.Idle->{}
                    is HomeViewState.Loading ->{}
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            viewModel.intent.send(HomeIntent.getData)
        }
        super.onCreate(savedInstanceState)
    }

    private fun setUpView() {
        val adapter: ArrayAdapter<String?> = object :
            ArrayAdapter<String?>(activity!!, android.R.layout.simple_spinner_dropdown_item) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    (v.findViewById<View>(android.R.id.text1) as TextView).text = ""
                    (v.findViewById<View>(android.R.id.text1) as TextView).hint = getItem(
                        count
                    ) //"Hint to be displayed"
                }
                return v
            }

            override fun getCount(): Int {
                return super.getCount() - 1 // you dont display last item. It is used as hint.
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Electronics")
        adapter.add("Jewelery")
        adapter.add("Men's clothing")
        adapter.add("Women's clothing")
        adapter.add("Select a category"); //This is the text that will be displayed as hint.


        binding!!.searchSpinner.adapter = adapter;
        binding!!.searchSpinner.setSelection(adapter.count);


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