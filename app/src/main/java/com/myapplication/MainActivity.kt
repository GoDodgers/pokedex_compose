package com.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.sharp.AccountBalance
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.Bolt
import androidx.compose.material.icons.sharp.Lightbulb
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

private val generations: List<String> = listOf(
    "Kanto",
    "Johto",
    "Hoenn",
    "Sinnoh",
    "Unova",
    "Kalos",
    "Aloha",
    "Galar",
    "Paldea"
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                HomeScreen(top = { TopBar() }, bottom = { BottomBar() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    top: @Composable () -> Unit,
    bottom: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            top()
        },
        bottomBar = {
            bottom()
        },
        floatingActionButton = {
            Icon(
                imageVector = Icons.Sharp.AccountBalance,
                contentDescription = "content description"
            )
        }
    ) { innerPadding ->
        GenerationList(paddingValues = innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = "Pokedéx",
                fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                fontSize = MaterialTheme.typography.displayMedium.fontSize,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        },
        navigationIcon = {
            Icon(imageVector = Icons.Sharp.Menu, contentDescription = "content description")
        },
        actions = {
            Icon(imageVector = Icons.Sharp.Bolt, contentDescription = "content description")
            Icon(imageVector = Icons.Sharp.AccountCircle, contentDescription = "content description")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "content description"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "content description"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.AddBox,
                    contentDescription = "content description"
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.BackHand,
                    contentDescription = "Call contact"
                )
            }
        }
    )
}

@Composable
fun GenerationList(paddingValues: PaddingValues) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues), contentAlignment = Alignment.Center) {
        Column {
            for (gen in generations) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = gen,
                            fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                            fontSize = MaterialTheme.typography.displayMedium.fontSize,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}
