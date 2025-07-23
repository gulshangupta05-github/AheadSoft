package com.example.abhedwebsoft.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abhedwebsoft.R
import com.example.abhedwebsoft.data.model.MenuItem
import com.example.abhedwebsoft.databinding.MainItemMenuBinding


class MenuGridAdapter :
    ListAdapter<MenuItem, MenuGridAdapter.MainMenuViewHolder>(MenuDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        val binding =
            MainItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MainMenuViewHolder(private val binding: MainItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: MenuItem) {

            binding.tvLabel.text = menuItem.label
            if (menuItem.type == 0) {
                binding.tvLabel.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Headline)
                binding.ivIcon.visibility = View.GONE
            } else {
                binding.tvLabel.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1)
                if (menuItem.icon.isNotEmpty()) {
                    Glide.with(binding.root.context)
                        .load(menuItem.icon)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(binding.ivIcon)
                    binding.ivIcon.visibility = View.VISIBLE
                } else {
                    binding.ivIcon.setImageResource(R.drawable.ic_launcher_background)
                    binding.ivIcon.visibility = View.VISIBLE
                }
            }
        }
    }

    class MenuDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem.label == newItem.label
        }

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }
    }
}