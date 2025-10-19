package com.tepoz.rtepozmusicapp.screens

import android.R.attr.id
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tepoz.rtepozmusicapp.models.Album
import com.tepoz.rtepozmusicapp.services.MusicApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AlDetailScreen(albumId: String) {
    val BASE_URL = "https://music.juanfrausto.com/"
    var album by remember {
        mutableStateOf<Album?>(null)
    }
    LaunchedEffect(true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(MusicApi::class.java)
            val result = withContext(Dispatchers.IO) {
                service.getAlbumDetail(albumId)
            }
            album = result
            Log.i("ProductDetail", album.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}