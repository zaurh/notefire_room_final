package com.zaurh.notefirelocal.di

import android.app.Application
import androidx.room.Room
import com.zaurh.notefirelocal.data.NoteRepositoryImpl
import com.zaurh.notefirelocal.data.NotesDatabase
import com.zaurh.notefirelocal.domain.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Room
    @Singleton
    @Provides
    fun provideRoomDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            "notefire_local"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(db: NotesDatabase): NoteRepository = NoteRepositoryImpl(db.notesDao())
}