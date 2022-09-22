package com.example.project

import io.kvision.*
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.Span
import io.kvision.panel.root
import io.kvision.remote.RemoteSerialization
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Tabulator
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import io.kvision.toast.Toast
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    lateinit var tabulator: Tabulator<AppUser>

    @OptIn(InternalSerializationApi::class)
    override fun start(state: Map<String, Any>) {
        val root = root("kvapp") {
            tabulator = tabulator(
                data = emptyList(),
                options = TabulatorOptions(
                    columns = listOf(
                        ColumnDefinition(
                            title = "#",
                            formatterComponentFunction = { cell, onRendered, data ->
                                Button("#", style = ButtonStyle.OUTLINEPRIMARY).onClick {
                                    AppScope.launch {
                                        console.warn("sending user id", data._id)
                                        Model.sendUserId(data._id)
                                    }
                                }
                            }
                        ),
                        ColumnDefinition(
                            title = "_id",
                            field = "_id",
//                            field = "_id",
                            formatterComponentFunction = { cell, onRendered, data ->
                                console.warn("data", data._id.toString())
//                                Span(data._id.toString())
                                Span(data._id.toString())
                            }
                        ),
                        ColumnDefinition(
                            title = "userName",
                            field = "userName"
                        ),
                        ColumnDefinition(
                            title = "fullName",
                            field = "fullName"
                        ),
                        ColumnDefinition(
                            title = "password",
                            field = "password"
                        ),
                    )
                )
            )
        }
        AppScope.launch {
            Toast.success(Model.ping("Hello world from client!"))
        }
        AppScope.launch {
            val list = Model.appUserList()
            console.warn("list", list)
            tabulator.replaceData(list.toTypedArray())
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        CoreModule
    )
}
