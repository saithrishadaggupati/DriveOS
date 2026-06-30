package com.driveos.dashboard.ui.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class SpeedometerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var displaySpeed = 0f
    private var targetSpeed = 0f
    private val maxSpeed = 220f
    private var animator: ValueAnimator? = null

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1A1A2E")
        style = Paint.Style.FILL
    }
    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#16213E")
        style = Paint.Style.STROKE
        strokeWidth = 30f
        strokeCap = Paint.Cap.ROUND
    }
    private val needlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF6B35")
        strokeWidth = 6f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 60f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#8899AA")
        textSize = 28f
        textAlign = Paint.Align.CENTER
    }

    private fun zoneColor(value: Float): Int {
        val ratio = value / maxSpeed
        return when {
            ratio < 0.55f -> Color.parseColor("#00D4FF") // calm blue
            ratio < 0.8f  -> Color.parseColor("#FFC107") // caution yellow
            else          -> Color.parseColor("#FF3B30") // danger red
        }
    }

    fun setSpeed(value: Float) {
        targetSpeed = value.coerceIn(0f, maxSpeed)
        animator?.cancel()
        animator = ValueAnimator.ofFloat(displaySpeed, targetSpeed).apply {
            duration = 300L
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                displaySpeed = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2f
        val cy = height / 2f
        val radius = min(cx, cy) * 0.75f

        canvas.drawCircle(cx, cy, min(cx, cy) * 0.95f, bgPaint)

        val rect = RectF(cx - radius, cy - radius, cx + radius, cy + radius)
        canvas.drawArc(rect, 150f, 240f, false, arcPaint)

        val sweep = (displaySpeed / maxSpeed) * 240f
        val speedArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = zoneColor(displaySpeed)
            style = Paint.Style.STROKE
            strokeWidth = 30f
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawArc(rect, 150f, sweep, false, speedArcPaint)

        val angle = Math.toRadians((150 + sweep).toDouble())
        val nx = cx + radius * cos(angle).toFloat()
        val ny = cy + radius * sin(angle).toFloat()
        canvas.drawLine(cx, cy, nx, ny, needlePaint)

        canvas.drawText("${displaySpeed.toInt()}", cx, cy + 20f, textPaint)
        canvas.drawText("km/h", cx, cy + 60f, labelPaint)
    }
}