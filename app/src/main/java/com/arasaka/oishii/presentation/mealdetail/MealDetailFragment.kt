package com.arasaka.oishii.presentation.mealdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.arasaka.oishii.R
import com.arasaka.oishii.databinding.MealDetailFragmentBinding
import com.arasaka.oishii.presentation.BaseFragment

class MealDetailFragment : BaseFragment(R.layout.meal_detail_fragment) {
    private lateinit var binding:MealDetailFragmentBinding
    private val args: MealDetailFragmentArgs by navArgs()

    override fun setBinding(view: View) {
        binding = MealDetailFragmentBinding.bind(view)
        binding.apply {
            lifecycleOwner = this@MealDetailFragment
            meal = args.meal
        }
    }

}