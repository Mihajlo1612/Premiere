package com.rma.premiere.util

import java.util.Locale

fun formatMoney(amount: Long?): String {
    if (amount == null) return "N/A"
    return when {
        amount >= 1_000_000_000 -> String.format(Locale.US, "%.1fB", amount / 1_000_000_000.0)
        amount >= 1_000_000 -> "$${amount / 1_000_000}M"
        amount > 0 -> "$$amount"
        else -> "N/A"
    }
}

fun formatVotes(votes: Int?): String {
    if (votes == null) return "N/A"
    return when {
        votes >= 1_000_000 -> String.format(Locale.US, "%.1fM votes", votes / 1_000_000.0)
        votes >= 1_000 -> "${votes / 1_000}K votes"
        else -> "$votes votes"
    }
}