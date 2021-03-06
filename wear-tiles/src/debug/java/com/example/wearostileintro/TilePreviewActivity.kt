package com.example.wearostileintro

import android.content.ComponentName
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.wear.tiles.manager.TileUiClient
import com.example.wearostileintro.databinding.ActivityMainBinding

/**
 * Debug Activity that will render our Tile. This Activity lives inside the debug package, so it
 * will not be included in release builds.
 */
class TilePreviewActivity : ComponentActivity() {
    lateinit var tileUiClient: TileUiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // why can't I use data binding here?
        val rootLayout = findViewById<FrameLayout>(R.id.tile_container)

        // TODO: Review creation of Tile for Preview.
        tileUiClient = TileUiClient(
            context = this,
            component = ComponentName(this, GoalsTileService::class.java),
            parentView = rootLayout
        )
        tileUiClient.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        tileUiClient.close()
    }
}