package com.rsoumail.upskilling.ui.track

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rsoumail.upskilling.R
import com.rsoumail.upskilling.domain.entity.Track
import kotlinx.android.synthetic.main.track_item.view.*


/**
 * A RecyclerView adapter for [Track] class.
 */
class TrackListAdapter(private val tracks: List<Track>, private val context: Context, private val clickListener: (Track) -> Unit) : RecyclerView.Adapter<TrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false), context)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track: Track = tracks[position]
        holder.bind(track, clickListener)
    }

    override fun getItemCount(): Int = tracks.size
}

class TrackViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

    fun bind(track: Track, clickListener: (Track) -> Unit) {
        itemView.title.text = track.trackName
        itemView.artist.text = track.artistName
        Glide.with(context)
            .load(track.artworkUrl100)
            .into(itemView.item_track_thumb)
        itemView.setOnClickListener{clickListener(track)}
    }

}