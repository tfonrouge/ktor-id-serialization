package com.example.project

import io.kvision.remote.getService

object Model {

    private val pingService = getService<IPingService>()

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }

    suspend fun appUserList(): List<AppUser> {
        return pingService.appUserList()
    }

    suspend fun sendUserId(_id: Id<AppUser>): Boolean {
        return pingService.sendUserId(_id)
    }
}
