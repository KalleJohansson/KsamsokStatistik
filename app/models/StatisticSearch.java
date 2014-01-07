package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class StatisticSearch extends Model {
	
	public Date date;
	public String apiKey;
	public String query;
	public String index;
	
	public StatisticSearch(Date date, String apiKey, String query, String index){
		this.date=date;
		this.apiKey=apiKey;
		this.query=query;
		this.index=index;
	}
}
