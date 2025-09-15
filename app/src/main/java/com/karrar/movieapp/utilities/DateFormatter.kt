package com.karrar.movieapp.utilities


class DateFormatter  {

    fun releasedDate(isoDate: String): String {
        val (year, month, day) = isoDate.split("-")

        val monthAbbreviations = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        val monthName = monthAbbreviations[month.toInt() - 1]

        return "$year, $monthName $day"
    }

    fun runtimeToDate(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return "${hours}h ${minutes}m"
    }
}
