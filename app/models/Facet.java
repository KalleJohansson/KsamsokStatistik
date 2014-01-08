package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.db.jpa.Model;

@Entity
public class Facet extends Model {
	
	public Date date;
	
	public String apiKey;
	@Lob
	public String query;
	
	public String index;
	
	public Facet(Date date, String apiKey, String query, String index){
		this.date = date;
		this.apiKey = apiKey;
		this.query = query;
		this.index = index;
	}
}
