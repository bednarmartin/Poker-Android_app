package com.example.poker

object Cards {
    private val numbers = arrayOf(
        CardNumber.TWO,
        CardNumber.THREE,
        CardNumber.FOUR,
        CardNumber.FIVE,
        CardNumber.SIX,
        CardNumber.SEVEN,
        CardNumber.EIGHT,
        CardNumber.NINE,
        CardNumber.TEN,
        CardNumber.JACK,
        CardNumber.QUEEN,
        CardNumber.KING,
        CardNumber.ACE
    )
    private val suits = arrayOf(
        Suit.CLUBS,
        Suit.DIAMONDS,
        Suit.SPADES,
        Suit.HEARTS
    )
    val cards: MutableList<Card> = makeCards()

    private fun makeCards(): MutableList<Card> {
        val cards: MutableList<Card> = mutableListOf()
        for (number in numbers) {
            for (suit in suits) {
                cards.add(Card(suit, number))
            }
        }
        return cards
    }
}