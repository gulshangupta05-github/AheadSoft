package com.example.abhedwebsoft.data.model

data class UserResponse(
    val result: UserResult,
    val session_id: String
)

data class UserResult(
    val title: String,
    val user_photo: String,
    val menus: List<MenuItem>
)

data class MenuItem(
    val type: Int,
    val module: String?,
    val label: String,
    val icon: String,
    val url: String,
    val `class`: String
)
