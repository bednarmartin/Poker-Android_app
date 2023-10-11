package com.example.poker

class Game(private val player1: Player, private val player2: Player) {

    private var cards = Cards.cards.toMutableList()
    var smallBlindPlayer = player1
    var bigBlindPlayer = player2

    fun newGame() {
        cards.shuffle()
        player1.card1 = cards[0]
        player2.card1 = cards[1]
        player1.card2 = cards[2]
        player2.card2 = cards[3]
        smallBlindPlayer = if (smallBlindPlayer == player2) player1 else player2
        bigBlindPlayer = if (bigBlindPlayer == player2) player1 else player2
    }

    fun getTheFlop(): Array<Card> {
        return arrayOf(cards[4], cards[5], cards[6]);
    }

    fun getTheTurn(): Card {
        return cards[7]
    }

    fun getTheRiver(): Card {
        return cards[8]
    }
}