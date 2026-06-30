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

class RPMGaugeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var displayRpm = 0f
    private var targetRpm = 0f
    private val maxRpm = 8000f
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
    private val rpmArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF6B35")
        style = Paint.Style.STROKE
        strokeWidth = 30f
        strokeCap = Paint.Cap.ROUND
    }
    private val redlineArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF0000")
        style = Paint.Style.STROKE
        strokeWidth = 30f
        strokeCap = Paint.Cap.ROUND
    }
    private val needlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
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

    fun setRPM(value: Float) {
        targetRpm = value.coerceIn(0f, maxRpm)
        animator?.cancel()
        animator = ValueAnimator.ofFloat(displayRpm, targetRpm).apply {
            duration = 250L
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                displayRpm = it.animatedValue as Float
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

        val sweep = (displayRpm / maxRpm) * 240f
        val redlineStart = (6500f / maxRpm) * 240f

        if (sweep <= redlineStart) {
            canvas.drawArc(rect, 150f, sweep, false, rpmArcPaint)
        } else {
            canvas.drawArc(rect, 150f, redlineStart, false, rpmArcPaint)
            canvas.drawArc(rect, 150f + redlineStart, sweep - redlineStart, false, redlineArcPaint)
        }

        val angle = Math.toRadians((150 + sweep).toDouble())
        val nx = cx + radius * cos(angle).toFloat()
        val ny = cy + radius * sin(angle).toFloat()
        canvas.drawLine(cx, cy, nx, ny, needlePaint)

        canvas.drawText("${(displayRpm / 1000).toInt()}k", cx, cy + 20f, textPaint)
        canvas.drawText("RPM", cx, cy + 60f, labelPaint)
    }
}