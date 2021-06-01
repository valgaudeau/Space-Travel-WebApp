package main.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.model.SpaceTripDetails;
import main.repository.SpaceTripDetailsRepository;

@Service
@Transactional
public class SpaceTripDetailsServiceImpl implements SpaceTripDetailsService
{
	@Autowired
	private SpaceTripDetailsRepository spaceTripDetailsRepository;

	@Override
	public SpaceTripDetails getById(int id) 
	{
		return spaceTripDetailsRepository.getOne(id);
	}

	@Override
	public void saveOrUpdate(SpaceTripDetails spaceTripDetails) 
	{
		spaceTripDetailsRepository.save(spaceTripDetails);
	}

	@Override
	public void delete(int id) 
	{
		spaceTripDetailsRepository.deleteById(id);
	}
	
}
