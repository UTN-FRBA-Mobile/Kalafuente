package com.example.quecomohoy.ui.listeners

import com.example.quecomohoy.data.model.perfil.UserPreference

interface PreferenceListener {
    fun onPreferenceClick(preferenceCode: UserPreference)
}
