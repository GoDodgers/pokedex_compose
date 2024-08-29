package com.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Info
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
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pokedex.MainViewModel
import kotlinx.serialization.Serializable

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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = HomeGenList ) {
                        composable <HomeGenList> {
                            HomeScreen(top = { TopBar() }, bottom = { BottomBar() }, navController = navController)
                        }
                        composable <GenPokeList> {
                            val args =  it.toRoute<GenPokeList>()
                            var isSearchActive by rememberSaveable {
                                mutableStateOf(false)
                            }

                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center

                            ) {
                                Box(
                                    modifier = Modifier,
                                    contentAlignment = Alignment.Center
                                ) {
                                    PokemonList(gen = args.gen, navController = navController, isSearchActive, { isSearchActive = it })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonList(gen: String, navController: NavController, isSearchActive: Boolean, onActiveChange: (Boolean) -> Unit) {
    val viewModel = MainViewModel()
    val isSearching by viewModel.isSearching.collectAsState()
    val pokemonList by viewModel.pokemonList.collectAsState()

    var searchQuery by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Scaffold (
        modifier = Modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
                    )
                    .clip(RoundedCornerShape(25.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    SearchBar(
                        placeholder = { Text(text = gen) },
                        modifier = Modifier
                            .padding(bottom = 10.dp),
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                            viewModel.onSearchTextChange(it)
                        },
                        onSearch = viewModel::onSearchTextChange,
                        active = isSearching,
                        onActiveChange = onActiveChange,
                        colors = SearchBarDefaults.colors(
                            containerColor = Color.LightGray
                        )
                    ) {}
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(HomeGenList)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp)
                                .size(26.dp),
                            contentDescription = "Homepage"
                        )
                    }
                },
                actions = {
                    if (isSearchActive) {
                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "cancel search"
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(32.dp),
                            contentDescription = "$gen info"
                        )
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(32.dp),
                            contentDescription = "account info"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->

        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = paddingValues,
        ) {
            val filteredPokeList = pokemonList.toMutableList().filter { searchQuery in it.first }

            items(filteredPokeList.size) { i ->
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary),
                ) {
                    val poke = pokemonList.elementAt(i)
                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = poke.first)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        modifier = Modifier
                            .size(width = LocalConfiguration.current.screenWidthDp.dp, height = (LocalConfiguration.current.screenHeightDp.dp * 0.25f)),
                        painter = painterResource(id = filteredPokeList[i].second),
                        contentDescription = stringResource(id = R.string.app_name)
                    )

                }
            }
        }

    }
}

@Serializable
object HomeGenList

@Serializable
data class GenPokeList(val gen: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    top: @Composable () -> Unit,
    bottom: @Composable () -> Unit,
    navController: NavHostController
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
        GenerationList(navController, innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        title = {
            Text(
                text = "Pokedéx",
                fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                fontSize = MaterialTheme.typography.displayMedium.fontSize,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Sharp.Menu,
                contentDescription = "content description"
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Sharp.Bolt,
                contentDescription = "content description"
            )
            Icon(
                imageVector = Icons.Sharp.AccountCircle,
                contentDescription = "content description"
            )
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
                    contentDescription = "share"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "favorite"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.AddBox,
                    contentDescription = "add"
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.BackHand,
                    contentDescription = "stop"
                )
            }
        }
    )
}

@Composable
fun GenerationList(navController: NavController, paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .fillMaxSize()
            .padding(paddingValues), contentAlignment = Alignment.Center
    ) {
        Column {
            for (gen in generations) {
                Row (
//                    modifier = Modifier
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            navController.navigate(
                                GenPokeList(gen = gen)
                            )
                        }
                    ) {
                        Text(
                            text = gen,
                            fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                            fontSize = MaterialTheme.typography.displayMedium.fontSize,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}
