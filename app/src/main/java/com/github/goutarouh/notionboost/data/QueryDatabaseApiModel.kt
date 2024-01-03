package com.github.goutarouh.notionboost.data

import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import java.time.ZonedDateTime

data class QueryDatabaseApiModel(
    val results: List<Result> = listOf(),
) {

    data class Result(
        val properties: Properties = Properties(),
    ) {

        data class Properties(
            @SerializedName("Created time")
            val createdTime: CreatedTime = CreatedTime(),
            val checkBoxProperties: Map<String, CheckBoxProperty> = emptyMap(),
        ) {

            companion object {
                fun createFromJson(json: JsonElement) : Properties {
                    val checkBoxProperties = mutableMapOf<String, CheckBoxProperty>()
                    val properties = json.asJsonObject.entrySet()

                    properties.forEach { property ->
                        if (property.key != "Created time") {
                            val checkBoxProperty = CheckBoxProperty.createFromJson(property.value)
                            if (checkBoxProperty != null) {
                                checkBoxProperties[property.key] = checkBoxProperty
                            }
                        }
                    }

                    val createdTimeJson = properties.firstOrNull { it.key == "Created time" }
                    if (createdTimeJson != null) {
                        return Properties(
                            createdTime = CreatedTime.createFromJson(createdTimeJson.value),
                            checkBoxProperties = checkBoxProperties
                        )
                    } else {
                        throw JsonParseException("[Created time] property was not found in properties.")
                    }

                }
            }

            data class CreatedTime(
                @SerializedName("created_time")
                val createdTime: ZonedDateTime = ZonedDateTime.now(),
            ) {
                companion object {
                    fun createFromJson(json: JsonElement) : CreatedTime {
                        val properties = json.asJsonObject.entrySet()
                        val createdTime = properties.firstOrNull { it.key == "created_time" }?.value
                        if (createdTime != null) {
                            return CreatedTime(ZonedDateTime.parse(createdTime.asString))
                        } else {
                            throw JsonParseException("[created_time] property was not found in CreatedTime.")
                        }
                    }
                }
            }

            data class CheckBoxProperty(
                val checkbox: Boolean = false,
            ) {
                companion object {
                    fun createFromJson(json: JsonElement) : CheckBoxProperty? {
                        val properties = json.asJsonObject.entrySet()
                        val isCheckBoxProperty = try {
                            properties.first { it.key == "type" }.value.asString == "checkbox"
                        } catch (e: Exception) {
                            throw JsonParseException("[type] property was not found in CheckBoxProperty.")
                        }
                        if (!isCheckBoxProperty) return null

                        try {
                            val checkbox = properties.first { it.key == "checkbox" }.value
                            return CheckBoxProperty(checkbox.asBoolean)
                        } catch (e: Exception) {
                            throw JsonParseException("[checkbox] property was not found or not abel to convert to Boolean in CheckBoxProperty.")
                        }
                    }
                }
            }
        }
    }
}