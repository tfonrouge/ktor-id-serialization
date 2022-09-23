package com.example.project

import com.mongodb.reactivestreams.client.MongoClient
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.kvision.remote.RemoteSerialization
import io.kvision.remote.applyRoutes
import io.kvision.remote.getServiceManager
import io.kvision.remote.kvisionInit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.bson.types.ObjectId
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule
import org.litote.kmongo.reactivestreams.KMongo

@OptIn(ExperimentalSerializationApi::class)
fun Application.main() {
    val service by inject<PingService>()
    install(Compression)
    routing {
        get("/appUserList") {
            call.respond(service.appUserList())
        }
//        getAllServiceManagers().forEach {
//            applyRoutes(it)
//        }
        applyRoutes(
            getServiceManager<IPingService>(), listOf(
                IdKotlinXSerializationModule
            )
        )
    }
    val module = module {
        factoryOf(::PingService)
        singleOf(::MongoDbCollection)
    }
//    RemoteSerialization.customConfiguration = Json {
//        serializersModule = SerializersModule {
//            IdKotlinXSerializationModule
//        }
//    }
    kvisionInit(
//        json = Json {
//            serializersModule = IdKotlinXSerializationModule
//            encodeDefaults = true
//            isLenient = true
//            allowSpecialFloatingPointValues = true
//            allowStructuredMapKeys = true
//            prettyPrint = false
//            useArrayPolymorphism = true
//        },
        module
    )
}

class MongoDbCollection {
    private val client: MongoClient =
        KMongo.createClient("mongodb://user1:fb513d2033@dulceserver.dulcesdulcemaria.com:27017/?serverSelectionTimeoutMS=5000&connectTimeoutMS=10000&authSource=CasaDulce&authMechanism=SCRAM-SHA-1")
    val collection = client.getDatabase("CasaDulce").coroutine.getCollection<AppUser>(collectionName = "userItms")
}
