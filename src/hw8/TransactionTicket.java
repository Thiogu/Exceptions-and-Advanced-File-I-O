package hw8;

import java.util.Calendar;

public class TransactionTicket{
    private int acctNum;
    private String typeOfTran;
    private double amountofTran;
    private int termofCD;
    private Calendar dateOfTran;
    private String dateOT;
    
    
 //: add a copy constructor and implement the toString() method

    //constructors without Parameters
    public TransactionTicket(){
       acctNum = 0; 
       typeOfTran = "";
       amountofTran = 0.0;
       termofCD = 0;
       dateOfTran = Calendar.getInstance();
    }
    
    //Constructor with Parameters
	public TransactionTicket(int x,String nameofTran, double xi,int xx) {
		//System.out.println("Transaction Ticket: 5-parameter Constructor running");
		acctNum = x;
		typeOfTran = nameofTran;
		amountofTran = xi;
	    termofCD = xx;
	    dateOfTran = Calendar.getInstance();
	    	
	}
	//Copy Constructor
	public TransactionTicket(TransactionTicket ticket) {
		//System.out.println("Transaction Ticket: 5-parameter copy Constructor running");
		acctNum = ticket.acctNum;
		typeOfTran = ticket.typeOfTran; 
	    amountofTran = ticket.amountofTran;
	    termofCD = ticket.termofCD;
	    dateOfTran = ticket.dateOfTran;
	    dateOT = ticket.dateOT;
	}
  //toString() method - uses String static method .format()
	public String toString() {
	    System.out.println("Transationticket to string running");
        String str = String.format("Date of Transaction: " + dateOfTran.getTime());
		return str;
	}
	 //toString() method - uses String static method .format()
	public String toString1() { //sucess close account
	    System.out.println("Transationticket to string running");
        String str = String.format("Account number:" + getAcctNum () + " has been closed" +"\n"+
                                    "No type of transaction will be allowed on a close account");
		return str;
	}
    //toString() method - uses String static method .format()
	public String toString2() { //sucess reopen account
	    System.out.println("Transationticket to string running");
        String str = String.format("Account number:" + getAcctNum () + " has been reopened" +"\n"+
                                    "Transactions will be allowed on this account");
		return str;
	}
	public String toString3() { //sucess reopen account
	    System.out.println("Transationticket to string running");
        String str = String.format("%-12s%-20s%-8.2f",
                                getDateOT(),
                                getTypeOfTran(),
                                getAmountofTran());
		return str;
	}

    //setters
    private void setAcctNum(int x){
        acctNum = x;
    }
    private void setDateOfTran() {
         dateOfTran = Calendar.getInstance();
	}
	private void setTypeOfTran(String str){
	    typeOfTran = str;
	}
	private void setAmountofTran(double num){
	    amountofTran = num;
	}
	private void setTermofCD(int x){
	    termofCD = x;
	}
    
    //getters
    public int getAcctNum(){
        return acctNum;
    }
   public Calendar getdateOfTran() {
       return dateOfTran;
	}
	public String getDateOT() {
		String str;
		str = String.format("%02d/%02d/%4d",
							dateOfTran.get(Calendar.MONTH) + 1,
							dateOfTran.get(Calendar.DAY_OF_MONTH),
							dateOfTran.get(Calendar.YEAR)
							);
		return str;
	}
	public String getTypeOfTran(){
	    return typeOfTran;
	}
	public double getAmountofTran(){
	    return amountofTran;
	}
	public int getTermofCD(){
	    return termofCD;
	}
    
    
}
