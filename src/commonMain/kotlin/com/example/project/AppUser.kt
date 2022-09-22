package com.example.project

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
class AppUser(
    @Contextual
    override var _id: Id<AppUser>,
    var userName: String,
    var fullName: String,
    var password: String,
) : BaseModel<Id<AppUser>>
