package main.service;

import main.model.SpaceTripDetails;

public interface SpaceTripDetailsService 
{
	public SpaceTripDetails getById(int id);
	
	public void saveOrUpdate(SpaceTripDetails spaceTripDetails);
	
	public void delete(int id);
}
