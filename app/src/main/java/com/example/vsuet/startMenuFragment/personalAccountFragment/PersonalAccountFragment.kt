package com.example.vsuet.startMenuFragment.personalAccountFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.R
import com.example.vsuet.databinding.FragmentPersonalAccountBinding
import com.example.vsuet.listDialogFragment.ListDialogFragment
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDataBase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PersonalAccountFragment : Fragment() {

    private lateinit var binding: FragmentPersonalAccountBinding
    private lateinit var viewModel: PersonalAccountViewModel
    private lateinit var viewModelFactory: PersonalAccountViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPersonalAccountBinding.inflate(inflater)
        val settingsEditor =
            requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
                .edit()

        val application = requireNotNull(this.activity).application
        val lessonDataBase = LessonDataBase.getInstance(requireContext())
        val lessonDataSource = lessonDataBase.lessonDataBaseDao
        viewModelFactory = PersonalAccountViewModelFactory(application, lessonDataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PersonalAccountViewModel::class.java)

        binding.apply {

            groupTextButton.text = requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE).getString("groupNumber", "Номер группы")
            underGroupButton.text = requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE).getString("underGroupNumber", "1")

            requireActivity().findViewById<FloatingActionButton>(R.id.personalAccountButton).apply {
                animate().alpha(0f).duration = 500
            }

            val lessons = viewModel.getGroups()
            val groups = mutableListOf<String>()
            lessons.observeForever{ list ->
                for(item in list){
                    if(!groups.contains(item.groupName)) {
                        groups.add(item.groupName)
                    }
                }
                groupTextButton.setOnClickListener {
                    val dialog = ListDialogFragment("Settings", null, groups, true)
                    dialog.show(requireActivity().supportFragmentManager, "settingsChanger")
                }

            }

            underGroupButton.setOnClickListener {
                val thisText = underGroupButton.text
                underGroupButton.text = when (thisText) {
                    "2" -> {
                        "1"
                    }
                    else -> {
                        (thisText.toString().toInt() + 1).toString()
                    }
                }
                settingsEditor.putString("underGroupNumber", underGroupButton.text.toString())
                    .apply()
                println(
                    requireActivity().getSharedPreferences(
                        "accountSettings",
                        Context.MODE_PRIVATE
                    ).getString("personalScorerNumber", "")
                )
            }
            personalScorerContainer.addTextChangedListener {
                settingsEditor.putString(
                    "personalScorerNumber",
                    personalScorerContainer.text.toString()
                ).apply()
            }

            return root
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<FloatingActionButton>(R.id.personalAccountButton).apply {
            animate().alpha(1f).duration = 500
            isClickable = true
        }
    }

}