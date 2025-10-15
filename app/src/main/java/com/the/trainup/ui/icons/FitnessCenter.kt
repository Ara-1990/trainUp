package com.the.trainup.ui.icons



import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val FitnessCenterIcon: ImageVector
    get() {
        if (_fitnessCenterIcon != null) return _fitnessCenterIcon!!
        _fitnessCenterIcon = Builder(
            name = "FitnessCenter",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {

            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(2f, 9f)
                lineTo(6f, 9f)
                lineTo(6f, 15f)
                lineTo(2f, 15f)
                close()
            }

            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(18f, 9f)
                lineTo(22f, 9f)
                lineTo(22f, 15f)
                lineTo(18f, 15f)
                close()
            }

            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(6f, 11f)
                lineTo(18f, 11f)
                lineTo(18f, 13f)
                lineTo(6f, 13f)
                close()
            }

            path(fill = SolidColor(Color.Black)) {
                moveTo(6.75f, 10f)
                lineTo(8.5f, 10f)
                lineTo(8.5f, 14f)
                lineTo(6.75f, 14f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(15.5f, 10f)
                lineTo(17.25f, 10f)
                lineTo(17.25f, 14f)
                lineTo(15.5f, 14f)
                close()
            }
        }.build()
        return _fitnessCenterIcon!!
    }

private var _fitnessCenterIcon: ImageVector? = null
