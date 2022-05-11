package com.example.recyclermasterkotlin

data class Email(
    val user: String,
    val subject: String,
    val preview: String,
    val date: String,
    val stared: Boolean = false,
    val unread: Boolean = false,
    val selected: Boolean = false
)
