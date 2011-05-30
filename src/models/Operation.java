package models;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.annotations.Expose;

public class Operation   {

	
	@Expose public String name;
	public String importName;
	
	@Expose public String comment;
	
	@Expose public Date date;

	@Expose public String description;
	
	@Expose public double amount;
	
	public boolean fictive;
	
	
	public String toString() {
		return name+"|"+amount;
	}
	
	@Expose public Set<Tag> tags;
	
	public Operation() {
		super();
		tags = new TreeSet<Tag>();
		fictive = false;
	}
}
