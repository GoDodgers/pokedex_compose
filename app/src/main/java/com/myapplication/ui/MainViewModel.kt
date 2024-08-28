package com.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {

    val pokemonMap = listOf(
        "bulbasaur" to R.drawable.bulbasaur_1,
        "ivysaur" to R.drawable.ivysaur_2,
        "venusaur" to R.drawable.venusaur_3,
        "charmander" to R.drawable.charmander_4,
        "charmeleon" to R.drawable.charmeleon_5,
        "charizard" to R.drawable.charizard_6,
        "squirtle" to R.drawable.squirtle_7,
        "warturtle" to R.drawable.wartortle_8,
        "blastoise" to R.drawable.blastoise_9,
        "caterpie" to R.drawable.caterpie_10,
        "metapod" to R.drawable.metapod_11,
        "butterfree" to R.drawable.butterfree_12,
        "weedle" to R.drawable.weedle_13,
        "kakuna" to R.drawable.kakuna_14,
        "beedrill" to R.drawable.beedrill_15
    )

    // first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    // third state the list to be filtered
    private val _pokemonList = MutableStateFlow(pokemonMap)
    val pokemonList = _pokemonList.asStateFlow()

    fun onSearchTextChange(text: String) {
        _pokemonList.value = pokemonMap.filter { poke ->
            poke.first.contains(text.trim(), true)
        }
    }

}

