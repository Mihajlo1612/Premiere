package com.rma.premiere.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.rma.premiere.theme.BackgroundColor
import com.rma.premiere.theme.ContentColor
import com.rma.premiere.theme.RedColor
import com.rma.premiere.theme.StarColor
import com.rma.premiere.theme.WhiteColor
import com.rma.premiere.theme.WhiteSecondary
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale
import androidx.core.net.toUri
import com.rma.premiere.util.formatMoney
import com.rma.premiere.util.formatVotes

@Composable
fun MovieDetaisScreen(
    imdbId: String,
    onBackClick: () -> Unit,
    viewModel: MovieDetailsViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(imdbId) {
        viewModel.onEvent(MovieDetailsEvent.LoadMovieDetails(imdbId))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    color = RedColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.error != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.error!!,
                        color = WhiteSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.onEvent(MovieDetailsEvent.OnRetry) }
                    ) {
                        Text(text = "Retry", color = WhiteColor)
                    }
                }
            }

            state.movie != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w780${state.movie!!.backdropPath}",
                            contentDescription = "Backdrop",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.4f))
                        )

                        val context = LocalContext.current

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(RedColor)
                                .align(Alignment.Center)
                                .clickable {
                                    println("TRAILER_DEBUG: key = ${state.trailerKey}")
                                    state.trailerKey?.let { key ->
                                        openYouTube(context, key)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = WhiteColor,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(top = 32.dp, start = 12.dp)
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.5f))
                                .align(Alignment.TopStart)
                                .clickable { onBackClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = WhiteColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .offset(y = (-60).dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w185${state.movie!!.posterPath}",
                            contentDescription = "Poster",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(90.dp)
                                .height(135.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = state.movie!!.title,
                                color = WhiteColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${state.movie!!.year ?: ""} • ${state.movie!!.runtime ?: ""} min",
                                color = WhiteSecondary,
                                fontSize = 13.sp
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .offset(y = (-24).dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⭐", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${state.movie!!.imdbRating ?: "N/A"}",
                                color = StarColor,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "/10",
                                color = WhiteSecondary,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = formatVotes(state.movie!!.imdbVotes),
                                color = WhiteSecondary,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "TMDB",
                                color = RedColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = String.format(
                                    Locale.US,
                                    "%.1f",
                                    state.movie!!.tmdbRating ?: 0f
                                ),
                                color = WhiteColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            state.movie!!.genres.forEach { genre ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(RedColor)
                                        .padding(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = genre.name,
                                        color = WhiteColor,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "OVERVIEW",
                            color = WhiteColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.movie!!.overview ?: "No overview available.",
                            color = WhiteSecondary,
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "INFO",
                            color = WhiteColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            InfoCard(
                                label = "Budget",
                                value = formatMoney(state.movie!!.budget),
                                modifier = Modifier.weight(1f)
                            )
                            InfoCard(
                                label = "Revenue",
                                value = formatMoney(state.movie!!.revenue),
                                modifier = Modifier.weight(1f)
                            )
                            InfoCard(
                                label = "Language",
                                value = state.movie!!.languageCode?.uppercase() ?: "N/A",
                                modifier = Modifier.weight(1f)
                            )
                            InfoCard(
                                label = "Popularity",
                                value = state.movie!!.popularity?.let {
                                    String.format(
                                        Locale.US,
                                        "%.1f",
                                        it
                                    )
                                } ?: "N/A",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "IMAGES",
                            color = WhiteColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.backdrops.forEach { backdrop ->
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w780${backdrop.filePath}",
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(120.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "ACTORS",
                            color = WhiteColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        state.cast.forEachIndexed { index, person ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w185${person.profilePath}",
                                    contentDescription = person.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(ContentColor)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = person.name,
                                    color = WhiteColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            if (index < state.cast.size - 1) {
                                HorizontalDivider(color = ContentColor, thickness = 0.5.dp)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

fun openYouTube(context: Context, key: String) {
    val intent = Intent(Intent.ACTION_VIEW, "https://www.youtube.com/watch?v=$key".toUri())
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

@Composable
fun InfoCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(ContentColor)
            .padding(horizontal = 8.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = WhiteSecondary,
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = WhiteColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}