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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.arasaka.oishii.R
import com.arasaka.oishii.core.extension.failure
import com.arasaka.oishii.core.extension.observe
import com.arasaka.oishii.core.utils.LayoutType
import com.arasaka.oishii.databinding.MealFragmentBinding
import com.arasaka.oishii.databinding.RowMealBinding
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.presentation.BaseFragment
import com.arasaka.oishii.presentation.BaseViewState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@WithFragmentBindings
@AndroidEntryPoint
class MealFragment : BaseFragment(R.layout.meal_fragment) {

    private lateinit var binding: MealFragmentBinding
    private lateinit var bindingRow: RowMealBinding
    private val adapter: MealAdapter by lazy {MealAdapter()}
    private var gridLayoutManager: GridLayoutManager?=null


    private val mealViewModel by viewModels<MealViewModel>(); //view model injection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mealViewModel.apply {
            observe(state, ::onViewStateChanged)//Observe when livedata is modified
            failure(failure, ::handleFailure)

            doGetMealsByName("")
        }

        val inflatedView : View = layoutInflater.inflate(R.layout.row_meal, null);
        val imgLike: LottieAnimationView = inflatedView.findViewById(R.id.imgLike);


        var isLike = false
        imgLike.setOnClickListener{
            isLike = likeAnimation(imgLike, R.raw.like_burst, isLike)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val searchInput: SearchView = requireView().findViewById(R.id.svCocktail)


        val btnChangeView: FloatingActionButton =
            requireView().findViewById(R.id.floatingViewChange)

        val rv: RecyclerView = requireView().findViewById(R.id.rcMeals)
        refreshMeals()
        adapter.changeView(LayoutType.LINEAR);
        mealViewModel.doGetMealsByName("")


    }

    private fun refreshMeals(){
        val sw : SwipeRefreshLayout = requireView().findViewById(R.id.swRefresh)
        sw.setOnRefreshListener {
            mealViewModel.doGetMealsByName("")
            sw.isRefreshing = false;
        }

    }

    private fun likeAnimation(imageLike: LottieAnimationView, animation: Int, isLike:Boolean):Boolean{

        if(!isLike){
            imageLike.setAnimation(animation)
            imageLike.playAnimation()
        }else{
            imageLike.setImageResource(R.drawable.ic_like)

        }

        return !isLike
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
            adapter = this@MealFragment.adapter
        }



    }


    override fun setBinding(view: View) {
        binding = MealFragmentBinding.bind(view)


        //val imgLike: LottieAnimationView = requireView().findViewById(R.id.imgLike)
        binding.lifecycleOwner = this
        binding.svMeal.setBackgroundResource(R.drawable.bg_search);
        binding.svMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            //ENTER BUTTON IN KEYBOARD (submit search)
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    binding.rcMeals.scrollToPosition(0)
                    mealViewModel.doGetMealsByName(query.lowercase()?:"")
                    //searchInput.clearFocus() ->Hide keyboard at type key...
                }
                return true
            }
        })

        binding.floatingViewChange.setOnClickListener{
            val newLayout = if(adapter.layoutType == LayoutType.LINEAR){
                binding.rcMeals.layoutManager = GridLayoutManager(requireContext(),2);
                LayoutType.GRID
               // binding.floatingViewChange.setImageResource(R.drawable.ic_row);

            }else{
                binding.rcMeals.layoutManager = LinearLayoutManager(requireContext());
                LayoutType.LINEAR
                //binding.floatingViewChange.setImageResource(R.drawable.ic_grid);
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