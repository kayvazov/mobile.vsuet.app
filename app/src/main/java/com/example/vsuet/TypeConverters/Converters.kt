package com.example.vsuet.TypeConverters

import androidx.room.TypeConverter
import com.example.vsuet.API.*
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromValue(value: List<String>): String {
        return value.toString()
    }

    @TypeConverter
    fun toValue(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromTime(time: LessonTime): String {
        return time.start + "," + time.end
    }

    @TypeConverter
    fun toTime(time: String): LessonTime {
        val timePieces = time.split(",")
        return LessonTime(timePieces[0], timePieces[1])
    }

    @TypeConverter
    fun fromAttachments(attachments: List<AttachmentItem>?): String {
        var result = ""
        if (attachments != null) {
            for (item in attachments) {
                result += Gson().toJson(item) + ","
            }
        }
        return result
    }

    @TypeConverter
    fun fromTeacherLessons(teacherLessons: List<TeacherLesson>?): String {
        var result = ""
        if (teacherLessons != null) {
            for (item in teacherLessons) {
                result += Gson().toJson(item) + ","
            }
        }
        return result
    }

    @TypeConverter
    fun toTeacherLessons(teacherLessons: String): List<TeacherLesson> {
        val result = Gson().fromJson("[$teacherLessons]", Array<TeacherLesson>::class.java)
        return result.toList()
    }

    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        var result = ""
        if (list != null) {
            for (item in list) {
                result += "$item,"
            }
        }
        return result
    }

    @TypeConverter
    fun toIntList(list: String): List<Int> {
        val ourIntList = list.split(",").subList(0, list.lastIndex - 1)
        val result = mutableListOf<Int>()
        for (i in ourIntList){
            result.add(i.toInt())
        }
        return result
    }


    @TypeConverter
    fun toAttachments(json: String?): List<AttachmentItem> {
        val result = Gson().fromJson("[$json]", Array<AttachmentItem>::class.java)
        return result.toList()
    }

    @TypeConverter
    fun fromRatingLesson(ratingLesson: RatingLesson): String? {
        return Gson().toJson(ratingLesson)
    }

    @TypeConverter
    fun toRatingLesson(ratingLesson: String?): RatingLesson {
        return Gson().fromJson(ratingLesson, RatingLesson::class.java)
    }

    @TypeConverter
    fun fromUpgradedRating(upgradedRating: List<UpgradedRatingItem>?): String {
        var result = ""
        if (upgradedRating != null) {
            for (item in upgradedRating) {
                result += Gson().toJson(item) + ","
            }
        }
        val last = result.lastIndex
        return result.substring(0, last)
    }

    @TypeConverter
    fun toUpgradedRating(upgradedRating: String): List<UpgradedRatingItem> {
        val result = Gson().fromJson("[$upgradedRating]", Array<UpgradedRatingItem>::class.java)
        return result.toList()
    }

}