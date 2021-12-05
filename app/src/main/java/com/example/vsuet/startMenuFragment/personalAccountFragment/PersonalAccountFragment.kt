package com.example.vsuet.startMenuFragment.personalAccountFragment

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.R
import com.example.vsuet.databinding.FragmentPersonalAccountBinding
import com.example.vsuet.listDialogFragment.ListDialogFragment
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase
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
        val repository = RepositoryDataBase.getInstance(application)
        val repositoryDataSource = repository.repositoryDao
        viewModelFactory = PersonalAccountViewModelFactory(application, repositoryDataSource)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PersonalAccountViewModel::class.java)

        binding.apply {
            viewModel.internetConnection.observe(viewLifecycleOwner) {
                if (!it) {
                    groupText.visibility = View.GONE
                    groupTextButton.visibility = View.GONE
                    underGroupButton.visibility = View.GONE
                    underGroupText.visibility = View.GONE
                    personalScorerContainer.visibility = View.GONE
                    personalScorerText.visibility = View.GONE
                    noInternetText.visibility = View.VISIBLE
                }
            }

            groupTextButton.text =
                requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
                    .getString("groupNumber", "Номер группы")
            underGroupButton.text =
                requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
                    .getString("underGroupNumber", "1")
            personalScorerContainer.text = SpannableStringBuilder(
                requireActivity().getSharedPreferences(
                    "accountSettings",
                    Context.MODE_PRIVATE
                ).getString("personalScorerNumber", "")
            )

            requireActivity().findViewById<FloatingActionButton>(R.id.personalAccountButton).apply {
                animate().alpha(0f).duration = 500
            }

            viewModel.getGroups()
            viewModel.groups.observeForever { list ->
                val groupNames = list.map { it.name }.toMutableList()
                groupTextButton.setOnClickListener {
                    val dialog = ListDialogFragment("Settings", null, groupNames)
                    dialog.show(requireActivity().supportFragmentManager, "settingsChanger")
                }

            }

            underGroupButton.setOnClickListener {
                settingsEditor.putBoolean("isGroupChanged", true).apply()
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
            personalScorerContainer.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE && personalScorerContainer.text.toString().length == 6) {
                    settingsEditor.putString(
                        "personalScorerNumber",
                        personalScorerContainer.text.toString()
                    ).apply()
                    viewModel.validateStudent(personalScorerContainer.text.toString())
                    return@setOnEditorActionListener true
                }
                false
            }

            viewModel.group.observe(viewLifecycleOwner) { value ->
                println(value)
                if (value == "Invalid") {
                    Toast.makeText(
                        requireContext(),
                        "Неправильно введён номер зачётки",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (groupTextButton.text.toString() != "Номер группы") {
                        settingsEditor.putString("groupNumber", value)
                        groupTextButton.text = value
                    }
                }
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