package com.metehanbolat.searchiconanimationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.metehanbolat.searchiconanimationcompose.ui.theme.SearchIconAnimationComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchIconAnimationComposeTheme {

            }
        }
    }
}