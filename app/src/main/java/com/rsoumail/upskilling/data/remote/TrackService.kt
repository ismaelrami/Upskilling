package com.rsoumail.upskilling.data.remote

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
     * @return a live data of TrackSearchResponse as ApiResponse
     */
    @GET("/search")
    suspend fun search(@Query("term") term : String?) : ApiResponse<TrackSearchResponse>
}
