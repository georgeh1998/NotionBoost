package com.github.goutarouh.notionboost.ui.welcome

data class WelcomeUiModel(
    val inputApiKey: String = "",
    val apiKeySaved : Boolean? = null,

    val updateInputApiKey: (String) -> Unit = {},
    val saveApiKey: (String) -> Unit = {}
) {

    val saveButtonEnabled = inputApiKey.isNotEmpty()

    val finishWelcomeScreen: Boolean
        get() = apiKeySaved == true

}