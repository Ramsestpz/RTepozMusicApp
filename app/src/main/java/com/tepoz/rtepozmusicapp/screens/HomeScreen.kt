package com.tepoz.rtepozmusicapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.tepoz.rtepozmusicapp.components.AlbumCard
import com.tepoz.rtepozmusicapp.models.AlDetailScreenRoute
import com.tepoz.rtepozmusicapp.models.Album
import com.tepoz.rtepozmusicapp.services.MusicApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(navController: NavController) {
    val BASE_URL = "https://music.juanfrausto.com/"
    var albums by remember { mutableStateOf(listOf<Album>()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        try {
            Log.i("HomeScreen", "Creando instancoa Retrofit")
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(MusicApi::class.java)
            val result = withContext(Dispatchers.IO) {
                service.getAlbums()
            }
            Log.i("HomeScreen", "El resultado es: ${result}")
            albums = result
            loading = false
        } catch (e: Exception) {
            Log.e("HomeScreen", "Esta mal: ${e.toString()}")
            loading = false
        }
    }

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn() {
            items(albums) { album ->
                AlbumCard(album = album, onClick = {
                    navController.navigate(AlDetailScreenRoute(album.id))
                }
                )
            }
        }
    }
}

