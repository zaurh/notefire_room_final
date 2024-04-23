package com.zaurh.notefirelocal.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("notefire_local")


class StoreSettings(private val context: Context) {

    companion object {
        val GRID_CELLS = intPreferencesKey("grid_cells")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    val getGridCells: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[GRID_CELLS] ?: 2
        }

    suspend fun saveGridCells(gridCells: Int) {
        context.dataStore.edit { preferences ->
            preferences[GRID_CELLS] = gridCells
        }
    }

    val getDarkMode: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE] ?: false
        }

    suspend fun saveDarkMode(switched: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = switched
        }
    }

}