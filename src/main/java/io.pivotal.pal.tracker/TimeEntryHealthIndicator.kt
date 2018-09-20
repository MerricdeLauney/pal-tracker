package io.pivotal.pal.tracker
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.boot.actuate.metrics.GaugeService
import org.springframework.stereotype.Component

@Component
class TimeEntryHealthIndicator constructor(private var timeEntryRepository: JdbcTimeEntryRepository): HealthIndicator{


    override fun health(): Health {
        var builder: Health.Builder = Health.Builder()
        if (timeEntryRepository.list().size < 5) builder.up()
        else builder.down()
        return builder.build()
    }
}