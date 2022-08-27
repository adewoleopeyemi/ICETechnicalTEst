package com.iceapk.presentation.cart

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iceapk.R
import com.example.iceapk.databinding.FragmentCartBinding
import com.example.iceapk.databinding.FragmentHomeBinding
import com.iceapk.data.dao.entities.Cart
import com.iceapk.data.dao.entities.Product
import com.iceapk.presentation.adapters.ProductsAdapter
import com.iceapk.presentation.adapters.ProductsViewHolder
import com.iceapk.presentation.cart.intent.CartIntent
import com.iceapk.presentation.cart.viewstate.CartViewState
import com.iceapk.presentation.home.HomeViewModel
import com.iceapk.presentation.home.intent.HomeIntent
import com.iceapk.presentation.home.viewstates.HomeViewState
import com.iceapk.utils.UIController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment(), ProductsViewHolder.EventsListener {
    lateinit var uiController: UIController
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<CartViewModel>()
    lateinit var productsAdapter: ProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        setUpView()
        setUpObservers()
        setUpListeners()
        uiController.statusBarColor(R.color.white, false)
        return binding!!.root
    }

    private fun setUpListeners() {

        binding!!.swipeToRefresh.setOnRefreshListener {
            lifecycleScope.launchWhenCreated {
                viewModel.intent.send(CartIntent.getProductsFromCart)
            }
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewModel.viewState.collect {
                when (it) {
                    is CartViewState.Idle -> {}
                    is CartViewState.Loading -> {
                        binding!!.progressBar.visibility = View.VISIBLE
                    }
                    is CartViewState.Success -> {
                        binding!!.progressBar.visibility = View.GONE
                        productsAdapter.submitList(it.product)
                        binding!!.swipeToRefresh.isRefreshing = false
                    }
                    is CartViewState.Error -> {
                        uiController.showToast(
                            R.color.red,
                            R.color.white,
                            R.color.white,
                            "Oops couldn't fetch product. Please try again"
                        )
                        binding!!.progressBar.visibility = View.GONE
                        binding!!.swipeToRefresh.isRefreshing = false
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            viewModel.intent.send(CartIntent.getProductsFromCart)
        }
        super.onCreate(savedInstanceState)
    }

    private fun setUpView() {
        binding!!.toolbar.textView16.text = "Cart"
        productsAdapter = ProductsAdapter(this)
        binding!!.productsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
        }


    }

    override fun onResume() {
        uiController.statusBarColor(R.color.white, false)
        uiController.hideBottomNav(false)
        super.onResume()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is UIController) {
            uiController = context
            uiController.statusBarColor(R.color.white, false)
        } else {
            throw (ClassCastException(
                requireContext().toString()
                        + "Must imaplement UIController in activity"
            ))
        }
    }

    override fun onProductClicked(product: Product) {
    }

    override fun addToCartClicked(product: Product) {}
}