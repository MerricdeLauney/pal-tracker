package io.pivotal.pal.tracker

import com.mysql.cj.api.jdbc.Statement
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.Instant
import java.time.LocalDate
import javax.sql.DataSource

class JdbcTimeEntryRepository constructor(dataSource: DataSource) : TimeEntryRepository {
    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

    override fun find(id: Long?): TimeEntry? {
       return jdbcTemplate.query("SELECT * FROM time_entries WHERE ID=$id", extractor)
    }

    override fun list(): List<TimeEntry?> {
        return jdbcTemplate.query("SELECT * FROM time_entries", mapper)
    }

    override fun update(id: Long?, timeEntry: TimeEntry?): TimeEntry? {
        if (find(id) == null) return null
        jdbcTemplate.update { con ->
            val statement = con.prepareStatement("UPDATE time_entries SET project_id=?, user_id=?, date=?, hours=? WHERE id=?", Statement.RETURN_GENERATED_KEYS)
            statement.setLong(1, timeEntry?.projectId ?: 0)
            statement.setLong(2, timeEntry?.userId ?: 0)
            statement.setDate(3, Date.valueOf(timeEntry?.date ?: LocalDate.MAX))
            statement.setInt(4, timeEntry?.hours ?: 0)
            statement.setLong(5, id ?: 0)


            statement
        }

        return find(id)
    }

    override fun delete(id: Long?): TimeEntry? {
        val timeEntry = find(id);
        jdbcTemplate.update("DELETE FROM time_entries WHERE id=$id")
        return timeEntry
    }

    override fun create(timeEntry: TimeEntry?): TimeEntry? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ con ->
            val statement = con.prepareStatement("INSERT into time_entries (project_id, user_id, date, hours)" +
                    "VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
            statement.setLong(1, timeEntry?.projectId ?: 0)
            statement.setLong(2, timeEntry?.userId ?: 0)
            statement.setDate(3, Date.valueOf(timeEntry?.date ?: LocalDate.MAX))
            statement.setInt(4, timeEntry?.hours ?: 0)

            statement
        }, keyHolder)

        return find(keyHolder.key?.toLong())
    }

    private var mapper: RowMapper<TimeEntry> = RowMapper { rs, _ -> TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours"))
    }


    private var extractor: ResultSetExtractor<TimeEntry?> = ResultSetExtractor { resultSet ->
        if (resultSet.next()) mapper.mapRow(resultSet, 1); else null}

}