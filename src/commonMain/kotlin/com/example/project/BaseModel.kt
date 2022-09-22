package com.example.project

import kotlin.js.JsExport

@JsExport
interface BaseModel<T> {
    var _id: T
}
