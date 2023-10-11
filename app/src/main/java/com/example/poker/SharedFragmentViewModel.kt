package com.example.poker

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedFragmentViewModel : ViewModel() {

    private val player1 = Player(1, null, null, Constants.MONEY_AT_THE_BEGINNING, 0)
    private val player2 = Player(2, null, null, Constants.MONEY_AT_THE_BEGINNING, 0)

    private val game = Game(player1, player2)

    private val _actualPlayer: MutableLiveData<Player> = MutableLiveData(player1)
    val actualPlayer: LiveData<Player>
        get() = _actualPlayer

    private val _otherPlayer: MutableLiveData<Player> = MutableLiveData(player2)
    val otherPlayer: LiveData<Player>
        get() = _otherPlayer

    var areCardsVisible: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _actualPlayerCard1: MutableLiveData<Card?> = MutableLiveData()
    val actualPlayerCard1: LiveData<Card?>
        get() = _actualPlayerCard1

    private val _actualPlayerCard2: MutableLiveData<Card?> = MutableLiveData()
    val actualPlayerCard2: LiveData<Card?>
        get() = _actualPlayerCard2

    private val _actualPlayerMoney: MutableLiveData<Int> = MutableLiveData(0)
    val actualPlayerMoney: LiveData<Int>
        get() = _actualPlayerMoney

    private val _actualPlayerBet: MutableLiveData<Int> = MutableLiveData(0)
    val actualPlayerBet: LiveData<Int>
        get() = _actualPlayerBet

    private val _otherPlayerBet: MutableLiveData<Int> = MutableLiveData(0)
    val otherPlayerBet: LiveData<Int>
        get() = _otherPlayerBet

    private val _cardsOnDesk: MutableLiveData<MutableList<Card?>> =
        MutableLiveData(mutableListOf(null, null, null, null, null))
    val cardsOnDesk: LiveData<MutableList<Card?>>
        get() = _cardsOnDesk

    private val _actualBet: MutableLiveData<Int> = MutableLiveData(0)
    val actualBet: LiveData<Int>
        get() = _actualBet

    var playerCard1Drawable: MutableLiveData<Drawable> = MutableLiveData()
    var playerCard2Drawable: MutableLiveData<Drawable> = MutableLiveData()

    var CardOnDesk1Drawable: MutableLiveData<Drawable> = MutableLiveData()
    var CardOnDesk2Drawable: MutableLiveData<Drawable> = MutableLiveData()
    var CardOnDesk3Drawable: MutableLiveData<Drawable> = MutableLiveData()
    var CardOnDesk4Drawable: MutableLiveData<Drawable> = MutableLiveData()
    var CardOnDesk5Drawable: MutableLiveData<Drawable> = MutableLiveData()


    private val _isTheFlop: MutableLiveData<Boolean> = MutableLiveData(false)
    val isTheFlop: LiveData<Boolean>
        get() = _isTheFlop

    private val _isTheTurn: MutableLiveData<Boolean> = MutableLiveData(false)
    val isTheTurn: LiveData<Boolean>
        get() = _isTheTurn

    private val _isTheRiver: MutableLiveData<Boolean> = MutableLiveData(false)
    val isTheRiver: LiveData<Boolean>
        get() = _isTheRiver

    var isCheck = false


    private val _theWinner: MutableLiveData<Int> = MutableLiveData(0)
    val theWinner: LiveData<Int>
        get() = _theWinner

    private val _showWinner: MutableLiveData<Boolean> = MutableLiveData(false)
    val showWinner: LiveData<Boolean>
        get() = _showWinner


    init {
        newGame()
    }


    fun newGame() {
        areCardsVisible.value = false
        _showWinner.value = false
        if (player1.money == 0 || player2.money == 0) {
            player1.money = Constants.MONEY_AT_THE_BEGINNING
            player2.money = Constants.MONEY_AT_THE_BEGINNING
        }
        game.newGame()
        _actualBet.value = 0
        for (i in 0..4) {
            _cardsOnDesk.value?.set(i, null)
        }
        if (game.smallBlindPlayer.money < Constants.SMALL_BLIND) {
            _actualBet.value = _actualBet.value?.plus(game.smallBlindPlayer.money)
            game.smallBlindPlayer.bet = game.smallBlindPlayer.money
            game.smallBlindPlayer.money = 0
        } else {
            _actualBet.value = _actualBet.value?.plus(Constants.SMALL_BLIND)
            game.smallBlindPlayer.money -= Constants.SMALL_BLIND
            game.smallBlindPlayer.bet = Constants.SMALL_BLIND
        }
        if (game.bigBlindPlayer.money < Constants.BIG_BLIND) {
            _actualBet.value = _actualBet.value?.plus(game.bigBlindPlayer.money)
            game.bigBlindPlayer.bet = game.bigBlindPlayer.money
            game.bigBlindPlayer.money = 0
        } else {
            _actualBet.value = _actualBet.value?.plus(Constants.BIG_BLIND)
            game.bigBlindPlayer.money -= Constants.BIG_BLIND
            game.bigBlindPlayer.bet = Constants.BIG_BLIND
        }
        setActualPlayer(game.smallBlindPlayer)
        _isTheFlop.value = false
        _isTheRiver.value = false
        _isTheTurn.value = false
        isCheck = false

    }

    fun theFlop() {
        _isTheFlop.value = true
        val cards = game.getTheFlop()
        for (i in 0..2) {
            _cardsOnDesk.value?.set(i, cards[i])
        }
        _isTheFlop.value = true
        isCheck = false
    }

    fun theTurn() {
        _isTheTurn.value = true
        val card = game.getTheTurn()
        _cardsOnDesk.value?.set(3, card)
        _isTheTurn.value = true
        isCheck = false
    }

    fun theRiver() {
        _isTheRiver.value = true
        val card = game.getTheRiver()
        _cardsOnDesk.value?.set(4, card)
        _isTheRiver.value = true
        isCheck = false
    }

    private fun setActualPlayer(player: Player) {
        _actualPlayer.value = player
        _actualPlayerMoney.value = player.money
        _actualPlayerCard1.value = player.card1
        _actualPlayerCard2.value = player.card2
        _actualPlayerBet.value = player.bet
        _otherPlayer.value = if (player == player1) player2 else player1
        _otherPlayerBet.value = _otherPlayer.value!!.bet
    }

    fun raise() {
        val actualPlayer = _actualPlayer.value!!
        val otherPlayer = _otherPlayer.value!!
        val difference = otherPlayer.bet - actualPlayer.bet
        if (actualPlayer.money >= Constants.RAISE_MONEY + difference) {
            actualPlayer.money -= Constants.RAISE_MONEY + difference
            actualPlayer.bet += Constants.RAISE_MONEY + difference
            _actualBet.value = _actualBet.value?.plus(Constants.RAISE_MONEY + difference)
        } else {
            actualPlayer.bet += actualPlayer.money
            _actualBet.value = _actualBet.value?.plus(actualPlayer.money)
            actualPlayer.money = 0
        }
        setActualPlayer(otherPlayer)
        isCheck = false
    }


    fun call() {
        val actualPlayer = _actualPlayer.value!!
        val otherPlayer = _otherPlayer.value!!
        val difference = otherPlayer.bet - actualPlayer.bet
        if (difference < 0) {
            setActualPlayer(otherPlayer)
            isCheck = false
            return
        }
        if (actualPlayer.money >= difference) {
            actualPlayer.money -= difference
            actualPlayer.bet += difference
            _actualBet.value = _actualBet.value?.plus(difference)
        } else {
            actualPlayer.bet += actualPlayer.money
            _actualBet.value = _actualBet.value?.plus(actualPlayer.money)
            actualPlayer.money = 0
        }
        setActualPlayer(otherPlayer)
        isCheck = false
    }

    fun fold() {
        val actualPlayer = _actualPlayer.value!!
        val otherPlayer = _otherPlayer.value!!
        _actualPlayerBet.value = 0
        actualPlayer.bet = 0
        otherPlayer.bet = 0
        _otherPlayerBet.value = 0
        otherPlayer.money += _actualBet.value!!
        _actualBet.value = 0
        newGame()
    }

    fun check() {
        isCheck = !isCheck
        val otherPlayer = _otherPlayer.value!!
        setActualPlayer(otherPlayer)

    }

    fun endGame() {
        val firstPlayerCards = mutableListOf(player1.card1!!, player1.card2!!)
        val secondPlayerCards = mutableListOf(player2.card1!!, player2.card2!!)
        for (i in 0..4) {
            firstPlayerCards.add(_cardsOnDesk.value?.get(i)!!)
            secondPlayerCards.add(_cardsOnDesk.value?.get(i)!!)
        }
        val ending = WinnerDeterminer.determine(firstPlayerCards, secondPlayerCards)
        _theWinner.value = ending
        when (ending) {
            1 -> {
                player1.money += _actualBet.value!!
            }
            2 -> {
                player2.money += _actualBet.value!!
            }
            0 -> {
                val money = _actualBet.value!!
                player1.money += money / 2
                player2.money += money / 2
            }
        }
        _showWinner.value = true

    }
}