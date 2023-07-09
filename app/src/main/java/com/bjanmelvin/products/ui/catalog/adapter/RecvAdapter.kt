package com.bjanmelvin.products.ui.catalog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bjanmelvin.products.MainApplication.Companion.CATALOG_DISPLAY_LIMIT
import com.bjanmelvin.products.R
import com.bjanmelvin.products.models.Product
import com.bumptech.glide.Glide
import java.lang.Exception

class RecvAdapter(
    private var products: MutableList<Product>,
    private val loadMoreCallback: LoadMoreCallback,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_ITEM = 0
    private val VIEW_LOADING = 1

    private var isLoading = false

    private val mMasterList: MutableList<Item> = mutableListOf()

    init {
        updateList(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
                ProductViewHolder(view)
            }
            VIEW_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw Exception("Invalid Layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            val product = mMasterList[position].product
            product?.let { holder.bind(it) }
        }
        else if (holder is LoadingViewHolder) {
            holder.showLoading()
            loadMoreCallback.onLoadMore(mMasterList.size)
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) mMasterList.size + 1 else mMasterList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mMasterList.size && isLoading) {
            VIEW_LOADING
        } else {
            VIEW_ITEM
        }
    }

    fun updateList(list: MutableList<Product>){
        list.forEach {
            mMasterList.add(Item(it))
        }
    }

    fun addLoading() {
        isLoading = true
        mMasterList.add(Item(null))
        notifyItemInserted(mMasterList.size - 1)
    }

    fun removeLoading() {
        isLoading = false
        val position = mMasterList.size - 1
        if (position >= 0) {
            mMasterList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgv_thumb: ImageView = itemView.findViewById(R.id.imgv_thumb)
        private val txtv_prod_name: TextView = itemView.findViewById(R.id.txtv_prod_name)
        private val txtv_prod_desc: TextView = itemView.findViewById(R.id.txtv_prod_desc)
        private val txtv_prod_price: TextView = itemView.findViewById(R.id.txtv_prod_price)

        fun bind(product: Product) {
            Glide.with(itemView.context).load(product.thumbnail).into(imgv_thumb)
            txtv_prod_name.text = product.title
            txtv_prod_desc.text = product.description
            txtv_prod_price.text = "P${product.price}"
            itemView.setOnClickListener {
                itemClickListener.onItemClick(product)
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val loadingProgressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        fun showLoading() {
            loadingProgressBar.visibility = View.VISIBLE
        }
    }

    interface LoadMoreCallback {
        fun onLoadMore(size: Int)
    }

    interface ItemClickListener {
        fun onItemClick(product: Product)
    }

    inner class Item(var product:Product? = null)
}