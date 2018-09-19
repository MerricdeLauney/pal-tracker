package io.pivotal.pal.tracker

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TimeEntryController constructor(private var timeEntryRepository: TimeEntryRepository) {

    init {
        this.timeEntryRepository = timeEntryRepository
    }

    @PostMapping("/time-entries")
    fun create(@RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry?> {
        return ResponseEntity(timeEntryRepository.create(timeEntry), HttpStatus.CREATED)
    }

    @GetMapping("/time-entries")
    fun list(): ResponseEntity<List<TimeEntry>> {
        return ResponseEntity(timeEntryRepository.list(), HttpStatus.OK)
    }

    @GetMapping("/time-entries/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<TimeEntry?> {
        var foundEntry = timeEntryRepository.find(id)
        if (foundEntry != null) {
            return ResponseEntity(foundEntry, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)

    }

    @PutMapping("/time-entries/{id}")
    fun update(@PathVariable id: Long, @RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry?> {
        var updatedEntry = timeEntryRepository.update(id, timeEntry)
        if (updatedEntry != null) {
            return ResponseEntity(updatedEntry, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/time-entries/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<TimeEntry?> {
        return ResponseEntity(timeEntryRepository.delete(id), HttpStatus.NO_CONTENT)
    }


}