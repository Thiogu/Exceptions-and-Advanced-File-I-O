package hw8;

import java.util.Calendar;

public class Check{
    private int acctNum;
    private double checkAmount;
    private Calendar dateOfCheck;
    private String Date;
    
    
  
   //: add a copy constructor and implement the toString() method

    //constructor without Parameter
    public Check(){
        acctNum = 0;
       checkAmount = 0;
       dateOfCheck = null;
        Date = "";
        
    }
    
    //Constructor with Parameters
	public Check(int x, double xx, String date) {
		//System.out.println("Check: 3-parameter Constructor running");
		acctNum = x;
		checkAmount = xx;
		setDateOfCheck(date);
	    Date = date;
	}

    //Copy Constructor
	public Check(Check ch) {
		//System.out.println("Check: 4-parameter Constructor running");
		acctNum = ch.acctNum;
		checkAmount = ch.checkAmount; 
	    dateOfCheck = ch.dateOfCheck;
	    Date = ch.Date;
	}
	//toString() method - uses String static method .format()
	public String toString() {

		String str = String.format("%8d%9.2f%-8s",
									getAcctNum(),
									getCheckAmount(),
									getDate());
									
		return str;
	}
    
    //setters
    private void setAcctNum(int num){
        acctNum = num;
    }
    private void setcheckAmount(double num){
        checkAmount = num;
    }
    private void setDateOfCheck(String date) {
        dateOfCheck = Calendar.getInstance();
		dateOfCheck.clear();
		String[] docArray = date.split("/");
		dateOfCheck.set(Integer.parseInt(docArray[2]),
				Integer.parseInt(docArray[0]) - 1,
				Integer.parseInt(docArray[1]));
	}
    
    
    
    //getters
    public int getAcctNum(){
        return acctNum;
    }
    public double getCheckAmount(){
        return checkAmount;
    } 
    public String getDate(){
        return Date;
    }
    public Calendar getDateOfCheck() {
		return dateOfCheck;
	}
}