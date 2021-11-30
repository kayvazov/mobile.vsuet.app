package com.example.vsuet.TypeConverters

import androidx.room.TypeConverter
import com.example.vsuet.API.AttachmentItem
import com.example.vsuet.API.LessonTime
import com.example.vsuet.API.RatingLesson
import com.example.vsuet.API.UpgradedRatingItem
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
        println(result)
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