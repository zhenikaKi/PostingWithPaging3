package ru.kirea.postingwithpaging3.windows.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.kirea.postingwithpaging3.data.entities.PostData
import ru.kirea.postingwithpaging3.databinding.ItemListBinding

class MainAdapter: PagingDataAdapter<PostData, MainViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<PostData>() {
            override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val post = getItem(position)
        post?.let { holder.setData(it) }
    }
}