package main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length=500)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "spacetrip_id")
	private SpaceTrip spaceTrip;
	
	public int getId() { return id; }
	
	public void setId(int id) { this.id = id; }
	
	public String getContent() { return content; }
	
	public void setContent(String content) { this.content = content; }
	
	public SpaceTrip getSpaceTrip() { return spaceTrip; }
	
	public void setSpaceTrip(SpaceTrip spaceTrip) { this.spaceTrip = spaceTrip; }
	
}
