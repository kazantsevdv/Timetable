package com.example.timetable.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.databinding.ItemHomeworkBinding
import com.example.timetable.model.Homework
import com.example.timetable.repo.image.IImageLoader

class AdapterHomeworkList(
    private var imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<AdapterHomeworkList.ItemViewHolder>() {

    private lateinit var bindingItem: ItemHomeworkBinding

    private var data: MutableList<Homework> = mutableListOf()

    fun updateData(newData: List<Homework>) {

        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        bindingItem =
            ItemHomeworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data = data[position])
    }


    override fun getItemCount(): Int {
        return data.size
    }


    inner class ItemViewHolder(private val binding: ItemHomeworkBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {
        fun bind(data: Homework) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.tvTime.text = "${data.DateEnd}"
                binding.tvTheme.text = data.theme
                binding.tvHomework.text = data.info
                imageLoader.loadInto(data.img, binding.imageView)

            }
        }
    }


}
