package com.barabasevd.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var playerX = Player("X")
    private var playerO = Player("O")
    private var activePlayer = playerX
    private var moveCount = 0
    private var usedBtns = ArrayList<Button>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main_constraint)
        initColors()
    }

    private fun initColors() {
        playerX.setColor(ContextCompat.getColor(this, R.color.colorRed))
        playerO.setColor(ContextCompat.getColor(this, R.color.colorBlue))
    }

    fun btnReset(view: View) {
        resetGame()
    }

    fun btnClick(view: View) {

        val btnSelected = view as Button
        var btnID = 0

        usedBtns.add(btnSelected)

        when (btnSelected.id) {
            R.id.btn0 -> btnID = 0
            R.id.btn1 -> btnID = 1
            R.id.btn2 -> btnID = 2
            R.id.btn3 -> btnID = 3
            R.id.btn4 -> btnID = 4
            R.id.btn5 -> btnID = 5
            R.id.btn6 -> btnID = 6
            R.id.btn7 -> btnID = 7
            R.id.btn8 -> btnID = 8
        }

        playGame(btnID, btnSelected)
    }

    private fun playGame(cellID: Int, btnSelected: Button) {
        btnSelected.isEnabled = false

        btnSelected.text = activePlayer.name
        btnSelected.setBackgroundColor(activePlayer.getColor())
        activePlayer.addMove(cellID)
        moveCount++
        checkWinner()

        when (activePlayer.name) {
            "X" -> activePlayer = playerO
            "O" -> activePlayer = playerX
        }
    }

    private fun checkWinner() {
        if (activePlayer.isWinner()) {
            Toast.makeText(
                this, "The game winner is the player with ${activePlayer.name}",
                Toast.LENGTH_LONG
            ).show()
            changeEnable(false)
        } else if (moveCount == 9) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show()
        }
    }

    private fun resetGame() {
        moveCount = 0
        playerO = Player("O")
        playerX = Player("X")
        activePlayer = playerX
        initColors()

        usedBtns.forEach {
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey))
            it.text = ""
        }
        changeEnable(true)

        usedBtns.clear()
    }

    private fun changeEnable(status: Boolean) {
        for (index in 0..8) {
            val id: Int = resources.getIdentifier("btn$index", "id", packageName)
            var button: Button = findViewById(id)
            button.isEnabled = status
        }

    }


}
