package com.example.vsuet.startMenuFragment.ratingMenuFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.API.RatingApi
import com.example.vsuet.API.RatingItem
import com.example.vsuet.API.RatingProperty
import com.example.vsuet.roomDataBase.repository.RepositoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingViewModel(application: Application, private val repositoryDataSource: RepositoryDao) :
    AndroidViewModel(application) {

    val rating = MutableLiveData<List<RatingItem>>()

    fun getRating(recordNumBook: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                RatingApi.retrofitService.getProperties(recordNumBook).enqueue(
                    object : Callback<RatingProperty> {
                        override fun onResponse(
                            call: Call<RatingProperty>,
                            response: Response<RatingProperty>
                        ) {
                            rating.value = response.body()?.data?.rating
                            if(response.body()?.data?.rating != null) {
                                insertData(response.body()?.data?.rating!!)
                            }
                        }
                        override fun onFailure(call: Call<RatingProperty>, t: Throwable) {
                            println(t.message)
                            getData()
                        }
                    }
                )
            }
        }
    }

    fun getData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                rating.postValue(repositoryDataSource.getRating())
            }
        }
    }

    fun insertData(data: List<RatingItem>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repositoryDataSource.insertRating(data)
            }
        }
    }


}