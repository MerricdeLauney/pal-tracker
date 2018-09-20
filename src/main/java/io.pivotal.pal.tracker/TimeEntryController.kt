package io.pivotal.pal.tracker
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.actuate.metrics.GaugeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class TimeEntryController @Autowired
constructor(private val timeEntryRepository: TimeEntryRepository,
            private val counter: CounterService?,
            private val gauge: GaugeService) {

    @PostMapping("/time-entries")
    fun create(@RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry?> {
        counter?.increment("created")
        val responseTimeEntry: TimeEntry? = timeEntryRepository.create(timeEntry)
        gauge.submit("gauche", list().body.size.toDouble())
        return if (responseTimeEntry == null) ResponseEntity(HttpStatus.I_AM_A_TEAPOT) else ResponseEntity(responseTimeEntry, HttpStatus.CREATED)
    }

    @GetMapping("/time-entries/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<TimeEntry> {
        counter?.increment("read")
        val responseTimeEntry: TimeEntry? = timeEntryRepository.find(id)
        return if (responseTimeEntry == null) ResponseEntity(HttpStatus.NOT_FOUND) else ResponseEntity(responseTimeEntry, HttpStatus.OK)
    }

    @GetMapping("/time-entries")
    fun list(): ResponseEntity<List<TimeEntry?>> {
        counter?.increment("listed")
        val responseTimeEntryList: List<TimeEntry?> = timeEntryRepository.list()
        return ResponseEntity(responseTimeEntryList, HttpStatus.OK)
    }

    @PutMapping("/time-entries/{id}")
    fun update(@PathVariable id: Long,@RequestBody timeEntry: TimeEntry?): ResponseEntity<TimeEntry> {
        counter?.increment("updated")
        val responseTimeEntry: TimeEntry? = timeEntryRepository.update(id, timeEntry)
        return if (responseTimeEntry == null) ResponseEntity(HttpStatus.NOT_FOUND) else ResponseEntity(responseTimeEntry, HttpStatus.OK)
    }

    @DeleteMapping("/time-entries/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<TimeEntry> {
        counter?.increment("deleted")
        timeEntryRepository.delete(id)
        gauge.submit("gauche", list().body.size.toDouble())
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}

