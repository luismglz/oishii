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


    private var list: MutableList<Category> = mutableListOf()
    var layoutType = LayoutType.LINEAR

    lateinit var listener: (category: Category) -> Unit

    fun addData(list: List<Category>) {
        this.list = list.toMutableList()
        notifyDataSetChanged();
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



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as BaseViewHolder).bind(
            list[position],listener
        )


    override fun getItemCount() = list.size
}

class ViewHolderItem(private val binding: RowCategoriesBinding) :
    BaseViewHolder(binding.root){
    override fun bind(data: Category, listener: (category: Category) -> Unit){
        binding.item = data
        binding.root.setOnClickListener {
            listener(data)
        }
    }
}

class ViewHolderGridItem(private val binding: GridCategoriesBinding) :
    BaseViewHolder(binding.root) {
    override fun bind(data: Category, listener: (category: Category) -> Unit){
        binding.root.setOnClickListener {
            listener(data)
        }
    }
}

abstract class BaseViewHolder(private val root: View): RecyclerView.ViewHolder(root){
    abstract fun bind(data: Category, listener: (category: Category) -> Unit)
}
