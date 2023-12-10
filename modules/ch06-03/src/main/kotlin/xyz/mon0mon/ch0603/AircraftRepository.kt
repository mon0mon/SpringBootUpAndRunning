package xyz.mon0mon.ch0603

import org.springframework.data.repository.CrudRepository

interface AircraftRepository: CrudRepository<Aircraft, String> {
}