package com.longterm.artschools

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.longterm.artschools.ui.core.theme.ArtSchoolsTheme
import com.longterm.artschools.ui.navigation.CustomNavHost
import com.longterm.artschools.ui.navigation.Destination
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthCallback


class MainActivity : ComponentActivity() {
    private var vkAuthCallback: VKAuthCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSchoolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }

    //todo remove this !@#$ if new oauth is working
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = vkAuthCallback ?: return super.onActivityResult(requestCode, resultCode, data)

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun setVkAuthListener(listener: VKAuthCallback? = null) {
        vkAuthCallback = listener
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    CustomNavHost(navController)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ArtSchoolsTheme {
        Main()
    }
}