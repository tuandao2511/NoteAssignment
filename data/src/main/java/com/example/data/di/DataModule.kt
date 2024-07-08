package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.datasource.NoteDao
import com.example.data.datasource.NoteDatabase
import com.example.data.repository.NoteRepository
import com.example.data.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindNoteRepository(repository: NoteRepositoryImpl): NoteRepository
}
