package com.example.poker

enum class CardNumber(val number: String, val rank: Int) {
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9),
    TEN("ten", 10),
    JACK("jack", 11),
    QUEEN("queen", 12),
    KING("king", 13),
    ACE("ace", 14);

    fun next(): CardNumber = when(this){
        TWO -> THREE
        THREE -> FOUR
        FOUR -> FIVE
        FIVE -> SIX
        SIX -> SEVEN
        SEVEN -> EIGHT
        EIGHT -> NINE
        NINE -> TEN
        TEN -> JACK
        JACK -> QUEEN
        QUEEN -> KING
        KING -> ACE
        ACE -> TWO
    }
}