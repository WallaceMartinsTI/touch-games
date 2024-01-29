package com.wcsm.touchgames.hangman

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HMWords(
    val worldCountries: List<String>,
    val famousCitiesInTheWorld: List<String>,
    val animals: List<String>,
    val foods: List<String>,
    val movieCharacters: List<String>,
    val sports: List<String>,
    val christmasWords: List<String>,
    val professions: List<String>,
    val brandsAndCompanies: List<String>,
    val soccerTeams: List<String>,
    val celebrityNames: List<String>,
    val colors: List<String>,
    val heroes: List<String>,
    val techs: List<String>,
    val villains: List<String>,
) : Parcelable
