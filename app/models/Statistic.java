package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Statistic extends Model {
	
	public Date date;
	public String apiKey;
	public String index;
	
	public Statistic(Date date, String apiKey, String index){
		this.date=date;
		this.apiKey=apiKey;
		this.index=index;
	}
}
