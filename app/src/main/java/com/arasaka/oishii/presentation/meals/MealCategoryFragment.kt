package com.arasaka.oishii.presentation.meals

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arasaka.oishii.R
import com.arasaka.oishii.core.extension.failure
import com.arasaka.oishii.core.extension.observe
import com.arasaka.oishii.core.utils.LayoutType
import com.arasaka.oishii.databinding.MealCategoryFragmentBinding
import com.arasaka.oishii.databinding.MealFragmentBinding
import com.arasaka.oishii.domain.model.Category
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.presentation.BaseFragment
import com.arasaka.oishii.presentation.BaseViewState
import com.arasaka.oishii.presentation.mealdetail.MealDetailFragmentArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@WithFragmentBindings
@AndroidEntryPoint
class MealCategoryFragment : BaseFragment(R.layout.meal_category_fragment) {
    private lateinit var binding: MealCategoryFragmentBinding

    // private val cate: new Category
    private val args: MealCategoryFragmentArgs by navArgs()

    private val adapter: MealAdapter by lazy { MealAdapter() }
    private var gridLayoutManager: GridLayoutManager? = null

    private val mealCategoryViewModel by viewModels<MealCategoryViewModel>(); //view model injection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealCategoryViewModel.apply {
            observe(state, ::onViewStateChanged)//Observe when livedata is modified
            failure(failure, ::handleFailure)

            doGetMealsByCategories(args.category.nameCategory)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnChangeView: FloatingActionButton =
            requireView().findViewById(R.id.floatingViewChange)

        val rv: RecyclerView = requireView().findViewById(R.id.rcMeals)
        refreshMealsByCategory()

        adapter.changeView(LayoutType.LINEAR);
        mealCategoryViewModel.doGetMealsByCategories(args.category.nameCategory)

    }


    private fun refreshMealsByCategory() {
        val sw: SwipeRefreshLayout = requireView().findViewById(R.id.swRefresh)
        sw.setOnRefreshListener {
            mealCategoryViewModel.doGetMealsByCategories(args.category.nameCategory)
            sw.isRefreshing = false;
        }

    }




    override fun onViewStateChanged(state: BaseViewState?) {
        super.onViewStateChanged(state)
        when (state) {
            is MealViewState.MealsReceived -> setUpAdapter(state.meals)
        }
    }

    private fun setUpAdapter(meals: List<Meal>) {
        binding.emptyView.isVisible = meals.isEmpty()
        binding.rcMeals.setHasFixedSize(true)
        adapter.addData(meals);
        adapter.listener = {
            navController.navigate(
                MealCategoryFragmentDirections.actionMealCategoryFragmentToMealDetailFragment(
                    it
                )
            )
        }


        binding.rcMeals.apply {

            isVisible = meals.isNotEmpty()
            refreshMealsByCategory()
            adapter = this@MealCategoryFragment.adapter

        }

    }


    override fun setBinding(view: View) {
        binding = MealCategoryFragmentBinding.bind(view)
        binding.lifecycleOwner = this
        binding.floatingViewChange.setOnClickListener {
            val newLayout = if (adapter.layoutType == LayoutType.LINEAR) {
                binding.rcMeals.layoutManager = GridLayoutManager(requireContext(), 2);
                LayoutType.GRID

            } else {
                binding.rcMeals.layoutManager = LinearLayoutManager(requireContext());
                LayoutType.LINEAR
            }

            if (adapter.layoutType == LayoutType.LINEAR){
                binding.floatingViewChange.setImageResource(R.drawable.ic_row);
            }else{

                binding.floatingViewChange.setImageResource(R.drawable.ic_grid);
            }

            adapter.changeView(newLayout)
        }

        binding.apply {
            lifecycleOwner = this@MealCategoryFragment
            category = args.category
        }
    }

}
