package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Stem extends Model {
	
	public Date date;
	public String apiKey;
	public String words;
		
	public Stem(Date date, String apiKey, String words){
		this.date=date;
		this.apiKey=apiKey;
		this.words=words;
	}
}
