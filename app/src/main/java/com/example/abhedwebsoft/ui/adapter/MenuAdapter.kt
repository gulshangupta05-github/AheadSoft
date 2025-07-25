package com.example.abhedwebsoft.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abhedwebsoft.R
import com.example.abhedwebsoft.data.model.MenuItem

class MenuAdapter : ListAdapter<MenuItem, MenuAdapter.MenuViewHolder>(MenuDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconView: AppCompatImageView = itemView.findViewById(R.id.menuIcon)
        private val labelView: AppCompatTextView = itemView.findViewById(R.id.menuLabel)

        fun bind(menuItem: MenuItem) {
                labelView.text = menuItem.label
            if (menuItem.icon.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(menuItem.icon)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(iconView)
            } else {
                iconView.setImageResource(R.drawable.ic_launcher_background)
            }
            itemView.setOnClickListener {
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
