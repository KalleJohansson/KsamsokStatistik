package models;

import java.util.Date;

import javax.persistence.Entity;


import play.db.jpa.Model;

@Entity
public class GetGeoResource extends Model {
	
	public Date date;
	public String apiKey;
	public String uri;
		
	public GetGeoResource(Date date, String apiKey, String uri){
		this.date=date;
		this.apiKey=apiKey;
		this.uri=uri;
	}
}
