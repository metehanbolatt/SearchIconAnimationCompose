package com.metehanbolat.searchiconanimationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.metehanbolat.searchiconanimationcompose.ui.theme.SearchIconAnimationComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchIconAnimationComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Search Animation",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )

                                }
                            )
                        }
                    ) {
                        InfinitelyFlowingCircles()
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 28.dp, end = 28.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimateSearch()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun scaleInfiniteTransition(
    initialValue: Float = 0f,
    targetValue: Float,
    durationMillis: Int
): Float {
    val infiniteTransition = rememberInfiniteTransition()
    val scale: Float by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    return scale
}

@Composable
fun DrawCircleOnCanvas(
    scale: Float,
    color: Color,
    radiusRatio: Float
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = color,
            center = Offset(
                x = canvasWidth / 2,
                y = canvasHeight / 2
            ),
            radius = size.minDimension / radiusRatio
        )
    }
}

@Composable
fun InfinitelyFlowingCircles() {
    val primaryColor = MaterialTheme.colors.primary
    val frontCircle = primaryColor.copy(alpha = 0.75f)
    val midCircle = primaryColor.copy(alpha = 0.50f)
    val backCircle = primaryColor.copy(alpha = 0.25f)
    
    DrawCircleOnCanvas(
        scale = scaleInfiniteTransition(targetValue = 2f, durationMillis = 1200),
        color = backCircle,
        radiusRatio = 4f
    )

    DrawCircleOnCanvas(
        scale = scaleInfiniteTransition(targetValue = 2.5f, durationMillis = 1600),
        color = midCircle,
        radiusRatio = 6f
    )

    DrawCircleOnCanvas(
        scale = scaleInfiniteTransition(targetValue = 3f, durationMillis = 2000),
        color = frontCircle,
        radiusRatio = 12f
    )
}

@Composable
fun AnimateShapeInfinitely(
    animateShape: Animatable<Float, AnimationVector1D>,
    targetValue: Float = 1f,
    durationMillis: Int = 2000
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }
}

@Composable
fun AnimateSearch() {
    val animateCircle = remember { Animatable(0f) }.apply {
        AnimateShapeInfinitely(animateShape = this)
    }
    val animateLine = remember { Animatable(0f) }.apply {
        AnimateShapeInfinitely(animateShape = this)
    }
    val surfaceColor = MaterialTheme.colors.surface
    
    Canvas(modifier = Modifier) {
        drawArc(
            color = surfaceColor,
            startAngle = 45f,
            sweepAngle = 360f * animateCircle.value,
            useCenter = false,
            size = Size(80f, 80f),
            style = Stroke(16f, cap = StrokeCap.Round)
        )
        drawLine(
            color = surfaceColor,
            strokeWidth = 16f,
            cap = StrokeCap.Round,
            start = Offset(
                x = animateLine.value * 80f,
                y = animateLine.value * 80f
            ),
            end = Offset(
                x = animateLine.value * 110f,
                y = animateLine.value * 110f
            )
        )
    }
}
