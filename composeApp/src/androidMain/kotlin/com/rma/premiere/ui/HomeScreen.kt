package com.rma.premiere.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rma.premiere.model.Movie
import com.rma.premiere.theme.ContentColor
import com.rma.premiere.theme.BackgroundColor
import com.rma.premiere.theme.RedColor
import com.rma.premiere.theme.WhiteColor
import com.rma.premiere.theme.WhiteSecondary
import com.rma.premiere.ui.components.MovieCard

val mockMovies = listOf(
    Movie("The Shawshank Redemption", "1994", 9.3, "3.2M votes", listOf("Drama", "Crime")),
    Movie("The Godfather", "1972", 9.2, "2.2M votes", listOf("Drama", "Crime")),
    Movie("The Dark Knight", "2008", 9.1, "3.2M votes", listOf("Action", "Thriller", "Crime")),
    Movie("12 Angry Men", "1957", 9.0, "979K votes", listOf("Drama")),
    Movie("The Godfather Part II", "1974", 9.0, "1.5M votes", listOf("Drama", "Crime")),
    Movie("Schindler's List", "1993", 9.0, "1.6M votes", listOf("Drama", "History", "War")),
    Movie(
        "The Lord of the Rings: The Return of the King",
        "2003",
        9.0,
        "2.2M votes",
        listOf("Adventure", "Fantasy", "Action")
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen() {
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
                        onClick = { },
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

                var expanded by remember { mutableStateOf(false) }
                var selectedSort by remember { mutableStateOf("Rating") }
                var sortOptions = listOf("Rating", "Year", "Title", "Votes")

                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(ContentColor)
                            .clickable { expanded = true }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(text = "Sort: ", color = WhiteColor, fontSize = 13.sp)
                        Text(
                            text = "$selectedSort ↓",
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
                                        text = option,
                                        color = if (option == selectedSort) RedColor else WhiteColor
                                    )
                                },
                                onClick = {
                                    selectedSort = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                //TODO Promena broja filmova nakon filtriranja
                Text(
                    text = "1000 movies",
                    color = WhiteSecondary,
                    fontSize = 13.sp
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(mockMovies) { movie ->
                    MovieCard(movie = movie)
                }
            }
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                modifier = Modifier
//                    .fillMaxSize(),
//                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                items(mockMovies) { movie ->
//                    MovieGridCard(movie = movie)
//                }
//            }
        }
    }
}

