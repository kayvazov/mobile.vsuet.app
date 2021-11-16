package com.example.vsuet.startMenuFragment.newsMenuFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.NewsPost
import com.example.vsuet.API.NewsProperty
import com.example.vsuet.API.VkontakteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsViewModel : ViewModel() {


    val itemsList = MutableLiveData<List<NewsItem>>()
    val _response = MutableLiveData<List<NewsPost>>()

    private suspend fun getRawVsuetNews() {
        val itemsContainer = mutableListOf<NewsItem>()
        withContext(Dispatchers.IO) {
            val newsWebsite = Jsoup.connect("https://vsuet.ru/news").get()
            val newsList = newsWebsite.select("section.s-content").select("div.col-md-4")
            for (newsItem in newsList) {
                val previewImage = newsItem.select("div.preview-img").attr("style")
                val previewImageURL =
                    "vsuet.ru" + previewImage.substring(22, previewImage.length - 2)
                val previewTitle = newsItem.select("div.news__title").select("a").text()
                val insideLink =
                    "vsuet.ru" + newsItem.select("div.news__title").select("a").attr("href")
                val doneItem = NewsItem(previewImageURL, previewTitle, insideLink)
                itemsContainer.add(doneItem)
            }
        }
        itemsList.value = itemsContainer
    }

    fun getVsuetNews() {
        viewModelScope.launch {
            getRawVsuetNews()
        }
    }

    fun getVkontakteNews() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                VkontakteApi.retrofitService.getProperties().enqueue(
                    object : Callback<NewsProperty> {
                        override fun onResponse(
                            call: Call<NewsProperty>,
                            response: Response<NewsProperty>
                        ) {
                            _response.value = response.body()?.response?.items
                        }

                        override fun onFailure(call: Call<NewsProperty>, t: Throwable) {
                            println(t.message)
                            println("?")
                        }

                    }
                )
            }
        }
    }


}