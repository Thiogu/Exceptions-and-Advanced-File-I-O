package hw8;

public class Depositor{
    private String socSec;
    private Name nameprv;
    
    //: add a copy constructor and implements both the toString() and the equals() methods
    
    //constructor without parameter
    public Depositor(){
       socSec = "";
       nameprv = new Name();
    }
    
    //Constructor with Parameters
	public Depositor( String sc, Name n) {
		//System.out.println("Depositor: 2-parameter Constructor running");
		socSec = sc;
		nameprv = n;
	}
	//copy Constructor
    public Depositor(Depositor dep) {
		//System.out.println("Depositor: 2-parameter Constructor running");
		socSec = dep.socSec;
		nameprv = new Name(dep.nameprv); //nested copy Constructor
	}
	//toString() method - uses String static method .format()
	public String toString() {
		Name myName;
        myName = getName();
		
		String str = String.format("%-12s%-10s%-8s",
									myName.getFirst(),
									myName.getLast(),
									getSocSec());
		return str;
	}
	//equals() method
	public boolean equals(Depositor myDep) {
		if(socSec.equals(myDep.socSec))
			return true;			//Depositor found
		else
			return false;			//Depositor not found
	}
	
     //setters
    private void setSocSec(String str) {
    socSec = str;
    }
    private void setName(Name names) {
    nameprv = names;
    }
    
    //getters
    public String getSocSec() {
    return socSec;
    }
    
    public Name getName() {
    return new Name(nameprv);
    }
}