package com.arasaka.oishii.presentation.mealscategories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
   /* companion object {
        fun newInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }

    */

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

        //categoriesViewModel.doGetCategories("")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.setUpNavigation(findNavController());

        val rv: RecyclerView = requireView().findViewById(R.id.rcMealsCategories)
        refreshCategories()
        adapter.changeView(LayoutType.LINEAR);
        categoriesViewModel.doGetCategories("")
    }


    private fun refreshCategories(){
        val sw : SwipeRefreshLayout = requireView().findViewById(R.id.swRefresh)
        sw.setOnRefreshListener {
            categoriesViewModel.doGetCategories("")
            sw.isRefreshing = false;
        }

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
            navController.navigate(CategoriesFragmentDirections.actionCategoriesFragmentToMealCategoryFragment(it))
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
                binding.rcMealsCategories.layoutManager = GridLayoutManager(requireContext(),2);
                LayoutType.GRID

            }else{
                binding.rcMealsCategories.layoutManager = LinearLayoutManager(requireContext());
                LayoutType.LINEAR
            }


            if (adapter.layoutType == LayoutType.LINEAR){
                binding.floatingViewChange.setImageResource(R.drawable.ic_row);
            }else{

                binding.floatingViewChange.setImageResource(R.drawable.ic_grid);
            }

            adapter.changeView(newLayout)
        }


    }

}