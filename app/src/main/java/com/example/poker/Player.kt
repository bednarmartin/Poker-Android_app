package com.example.poker

class Player(var number: Int, var card1: Card?, var card2: Card?, var money: Int, var bet: Int) {

    override fun toString(): String {
        return "Player $number"
    }
}