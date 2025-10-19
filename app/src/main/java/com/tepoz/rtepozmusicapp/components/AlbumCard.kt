package com.tepoz.rtepozmusicapp.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tepoz.rtepozmusicapp.models.Album
import com.tepoz.rtepozmusicapp.ui.theme.RTepozMusicAppTheme

@Composable
fun AlbumCard(
    album: Album,
    onClick: () -> Unit
){

}

@Preview
@Composable
fun AlbumCardPreview(){
    RTepozMusicAppTheme {
        AlbumCard(
            album = Album(
                id = "1",
                title = "Album de prueba",
                artist = "Artista de prueba",
                description = "Descripción de prueba",
                image = "https://via.placeholder.com/150"
            )
        ){

        }
    }
}