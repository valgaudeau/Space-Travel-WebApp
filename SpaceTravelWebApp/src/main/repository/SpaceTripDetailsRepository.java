package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.model.SpaceTripDetails;

@Repository
public interface SpaceTripDetailsRepository extends JpaRepository<SpaceTripDetails, Integer> 
{

}
