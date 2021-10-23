package com.arasaka.oishii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.arasaka.oishii.databinding.ActivityMainBinding
import com.arasaka.oishii.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity()  {
    private lateinit var binding: ActivityMainBinding

    override fun layoutId() = R.layout.activity_main;

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)
        setBinding()
    }

    override val fragmentContainer: FragmentContainerView
        get() = binding.fcv


    override fun setUpNavigation(navController: NavController) =
        NavigationUI.setupWithNavController(binding.bnvMain, navController)

    override fun showProgress(show: Boolean) {
        binding.progressView.isVisible = show
    }

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId())
    }

}