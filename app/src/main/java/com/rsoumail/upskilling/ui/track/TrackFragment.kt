package com.rsoumail.upskilling.ui.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rsoumail.upskilling.R
import com.rsoumail.upskilling.ui.BaseFragment
import kotlinx.android.synthetic.main.track_fragment.*

class TrackFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.track_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val params = TrackFragmentArgs.fromBundle(arguments!!)
        track_title.text = params.trackTitle
        track_artits.text = params.trackArstist
        Glide.with(this)
            .load(params.trackThumb)
            .into(track_thumb)
    }

    override fun initUI() {
        backBtn.setOnClickListener{
            findNavController().navigate(R.id.backToSearch)
        }
    }

}