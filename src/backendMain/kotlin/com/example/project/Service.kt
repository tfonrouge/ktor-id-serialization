package com.example.project

import io.ktor.server.application.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject
import org.litote.kmongo.id.StringId
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule
import org.litote.kmongo.id.toId

@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class PingService(private val call: ApplicationCall) : IPingService {

    override suspend fun ping(message: String): String {
        println(message)
        return "$message, -> Hello world from server. $call"
    }

    override suspend fun appUserList(): List<AppUser> {
        println(call)
        val coll = with(call.application) {
            inject<MongoDbCollection>().value
        }
//        val one = coll.collection.findOneById(ObjectId("628c4ef4aa06075e65f94afb"))
        val one = coll.collection.findOneById(ObjectId("628c4ef4aa06075e65f94afb").toId<AppUser>())
        val id = one?._id
        println(one)
        val result = coll.collection.find(
//            AppUser::_id eq WrappedObjectId("628c4ef4aa06075e65f94afb")
//            AppUser::_id eq id
        ).toList()
        val json = Json { serializersModule = IdKotlinXSerializationModule }
        Json.serializersModule
        val s = json.encodeToString(result)
        println(s)
        return result
    }

    override suspend fun sendUserId(_id: Id<AppUser>): Boolean {
        println("_id -> $_id")
        return true
    }
}

@Serializable
class AppUser2(
    @Contextual
    var _id: StringId<AppUser2>,
)
