package ru.ookamilb.rugball;

import java.util.Date;

public class Message {
	int id;
	Date date;
	String text;
	
	public Message(String text) { this.text = text; this.date = new Date();}
	public Message(int id, String text) { this.id = id; this.text = text; this.date = new Date(); }
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	public String getMessage() { return this.text; }
	public void setMessage(String text) { this.text = text; }
	public Date getDate() { return this.date; }
	public void setDate(Date date) { this.date = date; }
}
