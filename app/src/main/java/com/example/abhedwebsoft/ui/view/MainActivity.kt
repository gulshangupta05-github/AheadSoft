package com.example.abhedwebsoft.ui.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.abhedwebsoft.R
import com.example.abhedwebsoft.databinding.ActivityMainBinding
import com.example.abhedwebsoft.ui.viewmodel.NavigationViewModel
import com.example.abhedwebsoft.utils.Resource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val headerView = binding.navView.getHeaderView(0)
        val headerImageView = headerView.findViewById<AppCompatImageView>(R.id.profileImageView)
        val headerNameTextView = headerView.findViewById<AppCompatTextView>(R.id.usernameTextView)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.ivDrawerMenu.setOnApplyWindowInsetsListener { view, insets ->
                val statusBarHeight = insets.getInsets(WindowInsets.Type.statusBars()).top
                view.updateLayoutParams<androidx.constraintlayout.widget.ConstraintLayout.LayoutParams> {
                    topMargin = statusBarHeight + 20 // Add existing 20dp margin
                }
                insets
            }
        } else {
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight =
                if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
            binding.ivDrawerMenu.updateLayoutParams<androidx.constraintlayout.widget.ConstraintLayout.LayoutParams> {
                topMargin = statusBarHeight + 20 // Add existing 20dp margin
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, MainMenuFragment())
            .commit()


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
                            .into(headerImageView)

                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }



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