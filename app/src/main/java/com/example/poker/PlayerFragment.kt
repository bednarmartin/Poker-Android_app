package com.example.poker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.poker.databinding.FragmentPlayerBinding


class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding

    private val sharedViewModel: SharedFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.sharedViewModel = sharedViewModel
    }

    fun onChangeVisibility() {
        sharedViewModel.areCardsVisible.value = sharedViewModel.areCardsVisible.value?.not()
        sharedViewModel.playerCard1Drawable.value =
            ContextCompat.getDrawable(requireContext(), getFirstCardDrawable())
        sharedViewModel.playerCard2Drawable.value =
            ContextCompat.getDrawable(requireContext(), getSecondCardDrawable())
    }

    fun check() {
        sharedViewModel.areCardsVisible.value = false
        if (sharedViewModel.isCheck) {
            if (!sharedViewModel.isTheFlop.value!!) {
                sharedViewModel.check()
                sharedViewModel.theFlop()
                sharedViewModel.CardOnDesk1Drawable.value =
                    ContextCompat.getDrawable(requireContext(), getCardDrawable(0))!!
                sharedViewModel.CardOnDesk2Drawable.value =
                    ContextCompat.getDrawable(requireContext(), getCardDrawable(1))!!
                sharedViewModel.CardOnDesk3Drawable.value =
                    ContextCompat.getDrawable(requireContext(), getCardDrawable(2))!!


            } else if (!sharedViewModel.isTheTurn.value!!) {

                sharedViewModel.check()
                sharedViewModel.theTurn()
                sharedViewModel.CardOnDesk4Drawable.value =
                    ContextCompat.getDrawable(requireContext(), getCardDrawable(3))!!
            } else if (!sharedViewModel.isTheRiver.value!!) {

                sharedViewModel.check()
                sharedViewModel.theRiver()
                sharedViewModel.CardOnDesk5Drawable.value =
                    ContextCompat.getDrawable(requireContext(), getCardDrawable(4))!!
            } else {
                sharedViewModel.endGame()
                return
            }
        } else {
            sharedViewModel.check()
        }
    }

    private fun getCardDrawable(i: Int): Int {
        return resources.getIdentifier(
            sharedViewModel.cardsOnDesk.value?.get(i)?.getDrawable(),
            "drawable",
            requireContext().packageName
        )
    }

    fun fold() {
        sharedViewModel.areCardsVisible.value = false
        sharedViewModel.fold()
    }

    fun call() {
        sharedViewModel.areCardsVisible.value = false
        sharedViewModel.call()
    }

    fun raise() {
        sharedViewModel.areCardsVisible.value = false
        sharedViewModel.raise()
    }


    private fun getFirstCardDrawable(): Int {
        return resources.getIdentifier(
            sharedViewModel.actualPlayerCard1.value?.getDrawable(),
            "drawable",
            requireContext().packageName
        )
    }

    private fun getSecondCardDrawable(): Int {
        return resources.getIdentifier(
            sharedViewModel.actualPlayerCard2.value?.getDrawable(),
            "drawable",
            requireContext().packageName
        )
    }

}
