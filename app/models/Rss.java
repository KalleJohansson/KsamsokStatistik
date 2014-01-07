package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Rss extends Model {
	
	public Date date;
	public String apiKey;
	public String query;
	public String recordSchema;
	public String fields;
	
	public Rss(Date date, String apiKey, String query, String recordSchema, String fields){
		this.date=date;
		this.apiKey=apiKey;
		this.query=query;
		this.recordSchema=recordSchema;
		this.fields=fields;
	}
}
