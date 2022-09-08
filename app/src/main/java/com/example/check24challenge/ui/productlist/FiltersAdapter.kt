package com.example.check24challenge.ui.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.check24challenge.R
import com.example.check24challenge.databinding.FilterListItemBinding
import com.example.check24challenge.model.FilterItemModel

class FiltersAdapter : ListAdapter<FilterItemModel, FiltersAdapter.ItemHolder>(DIFF_CALLBACK) {
  private var listener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
    val binding: FilterListItemBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      R.layout.filter_list_item,
      parent,
      false)
    return ItemHolder(binding)
  }

  override fun submitList(list: List<FilterItemModel>?) {
    super.submitList(if (list != null) ArrayList(list) else null)
  }

  override fun onBindViewHolder(holder: ItemHolder, position: Int) {
    holder.binding.filterItem.text = getItem(position).title
    if (getItem(position).isSelected){
      holder.binding.filterItem.setBackgroundResource(R.drawable.bg_light_selected)
    }else{
      holder.binding.filterItem.setBackgroundResource(R.drawable.bg_light_not_selected)
    }
  }

  fun setOnItemClickListener(listener: OnItemClickListener) {
    this.listener = listener
  }

  interface OnItemClickListener {
    fun onItemClick(item: FilterItemModel, position: Int)
  }

  inner class ItemHolder(val binding: FilterListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
      itemView.setOnClickListener {
        val position = bindingAdapterPosition
        if (listener != null && position != RecyclerView.NO_POSITION) {
          val item = getItem(position)
          listener!!.onItemClick(item, position)
        }
      }
    }
  }

  companion object {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FilterItemModel>() {
      override fun areItemsTheSame(oldItem: FilterItemModel, newItem: FilterItemModel): Boolean {
        return newItem == oldItem
      }

      override fun areContentsTheSame(oldItem: FilterItemModel, newItem: FilterItemModel): Boolean {
        return newItem.isSelected == oldItem.isSelected
      }
    }
  }
}
