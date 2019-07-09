package com.fakher.mvi.ui.list

import com.fakher.mvi.data.DataProvider
import com.fakher.mvi.ui.BaseViewModel
import com.fakher.mvi.ui.list.ListContext.Error.AlbumError
import com.fakher.mvi.ui.list.ListContext.ListAction
import com.fakher.mvi.ui.list.ListContext.ListAction.LoadAlbums
import com.fakher.mvi.utils.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListViewModel(private val dataProvider: DataProvider) :
    BaseViewModel<ListAction, ListContext.ListState>(ListContext.ListState()) {

    private val subscription = CompositeDisposable()

    override fun handleAction(action: ListAction) {

        when (action) {
            is LoadAlbums -> loadList()
        }
    }

    private fun loadList() {
        val shouldForceUpdate = currentState().albums.isNullOrEmpty() //We can choose whatever cache mechanism here to decide wither we force network fetch or not.
        subscription += dataProvider.loadAlbums(shouldForceUpdate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateState { state -> state.copy(albums = it) }
            }, {
                updateState { state -> state.copy(error = AlbumError) }
            })
    }
}