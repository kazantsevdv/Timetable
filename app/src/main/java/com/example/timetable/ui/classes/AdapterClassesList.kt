package com.example.timetable.ui.classes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.databinding.ItemClassesAddPadBinding
import com.example.timetable.databinding.ItemClassesPadBinding
import com.example.timetable.databinding.ItemDelimetrBinding
import com.example.timetable.repo.image.IImageLoader
import com.example.timetable.ui.model.DataItemClasses

class AdapterClassesList(
    private var onListItemClickListener: OnListItemClickListener,
    private var imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1
    private val ITEM_VIEW_TYPE_ITEM_ADD = 2
    private lateinit var bindingHeader: ItemDelimetrBinding
    private lateinit var bindingItem: ItemClassesPadBinding
    private lateinit var bindingItemAdd: ItemClassesAddPadBinding
    private var data: MutableList<DataItemClasses> = mutableListOf()

    fun updateData(newData: List<DataItemClasses>) {

        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                bindingHeader =
                    ItemDelimetrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(bindingHeader)
            }

            ITEM_VIEW_TYPE_ITEM -> {
                bindingItem =
                    ItemClassesPadBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ItemViewHolder(bindingItem)
            }
            ITEM_VIEW_TYPE_ITEM_ADD -> {
                bindingItemAdd =
                    ItemClassesAddPadBinding.inflate(
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
            is HeaderViewHolder -> holder.bind(data[position])
            is ItemViewHolder -> holder.bind(data[position])
            is ItemAddViewHolder -> holder.bind(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is DataItemClasses.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItemClasses.Classes -> ITEM_VIEW_TYPE_ITEM
            is DataItemClasses.ClassesAdd -> ITEM_VIEW_TYPE_ITEM_ADD
        }
    }

    inner class HeaderViewHolder(private val binding: ItemDelimetrBinding) :
        RecyclerView.ViewHolder(bindingHeader.root) {
        fun bind(data: DataItemClasses) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                if (data is DataItemClasses.Header) {
                    binding.tvDate.text = "${data.timeStart}-${data.timeEnd}"
                    binding.ivNotNow.isVisible = data.isActiv != true
                    binding.ivNow.isVisible = data.isActiv == true
                }
            }
        }
    }

    inner class ItemViewHolder(private val binding: ItemClassesPadBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {
        fun bind(data: DataItemClasses) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                if (data is DataItemClasses.Classes) {
                    binding.tvClasses.text = data.theme
                    binding.tvTeacher.text = data.teacher
                    binding.grOpenin.isVisible = data.openIn
                    if (data.openIn)
                        binding.cardView.setOnClickListener { onListItemClickListener.onItemClick() }

                    imageLoader.loadInto(data.img,binding.imageView)
                }
            }
        }
    }

    inner class ItemAddViewHolder(private val binding: ItemClassesAddPadBinding) :
        RecyclerView.ViewHolder(bindingItemAdd.root) {
        fun bind(data: DataItemClasses) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                if (data is DataItemClasses.ClassesAdd) {

                    binding.tvClasses.text = data.theme
                    binding.tvTeacher.text = data.teacher
                    binding.tvInfo.text = data.info
                    binding.grOpenin.isVisible = data.openIn
                    if (data.openIn)
                        binding.cardView.setOnClickListener { onListItemClickListener.onItemClick() }
                    imageLoader.loadInto(data.img,binding.imageView)
                }
            }
        }
    }


}

interface OnListItemClickListener {
    fun onItemClick()
}