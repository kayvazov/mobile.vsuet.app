package com.example.vsuet.API

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class RatingProperty(
    @field:Json(name = "status") val status: Boolean,
    @field:Json(name = "data") val data: FullRatingData,
)

data class FullRatingData(
    @field:Json(name = "rating") val rating: List<RatingItem>,
    @field:Json(name = "student") val student: StudentItem,
)

data class StudentItem(
    @field:Json(name = "_id") val _id: String,
    @field:Json(name = "recordBookNum") val recordBookNum: String,
    @field:Json(name = "faculty") val faculty: FacultyItem,
    @field:Json(name = "groups") val groups: List<GroupItem>,
    @field:Json(name = "ratingUpdatedAt") val ratingUpdatedAt: String,
)

data class FacultyItem(
    @field:Json(name = "_id") val _id: String,
    @field:Json(name = "createdTime") val createdTime: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "value") val value: String,
    @field:Json(name = "__v") val __v: String
)

data class GroupItem(
    @field:Json(name = "_id") val _id: String,
    @field:Json(name = "faculty") val faculty: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "value") val value: String,
    @field:Json(name = "__v") val __v: String
)


data class UpgradedRatingContainer(
    val items: List<UpgradedRatingItem>
)

@Entity
data class RatingItem(
    @PrimaryKey @ColumnInfo(name = "_id") @field:Json(name = "_id") val _id: String,
    @ColumnInfo(name = "value") @field:Json(name = "value") val value: List<String>,
    @ColumnInfo(name = "upgradedRating") @field:Json(name = "upgradedRating") val upgradedRating: List<UpgradedRatingItem>,
    @ColumnInfo(name = "createdTime") @field:Json(name = "createdTime") val createdTime: String,
    @ColumnInfo(name = "student") @field:Json(name = "student") val student: String,
    @ColumnInfo(name = "faculty") @field:Json(name = "faculty") val faculty: String,
    @ColumnInfo(name = "group") @field:Json(name = "group") val group: String,
    @ColumnInfo(name = "lesson") @field:Json(name = "lesson") val lesson: RatingLesson,
    @ColumnInfo(name = "__v") @field:Json(name = "__v") val __v: String
)

data class RatingLesson(
    @field:Json(name = "_id") val _id: String,
    @field:Json(name = "faculty") val faculty: String,
    @field:Json(name = "group") val group: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "__v") val __v: String,
    @field:Json(name = "href") val href: String,
    @field:Json(name = "isClose") val isClose: Boolean,
    @field:Json(name = "header") val header: List<HeaderItem>,
    @field:Json(name = "information") val information: InformationItem
)

data class InformationItem(
    @field:Json(name = "department") val department: String,
    @field:Json(name = "teacher") val teacher: String,
    @field:Json(name = "hours") val hours: String,
    @field:Json(name = "semester") val semester: String,
    @field:Json(name = "course") val course: String,
    @field:Json(name = "updatedAt") val updatedAt: String
)

data class HeaderItem(
    @field:Json(name = "trIndex") val trIndex: String,
    @field:Json(name = "children") val children: List<HeaderChildrenItem>
)

data class HeaderChildrenItem(
    @field:Json(name = "text") val text: String,
    @field:Json(name = "colSpan") val colSpan: Int,
    @field:Json(name = "rowSpan") val rowSpan: Int
)

data class UpgradedRatingItem(
    @field:Json(name = "items") val items: List<DisciplineInnerPoint>,
    @field:Json(name = "total") val total: DisciplineItem
)


data class DisciplineItem(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "score") val score: String
)

data class DisciplineInnerPoint(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "weight") val weight: String,
    @field:Json(name = "score") val score: String
)