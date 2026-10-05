package com.ayushi.productcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import coil.Coil
import com.ayushi.productcatalog.ui.navigation.AppNavigation
import com.ayushi.productcatalog.ui.theme.ProductCatalogTheme
import com.ayushi.productcatalog.util.createImageLoader

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Coil.setImageLoader {
            createImageLoader(applicationContext)
        }

        enableEdgeToEdge()

        setContent {
            ProductCatalogTheme {
                AppNavigation()
            }
        }
    }
}