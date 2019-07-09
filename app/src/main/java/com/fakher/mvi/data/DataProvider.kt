package com.fakher.mvi.data

import com.fakher.mvi.data.models.Album
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class DataProvider(private val apiService: ApiService) {

    private val albumCache = mutableListOf<Album>()

    fun loadAlbums(forceUpdate: Boolean): Observable<List<Album>> {

        return if (forceUpdate || albumCache.isEmpty()) {
            val photoStream = apiService.loadPhotos()
            val albumStream = apiService.loadAlbums()

            Observable.zip(photoStream, albumStream, BiFunction { photos, albums ->

                for (album in albums) {
                    val albumPhotos = photos.filter { album.id == it.albumId }
                    album.albumPhotos = albumPhotos.toMutableList()
                }
                albumCache.clear()
                albumCache.addAll(albums)
                albums
            })
        } else {
            Observable.just(albumCache)
        }
    }
}