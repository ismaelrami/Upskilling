package com.rsoumail.upskilling.ui.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsoumail.upskilling.R
import com.rsoumail.upskilling.common.Status
import com.rsoumail.upskilling.ui.BaseFragment
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackSearchFragment : BaseFragment() {

    private val trackViewModel: TrackViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun initUI() {
        initButtonListeners()
    }

    override fun initObserver() {

        trackViewModel.results.observe(this, Observer {
            if (it.status == Status.SUCCESS && it.data != null){
                    track_list.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = TrackListAdapter(it.data!!.tracks, this@TrackSearchFragment)
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
        if(!query.isEmpty()) {
            trackViewModel.setQuery(query)
        }
    }
}