package com.bjanmelvin.products.ui.catalog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjanmelvin.products.MainApplication
import com.bjanmelvin.products.R
import com.bjanmelvin.products.databinding.FragCatalogBinding
import com.bjanmelvin.products.models.Product
import com.bjanmelvin.products.ui.catalog.adapter.RecvAdapter
import kotlinx.coroutines.launch

class FragCatalog : Fragment(R.layout.frag_catalog) {
    private lateinit var mBinding: FragCatalogBinding

    private val mViewModel: FragCatalogViewModel by viewModels()

    private lateinit var mRecvAdapter: RecvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding = FragCatalogBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        observeViews()

        if(mViewModel.productList.value == null)
            initCatalog()

    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    private fun initViews() {
        val list = mBinding.recvProducts
        list.layoutManager = LinearLayoutManager(context)
        mRecvAdapter = RecvAdapter(
            mutableListOf(),
            loadMoreCallback = mOnLoadMoreCallback,
            itemClickListener = mOnItemClickListener
        )
        list.adapter = mRecvAdapter
    }

    private fun observeViews() {
        mViewModel.productList.observe(viewLifecycleOwner, Observer {
            mRecvAdapter.removeLoading()
            mRecvAdapter.updateList(it.toMutableList())
            if ((it.size % MainApplication.CATALOG_DISPLAY_LIMIT) == 0) {
                mRecvAdapter.addLoading()
            }
            mRecvAdapter.notifyDataSetChanged()

        })
    }

    private fun initCatalog() {
        lifecycleScope.launch {
            mViewModel.loadCatalogData()
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Products"
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    private val mOnLoadMoreCallback: RecvAdapter.LoadMoreCallback =
        object : RecvAdapter.LoadMoreCallback {
            override fun onLoadMore(size: Int) {
                lifecycleScope.launch {
                    mViewModel.loadmoreCatalogData(size)
                }
            }
        }

    private val mOnItemClickListener: RecvAdapter.ItemClickListener =
        object : RecvAdapter.ItemClickListener {
            override fun onItemClick(product: Product) {
//            findNavController().navigate()
                findNavController().navigate(
                    FragCatalogDirections.actionFragCatalogToFragDetails(
                        product
                    )
                )
            }

        }

}