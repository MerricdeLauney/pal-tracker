package io.pivotal.pal.tracker

class InMemoryTimeEntryRepository: TimeEntryRepository{
    var storage: HashMap<Long?, TimeEntry> = HashMap()

    override fun create(timeEntry: TimeEntry): TimeEntry?{
        timeEntry.id = storage.size.toLong() + 1
        storage.put(timeEntry.id, timeEntry)
        return  storage.get(timeEntry.id)
    }

    override fun find(id: Long): TimeEntry? {
        return storage.get(id)
    }

    override fun list(): List<TimeEntry> {
        return storage.values.toList()
    }

    override fun update(id: Long, timeEntry: TimeEntry): TimeEntry? {
        timeEntry.id = id
        storage.put(id, timeEntry)
        return storage.get(id)
    }

    override fun delete(id: Long): TimeEntry? {
        return storage.remove(id)
    }
}