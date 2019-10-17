package com.rsoumail.upskilling.ui.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsoumail.upskilling.R
import com.rsoumail.upskilling.common.Status
import com.rsoumail.upskilling.domain.entity.Track
import com.rsoumail.upskilling.ui.BaseFragment
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackSearchFragment : BaseFragment() {

    private val trackSearchViewModel: TrackSearchViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun initUI() {
        initButtonListeners()
    }

    override fun initObserver() {

        trackSearchViewModel.results.observe(this, Observer {
            if (it.status == Status.SUCCESS && it.data != null){
                val trackListener = { track : Track -> trackItemClicked(track) }
                    track_list.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = TrackListAdapter(it.data.tracks, context, trackListener)
                    }
            }
        })

    }

    private fun initButtonListeners() {
        searchBtn.setOnClickListener{
            doSearch()
        }
    }

    private fun doSearch() {
        val query = input.text.toString()
        if(query.isNotEmpty()) {
            trackSearchViewModel.setQuery(query)
        }
    }

    private fun trackItemClicked(track: Track) {
        findNavController().navigate(TrackSearchFragmentDirections.showTrack(track.trackName, track.artistName, track.artworkUrl100))
    }
}