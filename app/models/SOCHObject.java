package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class SOCHObject extends Model {
	
	public Date date;
	public String institution;
	public String service;
	public String format;
	public String instId;
	
	public SOCHObject(Date date, String institution, String service, String format, String instId){
		this.date=date;
		this.institution=institution;
		this.service=service;
		this.format=format;
		this.instId=instId;
	}
}
