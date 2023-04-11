package org.androidnoteapp.notes.data.sync.core

import org.androidnoteapp.notes.preferences.CloudService

interface ProviderConfig {
    val remoteAddress: String
    val username: String
    val provider: CloudService
    val authenticationHeaders: Map<String, String>
}
