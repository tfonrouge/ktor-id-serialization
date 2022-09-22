package com.example.project

import io.kvision.annotations.KVService

@KVService
interface IPingService {
    suspend fun ping(message: String): String
    suspend fun appUserList(): List<AppUser>
    suspend fun sendUserId(_id: Id<AppUser>): Boolean
}
