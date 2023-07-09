package com.bjanmelvin.products.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bjanmelvin.products.R
import com.bjanmelvin.products.databinding.ActCatalogBinding
import com.bjanmelvin.products.ui.catalog.FragCatalog

class ActCatalog : AppCompatActivity() {
    private lateinit var mBinding: ActCatalogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_catalog)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FragCatalog.newInstance())
                .commitNow()
        }

    }
}