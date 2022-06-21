package com.example.data.remote.model

import com.squareup.moshi.Json

data class TokenResponse(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "token_type")
    val tokenType: String,
    @Json(name = "expires_in")
    val expiresIn: String,
    @Json(name = "scope")
    val scope: String,
    @Json(name = "refresh_token")
    val refreshToken: String
)