package com.tepoz.rtepozmusicapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tepoz.rtepozmusicapp.ui.theme.LightColor

@Composable
fun AlbumTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp
        )
        Text(
            text = "ver mas..",
            color = LightColor,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp
        )
    }
}