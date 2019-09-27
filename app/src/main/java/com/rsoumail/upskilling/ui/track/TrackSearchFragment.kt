package com.rsoumail.upskilling.ui.track

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
//import androidx.databinding.DataBindingComponent
//import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsoumail.upskilling.R
import com.rsoumail.upskilling.common.Status
import com.rsoumail.upskilling.data.local.AppDb
import com.rsoumail.upskilling.data.remote.ApiErrorResponse
import com.rsoumail.upskilling.data.remote.ApiResponse
import com.rsoumail.upskilling.data.remote.ApiSuccessResponse
import com.rsoumail.upskilling.data.remote.TrackSearchResponse
import com.rsoumail.upskilling.data.repository.TrackRepository
import com.rsoumail.upskilling.domain.entity.Track
// import com.rsoumail.upskilling.binding.FragmentDataBindingComponent
import com.rsoumail.upskilling.ui.BaseFragment
import com.rsoumail.upskilling.util.Api
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackSearchFragment : BaseFragment() {

    private val trackViewModel: TrackViewModel by viewModel()

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)

        /* binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_fragment,
            container,
            false,
            dataBindingComponent
        )

        return binding.root */
    }

    override fun initUI() {
        initButtonListeners()
    }

    override fun initObserver() {

        trackViewModel.results.observe(this, Observer {
           /* when(it){
                is ApiSuccessResponse -> {
                    track_list.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = TrackListAdapter(it.body.results,this@TrackSearchFragment)
                    }
                }

                is ApiErrorResponse -> {
                    Log.i("TrackSearchFragment", "Log Error" )
                    Log.i("TrackSearchFragment", "Log " + it.errorMessage )
                }
            }*/
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