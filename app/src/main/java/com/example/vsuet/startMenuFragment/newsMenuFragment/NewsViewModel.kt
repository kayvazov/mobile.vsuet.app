package com.example.vsuet.startMenuFragment.newsMenuFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vsuet.wallApiService.WallApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response


class NewsViewModel : ViewModel() {

    var _response = MutableLiveData("")

    private fun convertResponse(response: String?): String {
        val itemsStart = response?.substring(response.indexOf("[") + 1)
        return itemsStart!!
    }

    val itemsList = MutableLiveData<List<NewsItem>>()

    private suspend fun getRawVsuetNews(){
        val itemsContainer = mutableListOf<NewsItem>()
        withContext(Dispatchers.IO) {
            val newsWebsite = Jsoup.connect("https://vsuet.ru/news").get()
            val newsList = newsWebsite.select("section.s-content").select("div.col-md-4")
            for (newsItem in newsList){
                val previewImage = newsItem.select("div.preview-img").attr("style")
                val previewImageURL = "vsuet.ru" + previewImage.substring(22, previewImage.length - 2)
                val previewTitle = newsItem.select("div.news__title").select("a").text()
                val insideLink = "vsuet.ru" + newsItem.select("div.news__title").select("a").attr("href")
                val doneItem = NewsItem(previewImageURL, previewTitle, insideLink)
                itemsContainer.add(doneItem)
            }
        }
        itemsList.value = itemsContainer
    }

    fun getVsuetNews(){
        viewModelScope.launch {
            getRawVsuetNews()
        }
    }

    fun getWallPosts() {
        WallApi.retrofitService.getProperties().enqueue(
            object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _response.value = convertResponse(response.body())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "oh shit im sorry"
                }

            }
        )
    }

}