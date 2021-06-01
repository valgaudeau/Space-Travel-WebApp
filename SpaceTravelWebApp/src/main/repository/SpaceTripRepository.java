package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.model.SpaceTrip;

@Repository
public interface SpaceTripRepository extends JpaRepository<SpaceTrip, Integer>
{

}
