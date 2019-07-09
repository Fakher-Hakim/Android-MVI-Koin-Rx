package com.fakher.mvi.ui.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakher.mvi.R
import com.fakher.mvi.data.models.Album
import com.fakher.mvi.ui.bind
import com.fakher.mvi.ui.list.ListContext.Error
import com.fakher.mvi.ui.list.ListContext.Error.AlbumError
import com.fakher.mvi.ui.list.ListContext.ListAction.ClearError
import com.fakher.mvi.ui.list.ListContext.ListAction.LoadAlbums
import com.fakher.mvi.ui.list.adapter.AlbumAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_list_photo.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity(), AlbumAdapter.AlbumViewHolder.CallBack {

    private val viewModel: ListViewModel by viewModel()
    private val adapter by lazy { AlbumAdapter(this) }
    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_photo)

        photosRecyclerView.layoutManager = LinearLayoutManager(this)
        photosRecyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.handleAction(LoadAlbums)

        viewModel.state.map { it.albums }.distinctUntilChanged().bind(subscriptions, this::onAlbumsLoaded)

        viewModel.state.map { it.error }.distinctUntilChanged().bind(subscriptions, this::onErrorChanged)
    }

    private fun onErrorChanged(error: Error) {
        when (error) {
            is AlbumError -> {
                Toast.makeText(this, "Error while fetching albums!", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.handleAction(ClearError)
    }

    private fun onAlbumsLoaded(albumPhotoData: List<Album>) {
        adapter.update(albumPhotoData)
    }

    override fun onAlbumClicked(album: Album) {
        Toast.makeText(this, album.title, Toast.LENGTH_LONG).show()
    }
}