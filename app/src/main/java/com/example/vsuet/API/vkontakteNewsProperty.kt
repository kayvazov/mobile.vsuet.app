package com.example.vsuet.API

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class NewsProperty(
    @field:Json(name = "response") val response: Response
)

data class Response(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "items") val items: List<NewsPost>
)

@Entity
data class NewsPost(
    @PrimaryKey @ColumnInfo(name = "date") @field:Json(name = "date") val date: Int,
    @ColumnInfo(name = "text") @field:Json(name = "text") val text: String,
    @ColumnInfo(name = "attachments") @field:Json(name = "attachments") val attachments: List<AttachmentItem>? = listOf()
)


data class AttachmentItem(
    @field:Json(name = "type") val type: String,
    @field:Json(name = "photo") val photo: PhotoItem = PhotoItem(listOf()),
    @field:Json(name = "link") val link: LinkItem = LinkItem("", "")
)

data class LinkItem(
    @field:Json(name = "url") val url: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "photo") val photo: PhotoItem = PhotoItem(listOf())
)

data class PhotoItem(
    @field:Json(name = "sizes") val sizes: List<PhotoSize>
)

data class PhotoSize(
    @field:Json(name = "url") val url: String
)