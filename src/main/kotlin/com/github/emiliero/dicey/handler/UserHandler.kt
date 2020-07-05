package com.github.emiliero.dicey.handler

import com.github.emiliero.dicey.model.User
import discord4j.core.`object`.entity.Message

fun getMessageAuthor(m: Message): User {
    val user = m.author.get().username
    val discriminator = m.author.get().discriminator
    val snowflake = m.author.get().id.toString().split("{", "}")[1]

    return User(user, discriminator, snowflake)
}

fun fetchTaggedUserInMessage(message: String): String {
    val start = "<"
    val end = ">"

    val startIndex = message.indexOf(start)
    val endIndex = message.indexOf(end)

    val snowflake = if (startIndex >= 0 && endIndex >= 0) message.substring(startIndex, endIndex + 1) else "someone"

    return checkType(snowflake)
}

private fun checkType(snowflake: String): String {
    return when {
        snowflake.contains("!", true) -> "in $snowflake"
        snowflake.contains("&", true) -> "in ${snowflake}s"
        snowflake.contains("@", true) -> "in $snowflake"
        snowflake.contains("someone") -> "you in"
        else -> "you in"
    }
}
