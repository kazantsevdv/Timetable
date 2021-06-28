package com.example.timetable.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.databinding.ItemClassesAddBinding
import com.example.timetable.databinding.ItemClassesBinding
import com.example.timetable.repo.image.IImageLoader
import com.example.timetable.ui.model.DataItemClasses

class AdapterClassesList(
    private var onListItemClickListener: OnListItemClickListener,
    private var imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_VIEW_TYPE_ITEM = 1
    private val ITEM_VIEW_TYPE_ITEM_ADD = 2

    private lateinit var bindingItem: ItemClassesBinding
    private lateinit var bindingItemAdd: ItemClassesAddBinding
    private var data: MutableList<DataItemClasses> = mutableListOf()

    fun updateData(newData: List<DataItemClasses>) {

        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            ITEM_VIEW_TYPE_ITEM -> {
                bindingItem =
                    ItemClassesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ItemViewHolder(bindingItem)
            }
            ITEM_VIEW_TYPE_ITEM_ADD -> {
                bindingItemAdd =
                    ItemClassesAddBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ItemAddViewHolder(bindingItemAdd)
            }

            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(data[position])
            is ItemAddViewHolder -> holder.bind(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is DataItemClasses.Classes -> ITEM_VIEW_TYPE_ITEM
            is DataItemClasses.ClassesAdd -> ITEM_VIEW_TYPE_ITEM_ADD
            else -> 0
        }
    }


    inner class ItemViewHolder(private val binding: ItemClassesBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {
        fun bind(data: DataItemClasses) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                if (data is DataItemClasses.Classes) {
                    binding.tvClasses.text = data.theme
                    binding.tvTime.text = "${data.timeStart}-${data.timeEnd}"
                    binding.grOpenin.isVisible = data.openIn
                    if (data.openIn)
                        binding.cardView.setOnClickListener { onListItemClickListener.onItemClick() }

                    imageLoader.loadInto(data.img, binding.imageView)
                }
            }
        }
    }

    inner class ItemAddViewHolder(private val binding: ItemClassesAddBinding) :
        RecyclerView.ViewHolder(bindingItemAdd.root) {
        fun bind(data: DataItemClasses) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                if (data is DataItemClasses.ClassesAdd) {

                    binding.tvClasses.text = data.theme
                    binding.tvTime.text = "${data.timeStart}-${data.timeEnd}"
                    binding.tvInfo.text = data.info
                    binding.grOpenin.isVisible = data.openIn
                    if (data.openIn)
                        binding.cardView.setOnClickListener { onListItemClickListener.onItemClick() }
                    imageLoader.loadInto(data.img, binding.imageView)
                }
            }
        }
    }


}

interface OnListItemClickListener {
    fun onItemClick()
}