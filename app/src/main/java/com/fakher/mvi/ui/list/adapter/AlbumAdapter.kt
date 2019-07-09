package com.fakher.mvi.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fakher.mvi.R
import com.fakher.mvi.data.models.Album
import kotlinx.android.synthetic.main.item_album_view.view.*

class AlbumAdapter(private val callBack: AlbumViewHolder.CallBack) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private val data = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_album_view, parent, false)
        return AlbumViewHolder(view, callBack)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onBindViewHolder(
        holder: AlbumViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(data[position])
    }

    fun update(albumPhotoData: List<Album>) {
        data.clear()
        data.addAll(albumPhotoData)
        notifyDataSetChanged()
    }

    class AlbumViewHolder(itemView: View, val callBack: CallBack) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(album: Album) {

            Glide
                .with(itemView)
                .load(album.albumPhotos[0].thumbnailUrl)
                .centerCrop()
                .into(itemView.coverImageHolder)
            itemView.titleTextView.text = album.title

            itemView.setOnClickListener { callBack.onAlbumClicked(album) }
        }

        interface CallBack {
            fun onAlbumClicked(album: Album)
        }
    }
}