package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class GetRelations extends Model {
	
	public Date date;
	public String apiKey;
	public String relation;
	public String uri;
		
	public GetRelations(Date date, String apiKey, String relation, String uri){
		this.date=date;
		this.apiKey=apiKey;
		this.relation=relation;
		this.uri=uri;
	}
}
