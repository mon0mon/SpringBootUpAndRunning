package xyz.mon0mon.ch0604;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;

public interface AircraftRepository extends Neo4jRepository<Aircraft, Long> {
}
