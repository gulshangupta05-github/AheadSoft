package com.example.abhedwebsoft.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhedwebsoft.data.model.UserResponse
import com.example.abhedwebsoft.data.repository.NavigationRepository
import com.example.abhedwebsoft.utils.Resource
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    private val repository = NavigationRepository()

    private val _menusLiveData = MutableLiveData<Resource<UserResponse>>()
    val menusLiveData: LiveData<Resource<UserResponse>> = _menusLiveData

    fun getMenus() {
        viewModelScope.launch {
            _menusLiveData.postValue(Resource.Loading())
            try {
                val response = repository.fetchMenuData()
                if (response.isSuccessful && response.body() != null) {
                    _menusLiveData.postValue(Resource.Success(response.body()!!))
                } else {
                    _menusLiveData.postValue(Resource.Error("API error"))
                }
            } catch (e: Exception) {
                _menusLiveData.postValue(Resource.Error("Error: ${e.localizedMessage}"))
            }
        }
    }
}
