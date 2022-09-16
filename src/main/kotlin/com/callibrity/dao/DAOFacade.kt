package com.callibrity.dao

import com.callibrity.models.Artist

interface DAOFacade {
    suspend fun allArtists(): List<Artist>
    suspend fun artist(id: String): Artist?
    suspend fun addNewArtist(name: String, genre: String): Artist?
    suspend fun editArtist(id: String, name: String, genre: String): Artist?
    suspend fun deleteArtist(id: String): Boolean
}