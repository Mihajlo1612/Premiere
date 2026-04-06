package com.rma.premiere.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rma.premiere.theme.BackgroundColor
import com.rma.premiere.theme.ContentColor
import com.rma.premiere.theme.RedColor
import com.rma.premiere.theme.StarColor
import com.rma.premiere.theme.WhiteColor
import com.rma.premiere.theme.WhiteSecondary
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterMoviesScreen(
    onBackClick: () -> Unit,
    onApplyFilters: (query: String, genreId: Int?, minYear: Int, maxYear: Int, minRating: Float) -> Unit,
    viewModel: FilterMoviesViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = WhiteColor
                        )
                    }
                },
                title = {
                    Text(
                        text = "Filter Movies",
                        color = WhiteColor,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    TextButton(onClick = { viewModel.onEvent(FilterMoviesEvent.OnClearAll) }) {
                        Text(
                            text = "Clear All",
                            color = RedColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ContentColor
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Search
            Text(
                text = "SEARCH",
                color = WhiteColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.query,
                onValueChange = { viewModel.onEvent(FilterMoviesEvent.OnQueryChanged(it)) },
                placeholder = {
                    Text(
                        text = "Search by movie title...",
                        color = WhiteSecondary
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = ContentColor,
                    focusedBorderColor = RedColor,
                    unfocusedContainerColor = ContentColor,
                    focusedContainerColor = ContentColor,
                    cursorColor = RedColor
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "GENRE",
                color = WhiteColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                state.genres.forEach { genre ->
                    val isSelected = state.selectedGenreId == genre.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) RedColor else ContentColor)
                            .clickable {
                                viewModel.onEvent(
                                    FilterMoviesEvent.OnGenreSelected(
                                        if (isSelected) null else genre.id
                                    )
                                )
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = genre.name,
                            color = if (isSelected) WhiteColor else WhiteSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "YEAR RANGE",
                color = WhiteColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "From", color = WhiteSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.minYear.toString(),
                        onValueChange = {
                            it.toIntOrNull()?.let { year ->
                                viewModel.onEvent(FilterMoviesEvent.OnMinYearChanged(year))
                            }
                        },
                        modifier = Modifier.width(170.dp),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = LocalTextStyle.current.copy(
                            color = WhiteColor,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = ContentColor,
                            focusedBorderColor = RedColor,
                            unfocusedContainerColor = ContentColor,
                            focusedContainerColor = ContentColor,
                            cursorColor = RedColor
                        ),
                        singleLine = true
                    )
                }
                Text(
                    text = "—",
                    color = WhiteSecondary,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 15.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "To", color = WhiteSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.maxYear.toString(),
                        onValueChange = {
                            it.toIntOrNull()?.let { year ->
                                viewModel.onEvent(FilterMoviesEvent.OnMaxYearChanged(year))
                            }
                        },
                        modifier = Modifier.width(170.dp),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = LocalTextStyle.current.copy(
                            color = WhiteColor,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = ContentColor,
                            focusedBorderColor = RedColor,
                            unfocusedContainerColor = ContentColor,
                            focusedContainerColor = ContentColor,
                            cursorColor = RedColor
                        ),
                        singleLine = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "MINIMUM RATING",
                color = WhiteColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Slider(
                    value = state.minRating,
                    onValueChange = { viewModel.onEvent(FilterMoviesEvent.OnMinRatingChanged(it)) },
                    valueRange = 0f..10f,
                    modifier = Modifier.weight(1f),
                    colors = SliderDefaults.colors(
                        thumbColor = RedColor,
                        activeTrackColor = RedColor,
                        inactiveTrackColor = ContentColor
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ContentColor)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⭐", fontSize = 13.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format(Locale.US, "%.1f", state.minRating),
                            color = StarColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    onApplyFilters(
                        state.query,
                        state.selectedGenreId,
                        state.minYear,
                        state.maxYear,
                        state.minRating
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedColor
                )
            ) {
                Text(
                    text = "Apply Filters",
                    color = WhiteColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}














