package com.callibrity.models

@kotlinx.serialization.Serializable
data class CreateArtistRequest(val name: String, val genre: String)