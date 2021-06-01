package main.service;

import java.util.List;

import main.model.SpaceTrip;

public interface SpaceTripService 
{
	public List<SpaceTrip> getAll();
	
	public SpaceTrip getById(int id);
	
	public void saveOrUpdate(SpaceTrip spaceTrip);
	
	public void delete(int id);
	
	public void addUserToTrip(int spaceTripId, String login);
	
	public boolean userAlreadySignedUp(String login, int spaceTripId);
}
