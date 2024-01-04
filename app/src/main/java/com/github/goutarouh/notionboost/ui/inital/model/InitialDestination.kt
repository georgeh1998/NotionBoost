package com.github.goutarouh.notionboost.ui.inital.model

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