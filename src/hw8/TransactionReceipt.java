package hw8;

import java.util.Calendar;

public class TransactionReceipt{
    private TransactionTicket ticket;
    private boolean successIndicator;
    private String reasonForFailure;
    private String acctType;
    private double preTranBalance;
    private double postTranBalance;
    private Calendar postTranMtDate;
    private String Date;
    private Name name;
    
    
    //: add a copy constructor and implement the toString() method
    
    //constructor without Parameters
    public TransactionReceipt(){
       ticket = new  TransactionTicket();
       successIndicator = false;
       reasonForFailure = " ";
       acctType = "";
       preTranBalance = 0.0;
       postTranBalance = 0.0;
       postTranMtDate = Calendar.getInstance();
	   postTranMtDate.clear();
	   Date = " ";
	   name = new Name();
    }

    
    //Constructor with Parameters
	public TransactionReceipt(TransactionTicket tt,boolean ss,String reason,String str,double xi, double xii, String date, Name n) {
		//System.out.println("Transaction receipt: 7-parameter Constructor running");
		ticket = tt;
		successIndicator = ss;
		reasonForFailure = reason;
		acctType = str;
		preTranBalance = xi;
		postTranBalance = xii;
	    Date = date;
	    name = n;
	}
    //Copy Constructor
	public TransactionReceipt(TransactionReceipt receipt) {
		//System.out.println("Transaction Ticket: 5-parameter copy Constructor running");
		ticket = new TransactionTicket(receipt.ticket);
		successIndicator = receipt.successIndicator; 
	    reasonForFailure = receipt.reasonForFailure;
	    acctType = receipt.acctType;
	    preTranBalance = receipt.preTranBalance;
	    postTranBalance = receipt.postTranBalance;
	    Date = receipt.Date;
	}
	
	//toString() method - uses String static method .format()
	public String toString() { //failure String
	    System.out.println("Transaction Receipt to string running");
        String str = String.format("Name:"+ name.toString()+"\n"+"Account number:" + ticket.getAcctNum () + "\n" +
                                    getreasonForFailure() + "\n");
       
		return str;
	}
	//toString() method - uses String static method .format()
	public String toString1() { //failure String for CD account
	    System.out.println("Transaction Receipt to string running");
        String str = String.format("Account number:" + ticket.getAcctNum () + "\n" +
                                    getreasonForFailure() + "\n" + "CD Maturity Date:" + getDate());
       
		return str;
	}
	
  //toString() method - uses String static method .format()
	public String toString2() { //success string for balance only
	    System.out.println("Transaction Receipt to string2 running");
        String str = String.format("Name:"+ name.toString()+"\n"+"Account number: %d\nAccount type: %s\nBalance: $%.2f",
								    ticket.getAcctNum(),
									getAcctType(),
								    getpostTranBalance());
		return str;
	}
	
