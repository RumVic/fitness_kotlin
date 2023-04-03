package com.example.kotlin.fitness.demo.vic.serializator

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeParseException
import java.util.*

class DateDeSerializer : StdDeserializer<Date>(Date::class.java) {
    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(
        jsonParser: JsonParser,
        deserializationContext: DeserializationContext
    ): Date {
        val value = jsonParser.readValueAs(String::class.java)
        return try {
            SimpleDateFormat("yyyy-MM-dd").parse(value)
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Not valid birthday")
        } catch (e: ParseException) {
            throw IllegalArgumentException("Not valid birthday")
        }
    }
}