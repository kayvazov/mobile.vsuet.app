package com.example.prepodsearch.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.prepodsearch.R
import com.example.prepodsearch.RoomDataBase.LessonDataBase.LessonDataBase
import com.example.prepodsearch.RoomDataBase.TeacherDataBase.Teacher
import com.example.prepodsearch.databinding.ActivityMainBinding
import com.example.prepodsearch.RoomDataBase.TeacherDataBase.TeacherDataBase
import com.example.prepodsearch.searchDialogFragment.SearchDialogFragment
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var viewModelFactory: MainViewModelFactory

    fun checkPairNumber(): String {
        val timeFormatter = SimpleDateFormat("HH:MM")
        val time = timeFormatter.format(java.util.Calendar.getInstance().time)
        if ("8:00" < time && time < "9:45") {
            return "8:00-9:45"
        } else if ("9:55" < time && time < "11:20") {
            return "9:55-11:20"
        } else if ("11:50" < time && time < "13:25") {
            return "11:50-13:25"
        } else if ("13:35" < time && time < "15:10") {
            return "13:35-15:10"
        } else if ("15:20" < time && time < "16:55") {
            return "15:20-16:55"
        } else if ("17:05" < time && time < "18:40") {
            return "17:05-18:40"
        } else if ("18:50" < time && time < "20:25") {
            return "18:50-20:25"
        } else if ("8:00" > time) {
            return "Рабочий день не начался"
        } else if ("20:25" > time) {
            return "Рабочий день закончен"
        } else return "Перерыв"
    }

    fun getTeachersNames(teachers: List<Teacher>?): List<String> {

        val teachersNames = mutableListOf<String>()
        if(teachers != null) {
            for (teacher in teachers) {
                teachersNames.add(teacher.teacherName)
            }
        }

        return teachersNames
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val bindingView = binding.root
        setContentView(bindingView)

        val teacherDataBase = TeacherDataBase.getInstance(this.applicationContext)
        val lessonDataBase = LessonDataBase.getInstance(this.applicationContext)
        val teacherDataSource = teacherDataBase.teacherDataBaseDao
        val lessonDataSource = lessonDataBase.lessonDataBaseDao
        val application = requireNotNull(this).application

        viewModelFactory = MainViewModelFactory(teacherDataSource, lessonDataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)



        val facultySpinnerData = resources.getStringArray(R.array.Факультеты).toMutableList()
        val facultyAdapter = ArrayAdapter(this, R.layout.spinner_text_view, facultySpinnerData)

        binding.apply {

            facultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    var check = 0
                    if (++check > 1) {

                        val tempData = parent?.getItemAtPosition(0)
                        facultySpinnerData[0] = parent?.getItemAtPosition(pos).toString()
                        facultySpinnerData[pos] = tempData.toString()
                        facultySpinner.setSelection(0)

                        facultyAdapter.notifyDataSetChanged()
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }





            searchButton.setOnClickListener {

                val teacherName = binding.teacherSpinner.selectedItem ?: ""
                val pairTime = checkPairNumber()
                val currentLesson = viewModel.getCurrentLesson(pairTime, teacherName.toString())
                val dialog = SearchDialogFragment(
                    currentLesson.value,
                    pairTime
                )

                dialog.show(supportFragmentManager, "RESPONSE")
            }

            facultySpinner.adapter = facultyAdapter

            val teacherData =
                viewModel.getFacultyTeachers(binding.facultySpinner.selectedItem.toString()).value

            val teacherSpinnerData = getTeachersNames(teacherData)
            val teacherAdapter = ArrayAdapter(this@MainActivity, R.layout.spinner_text_view, teacherSpinnerData)
            teacherSpinner.adapter = teacherAdapter

        }


    }


}