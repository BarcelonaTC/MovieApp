package com.karrar.movieapp.utilities

import kotlinx.datetime.LocalDate

class DateFormatter {

    fun releasedDate(isoDate: String): String {
        val date = LocalDate.parse(isoDate)
        val monthName = date.month.name
            .lowercase()
            .replaceFirstChar { it.uppercase() }
            .take(FIRST_THREE_CHARACTERS)
        return "${date.year}, $monthName ${date.day}"
    }

    fun runtimeToDate(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return "${hours}h ${minutes}m"
    }

    companion object {
        private const val FIRST_THREE_CHARACTERS = 3
    }
}
