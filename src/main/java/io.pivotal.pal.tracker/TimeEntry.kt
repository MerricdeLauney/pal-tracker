package io.pivotal.pal.tracker

import java.time.LocalDate

class TimeEntry constructor(var id: Long?, val projectId: Long?, val userId: Long?, val date: LocalDate?, val hours: Int?) {

    constructor(projectId: Long, userId: Long, date: LocalDate, hours: Int) : this(null, projectId, userId, date, hours)
    constructor() : this(null, null, null, null, null)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TimeEntry

        if (id != other.id) return false
        if (projectId != other.projectId) return false
        if (userId != other.userId) return false
        if (date != other.date) return false
        if (hours != other.hours) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (projectId?.hashCode() ?: 0)
        result = 31 * result + (userId?.hashCode() ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (hours ?: 0)
        return result
    }


}