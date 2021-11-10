package com.example.vsuet.startMenuFragment.newsMenuFragment.newsInsideFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class NewsInsideViewModel(private val insideLink: String, application: Application) : AndroidViewModel(application) {

    val newsItemText = MutableLiveData<String>()

    private suspend fun getItemThings(){
        var text = ""
        withContext(Dispatchers.IO){
            val newsItemWeb = Jsoup.connect("https://$insideLink").get()
            val textList = newsItemWeb.select("div.text-justify").select("p")
            textList.removeAt(textList.indexOf(textList.last()))
            for(textPart in textList){
                text += textPart.text()
            }
        }

        newsItemText.value = text

    }

    fun setItemThings(){
        viewModelScope.launch {
            getItemThings()
        }
    }

}