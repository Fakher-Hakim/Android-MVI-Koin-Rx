package com.fakher.mvi.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class Album(var id: Int, var title: String, var albumPhotos: @RawValue MutableList<AlbumPhoto>) :
    Parcelable {

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (other is Album) {
            return id == other.id
        }
        return false
    }
}