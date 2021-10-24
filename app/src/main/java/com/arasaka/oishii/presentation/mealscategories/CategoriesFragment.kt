package com.arasaka.oishii.presentation.mealscategories

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
import com.arasaka.oishii.databinding.CategoriesFragmentBinding
import com.arasaka.oishii.databinding.MealFragmentBinding
import com.arasaka.oishii.domain.model.Category
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.presentation.BaseFragment
import com.arasaka.oishii.presentation.BaseViewState
import com.arasaka.oishii.presentation.meals.MealAdapter
import com.arasaka.oishii.presentation.meals.MealFragmentDirections
import com.arasaka.oishii.presentation.meals.MealViewModel
import com.arasaka.oishii.presentation.meals.MealViewState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
@WithFragmentBindings
@AndroidEntryPoint
class CategoriesFragment : BaseFragment(R.layout.categories_fragment) {

    private lateinit var binding: CategoriesFragmentBinding
    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }
    private var gridLayoutManager: GridLayoutManager?=null
    private val categoriesViewModel by viewModels<CategoriesViewModel>(); //view model injection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoriesViewModel.apply {
            observe(state, ::onViewStateChanged)//Observe when livedata is modified
            failure(failure, ::handleFailure)

            doGetCategories("")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val searchInput: SearchView = requireView().findViewById(R.id.svCocktail)

        /*val btnChangeView: FloatingActionButton =
            requireView().findViewById(R.id.floatingViewChange)*/

        val rv: RecyclerView = requireView().findViewById(R.id.rcMealsCategories)

    }

    override fun onViewStateChanged(state: BaseViewState?) {
        super.onViewStateChanged(state)
        when (state) {
            is CategoriesViewState.CategoriesReceived -> setUpAdapter(state.categories)
        }
    }

    private fun setUpAdapter(categories: List<Category>) {
        binding.emptyView.isVisible = categories.isEmpty()
        adapter.addData(categories);
        adapter.listener = {
            navController.navigate(CategoriesFragmentDirections.actionCategoriesFragmentToMealFragment2())

        }


        binding.rcMealsCategories.apply {

            isVisible = categories.isNotEmpty()
            adapter = this@CategoriesFragment.adapter
        }

    }


    override fun setBinding(view: View) {
        binding = CategoriesFragmentBinding.bind(view)
        binding.lifecycleOwner = this

        binding.floatingViewChange.setOnClickListener{
            val newLayout = if(adapter.layoutType == LayoutType.LINEAR){
                binding.rcMealsCategories.layoutManager = GridLayoutManager(requireContext(),3);
                LayoutType.GRID

            }else{
                binding.rcMealsCategories.layoutManager = LinearLayoutManager(requireContext());
                LayoutType.LINEAR
            }
            adapter.changeView(newLayout)
        }
    }

}