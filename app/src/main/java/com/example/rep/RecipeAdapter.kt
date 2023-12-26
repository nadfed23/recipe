package com.example.rep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rep.data.Recipe
import com.example.rep.databinding.ItemRepBinding

class RecipeAdapter (private val listener: OnItemClickListener) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DIFF){

    interface OnItemClickListener {
        fun onClickedItem(recipe: Recipe)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(ItemRepBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), listener)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RecipeViewHolder(private val binding: ItemRepBinding, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private lateinit var recipe: Recipe

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(model: Recipe) {
            this.recipe = model
            binding.apply {

                title.text = model.title
                servings.text = model.servings

            }
        }

        override fun onClick(p0: View?) {
            listener.onClickedItem(recipe)
        }
    }


}

