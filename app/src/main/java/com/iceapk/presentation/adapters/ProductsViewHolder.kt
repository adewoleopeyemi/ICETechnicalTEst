package com.iceapk.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iceapk.databinding.LayoutItemProductBinding
import com.iceapk.data.dao.entities.Product
import com.iceapk.utils.loadCircularImage


class ProductsViewHolder(private val context: Context, private val binding: LayoutItemProductBinding, private val listener: EventsListener) :
    RecyclerView.ViewHolder(binding.root) {

     fun bind(item: Product) {
         binding.productImageView.loadCircularImage(item.image)
         binding.productName .text= item.title
         binding.productCategory.text = item.category
         binding.rating.text = "${item.rating.rate}"
         binding.description.text = item.description

         binding.root.setOnClickListener {
             listener.onProductClicked(item)
         }
    }


    companion object {
        fun create(parent: ViewGroup, listener: EventsListener): ProductsViewHolder {
            val ItemBinding =
                LayoutItemProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ProductsViewHolder(parent.context, ItemBinding, listener)
        }
    }

    interface EventsListener{
        fun onProductClicked(product: Product)
        fun addToCartClicked(product: Product)
    }
}