package com.zaurh.notefirelocal.data.local

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id") val noteId: Int? = null,
    @ColumnInfo(name = "note_title") val noteTitle: String = "",
    @ColumnInfo(name = "note_description") val noteDescription: String = "",
    @ColumnInfo(name = "background_color") val backgroundColor: Int = Color.White.toArgb(),
    @ColumnInfo(name = "title_color") val title_color: Int = Color.Black.toArgb(),
    @ColumnInfo(name = "note_color") val note_color: Int = Color.Black.toArgb(),
    @ColumnInfo(name = "title_font_size") val title_font_size: Float = 20F,
    @ColumnInfo(name = "note_font_size") val note_font_size: Float = 14F,
    @ColumnInfo(name = "title_italic") val title_italic: Boolean = false,
    @ColumnInfo(name = "note_italic") val note_italic: Boolean = false,
    @ColumnInfo(name = "title_bold") val title_bold: Boolean = true,
    @ColumnInfo(name = "note_bold") val note_bold: Boolean = false,
    @ColumnInfo(name = "title_underline") val title_underline: Boolean = false,
    @ColumnInfo(name = "note_underline") val note_underline: Boolean = false,
    @ColumnInfo(name = "title_line_through") val title_line_through: Boolean = false,
    @ColumnInfo(name = "note_line_through") val note_line_through: Boolean = false,
    @ColumnInfo(name = "time") val date: String = System.currentTimeMillis().toString()
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(noteId)
        parcel.writeString(noteTitle)
        parcel.writeString(noteDescription)
        parcel.writeInt(backgroundColor)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotesEntity> {
        override fun createFromParcel(parcel: Parcel): NotesEntity {
            return NotesEntity(parcel)
        }

        override fun newArray(size: Int): Array<NotesEntity?> {
            return arrayOfNulls(size)
        }
    }


}
