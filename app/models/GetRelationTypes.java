package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class GetRelationTypes extends Model {
	
	public Date date;
	public String apiKey;
	public String relation;
		
	public GetRelationTypes(Date date, String apiKey, String relation){
		this.date=date;
		this.apiKey=apiKey;
		this.relation=relation;
	}
}
