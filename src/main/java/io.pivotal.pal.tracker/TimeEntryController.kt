package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class TimeEntryController @Autowired
constructor(private val timeEntryRepository: TimeEntryRepository) {

    @PostMapping("/time-entries")
    fun create(@RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry?> {
        val responseTimeEntry: TimeEntry? = timeEntryRepository.create(timeEntry)
        return if (responseTimeEntry == null) ResponseEntity(HttpStatus.I_AM_A_TEAPOT) else ResponseEntity(responseTimeEntry, HttpStatus.CREATED)

    }

    @GetMapping("/time-entries/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<TimeEntry> {
        val responseTimeEntry: TimeEntry? = timeEntryRepository.find(id)
        return if (responseTimeEntry == null) ResponseEntity(HttpStatus.NOT_FOUND) else ResponseEntity(responseTimeEntry, HttpStatus.OK)
    }

    @GetMapping("/time-entries")
    fun list(): ResponseEntity<List<TimeEntry?>> {
        val responseTimeEntryList: List<TimeEntry?> = timeEntryRepository.list()
        return ResponseEntity(responseTimeEntryList, HttpStatus.OK)
    }

    @PutMapping("/time-entries/{id}")
    fun update(@PathVariable id: Long,@RequestBody timeEntry: TimeEntry?): ResponseEntity<TimeEntry> {
        val responseTimeEntry: TimeEntry? = timeEntryRepository.update(id, timeEntry)
        return if (responseTimeEntry == null) ResponseEntity(HttpStatus.NOT_FOUND) else ResponseEntity(responseTimeEntry, HttpStatus.OK)
    }

    @DeleteMapping("/time-entries/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<TimeEntry> {
        timeEntryRepository.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}

