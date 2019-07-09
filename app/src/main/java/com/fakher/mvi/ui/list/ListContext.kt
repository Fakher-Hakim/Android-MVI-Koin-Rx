package com.fakher.mvi.ui.list

import com.fakher.mvi.data.models.Album
import com.fakher.mvi.ui.BaseAction
import com.fakher.mvi.ui.BaseState

interface ListContext {

    sealed class ListAction : BaseAction {
        object LoadAlbums : ListAction()
        object ClearError : ListAction()
    }

    sealed class Error {
        object NoError : Error()
        object AlbumError : Error()
    }

    data class ListState(val albums: List<Album> = listOf(), val error: Error = Error.NoError) : BaseState
}