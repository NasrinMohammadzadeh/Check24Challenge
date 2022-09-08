package com.example.check24challenge.ui.productlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.check24challenge.databinding.AvailableListItemViewBinding
import com.example.check24challenge.databinding.UnavailableListItemViewBinding
import com.example.check24challenge.model.ProductModel
import com.example.check24challenge.system.loadImageWithGlideMediumRadius
import com.example.check24challenge.system.setDate

class ProductListAdapter : ListAdapter<ProductModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
  private var listener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return getItemView(parent = parent, viewType = viewType)
  }

  private fun getItemView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      ItemTypeCode.AVAILABLE.get() -> {
        val binding = AvailableListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        AvailableItemHolder(binding, binding.root)
      }
      else -> {
        val binding = UnavailableListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        UnavailableItemHolder(binding, binding.root)
      }
    }
  }

  override fun submitList(list: List<ProductModel>?) {
    super.submitList(if (list != null) ArrayList(list) else null)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    bindView(getItem(position), holder, position)
  }

  @SuppressLint("SetTextI18n")
  fun bindView(item: ProductModel, holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is AvailableItemHolder -> {
        holder.binding.titleTxt.text = item.name
        holder.binding.descTxt.text = item.description
        holder.binding.priceTxt.text = "${item.price.value}${item.price.currency}"
        holder.binding.itemImage.loadImageWithGlideMediumRadius(item.imageURL)
        holder.binding.dateTxt.setDate(item.releaseDate)
        holder.binding.rating.rating = item.rating.toFloat()
      }
      is UnavailableItemHolder -> {
        holder.binding.titleTxt.text = item.name
        holder.binding.descTxt.text = item.description
        holder.binding.itemImage.loadImageWithGlideMediumRadius(item.imageURL)
      }
    }
  }

  fun setOnItemClickListener(listener: OnItemClickListener) {
    this.listener = listener
  }

  interface OnItemClickListener {
    fun onItemClick(item: ProductModel, position: Int)
  }

  override fun getItemViewType(position: Int): Int {
    val item = getItem(position)
    return if (item.available){
      ItemTypeCode.AVAILABLE.get()
    }else{
      ItemTypeCode.UNAVAILABLE.get()
    }
  }

  inner class AvailableItemHolder(val binding: AvailableListItemViewBinding, val view: View) : RecyclerView.ViewHolder(view) {
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

  inner class UnavailableItemHolder(val binding: UnavailableListItemViewBinding, val view: View) : RecyclerView.ViewHolder(view) {
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

  enum class ItemTypeCode constructor(private val intValue: Int) {
    AVAILABLE(1), UNAVAILABLE(2);

    fun get(): Int {
      return intValue
    }
  }

  companion object {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductModel>() {
      override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
      }
    }
  }
}
