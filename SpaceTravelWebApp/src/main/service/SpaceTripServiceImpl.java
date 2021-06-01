package main.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.model.SpaceTrip;
import main.model.User;
import main.repository.SpaceTripRepository;
import main.repository.UserRepository;

@Service
@Transactional
public class SpaceTripServiceImpl implements SpaceTripService
{
	@Autowired
	private SpaceTripRepository spaceTripRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<SpaceTrip> getAll() 
	{
		return spaceTripRepository.findAll();
	}

	@Override
	public SpaceTrip getById(int id) 
	{
		return spaceTripRepository.findById(id).get();
	}

	@Override
	public void saveOrUpdate(SpaceTrip spaceTrip) 
	{
		spaceTripRepository.save(spaceTrip);
	}

	@Override
	public void delete(int id) 
	{
		spaceTripRepository.deleteById(id);
	}

	@Override
	public void addUserToTrip(int spaceTripId, String login) 
	{
		SpaceTrip spaceTrip = getById(spaceTripId);
		if(spaceTrip.getUsers() == null) // If the trip currently has no users, we create a new ArrayList of type user
		{
			spaceTrip.setUsers(new ArrayList<User>());
		}
		
		// Now we implement the logic for adding users to the selected space trip
		User user = userRepository.findByLogin(login);
		if(user != null)
		{
			spaceTrip.getUsers().add(user);
			saveOrUpdate(spaceTrip);
		}
	}

	@Override
	public boolean userAlreadySignedUp(String login, int spaceTripId) // this method checks if the user is already signed up to the selected trip
	{
		SpaceTrip selectedSpaceTrip = getById(spaceTripId);
		User user = userRepository.findByLogin(login);
		boolean spaceTripFound = false;
		
		// Now we check if the selected space trip is already part of the list of space trips the user is registered for
		List<SpaceTrip> spaceTripsUserSignedupFor = user.getSpaceTrips();
		
		for (SpaceTrip temp : spaceTripsUserSignedupFor)
		{
			if(selectedSpaceTrip.equals(temp))
			{
				spaceTripFound = true;
				break;
			}
		}
		return spaceTripFound;
	}
	
}
