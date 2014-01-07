package models;

import java.util.Date;

import javax.persistence.Entity;


import play.db.jpa.Model;

@Entity
public class GetServiceOrganization extends Model {
	
	public Date date;
	public String apiKey;
	public String value;
		
	public GetServiceOrganization(Date date, String apiKey, String value){
		this.date=date;
		this.apiKey=apiKey;
		this.value=value;
	}
}
