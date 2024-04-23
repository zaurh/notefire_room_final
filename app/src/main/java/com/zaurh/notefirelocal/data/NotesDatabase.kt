package com.zaurh.notefirelocal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaurh.notefirelocal.data.local.NotesEntity

@Database(entities = [NotesEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}