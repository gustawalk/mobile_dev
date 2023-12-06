package com.example.doasans

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class user_response(
    @SerialName("auth") val auth: Boolean,
    @SerialName("username") val username: String,
    @SerialName("pontos") val pontos: Int
)