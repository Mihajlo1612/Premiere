package com.rma.premiere.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rma.premiere.data.model.Movie
import com.rma.premiere.theme.ContentColor
import com.rma.premiere.theme.GenreChipColor
import com.rma.premiere.theme.StarColor
import com.rma.premiere.theme.WhiteColor
import com.rma.premiere.theme.WhiteSecondary
import com.rma.premiere.util.formatVotes

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ContentColor)
            .clickable { onClick() }
            .padding(12.dp)
            .height(100.dp),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w185${movie.posterPath}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            placeholder = painterResource(android.R.drawable.ic_menu_gallery),
            error = painterResource(android.R.drawable.ic_menu_gallery)
        )
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = movie.title,
                color = WhiteColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.year?.toString() ?: "N/A",
                color = WhiteSecondary,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⭐", fontSize = 13.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movie.imdbRating?.toString() ?: "N/A",
                    color = StarColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = formatVotes(movie.imdbVotes),
                    color = WhiteSecondary,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                movie.genres.forEach { genre ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GenreChipColor)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = genre.name,
                            color = WhiteSecondary,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

