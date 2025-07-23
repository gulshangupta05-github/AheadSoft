package com.example.abhedwebsoft.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.abhedwebsoft.R
import com.example.abhedwebsoft.databinding.ActivityMainBinding
import com.example.abhedwebsoft.ui.adapter.MenuAdapter
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.abhedwebsoft.ui.viewmodel.NavigationViewModel
import com.example.abhedwebsoft.utils.Resource

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel: NavigationViewModel by viewModels()
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val headerView = binding.navView.getHeaderView(0)
        val headerImageView = headerView.findViewById<AppCompatImageView>(R.id.profileImageView)
        val headerNameTextView = headerView.findViewById<AppCompatTextView>(R.id.usernameTextView)

        menuAdapter = MenuAdapter()

        viewModel.getMenus()
        viewModel.menusLiveData.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    state.data?.let {
                        val result = it.result
                        headerNameTextView.text = result.title
                        Glide.with(this)
                            .load(result.user_photo)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(headerImageView) // fixed variable name

                        menuAdapter.submitList(result.menus)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        menuAdapter = MenuAdapter()
        val recyclerView = binding.navView.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.drawerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = menuAdapter

        binding.ivDrawerMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        onBackPressedDispatcher.addCallback(this) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                finish()
            }
        }
    }
}