package com.rsoumail.upskilling.data.remote

import com.rsoumail.upskilling.common.Resource

class RemoteDataSource(private val trackService: TrackService) {

    suspend fun search(term: String) : Resource<TrackSearchResponse> {
        val response : Resource<TrackSearchResponse> = Resource.loading(null)
        when(val apiResponse = trackService.search(term)){
            is ApiSuccessResponse -> {
                return Resource.success(apiResponse.body)
            }
            is ApiErrorResponse -> {
                return Resource.error(apiResponse.errorMessage, null)
            }
        }
        return response
    }
}