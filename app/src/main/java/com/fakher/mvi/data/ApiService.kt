package com.fakher.mvi.data

import com.fakher.mvi.data.models.Album
import com.fakher.mvi.data.models.AlbumPhoto
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("/photos")
    fun loadPhotos(): Observable<List<AlbumPhoto>>

    @GET("/albums")
    fun loadAlbums(): Observable<List<Album>>
}