package hw8;

public class Name {
    private String last;
    private String first;
   
     //: add a copy constructor and implements both the toString() and the equals() methods
     
    //constructors without Parameters
    public Name(){
       last ="";
       first = "";
    }
    //Copy Constructor
	public Name(Name myName) {
		last = myName.last;
		first = myName.first;
	}

    //Constructor with Parameters
	public Name( String L, String F) {
		//System.out.println("Name: 2-parameter Constructor running");
		last = L;
		first = F;
	}
    //setters private
    private void setLast(String str) {
    last = str;
    }
    private void setFirst(String str) {
    first = str;
    }
    //getters
    public String getLast() {
    return last;
    }
    public String getFirst() {
    return first;
    }
    //toString() method - uses String static method .format()
	public String toString() {
		//String str = String.format("%-10s%-10s", first,last);
		String str = String.format("%s %s", first,last);
		return str;
	}

	//.equals() method
	public boolean equals(Name myName) {
		if(last.equals(myName.last) && first.equals(myName.first))
			return true;			//myName found
		else
			return false;			//myName not found
	}

}