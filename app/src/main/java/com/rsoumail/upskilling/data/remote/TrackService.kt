package com.rsoumail.upskilling.data.remote

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackService{

    /**
     * [Itunes tracks](https://itunes.apple.com/search?term=pink+floyd)
     *
     * Get the tracks related to the term.
     *
     * @param [term] Specify the term related to tracks.
     *
     * @return [TrackSearchResult] response
     */
    @GET("/search")
    fun search(@Query("term") term : String?) : LiveData<ApiResponse<TrackSearchResponse>>
}
