package io.pivotal.pal.tracker

class InMemoryTimeEntryRepository : TimeEntryRepository {
    private val timeEntries: HashMap<Long?, TimeEntry?> = HashMap()
    private var currentId: Long = 1

    override fun create(timeEntry: TimeEntry?): TimeEntry? {
        val id: Long = currentId++
        timeEntry?.id = id
        timeEntries[id] = timeEntry
        return timeEntry
    }

    override fun find(id: Long?): TimeEntry? {
        if (id == null) throw Exception()
        return timeEntries[id]
    }

    override fun list(): List<TimeEntry?> {
        return timeEntries.values.toList()
    }

    override fun update(id: Long?, timeEntry: TimeEntry?): TimeEntry? {
        if (id == null) throw Exception()
        timeEntry?.id = id
        timeEntries[id] = timeEntry
        return timeEntries[id]
    }

    override fun delete(id: Long?): TimeEntry? {
        if (id == null) throw Exception()
        val retTimeEntry: TimeEntry? = timeEntries[id]
        timeEntries.remove(id)
        return retTimeEntry
    }
}