package com.rsoumail.upskilling.ui.track

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
/*import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingComponent */
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rsoumail.upskilling.R
// import com.rsoumail.upskilling.databinding.TrackItemBinding
import com.rsoumail.upskilling.domain.entity.Track


/**
 * A RecyclerView adapter for [Track] class.
 */
class TrackListAdapter(private val tracks: List<Track>, val fragment: TrackSearchFragment) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TrackViewHolder(inflater, parent, fragment)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track: Track = tracks[position]
        holder.bind(track)
    }

    override fun getItemCount(): Int = tracks.size
}


class TrackViewHolder(inflater: LayoutInflater, parent: ViewGroup, val fragment: TrackSearchFragment) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.track_item, parent, false)) {
    private var thumbIV: ImageView? = null
    private var titleTV: TextView? = null
    private var artiistTV: TextView? = null


    init {
        thumbIV = itemView.findViewById(R.id.item_track_thumb)
        titleTV = itemView.findViewById(R.id.title)
        artiistTV = itemView.findViewById(R.id.artist)
    }

    fun bind(track: Track) {
        titleTV?.text = track.trackName
        artiistTV?.text = track.artistName
       Glide.with(fragment)
            .load(track.artworkUrl100)
            .into(thumbIV!!)
    }

}