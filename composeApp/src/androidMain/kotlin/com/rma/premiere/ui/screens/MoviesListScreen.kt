package com.rma.premiere.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rma.premiere.theme.ContentColor
import com.rma.premiere.theme.BackgroundColor
import com.rma.premiere.theme.RedColor
import com.rma.premiere.theme.WhiteColor
import com.rma.premiere.theme.WhiteSecondary
import com.rma.premiere.ui.components.MovieCard
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun MoviesListScreen(
    onFilterClick: () -> Unit,
    query: String? = null,
    genreId: Int? = null,
    minYear: Int? = null,
    maxYear: Int? = null,
    minRating: Float? = null,
    viewModel: MoviesListViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    val sortOptions = listOf(
        "imdb_rating" to "Rating",
        "year" to "Year",
        "title" to "Title",
        "popularity" to "Popularity"
    )

    LaunchedEffect(query, genreId, minYear, maxYear, minRating) {
        viewModel.onEvent(
            MoviesListEvent.OnFiltersApplied(
                query = query,
                genreId = genreId,
                minYear = minYear,
                maxYear = maxYear,
                minRating = minRating
            )
        )

    }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = ContentColor),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "\uD83C\uDFAC", fontSize = 15.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Premiere",
                            color = WhiteColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = onFilterClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RedColor
                        ),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Filter",
                            tint = WhiteColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Filter",
                            color = WhiteColor,
                            fontSize = 14.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ContentColor
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(ContentColor)
                            .clickable { expanded = true }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Sort: ${sortOptions.find { it.first == state.sortBy }?.second ?: "Rating"} ${if (state.sortOrder == "asc") "↑" else "↓"}",
                            color = WhiteColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Sort",
                            tint = WhiteSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(ContentColor)
                    ) {
                        sortOptions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option.second,
                                        color = if (state.sortBy == option.first) RedColor else WhiteColor
                                    )
                                },
                                onClick = {
                                    viewModel.onEvent(
                                        MoviesListEvent.OnSortChanged(
                                            sortBy = option.first,
                                            sortOrder = if (option.first == "title") "asc" else "desc"
                                        )
                                    )
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Text(
                    text = "${state.totalMovies} movies",
                    color = WhiteSecondary,
                    fontSize = 13.sp
                )
            }
            // Loading, Error i Success states
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = RedColor)
                    }
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error!!,
                            color = WhiteSecondary,
                            fontSize = 14.sp
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(state.movies) { movie ->
                            MovieCard(movie = movie)
                        }
                    }
                }
            }
        }
    }
}



