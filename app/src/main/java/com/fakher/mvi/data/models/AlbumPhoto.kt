package com.fakher.mvi.data.models

data class AlbumPhoto(val id: Int,val albumId:Int, val title: String, val thumbnailUrl: String, val url: String) {

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (other is AlbumPhoto) {
            return id == other.id
        }
        return false
    }
}