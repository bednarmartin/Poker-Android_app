package com.example.poker

object WinnerDeterminer {
    fun determine(firstPlayerCards: List<Card>, secondPlayerCards: List<Card>): Int {
        val firstScore = getScore(firstPlayerCards)
        val secondScore = getScore(secondPlayerCards)
        if (firstScore > secondScore) {
            return 1
        }
        if (firstScore < secondScore) {
            return 2
        }
        if (firstScore == 1 && secondScore == 1) {
            val firstCard = getHighestCard(firstPlayerCards)
            val secondCard = getHighestCard(secondPlayerCards)
            if (firstCard.rank > secondCard.rank) {
                return 1
            }
            if (firstCard.rank < secondCard.rank) {
                return 2
            }
            return 0
        }
        return 0

    }

    private fun getScore(cards: List<Card>): Int {
        if (royalFlush(cards)) {
            return 10
        } else if (straightFlush(cards)) {
            return 9
        } else if (fourOfAKind(cards)) {
            return 8
        } else if (fullHouse(cards)) {
            return 7
        } else if (flush(cards)) {
            return 6
        } else if (straight(cards)) {
            return 5
        } else if (threeOfAKind(cards)) {
            return 4
        } else if (twoPairs(cards)) {
            return 3
        } else if (pair(cards)) {
            return 2
        }
        return 1
    }


    private fun getCardNumbers(cards: List<Card>): List<CardNumber> {
        val cardNumbers = mutableListOf<CardNumber>()
        for (card in cards) {
            cardNumbers.add(card.cardNumber)
        }
        return cardNumbers
    }


    private fun flush(cards: List<Card>): Boolean {
        for (suit in Suit.values()) {
            var counter = 0
            for (card in cards) {
                if (card.suit == suit) {
                    counter++
                }
            }
            if (counter == 5) {
                return true
            }
        }
        return false

    }

    private fun straight(cards: List<Card>): Boolean {
        val cardNumbers = getCardNumbers(cards)
        for (cardNumber in cardNumbers) {
            var counter = 0
            var actualCardNumber = cardNumber
            repeat(4) {
                if (cardNumbers.contains(actualCardNumber.next())) {
                    counter++
                    actualCardNumber = actualCardNumber.next()
                }
            }
            if (counter == 4) {
                return true
            }
        }
        return false
    }

    private fun straightFlush(cards: List<Card>): Boolean {
        for (card in cards) {
            var counter = 0
            var actualCardNumber = card.cardNumber
            val actualSuit = card.suit
            repeat(4) {
                if (cards.contains(Card(actualSuit, actualCardNumber.next()))) {
                    counter++
                    actualCardNumber = actualCardNumber.next()
                }
            }
            if (counter == 4) {
                return true
            }
        }
        return false
    }


    private fun fullHouse(cards: List<Card>): Boolean {
        for (i in 0..cards.lastIndex) {
            for (j in i + 1..cards.lastIndex) {
                for (k in j + 1..cards.lastIndex) {
                    val card1 = cards[i]
                    val card2 = cards[j]
                    val card3 = cards[k]
                    if (card1.cardNumber == card2.cardNumber && card2.cardNumber == card3.cardNumber) {
                        val newCards = cards.toMutableList()
                        newCards.remove(card1)
                        newCards.remove(card2)
                        newCards.remove(card3)
                        if (pair(newCards)) {
                            return true
                        }
                    }

                }

            }
        }
        return false
    }

    private fun royalFlush(cards: List<Card>): Boolean {
        for (card in cards) {
            if (card.cardNumber == CardNumber.TEN) {
                var counter = 0
                var actualCardNumber = card.cardNumber
                val actualSuit = card.suit
                repeat(4) {
                    if (cards.contains(Card(actualSuit, actualCardNumber.next()))) {
                        counter++
                        actualCardNumber = actualCardNumber.next()
                    }
                }
                if (counter == 4) {
                    return true
                }
            }
        }
        return false
    }

    private fun pair(cards: List<Card>): Boolean {
        for (i in 0..cards.lastIndex) {
            for (j in i + 1..cards.lastIndex) {
                val card1 = cards[i]
                val card2 = cards[j]
                if (card1.cardNumber == card2.cardNumber) {
                    return true
                }

            }
        }
        return false
    }

    private fun twoPairs(cards: List<Card>): Boolean {
        for (i in 0..cards.lastIndex) {
            for (j in i + 1..cards.lastIndex) {
                val card1 = cards[i]
                val card2 = cards[j]
                if (card1.cardNumber == card2.cardNumber) {
                    val newCards = cards.toMutableList()
                    newCards.remove(card1)
                    newCards.remove(card2)
                    if (pair(newCards)) {
                        return true

                    }
                }
            }
        }
        return false
    }

    private fun threeOfAKind(cards: List<Card>): Boolean {
        for (i in 0..cards.lastIndex) {
            for (j in i + 1..cards.lastIndex) {
                for (k in j + 1..cards.lastIndex) {
                    val card1 = cards[i]
                    val card2 = cards[j]
                    val card3 = cards[k]
                    if (card1.cardNumber == card2.cardNumber && card2.cardNumber == card3.cardNumber) {
                        return true
                    }

                }

            }
        }
        return false
    }

    private fun fourOfAKind(cards: List<Card>): Boolean {
        for (i in 0..cards.lastIndex) {
            for (j in i + 1..cards.lastIndex) {
                for (k in j + 1..cards.lastIndex) {
                    for (l in k + 1..cards.lastIndex) {
                        val card1 = cards[i]
                        val card2 = cards[j]
                        val card3 = cards[k]
                        val card4 = cards[l]
                        if (card1.cardNumber == card2.cardNumber && card2.cardNumber == card3.cardNumber && card3.cardNumber == card4.cardNumber) {
                            return true
                        }

                    }

                }

            }
        }
        return false
    }

    private fun getHighestCard(cards: List<Card>): CardNumber {
        val cardNumbers = getCardNumbers(cards)
        var max = cardNumbers[0]
        for (cardNumber in cardNumbers) {
            if (cardNumber.rank > max.rank) {
                max = cardNumber
            }

        }
        return max
    }
}