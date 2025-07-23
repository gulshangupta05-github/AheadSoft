package com.example.abhedwebsoft.ui.view

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.abhedwebsoft.R
import com.example.abhedwebsoft.data.model.MenuItem
import com.example.abhedwebsoft.databinding.FragmentMainManuBinding
import com.example.abhedwebsoft.ui.adapter.MenuGridAdapter
import com.example.abhedwebsoft.ui.viewmodel.NavigationViewModel
import com.example.abhedwebsoft.utils.Resource

class MainMenuFragment : Fragment() {

    private val viewModel: NavigationViewModel by lazy {
        ViewModelProvider(requireActivity())[NavigationViewModel::class.java]
    }
    private var _binding: FragmentMainManuBinding? = null
    private val binding get() = _binding!!
    private lateinit var initialAdapter: MenuGridAdapter
    private lateinit var appsAdapter: MenuGridAdapter
    private lateinit var helpMoreAdapter: MenuGridAdapter
    private var isSeeMoreEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainManuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialAdapter = MenuGridAdapter()
        val gridLayoutManager = GridLayoutManager(context, 2)
       binding.rvAllContent.layoutManager = gridLayoutManager
        binding.rvAllContent.adapter = initialAdapter

        appsAdapter = MenuGridAdapter()
        binding.rvAppsItems.layoutManager = GridLayoutManager(context, 2)
        binding.rvAppsItems.adapter = appsAdapter

        helpMoreAdapter = MenuGridAdapter()

        binding.rvHelpMoreItems.layoutManager = GridLayoutManager(context, 2)
        binding.rvHelpMoreItems.adapter = helpMoreAdapter

        binding.tvSeeMore.setOnClickListener {
            isSeeMoreEnabled = !isSeeMoreEnabled
            updateSeeMoreState()
        }

        viewModel.menusLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    state.data?.let { response ->
                        val menus = response.result.menus
                        binding.tvMenuUserName.text = response.result.title
                        Glide.with(this)
                            .load(response.result.user_photo)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.appCompatImageView2)
                        updateAdapterList(menus)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE

                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateAdapterList(menus: List<MenuItem>) {
        binding.tvAppsHeader.visibility = View.VISIBLE

        val initialItems = menus.filter { it.label.trim().lowercase() == "messages" || it.label.trim().lowercase() == "notifications" }.take(2)
        initialAdapter.submitList(initialItems)

        val appsItems = menus.filter { it.label.trim().lowercase() in listOf("album", "jobs", "groups", "games") }.take(4)
        appsAdapter.submitList(appsItems)


        val helpMoreItems = menus.filter {
            it.label.trim().lowercase() in listOf("settings", "privacy", "contact us", "terms of service")
        }.take(4)
        helpMoreAdapter.submitList(helpMoreItems)
        binding.tvHelpMoreHeader.visibility = View.VISIBLE
        binding.tvSeeMore.visibility = if (menus.size > 10) View.VISIBLE else View.GONE
        binding.tvSeeMore.text = if (isSeeMoreEnabled) "See Less" else "See More"
        Log.d("MainMenuFragment", "Initial menus: ${menus.map { it.label }}")
        Log.d("MainMenuFragment", "Help & More items: ${helpMoreItems.map { it.label }}")
        Log.d("MainMenuFragment", "Menus size: ${menus.size}")
    }

    private fun updateSeeMoreState() {
        viewModel.menusLiveData.value?.let { state ->
            if (state is Resource.Success) {
                val menus = state.data?.result?.menus?.filter { it.label !in listOf("Rate Us", "Sign Out") } ?: emptyList()
                if (isSeeMoreEnabled) {
                    val remainingIndex = 10
                    val remainingItems = if (remainingIndex < menus.size)
                        menus.subList(remainingIndex, menus.size).filter { it.type == 1 }
                    else
                        emptyList()
                    Log.d("MainMenuFragment", "Remaining index: $remainingIndex, Remaining items: ${remainingItems.map { it.label }}")
                    helpMoreAdapter.submitList(remainingItems)
                    binding.tvHelpMoreHeader.visibility = View.VISIBLE
                } else {
                    binding.tvHelpMoreHeader.visibility = View.VISIBLE
                    val helpMoreItems = menus.filter {
                        it.label.trim().lowercase() in listOf("settings", "privacy", "contact us", "terms of service")
                    }.take(4)
                    helpMoreAdapter.submitList(helpMoreItems)
                }
                binding.tvSeeMore.text = if (isSeeMoreEnabled) "See Less" else "See More"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}