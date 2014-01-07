package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class SearchHelp extends Model {
	
	public Date date;
	public String apiKey;
	public String index;
	public String prefix;
		
	public SearchHelp(Date date, String apiKey, String index, String prefix){
		this.date=date;
		this.apiKey=apiKey;
		this.prefix=prefix;
		this.index=index;
	}
}
