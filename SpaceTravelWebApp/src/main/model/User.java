package main.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String login;
	
	@Column(length = 68) // length is set to 68 because that is the size of passwords encoded by the Bcrypt algorithm
	private String password;
	
	/*
	 * Transient means that the field should not be a part of Serialization, and won't be saved in the database.
	 * That works for us because we only want to use it in the Java side during the sign up operation.
	 */
	@Transient 
	private String confirmedPassword;
	
	private Boolean enabled; // an account may be enabled or disabled
	
	@NotBlank(message = "Please include your first name")
	@Size(min = 1, max = 100)
	private String firstName;
	
	@NotBlank(message = "Please include your last name")
	@Size(min = 1, max = 100)
	private String lastName;
	
	@NotBlank(message = "Please include your email")
	@Email
	private String email;
	
	@ManyToMany
	@JoinTable(name = "spacetrip2user",
			   joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name="spacetrip_id"))
	private List<SpaceTrip> spaceTrips; // The list of Space Trips which the user has registered for

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getLogin() { return login; }

	public void setLogin(String login) { this.login = login; }
	
	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }
	
	public String getConfirmedPassword() { return confirmedPassword; }

	public void setConfirmedPassword(String confirmedPassword) { this.confirmedPassword = confirmedPassword; }

	public Boolean getEnabled() { return enabled; }

	public void setEnabled(Boolean enabled) { this.enabled = enabled; }

	public List<SpaceTrip> getSpaceTrips() { return spaceTrips; }

	public void setSpaceTrips(List<SpaceTrip> spaceTrips) { this.spaceTrips = spaceTrips; }

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	@Override
	public String toString() 
	{
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", confirmedPassword="
				+ confirmedPassword + ", enabled=" + enabled + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", spaceTrips=" + spaceTrips + "]";
	}
	
}
