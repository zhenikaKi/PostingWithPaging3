package ru.kirea.postingwithpaging3.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Преобразовать дату к нужному формату.
 * @param pattern формат даты, например "dd.MM.yyyy HH:mm"
 */
fun Date.asString(pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(this)
}