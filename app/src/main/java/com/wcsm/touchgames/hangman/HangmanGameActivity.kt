package com.wcsm.touchgames.hangman

import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.google.gson.Gson
import com.wcsm.touchgames.R
import com.wcsm.touchgames.databinding.ActivityHangmanGameBinding
import java.text.Normalizer
import kotlin.random.Random

class HangmanGameActivity : AppCompatActivity() {

    private val binding by lazy {ActivityHangmanGameBinding.inflate(layoutInflater)}

    private lateinit var triedWords: TextView
    private lateinit var correctWordsCounter: TextView
    private lateinit var guess: String

    private var hasWord: Boolean? = null
    private var wordCompleted: Boolean? = null

    private var lifes = 6
    private var chosenCategory = ""
    private var chosenWord = ""
    private var replacedWord = ""
    private var playerPoints = 0
    private var correctWordsCount = 0

    private var wrongWords = mutableListOf<Char>()
    private var allInputedWords = mutableListOf<Char>()

    private val emptyGibbet = R.drawable.hm_0
    private val gibbetWithHead = R.drawable.hm_1
    private val gibbetWithBody = R.drawable.hm_2
    private val gibbetWithOneArm = R.drawable.hm_3
    private val gibbetWithTwoArms = R.drawable.hm_4
    private val gibbetWithOneLeg = R.drawable.hm_5
    private val gibbetFull = R.drawable.hm_6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setInitialStates()
        generateNewWord()

