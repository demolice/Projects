package com.barabasevd.tictactoe

import android.graphics.Color

class Player(val name: String) {
    private var playersMoves = ArrayList<Int>()
    private var color: Int = 0

    fun getColor():Int {
        return color
    }

    fun setColor(color: Int) {
        this.color = color

    }


    fun addMove(id: Int) {
        playersMoves.add(id)
    }

    fun isWinner(): Boolean{
        if (playersMoves.contains(0) && playersMoves.contains(1) && playersMoves.contains(2)) {
            return true
        } else if (playersMoves.contains(3) && playersMoves.contains(4) && playersMoves.contains(5)) {
            return true
        } else if (playersMoves.contains(6) && playersMoves.contains(7) && playersMoves.contains(8)) {
            return true
        } else if (playersMoves.contains(0) && playersMoves.contains(3) && playersMoves.contains(6)) {
            return true
        } else if (playersMoves.contains(1) && playersMoves.contains(4) && playersMoves.contains(7)) {
            return true
        } else if (playersMoves.contains(2) && playersMoves.contains(5) && playersMoves.contains(8)) {
            return true
        } else if (playersMoves.contains(0) && playersMoves.contains(4) && playersMoves.contains(8)) {
            return true
        } else if (playersMoves.contains(2) && playersMoves.contains(4) && playersMoves.contains(6)) {
            return true
        }

        return false
    }
}