package com.mcsimf.reddittop.core

import java.util.concurrent.TimeUnit

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */

private val HOURS_IN_YEAR = 24 * 365
private val HOURS_IN_MONTH = 24 * 30
private val HOURS_IN_WEEK = 24 * 7
private val HOURS_IN_DAY = 24

private val MORE_THAN_YEAR = ">1y"
private val MONTH_MARK = "m"
private val WEEK_MARK = "w"
private val DAY_MARK = "d"
private val HOUR_MARK = "h"
private val MIN_MARK = "min"
private val AGO_MARK = " ago"


fun Long.toTimeAgo(): String {
    val diffs = System.currentTimeMillis() - this * 1000
    val hours = TimeUnit.MILLISECONDS.toHours(diffs)
    return when {
        hours >= HOURS_IN_YEAR -> MORE_THAN_YEAR
        hours >= HOURS_IN_MONTH -> "${hours / HOURS_IN_MONTH} ${MONTH_MARK}"
        hours >= HOURS_IN_WEEK -> "${hours / HOURS_IN_WEEK} ${WEEK_MARK}"
        hours >= HOURS_IN_DAY -> "${hours / HOURS_IN_DAY} ${DAY_MARK}"
        hours > 0 -> "$hours ${HOUR_MARK}"
        else -> "${TimeUnit.MILLISECONDS.toMinutes(diffs)} ${MIN_MARK}"
    } + AGO_MARK
}