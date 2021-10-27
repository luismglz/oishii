package com.arasaka.oishii.presentation.mealsrandom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arasaka.oishii.R
import com.arasaka.oishii.core.extension.failure
import com.arasaka.oishii.core.extension.observe
import com.arasaka.oishii.core.utils.LayoutType
import com.arasaka.oishii.databinding.MealFragmentBinding
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.presentation.BaseFragment
import com.arasaka.oishii.presentation.BaseViewState
import com.arasaka.oishii.presentation.meals.MealAdapter
import com.arasaka.oishii.presentation.meals.MealFragmentDirections
import com.arasaka.oishii.presentation.meals.MealViewState
import com.arasaka.oishii.presentation.mealscategories.CategoriesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RandomFragment :  BaseFragment(R.layout.meals_random_fragment) {

    private lateinit var binding: MealFragmentBinding
    private val adapter: MealAdapter by lazy { MealAdapter() }
    private var gridLayoutManager: GridLayoutManager?=null
    private val randomViewModel by viewModels<RandomViewModel>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        randomViewModel.apply {
            observe(state, ::onViewStateChanged)
            failure(failure, ::handleFailure)
            getMealsRandom()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnChangeView: FloatingActionButton =
            requireView().findViewById(R.id.floatingViewChange)

        val rv: RecyclerView = requireView().findViewById(R.id.rcMeals)

    }

    override fun onViewStateChanged(state: BaseViewState?) {
        super.onViewStateChanged(state)
        when (state) {
            is MealViewState.MealsReceived -> setUpAdapter(state.meals)
        }
    }

    private fun setUpAdapter(meals: List<Meal>) {
        binding.emptyView.isVisible = meals.isEmpty()
        adapter.addData(meals);
        adapter.listener = {
            navController.navigate(MealFragmentDirections.actionMealFragmentToMealDetailFragment(it))

        }


        binding.rcMeals.apply {

            isVisible = meals.isNotEmpty()
            adapter = this@RandomFragment.adapter
        }

    }


    override fun setBinding(view: View) {
        binding = MealFragmentBinding.bind(view)
        binding.lifecycleOwner = this
        binding.svMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //ENTER BUTTON IN KEYBOARD (submit search)
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    binding.rcMeals.scrollToPosition(0)
                    randomViewModel.getMealsRandom()
                    //searchInput.clearFocus() ->Hide keyboard at type key...
                }
                return true
            }
        })

        binding.floatingViewChange.setOnClickListener{
            val newLayout = if(adapter.layoutType == LayoutType.LINEAR){
                binding.rcMeals.layoutManager = GridLayoutManager(requireContext(),3);
                LayoutType.GRID

            }else{
                binding.rcMeals.layoutManager = LinearLayoutManager(requireContext());
                LayoutType.LINEAR
            }
            adapter.changeView(newLayout)
        }
    }

}