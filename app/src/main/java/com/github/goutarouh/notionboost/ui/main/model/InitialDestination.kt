package com.github.goutarouh.notionboost.ui.main.model

import com.github.goutarouh.notionboost.R

enum class InitialDestination(
    val destinationId: Int,
) {
    WelcomeScreen(
        destinationId = R.id.welcomeFragment,
    ),
    MonthlyWidgetSettingFragment(
        destinationId = R.id.monthlyWidgetSettingFragment,
    ),
}