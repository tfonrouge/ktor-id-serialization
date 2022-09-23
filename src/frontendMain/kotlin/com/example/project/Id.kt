package com.example.project

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = IdSerializer::class)
@Suppress("ACTUAL_WITHOUT_EXPECT")
//actual class Id<T>(
@JsExport
//@Serializable
actual class Id<T>(
    var _id: String
) {
    override fun toString(): String {
        return _id
    }
}

class IdSerializer<T : Id<*>> : KSerializer<T> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Id Serializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): T {
        val s = decoder.decodeString()
        console.warn("Id DESERIALIZE, decoder", decoder, "decodeString", s)
        return Id<Any>(s) as T
    }

    @OptIn(InternalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: T) {
        console.warn("Id SERIALIZE, encoder", encoder, "value", value)
//        val pair: Map<String, String> = mapOf("id" to value._id)
//        encoder.encodeSerializableValue(MapSerializer(String.serializer(), String.serializer()), value = pair)
        encoder.encodeString(value._id)
        console.warn("ID SERIALIZED...")
    }
}

class ObjectIdSerializer : KSerializer<ObjectId> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ObjectId Serializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ObjectId {
        val s = decoder.decodeString()
        console.warn("ObjectId DESERIALIZE, decoder", decoder, "decodeString", s)
        return ObjectId(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: ObjectId) {
        console.warn("ObjectId SERIALIZE, encoder", encoder, "value", value)
        encoder.encodeString(value.id)
    }

}

@Serializable
@SerialName("objectId")
class ObjectId(
    var id: String
) {
    override fun toString(): String {
        return id
    }
}