        with(binding) {
            hmBtnPreviousScreen.setOnClickListener {
                finish()
            }

            hmBtnEndgame.setOnClickListener {
                hmBtnEndgame.isEnabled = false
                endGameDialog()
            }

            hmBtnCheck.setOnClickListener {
                guess = binding.hmEditText.text.toString()
                val formattedGuess = guess.uppercase()

                if(validateGuess(formattedGuess)) {
                    checkLetter(formattedGuess[0])
                    checkPlay(formattedGuess)
                }
                binding.hmEditText.setText("")
            }
        }
    }

    private fun setInitialStates() {
        // Disable Guess Field AutoFill
        ViewCompat.setImportantForAutofill(binding.hmEditText, View.IMPORTANT_FOR_AUTOFILL_NO)

        binding.hmPoints.text = "Pontuação: 0"
        binding.hmCorrectWordsCounter.text = "Palavras Corretas: $correctWordsCount"
        binding.hmGameOver.visibility = View.INVISIBLE

        triedWords = binding.hmTriedWords
        triedWords.text = "LETRAS ERRADAS:\n"

        correctWordsCounter = binding.hmCorrectWordsCounter
    }

    private fun validateGuess(guess: String): Boolean {
        val guessLayout = binding.hmGuessInputLayout
        guessLayout.error = null

        if(guess.isEmpty()) {
            guessLayout.error = "Dê um palpite"
            return false
        } else if (guess[0] !in 'A'..'Z') {
            guessLayout.error = "Digite uma letra sem\n acento entre (A - Z)"
            return false
        } else if (allInputedWords.contains(guess[0])) {
            guessLayout.error = "Letra já utilizada!"
            return false
        }
        return true
    }

    private fun getRandomCategoryAndWord() {
        val assetManager = assets
        val inputStream = assetManager.open("hm_words.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()
        val words = gson.fromJson(json, HMWords::class.java)

        val random = Random
        val categories = listOf(
            "worldCountries",
            "famousCitiesInTheWorld",
            "animals",
            "foods",
            "movieCharacters",
            "sports",
            "christmasWords",
            "professions",
            "brandsAndCompanies",
            "soccerTeams",
            "celebrityNames",
            "colors",
            "heroes",
            "techs",
            "villains"
        )

        // Get random word
        when(categories[random.nextInt(categories.size)]) {
            "worldCountries" -> {
                chosenWord = words.worldCountries[random.nextInt(words.worldCountries.size)]
                chosenCategory = "País"
            }
            "famousCitiesInTheWorld" -> {
                chosenWord = words.famousCitiesInTheWorld[random.nextInt(words.famousCitiesInTheWorld.size)]
                chosenCategory = "Cidade Famosa no Mundo"
            }
            "animals" -> {
                chosenWord = words.animals[random.nextInt(words.animals.size)]
                chosenCategory = "Animal"
            }
            "foods" -> {
                chosenWord = words.foods[random.nextInt(words.foods.size)]
                chosenCategory = "Alimento"
            }
            "movieCharacters" -> {
                chosenWord = words.movieCharacters[random.nextInt(words.movieCharacters.size)]
                chosenCategory = "Personagem de Filme"
            }
            "sports" -> {
                chosenWord = words.sports[random.nextInt(words.sports.size)]
                chosenCategory = "Esporte"
            }
            "christmasWords" -> {
                chosenWord = words.christmasWords[random.nextInt(words.christmasWords.size)]
                chosenCategory = "Natal"
            }
            "professions" -> {
                chosenWord = words.professions[random.nextInt(words.professions.size)]
                chosenCategory = "Profissão"
            }
            "brandsAndCompanies" -> {
                chosenWord = words.brandsAndCompanies[random.nextInt(words.brandsAndCompanies.size)]
                chosenCategory = "Marca ou Empresa"
            }
            "soccerTeams" -> {
                chosenWord = words.soccerTeams[random.nextInt(words.soccerTeams.size)]
                chosenCategory = "Time de Futebol"
            }
            "celebrityNames" -> {
                chosenWord = words.celebrityNames[random.nextInt(words.celebrityNames.size)]
                chosenCategory = "Celebridade"
            }
            "colors" -> {
                chosenWord = words.colors[random.nextInt(words.colors.size)]
                chosenCategory = "Cor"
            }
            "heroes" -> {
                chosenWord = words.heroes[random.nextInt(words.heroes.size)]
                chosenCategory = "Herói"
            }
            "techs" -> {
                chosenWord = words.techs[random.nextInt(words.techs.size)]
                chosenCategory = "Tecnologia"
            }
            "villains" -> {
                chosenWord = words.villains[random.nextInt(words.villains.size)]
                chosenCategory = "Vilão"
            }
        }
        chosenWord = chosenWord.uppercase()
        chosenCategory = chosenCategory.uppercase()
    }

    private fun checkLives() {
        // Update Hangman Image State
        when(lifes){
            6 -> binding.hmImage.setImageResource(emptyGibbet)
            5 -> binding.hmImage.setImageResource(gibbetWithHead)
            4 -> binding.hmImage.setImageResource(gibbetWithBody)
            3 -> binding.hmImage.setImageResource(gibbetWithOneArm)
            2 -> binding.hmImage.setImageResource(gibbetWithTwoArms)
            1 -> binding.hmImage.setImageResource(gibbetWithOneLeg)
            0 -> binding.hmImage.setImageResource(gibbetFull)
        }

        if(lifes == 0) {
            gameOver()
        }
    }

    private fun checkLetter(letter: Char) {
        var roundPoints = 0
        val normalizedWord = normalizeString(chosenWord)
        val normalizedGuess = normalizeString(letter.toString())

        allInputedWords.add(normalizedGuess[0])

        if(normalizedWord.contains(normalizedGuess)) {
            for(i in normalizedWord.indices) {
                if (normalizedWord[i] == normalizedGuess[0]) {
                    roundPoints += 10
                    playerPoints += 10
                    replacedWord = replacedWord.substring(0, i) + letter + replacedWord.substring(i + 1)
                }
            }
            showMessage("+$roundPoints pontos")
            binding.hmWord.text = replacedWord
            hasWord = true

            wordCompleted = checkWordCompleted()
            if(wordCompleted as Boolean) {
                correctWordsCount += 1
                playerPoints += 100

                binding.hmCorrectWordsCounter.text = "Palavras Corretas: $correctWordsCount"
                showMessage("Palavra Completa +100 pontos")
            }
        } else {
            hasWord = false
        }
    }

    private fun checkPlay(guess: String) {
        if(hasWord!!) {
            binding.hmPoints.text = "Pontuação: $playerPoints"
        } else {
            var wrongGuess = ""
            wrongWords.add(guess[0])

            wrongWords.forEach {
                wrongGuess += "$it, "
            }

            lifes--
            checkLives()

            triedWords.text = "LETRAS ERRADAS:\n" + normalizeString(wrongGuess)
        }
    }

    private fun checkWordCompleted(): Boolean {
        for(i in replacedWord) {
            if(i == '_') {
                return false
            }
        }
        generateNewWord()
        return true
    }

    private fun generateNewWord() {
        lifes = 6
        chosenCategory = ""
        chosenWord = ""
        replacedWord = ""
        wrongWords = mutableListOf()
        triedWords.text = "LETRAS ERRADAS:\n"
        correctWordsCounter.text = "Palavras Corretas: $correctWordsCount"
        allInputedWords = mutableListOf()
        checkLives()

        getRandomCategoryAndWord()

        for(char in chosenWord) {
            replacedWord += if(char == ' ') {
                " "
            } else {
                "_"
            }
        }

        with(binding) {
            hmWord.text = replacedWord
            hmTip.text = "$chosenCategory (${chosenCategory.replace(" ", "").length})"
        }
    }

    private fun gameOver() {
        with(binding) {
            hmGuessInputLayout.isEnabled = false
            hmBtnCheck.isEnabled = false
            hmGameOver.visibility = View.VISIBLE
            hmBtnEndgame.isEnabled = true
            hmWord.text = chosenWord
        }
    }

    private fun endGameDialog() {
        AlertDialog.Builder(this)
            .setTitle("Fim de Jogo!")
            .setMessage("ESTATÍSTICAS\nPontuação: $playerPoints\nPalavras Adivinhadas: $correctWordsCount")
            .setPositiveButton("Novo Jogo") {dialog, _ ->
                dialog.dismiss()
                recreate()
            }
            .setNegativeButton("Menu Inicial") {dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setOnDismissListener {
                binding.hmGuessInputLayout.error = null
                binding.hmBtnEndgame.isEnabled = true
            }
            .show()
    }

    private fun normalizeString(input: String): String {
        val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
        return normalized.replace("[^\\p{ASCII}]".toRegex(), "")
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}