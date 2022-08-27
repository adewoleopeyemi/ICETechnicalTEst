package com.iceapk.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iceapk.databinding.LayoutItemProductBinding
import com.iceapk.data.dao.entities.Product


class ProductsViewHolder(private val context: Context, private val binding: LayoutItemProductBinding, private val listener: EventsListener) :
    RecyclerView.ViewHolder(binding.root) {

     fun bind(item: Product) {
         binding.textView15.text = "₦${item.id}"
         binding.description.text = "${item.quantity}"
         binding.amount.text = "${item.transaction_date}"
         if (item.transaction_amount.toString().startsWith("-")){
             binding.amount.setTextColor(R.color.red)
         }

         binding.root.setOnClickListener {
             listener.onClicked(item)
         }
    }


    companion object {
        fun create(parent: ViewGroup, listener: EventsListener): ProductsViewHolder {
            val ItemBinding =
                LayoutItemTransactionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ProductsViewHolder(parent.context, ItemBinding, listener)
        }
    }

    interface EventsListener{
        fun onClicked(agent: Transaction)
    }
}