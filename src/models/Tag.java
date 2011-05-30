package models;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Tag implements Comparable<Tag>{
	@Expose public String name;
	public String color;
	public boolean visible = true;
	
	public String toString() {
		return name;
	}
	
	public List<Operation> op;
	
	public int compareTo(Tag otherTag) {
        return name.compareTo(otherTag.name);
    }

	public boolean equals(Object other) {
		
		if (!(other instanceof Tag)) {
			return false;
		}
		
		Tag t = (Tag) other;
		
		if (this.name != t.name) {
			return false;
		}
		
		return true;
	}
	
	
}
