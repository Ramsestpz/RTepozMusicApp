package com.tepoz.rtepozmusicapp.screens

import android.R.attr.id
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tepoz.rtepozmusicapp.components.AlbumHeader
import com.tepoz.rtepozmusicapp.models.Album
import com.tepoz.rtepozmusicapp.services.MusicApi
import com.tepoz.rtepozmusicapp.ui.theme.BackgroundColor
import com.tepoz.rtepozmusicapp.ui.theme.DarkColor
import com.tepoz.rtepozmusicapp.ui.theme.LightColor
import com.tepoz.rtepozmusicapp.ui.theme.PrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AlbumDetailScreen(albumId: String) {
    val BASE_URL = "https://music.juanfrausto.com/"
    var album by remember { mutableStateOf<Album?>(null) }

    LaunchedEffect(true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(MusicApi::class.java)
            val result = withContext(Dispatchers.IO) { service.getAlbumDetail(albumId) }
            album = result
            Log.i("ProductDetail", album.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val Album = album

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        if (Album != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 45.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                item {
                    AlbumHeader(album = Album)
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(4.dp, RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .clip(RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            "About this album",
                            color = PrimaryColor,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            Album.description,
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterStart)
                                .width(240.dp)
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .clip(RoundedCornerShape(16.dp))
                                .padding(12.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                "Artist",
                                color = PrimaryColor,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                Album.artist,
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                val tracks = (1..24 step 1).map { trackNumber ->
                    "Track.$trackNumber"
                }

                itemsIndexed(tracks) { index, trackName ->
                    SongItem(
                        trackNumber = trackName,
                        title = Album.title,
                        artist = Album.artist,
                        imageUrl = Album.image
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        } else {
            CircularProgressIndicator(
                color = DarkColor,
                trackColor = LightColor
            )
        }
    }
}

@Composable
fun SongItem(
    trackNumber: String,
    title: String,
    artist: String,
    imageUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                error = ColorPainter(DarkColor),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp)
        ) {
            Text(
                "$title  • $trackNumber",
                style = MaterialTheme.typography.bodyLarge,
                color = PrimaryColor
            )
            Text(
                artist,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color.DarkGray
        )
    }
}