package main.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class SpaceTrip 
{
	public enum Destination
	{
		MARS, MERCURY, VENUS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO
	}
	
	public SpaceTrip()
	{
		setSpaceTripDetails(new SpaceTripDetails());
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	// Here we apply all the annotations for validation of the fields
	@NotBlank(message = "You have to include a name for the trip")
	@Size(min = 5, message = "The name should have at least 5 characters")
	private String name;
	
	@NotBlank
	@Size(min = 5, max=5)
	private String code;
	
	@NotNull(message = "Please select a destination")
	private Destination destination;
	
	@NotNull(message = "Please select a date")
	@Future(message = "The chosen date has to be in the future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@NotNull(message = "Please select trip duration")
	@Min(value = 7, message = "The duration of the trip must be between 7 and 28")
	@Max(value = 28, message = "The duration of the trip must be between 7 and 28")
	private int duration;
	
	/*
	 *  CascadeType.ALL means that the persistence will propagate all EntityManager operations (PERSIST, REMOVE, REFRESH, MERGE, DETACH) to
	 *  the relating entities. There are other options for CascadeType, such as PERSIST, REMOVE, REFRESH, MERGE.
	 *  See documentation for which is appropriate depending on context.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "trip_details_id")
	private SpaceTripDetails spaceTripDetails; // this field holds the foreign id key of the SpaceTripDetails class
	
	/*
	* mappedBy= "spaceTrip" is the name of the field in the Comment class that contains the information about the tour primary key.
	* We also added that we want all comments to be deleted if the tour they're associated with will be deleted, which is the
	* other parameters we've passed here regarding CascadeType.ALL and orphanRemoval.
	 */ 
	@OneToMany(mappedBy = "spaceTrip", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> comments;
	
	@ManyToMany
	@JoinTable(name = "spacetrip2user",
	   			joinColumns = @JoinColumn(name = "spacetrip_id"),
	   			inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> users;
	
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
		
	public String getCode() { return code; }
	
	public void setCode(String code) { this.code = code; }
	
	public Destination getDestination() { return destination; }
		
	public void setDestination(Destination destination) { this.destination = destination; }
	
	public Date getDate() { return date; }
	
	public void setDate(Date date) { this.date = date; }
	
	public int getDuration() { return duration; }
	
	public void setDuration(int duration) { this.duration = duration; }

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public SpaceTripDetails getSpaceTripDetails() { return spaceTripDetails; }

	public void setSpaceTripDetails(SpaceTripDetails spaceTripDetails) { this.spaceTripDetails = spaceTripDetails; }
	
	public List<Comment> getComments() { return comments; }
	
	public void setComments(List<Comment> comments) { this.comments = comments; }

	public List<User> getUsers() { return users; }

	public void setUsers(List<User> users) { this.users = users; }
		
}
