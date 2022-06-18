package com.example.wearostileintro

import androidx.core.content.ContextCompat
import androidx.wear.tiles.ColorBuilders.argb
import androidx.wear.tiles.DeviceParametersBuilders
import androidx.wear.tiles.DimensionBuilders.*
import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.LayoutElementBuilders.*
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
        // Retrieves progress value to populate the Tile.
        val goalProgress = GoalsRepository.getGoalProgress()
        // Retrieves device parameters to later retrieve font styles for any text in the Tile.
        val deviceParams = requestParams.deviceParameters!!

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
                                        // Creates the root [Box] [LayoutElement]
                                        layout(goalProgress, deviceParams)
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

    // Creates a simple [Box] container that lays out its children one over the other. In our
    // case, an [Arc] that shows progress on top of a [Column] that includes the current steps
    // [Text], the total steps [Text], a [Spacer], and a running icon [Image].
    private fun layout(
        goalProgress: GoalProgess,
        deviceParams: DeviceParametersBuilders.DeviceParameters,
    ) = Box.Builder()
        // Sets width and height to expand and take up entire Tile space.
        .setWidth(expand())
        .setHeight(expand())
        // Adds an [Arc] via local function.
        .addContent(progressArc(goalProgress.percentage))
        // TODO: Add Column containing the rest of the data.
        // TODO: START REPLACE THIS LATER
        .addContent(
            Text.Builder()
                .setText("REPLACE ME!")
                .setFontStyle(FontStyles.display3(deviceParams).build())
                .build()
        )
        // TODO: END REPLACE THIS LATER
        .build()


    // Creates an [Arc] representing current progress towards steps goal.
    private fun progressArc(percentage: Float) = Arc.Builder()
        .addContent(
            ArcLine.Builder()
                // Uses degrees() helper to build an [AngularDimension] which represents progress.
                .setLength(degrees(percentage * ARC_TOTAL_DEGREES))
                .setColor(argb(ContextCompat.getColor(this, R.color.primary)))
                .setThickness(PROGRESS_BAR_THICKNESS)
                .build()
        )
        // Element will start at 12 o'clock or 0 degree position in the circle.
        .setAnchorAngle(degrees(0.0f))
        // Aligns the contents of this container relative to anchor angle above.
        // ARC_ANCHOR_START - Anchors at the start of the elements. This will cause elements
        // added to an arc to begin at the given anchor_angle, and sweep around to the right.
        .setAnchorType(ARC_ANCHOR_START)
        .build()

    // TODO: Create functions that construct/stylize Text representations of the step count & goal.


    // TODO: Create a function that constructs/stylizes a clickable Image of a running icon.

}