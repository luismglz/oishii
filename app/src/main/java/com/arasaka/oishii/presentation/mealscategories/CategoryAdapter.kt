package com.arasaka.oishii.presentation.mealscategories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arasaka.oishii.core.utils.LayoutType
import com.arasaka.oishii.databinding.GridCategoriesBinding
import com.arasaka.oishii.databinding.GridMealBinding
import com.arasaka.oishii.databinding.RowCategoriesBinding
import com.arasaka.oishii.domain.model.Category

interface Listener{
    fun onClickRow(data: Category)
}

//ViewHolders: Fill the View elements in UI
@SuppressLint("NotifyDataSetChanged")
class CategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //Cocktails list initializes empty
    private var list: MutableList<Category> = mutableListOf()
    var layoutType = LayoutType.LINEAR

    lateinit var listener: (category: Category) -> Unit

    fun addData(list: List<Category>) {
        this.list = list.toMutableList()
        notifyDataSetChanged(); //to say that something changes in the list and show in row info
    }

    fun changeView(layoutType: LayoutType) {
        this.layoutType = layoutType
        notifyDataSetChanged()
    }



    override fun getItemViewType(position: Int) = layoutType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        LayoutType.LINEAR.ordinal -> ViewHolderItem(
            RowCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        else ->
            ViewHolderGridItem(
                GridCategoriesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }


    //bind information to rows, with respect to datacollection response
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as BaseViewHolder).bind(
            list[position],listener
        )

    //this is to know how many items are in recyclerview
    override fun getItemCount() = list.size
}

class ViewHolderItem(private val binding: RowCategoriesBinding) :
    BaseViewHolder(binding.root){
    //this meal type object will be binded in recycleview
    override fun bind(data: Category, listener: (category: Category) -> Unit){
        // binding.txvName.text
        binding.item = data// 'item' was created in layout row
        binding.root.setOnClickListener {
            listener(data)
        }
    }
}

class ViewHolderGridItem(private val binding: GridCategoriesBinding) :
    BaseViewHolder(binding.root) {
    //this cocktail type object will be binded in recycleview
    override fun bind(data: Category, listener: (category: Category) -> Unit){
        // binding.txvName.text
        binding.item = data// 'item' was created in layout row
        binding.root.setOnClickListener {
            listener(data)
        }
    }
}

abstract class BaseViewHolder(private val root: View): RecyclerView.ViewHolder(root){
    abstract fun bind(data: Category, listener: (category: Category) -> Unit)
}
