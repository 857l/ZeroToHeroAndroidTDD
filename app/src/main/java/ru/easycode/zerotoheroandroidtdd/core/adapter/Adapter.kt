package ru.easycode.zerotoheroandroidtdd.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ItemLayoutBinding

class Adapter : RecyclerView.Adapter<ItemViewHolder>() {

    private val itemList = mutableListOf<CharSequence>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun update(newList: List<CharSequence>) {
        val diff = DiffUtilCallback(itemList, newList)
        val result = DiffUtil.calculateDiff(diff)
        itemList.clear()
        itemList.addAll(newList)
        result.dispatchUpdatesTo(this)
    }
}

class ItemViewHolder(private val binding: ItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(source: CharSequence) {
        binding.elementTextView.text = source
    }
}

private class DiffUtilCallback(
    private val old: List<CharSequence>,
    private val new: List<CharSequence>
) : DiffUtil.Callback() {

    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]
}