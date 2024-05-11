package com.zaurh.notefirelocal

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.zaurh.notefirelocal.data_store.StoreSettings
import com.zaurh.notefirelocal.navigation.MyAnimatedNavigation
import com.zaurh.notefirelocal.ui.theme.NotefireLocalTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val dataStore = StoreSettings(context)
            var darkTheme by remember { mutableStateOf(false) }
            val savedDarkMode = dataStore.getDarkMode.collectAsState(initial = false)
            val scope = rememberCoroutineScope()

            NotefireLocalTheme(darkTheme = savedDarkMode.value ?: false) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MyAnimatedNavigation(
                        darkTheme = savedDarkMode.value ?: false
                    ){
                        scope.launch {
                            darkTheme = !(savedDarkMode.value ?: false)
                            dataStore.saveDarkMode(darkTheme)
                        }
                    }
                }
            }
        }
    }


}
