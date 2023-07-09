package com.bjanmelvin.products.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjanmelvin.products.R
import com.bjanmelvin.products.databinding.FragDetailsBinding
import com.bjanmelvin.products.ui.catalog.FragCatalogArgs
import com.bjanmelvin.products.ui.catalog.adapter.RecvAdapter
import com.bjanmelvin.products.ui.details.adapter.ImageAdapter
import com.bumptech.glide.Glide


class FragDetails : Fragment(R.layout.frag_details) {
    private lateinit var mBinding: FragDetailsBinding

    private val mViewModel: FragDetailsViewModel by viewModels()
    private val mArgs: FragCatalogArgs by navArgs()

    private lateinit var mRecvAdapter: RecvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding = FragDetailsBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    private fun initViews() {
        val product = mArgs.itemProduct
        mBinding.txtvProductName.text = product.title
        mBinding.txtvPrice.text = "P${product.price}"
        mBinding.txtvDesc.text = product.description
        mBinding.txtvCateg.text = product.category
        mBinding.txtvBrand.text = product.brand
        context?.let { Glide.with(it).load(product.thumbnail).into(mBinding.imgvProductImage)}

        mBinding.recvImages.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        val imageAdapter = context?.let { ImageAdapter(it, product.images) }
        mBinding.recvImages.setAdapter(imageAdapter)

    }

    private fun initToolbar(){
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Product Details"
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}