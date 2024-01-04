package com.github.goutarouh.notionboost.ui.inital.model

import com.github.goutarouh.notionboost.R

enum class InitialNavAction(
    val actionId: Int,
) {
    WelcomeScreen(
        actionId = R.id.actionInitialFragmentToWelcomeFragment,
    ),
    MonthlyWidgetSettingFragment(
        actionId = R.id.actionInitialFragmentToMonthlyWidgetSettingFragment,
    ),
}