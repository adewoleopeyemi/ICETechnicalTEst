package com.iceapk.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentHomeBinding
import com.iceapk.data.dao.entities.Product
import com.iceapk.presentation.adapters.ProductsAdapter
import com.iceapk.presentation.adapters.ProductsViewHolder
import com.iceapk.presentation.home.intent.HomeIntent
import com.iceapk.presentation.home.viewstates.HomeViewState
import com.iceapk.utils.UIController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(), ProductsViewHolder.EventsListener{
    lateinit var uiController: UIController
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var productsAdapter: ProductsAdapter

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

    private fun setUpListeners() {
        binding!!.searchCloseBtn.setOnClickListener {
            //Search category
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewModel.viewState.collect{
                when (it){
                    is HomeViewState.Idle->{}
                    is HomeViewState.Loading ->{
                        binding!!.progressBar.visibility = VISIBLE
                    }
                    is HomeViewState.Success ->{
                        productsAdapter.submitList(it.product)
                    }
                    is HomeViewState.Error -> {
                        uiController.showToast(R.color.red, R.color.white, R.color.white, "Oops couldn't fetch product. Please try again")
                        binding!!.progressBar.visibility = GONE
                    }
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
        val arrayAdapter: ArrayAdapter<String?> = object :
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
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.add("Electronics")
        arrayAdapter.add("Jewelery")
        arrayAdapter.add("Men's clothing")
        arrayAdapter.add("Women's clothing")
        arrayAdapter.add("Select a category"); //This is the text that will be displayed as hint.


        binding!!.searchSpinner.adapter = arrayAdapter
        binding!!.searchSpinner.setSelection(arrayAdapter.count)


        productsAdapter = ProductsAdapter(this)
        binding!!.productsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
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

    override fun onProductClicked(product: Product) {

    }

    override fun addToCartClicked(product: Product) {

    }
}