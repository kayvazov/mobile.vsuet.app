package com.example.vsuet.pointDialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.vsuet.API.DisciplineInnerPoint
import com.example.vsuet.R
import com.example.vsuet.databinding.PointsDialogFragmentBinding


class PointDialogFragment(private val pointsInfo: List<DisciplineInnerPoint>) : DialogFragment() {

    private lateinit var binding: PointsDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = PointsDialogFragmentBinding.inflate(layoutInflater)

        binding.apply {
            dialog?.window?.attributes?.windowAnimations = R.style.dialog_anim
            lectionsPoint.text = pointsInfo[0].name + "(" + pointsInfo[0].weight + ")" + " - " + pointsInfo[0].score
            practicePoint.text = pointsInfo[3].name + "(" + pointsInfo[3].weight + ")" + " - " + pointsInfo[3].score
            labPoint.text = pointsInfo[2].name + "(" + pointsInfo[2].weight + ")" + " - " + pointsInfo[2].score
            otherPoint.text = pointsInfo[1].name + "(" + pointsInfo[1].weight + ")" + " - " + pointsInfo[1].score
        }

        return binding.root
    }

}