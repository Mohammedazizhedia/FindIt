package com.gallerysearch.app.widget

import android.content.Context
import android.content.Intent
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import com.gallerysearch.app.MainActivity
import com.gallerysearch.app.R

private fun openTabIntent(ctx: Context, tab: String) =
    Intent(ctx, MainActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        .putExtra("tab", tab)

/**
 * Home-screen widgets (Glance). Tapping any widget opens the matching screen.
 */
abstract class FindItWidget(private val tab: String, private val iconRes: Int) : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Column(
                GlanceModifier.fillMaxSize().background(R.color.widget_bg).padding(12.dp),
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            ) {
                Image(
                    provider = ImageProvider(iconRes),
                    contentDescription = null,
                    modifier = GlanceModifier.size(30.dp),
                )
                Button(
                    text = context.getString(R.string.app_name),
                    onClick = actionStartActivity(openTabIntent(context, tab)),
                )
            }
        }
    }
}

class SearchWidget : FindItWidget("home", R.drawable.ic_widget_search)
class DupWidget : FindItWidget("clean", R.drawable.ic_widget_dup)
class CorruptWidget : FindItWidget("clean", R.drawable.ic_widget_corrupt)

class SearchWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = SearchWidget()
}

class DupWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = DupWidget()
}

class CorruptWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = CorruptWidget()
}
