package com.example.poker

class Card(val suit: Suit, val cardNumber: CardNumber) {

    fun getDrawable(): String {
        return "${cardNumber.number}_of_${suit.suit}"
    }
    fun next(): CardNumber = when(cardNumber){
        CardNumber.TWO -> CardNumber.THREE
        CardNumber.THREE -> CardNumber.FOUR
        CardNumber.FOUR -> CardNumber.FIVE
        CardNumber.FIVE -> CardNumber.SIX
        CardNumber.SIX -> CardNumber.SEVEN
        CardNumber.SEVEN -> CardNumber.EIGHT
        CardNumber.EIGHT -> CardNumber.NINE
        CardNumber.NINE -> CardNumber.TEN
        CardNumber.TEN -> CardNumber.JACK
        CardNumber.JACK -> CardNumber.QUEEN
        CardNumber.QUEEN -> CardNumber.KING
        CardNumber.KING -> CardNumber.ACE
        CardNumber.ACE -> CardNumber.TWO
    }

    override fun equals(other: Any?): Boolean {
        return other is Card && other.cardNumber == cardNumber && other.suit == suit
    }

}