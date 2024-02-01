package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var userGuessInput: EditText
    private lateinit var guessButton: Button
    private lateinit var resetButton: Button
    private lateinit var guessResults: Array<TextView>
    private lateinit var correctWordTextView: TextView
    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    private var guessCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userGuessInput = findViewById(R.id.userGuess)
        guessButton = findViewById(R.id.guessButton)
        resetButton = findViewById(R.id.resetButton)

        guessResults = arrayOf(
            findViewById(R.id.userGuess1),
            findViewById(R.id.userGuess2),
            findViewById(R.id.userGuess3),
            findViewById(R.id.userCorrect1),
            findViewById(R.id.userCorrect2),
            findViewById(R.id.userCorrect3)
        )
        correctWordTextView = findViewById(R.id.correctWord)

        guessButton.setOnClickListener { onGuess() }
        resetButton.setOnClickListener { resetGame() }

        resetButton.visibility = View.INVISIBLE
//        Toast.makeText(this, "Correct word: $wordToGuess", Toast.LENGTH_LONG).show()
    }
    private fun onGuess() {
        if (guessCount >= 3) {
            endGame()
            return
        }

        val guess = userGuessInput.text.toString().uppercase()
        if (guess.length != 4) {
            Toast.makeText(this, "4 Word Length Only", Toast.LENGTH_LONG).show()
            return
        }

        val result = checkGuess(guess)
        guessResults[guessCount].text = guess
        guessResults[guessCount + 3].text = result
        guessResults[guessCount].visibility = View.VISIBLE
        guessResults[guessCount + 3].visibility = View.VISIBLE

        guessCount++

        if (result == "OOOO" || guessCount == 3) {
            endGame()
        }

        userGuessInput.text.clear()
    }
    private fun endGame() {
        guessButton.visibility = View.INVISIBLE
        guessButton.isEnabled = false
        resetButton.visibility = View.VISIBLE
        if (guessCount == 3 && guessResults[5].text != "OOOO") {
            correctWordTextView.text = wordToGuess
            correctWordTextView.visibility = View.VISIBLE
        }
    }
    private fun resetGame() {
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        guessCount = 0
        guessButton.visibility = View.VISIBLE
        guessButton.isEnabled = true
        resetButton.visibility = View.INVISIBLE
        correctWordTextView.visibility = View.INVISIBLE

        for (i in guessResults.indices) {
            guessResults[i].text = ""
            guessResults[i].visibility = View.INVISIBLE
        }
    }
    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in guess.indices) {
            result += when {
                guess[i] == wordToGuess[i] -> "O"
                guess[i] in wordToGuess -> "+"
                else -> "X"
            }
        }
        return result
    }
}
