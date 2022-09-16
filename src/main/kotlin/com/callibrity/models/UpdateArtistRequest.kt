package com.callibrity.models

@kotlinx.serialization.Serializable
data class UpdateArtistRequest(val name: String, val genre: String)