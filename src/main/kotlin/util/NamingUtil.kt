package io.github.klahap.mccgen.util

private val VALID_CHAR_REGEX = Regex("[^a-zA-Z0-9_ -]")

private fun String.toNameParts() =
    replace(VALID_CHAR_REGEX) {
        when (it.value) {
            "ä" -> "ae"
            "ö" -> "oe"
            "ü" -> "ue"
            "Ä" -> "Ae"
            "Ö" -> "oe"
            "Ü" -> "Ue"
            "ß" -> "ss"
            else -> ""
        }
    }
        .split(' ', '_', '-')
        .filter { it.isNotEmpty() }

private fun String.toValidName() = if (isEmpty())
    "_empty"
else if (first().isDigit())
    "_$this"
else
    this

fun String.toCamelCase(capitalized: Boolean) = toNameParts()
    .mapIndexed { idx, s ->
        s.replaceFirstChar {
            if (idx == 0 && !capitalized)
                it.lowercaseChar()
            else
                it.titlecaseChar()
        }
    }.joinToString(separator = "")
    .toValidName()
