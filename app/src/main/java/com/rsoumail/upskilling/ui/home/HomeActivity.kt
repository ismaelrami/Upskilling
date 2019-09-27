package com.rsoumail.upskilling.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.rsoumail.upskilling.R
import com.rsoumail.upskilling.data.local.AppDb
import com.rsoumail.upskilling.ui.track.TrackSearchFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
       supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container,
                TrackSearchFragment()
            )
            .commit()
    }
}
