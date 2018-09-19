package io.pivotal.pal.tracker

import java.time.LocalDate

class TimeEntry constructor(id: Long?,
                            projectId: Long,
                            userId: Long,
                            date: LocalDate,
                            hours: Int) {

    constructor(projectId: Long,
                userId: Long,
                date: LocalDate,
                hours: Int) : this(null, projectId, userId, date, hours)

    constructor() : this(null, 0, 0, LocalDate.now(), 0)

    var id: Long? = null
    var projectId: Long? = null
    var userId: Long? = null
    var date: LocalDate? = null
    var hours: Int? = null

    init {
        this.id = id
        this.projectId = projectId
        this.userId = userId
        this.date = date
        this.hours = hours
    }

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