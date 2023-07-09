package com.bjanmelvin.products.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.bjanmelvin.products.R
import com.bjanmelvin.products.databinding.ActCatalogBinding

class ActCatalog : AppCompatActivity() {
    private lateinit var mBinding: ActCatalogBinding
    private lateinit var mNavHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_catalog)

        if (savedInstanceState == null) {
            initNavigation()
        }
    }

    private fun initNavigation() {
        mNavHostFragment = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, mNavHostFragment, "fragment_container")
            .setPrimaryNavigationFragment(mNavHostFragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mNavHostFragment.navController.navigateUp()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}