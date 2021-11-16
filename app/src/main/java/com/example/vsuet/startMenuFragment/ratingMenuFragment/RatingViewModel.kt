package com.example.vsuet.startMenuFragment.ratingMenuFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.*
import com.squareup.moshi.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingViewModel : ViewModel() {

    val _rating = MutableLiveData<List<RatingItem>>()

    fun getRating(recordNumBook: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
               RatingApi.retrofitService.getProperties(recordNumBook).enqueue(
                    object : Callback<RatingProperty> {
                        override fun onResponse(
                            call: Call<RatingProperty>,
                            response: Response<RatingProperty>
                        ) {
                            _rating.value = response.body()?.data?.rating
                            println(_rating.value?.get(0))
                        }

                        override fun onFailure(call: Call<RatingProperty>, t: Throwable) {
                            println("?!")
                            println(t.message)
                        }

                    }
                )
            }
        }
    }


}