	//toString() method - uses String static method .format()
	public String toString3() { //success string for CD Account (deposit)
	    System.out.println("Transaction Receipt to string2 running");
         String str = String.format("Name:"+ name.toString()+"\n"+"Account number: %d\nAccount type: %s\nOld balance: $%.2f\nAmount to Deposit: $%.2f\nNew balance: $%.2f\nCD new Maturity date : %s\n",
								    ticket.getAcctNum(),
									getAcctType(),
								    getPreTranBalance(),
								    ticket.getAmountofTran(),
								    getpostTranBalance(),
								    getDate());
		return str;
	}
		//toString() method - uses String static method .format()
	public String toString33() { //success string for CD Account (withdrawal)
	    System.out.println("Transaction Receipt to string2 running");
         String str = String.format("Name:"+ name.toString()+"\n"+"Account number: %d\nAccount type: %s\nOld balance: $%.2f\nAmount to Withdraw: $%.2f\nNew balance: $%.2f\nCD new Maturity date : %s\n",
								    ticket.getAcctNum(),
									getAcctType(),
								    getPreTranBalance(),
								    ticket.getAmountofTran(),
								    getpostTranBalance(),
								    getDate());
		return str;
	}
	//toString() method - uses String static method .format()
	public String toString4() { //success string (deposit)
	    System.out.println("Name:"+ name.toString()+"\n"+"Transaction Receipt to string2 running");
        String str = String.format("Account number: %d\nAccount type: %s\nOld balance: $%.2f\nAmount to Deposit: $%.2f\nNew balance: $%.2f\n",
								    ticket.getAcctNum(),
									getAcctType(),
								    getPreTranBalance(),
								    ticket.getAmountofTran(),
								    getpostTranBalance());
		return str;
	}
	//toString() method - uses String static method .format()
	public String toString5() { //success string for withdrawal
	    System.out.println("Transaction Receipt to string2 running");
        String str = String.format("Name:"+ name.toString()+"\n"+"Account number: %d\nAccount type: %s\nOld balance: $%.2f\nAmount to Withdraw: $%.2f\nNew balance: $%.2f\n",
								    ticket.getAcctNum(),
									getAcctType(),
								    getPreTranBalance(),
								    ticket.getAmountofTran(),
								    getpostTranBalance());
		return str;
	}
	//toString() method - uses String static method .format()
	public String toString6() { //failure string for check
	    System.out.println("Transaction Receipt to string2 running");
        String str = String.format("Account number: %d\nAccount type: %s\nOld balance: $%.2f\nAmount to Withdraw: $%.2f\nNew balance: $%.2f\n%s\n",
								     ticket.getAcctNum(),
									getAcctType(),
								    getPreTranBalance(),
								    ticket.getAmountofTran(),
								    getpostTranBalance(),
								    getreasonForFailure());
		return str;
	}
	//toString() method - uses String static method .format()
	public String toString7() { //success string for balance only
	    System.out.println("Transaction Receipt to string7 running");
        String str = String.format("Name: %s\nNew Account number: %s\nAccount type: %s\nOpening balance: $%.2f",
								    getName(),
								    ticket.getAcctNum(),
								    getAcctType(),
								    getpostTranBalance());
		return str;
	}
	
     
    //setters
    private void setTransactionTicket(TransactionTicket T) {
        ticket = T;
    }
     private void Name(Name n) {
        name = n;
    }
    private void setSuccessIndicator(){
        successIndicator = true;
    }
    private void setAcctType(String str){
        acctType = str;
    }
    private void setPreTranBalance(double num){
        preTranBalance = num;
    }
     private void setpostTranBalance(double num){
        postTranBalance = num;
    }
    private void setpostTranMtDate(String date) {
        postTranMtDate = Calendar.getInstance();
		postTranMtDate.clear();
		String[] mtdArray = date.split("/");
		postTranMtDate.set(Integer.parseInt(mtdArray[2]),
				Integer.parseInt(mtdArray[0]) - 1,
				Integer.parseInt(mtdArray[1]));
	}
    
  
   
    //getters
    public TransactionTicket getTransactionTicket(){
        return new TransactionTicket(ticket);
    }
    public Name getName(){
        return new Name(name);
    }
    public boolean getSuccessIndicator(){
        return successIndicator;
    }
    public String getreasonForFailure(){
        return reasonForFailure;
    }
    public String getAcctType(){
        return acctType;
    }
    public double getPreTranBalance(){
        return preTranBalance;
    }
    public double getpostTranBalance(){
        return postTranBalance;
    }
    public String getDate(){
        return Date;
    }
    public Calendar getpostTranMtDateStr() {
        return postTranMtDate;
    }
   /*public String getDate() {
		String str;
		if(!acctType.equals("CD")) {
		   str = " "; 
		   return str;
		}
		str = String.format("%02d/%02d/%4d",
							postTranMtDate.get(Calendar.MONTH) + 1,
							postTranMtDate.get(Calendar.DAY_OF_MONTH),
							postTranMtDate.get(Calendar.YEAR)
							);
		return str;
	}
	
  */
	
	
	
}