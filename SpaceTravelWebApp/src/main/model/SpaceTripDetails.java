package main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="space_trip_details")
public class SpaceTripDetails 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int numberOfPassengers;
	
	@Column(length = 2000)
	private String tripDescription;
	
	/*
	 * Uncomment this if you want to have bi-directional mapping between TourDetails and Tour 
	@OneToOne(mappedBy = "spaceTripDetails") // Here we put the name of the class field from the Tour class which holds the foreign key of TourDetails
	private Tour tour;
	*/
	
	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public int getNumberOfPassengers() { return numberOfPassengers; }

	public void setNumberOfPassengers(int numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }

	public String getTripDescription() { return tripDescription; }			

	public void setTripDescription(String tripDescription) { this.tripDescription = tripDescription; }
	
}
