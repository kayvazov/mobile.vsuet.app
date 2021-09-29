package com.example.prepodsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.prepodsearch.RoomDataBase.LessonDataBase.LessonDataBase
import com.example.prepodsearch.databinding.ActivityMainBinding
import com.example.prepodsearch.RoomDataBase.TeacherDataBase.TeacherDataBase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val bindingView = binding.root
        setContentView(bindingView)

        var check = 0

        val facultySpinnerData = resources.getStringArray(R.array.Факультеты).toMutableList()
        val facultyAdapter = ArrayAdapter(
            this,
            R.layout.spinner_text_view,
            facultySpinnerData
        )


        binding.apply {

            facultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {


                    if (++check > 1) {

                        val tempData = parent?.getItemAtPosition(0)
                        facultySpinnerData[0] = parent?.getItemAtPosition(pos).toString()
                        facultySpinnerData[pos] = tempData.toString()
                        facultySpinner.setSelection(0)
                        println(facultySpinnerData)


                        facultyAdapter.notifyDataSetChanged()
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
            facultySpinner.adapter = facultyAdapter
        }


        val teacherDataBase = TeacherDataBase.getInstance(this.applicationContext)
        val lessonDataBase = LessonDataBase.getInstance(this.applicationContext)
        val teacherDataSource = teacherDataBase.teacherDataBaseDao
        val lessonDataSource = lessonDataBase.lessonDataBaseDao
        val application = requireNotNull(this).application

        viewModelFactory = MainViewModelFactory(teacherDataSource, lessonDataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val teacherData =
            viewModel.getFacultyTeachers(binding.facultySpinner.selectedItem.toString())

    }


}