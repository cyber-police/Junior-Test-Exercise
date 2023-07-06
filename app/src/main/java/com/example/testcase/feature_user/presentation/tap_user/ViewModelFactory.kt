package com.example.testcase.feature_user.presentation.tap_user

import dagger.assisted.AssistedFactory

@AssistedFactory
interface ViewModelFactory {
    fun create(myString: String): ReposViewModel
}
