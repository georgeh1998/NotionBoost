package com.github.goutarouh.notionboost.widget

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.LinearProgressIndicator
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.github.goutarouh.notionboost.repository.model.MonthlyWidgetModel

@Composable
fun MonthlyWidgetContent(
    monthlyWidgetUiState: MonthlyWidgetUiState,
    modifier: GlanceModifier = GlanceModifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WidgetColor.widgetBackground),
    ) {

        when (monthlyWidgetUiState) {
            is MonthlyWidgetUiState.Preparing -> {
                Preparing()
            }
            is MonthlyWidgetUiState.Success -> {
                Success(
                    monthlyWidgetModel = monthlyWidgetUiState.monthlyWidgetModel,
                    modifier = GlanceModifier
                )
            }
            else -> {}
        }
    }
}

@Composable
private fun Preparing() {
    Box(
        modifier = GlanceModifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Under preparation...")
    }
}

@Composable
private fun NoData(
    modifier: GlanceModifier = GlanceModifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No Data")
    }
}

@Composable
private fun Success(
    monthlyWidgetModel: MonthlyWidgetModel,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = GlanceModifier.height(8.dp),
        )
        val title = monthlyWidgetModel.title.ifEmpty { "${monthlyWidgetModel.monthName}" }
        Text(
            text = title,
            modifier = GlanceModifier
                .clickable(
                    actionStartActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            monthlyWidgetModel.url.toUri()
                        )
                    )
                ),
            style = TextStyle(
                fontSize = WidgetText.LargeTextSize,
                fontWeight = FontWeight.Bold,
            ),
        )
        Spacer(
            modifier = GlanceModifier.height(2.dp),
        )
        Text(
            text = "${monthlyWidgetModel.startDate} - ${monthlyWidgetModel.endDate}",
            style = TextStyle(
                fontSize = WidgetText.SmallTextSize,
            ),
        )
        Spacer(
            modifier = GlanceModifier.height(8.dp),
        )
        if (monthlyWidgetModel.mapProgress.isEmpty()) {
            NoData(
                modifier = GlanceModifier.defaultWeight(),
            )
        } else {
            AccomplishmentColumn(
                monthlyWidgetModel = monthlyWidgetModel,
                modifier = GlanceModifier.defaultWeight(),
            )
        }
        Spacer(
            modifier = GlanceModifier.height(8.dp),
        )
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = GlanceModifier.defaultWeight())
            Text(
                text = "Last Updated at ${monthlyWidgetModel.lastUpdatedTime}",
                style = TextStyle(
                    fontSize = WidgetText.SmallTextSize,
                ),
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
        }
    }
}

@Composable
private fun AccomplishmentColumn(
    monthlyWidgetModel: MonthlyWidgetModel,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        monthlyWidgetModel.mapProgress.forEach { mapProgress ->
            AccomplishmentTrackRow(
                title = mapProgress.key,
                progress = mapProgress.value,
                progressPercent = monthlyWidgetModel.calculateProgress(mapProgress.value),
                modifier = GlanceModifier.padding(vertical = 3.dp),
            )
        }
    }
}

@Composable
private fun AccomplishmentTrackRow(
    title: String,
    progress: Float,
    progressPercent: Int,
    modifier: GlanceModifier = GlanceModifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = GlanceModifier.width(80.dp),
            style = TextStyle(
                textAlign = TextAlign.End,
                fontSize = WidgetText.MediumTextSize,
            ),
            maxLines = 1,
        )
        Spacer(
            modifier = GlanceModifier.width(12.dp),
        )
        Box(
            modifier = GlanceModifier.defaultWeight(),
            contentAlignment = Alignment.Center
        ) {
            AccomplishmentTrackBar(
                progress = progress,
                modifier = GlanceModifier.fillMaxWidth(),
            )

            Text(
                text = "$progressPercent%",
                modifier = GlanceModifier.fillMaxWidth().padding(end = 8.dp),
                style = TextStyle(
                    textAlign = TextAlign.End,
                    fontSize = WidgetText.SmallTextSize,
                ),
            )
        }
    }
}


@Composable
private fun AccomplishmentTrackBar(
    progress: Float,
    modifier: GlanceModifier,
) {

    val color = when {
        (progress < 0.4) -> WidgetColor.progressLow
        (progress < 0.8) -> WidgetColor.progressMedium
        else -> WidgetColor.progressHigh
    }
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier,
        color = ColorProvider(color),
        backgroundColor = ColorProvider(WidgetColor.progressBackground)
    )

}



