package com.example.wearostileintro

import androidx.wear.tiles.DimensionBuilders.dp
import androidx.wear.tiles.LayoutElementBuilders.Layout
import androidx.wear.tiles.LayoutElementBuilders.Text
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TileService
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.guava.future

// TODO: Review Constants.
// Updating this version triggers a new call to onResourcesRequest(). This is useful for dynamic
// resources, the contents of which change even though their id stays the same (e.g. a graph).
// In this sample, our resources are all fixed, so we use a constant value.
private const val RESOURCES_VERSION = "1"

// dimensions
private val PROGRESS_BAR_THICKNESS = dp(6f)
private val BUTTON_SIZE = dp(48f)
private val BUTTON_RADIUS = dp(24f)
private val BUTTON_PADDING = dp(12f)
private val VERTICAL_SPACING_HEIGHT = dp(8f)

// Complete degrees for a circle (relates to [Arc] component)
private const val ARC_TOTAL_DEGREES = 360f

// identifiers
private const val ID_IMAGE_START_RUN = "image_start_run"
private const val ID_CLICK_START_RUN = "click_start_run"

/**
 * Creates a Fitness Tile, showing your progress towards a daily goal. The progress is defined
 * randomly, for demo purposes only. A new random progress is shown when the user taps the button.
 */
class GoalsTileService : TileService() {
    // For coroutines, use a custom scope we can cancel when the service is destroyed
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    // TODO: Build a tile.
    override fun onTileRequest(
        requestParams: RequestBuilders.TileRequest
    ) = serviceScope.future {
        // Creates a tile
        Tile.Builder()
            // If there are any graphics/images defined in the Tile's layout, the system will
            // retrieve them via onResourcesRequest() and match them with this version number.
            .setResourcesVersion(RESOURCES_VERSION)
            // Creates a timeline to hold one or more tile entries for a specific time periods.
            .setTimeline(
                Timeline.Builder()
                    .addTimelineEntry(
                        TimelineEntry.Builder()
                            .setLayout(
                                Layout.Builder()
                                    .setRoot(
                                        Text.Builder().setText("Hello, world!").build()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build()
    }

    // TODO: Supply resources (graphics) for the Tile.
    override fun onResourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ) = serviceScope.future {
        Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            // No Resources quite yet!
            .build()
    }

    // TODO: Review onDestroy() - cancellation of the serviceScope
    override fun onDestroy() {
        super.onDestroy()
        // Cleans up the coroutine
        serviceScope.cancel()
    }

    // TODO: Create root Box layout and content.


    // TODO: Create a function that constructs an Arc representation of the current step progress.


    // TODO: Create functions that construct/stylize Text representations of the step count & goal.


    // TODO: Create a function that constructs/stylizes a clickable Image of a running icon.

}