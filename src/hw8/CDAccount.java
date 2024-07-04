package hw8;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
public class CDAccount extends SavingsAccount{
    private Calendar mtDate;
    private String Date;
    
     public CDAccount(){
       super();
       mtDate = Calendar.getInstance();
       Date = ""; 
    }
    
    
    //parameter with Constructor
   	public CDAccount(Depositor dep, int x, String str, double xx, String status, String date, int y) {
		//System.out.println("Account: 4-parameter Constructor running");
		super(dep,x,str,xx,status, y);
		Date = date;
		mtDate = null;
		if(str.equals("CD")) {
	        setMtDate(date);  
		}
	}
	
	//copy constructor
    public CDAccount(CDAccount acct){
        super(acct);
        //super().vroom //calling a superclass method
        Date = acct.Date;
        //super = acct.super();
    }
    
      /*     Method Deposit()
   * Input:
   *  Transaction ticket is a parameter
   * Process:
   *  process if condition for if account status is close
   *  if amount to deposit is a zero or negative
   *  if amount is greater than zero but is not or is a CD account
   *  if date of maturity of CD account has been reached
   * Output:
   *  returns reciept
   */
     public TransactionReceipt makeDeposit(TransactionTicket ticket) throws FileNotFoundException, IOException,InvalidAmountException,AccountClosedException{
        //System.out.println("DEP: size = "+receipts.size() + " " + acctNum);
        TransactionReceipt receipt = new TransactionReceipt(); 
        double pre; double post; double amt; int term;
        amt = ticket.getAmountofTran();
        term = ticket.getTermofCD();
        try {
        	throwInvalidAmount(amt);
        	throwAccountClosed();
        
            if (amt > 0 && ticket.getdateOfTran().after(mtDate)) {
                pre = balance;
                post = balance + amt;
                setBalance(post);
                mtDate.add(Calendar.MONTH,term );
                int YEAR = mtDate.get(Calendar.YEAR);
                int MONTH = mtDate.get(Calendar.MONTH) + 1;
                int DAY_OF_MONTH = mtDate.get(Calendar.DAY_OF_MONTH);
                setDate(MONTH +"/"+DAY_OF_MONTH+"/"+YEAR);
                setMtDate(Date);
                receipt = new TransactionReceipt(ticket, true, "", "CD", pre, post, Date,getDepositor ().getName () );
                writeReciept(receipt);
            }
            else {
            	if (ticket.getdateOfTran().before(mtDate)) {
            
                    pre = balance;
                    post = balance;
                    int YEAR = mtDate.get(Calendar.YEAR);
                    int MONTH = mtDate.get(Calendar.MONTH) + 1;
                    int DAY_OF_MONTH = mtDate.get(Calendar.DAY_OF_MONTH);
                    setDate(MONTH +"/"+DAY_OF_MONTH+"/"+YEAR);
                    receipt = new TransactionReceipt(ticket, false, "Error: CD Account has not attained its maturity date",getAcctType(),
                                              pre, post,Date,getDepositor ().getName () );
                    writeReciept(receipt);
                }
            }//close else       
            
        }//close try
        catch(InvalidAmountException ex) {
        	pre = balance;
            post = balance;
            receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(),pre, post,null, getDepositor().getName());
            writeReciept(receipt);   
        }
        catch (AccountClosedException ex) {
        	receipt = new TransactionReceipt(ticket, false,  ex.getMessage(),getAcctType(),0.0, 0.0,null, getDepositor ().getName ());  
            writeReciept(receipt);
        	
        }//close AccountClosedException
            //System.out.println("DEP: size = "+receipts.size() + " " + acctNum);
            return receipt;
       }//  closes deposit
       
             
      /*     Method Withdrawal()
   * Input:
   *  Transaction ticket is a parameter
   * Process:
   *  Process if condition for if account status is close
   *  if amount is valid(not negative or greater than balance)
   *  checks maturity date for CD account
   * Output:
   *  returns reciept
   */ 
    public TransactionReceipt makeWithdrawal(TransactionTicket ticket)throws IOException, FileNotFoundException,InvalidAmountException,
    AccountClosedException,InsufficientFundsException,CDMaturityDateException{
        TransactionReceipt receipt = new TransactionReceipt(); 
        double pre; double post;
        double amt; int term;
        amt = ticket.getAmountofTran();
        term = ticket.getTermofCD();
        try {
        	throwInvalidAmount(amt);
        	throwAccountClosed();
        	throwInsufficientFunds(amt);
        	throwCDMaturityDate(ticket);
            pre = balance;
            post = balance - amt;
            setBalance(post);
            mtDate.add(Calendar.MONTH,term );
            int YEAR = mtDate.get(Calendar.YEAR);
            int MONTH = mtDate.get(Calendar.MONTH) + 1;
            int DAY_OF_MONTH = mtDate.get(Calendar.DAY_OF_MONTH);
            setDate(MONTH +"/"+DAY_OF_MONTH+"/"+YEAR);
            setMtDate(Date);
            receipt = new TransactionReceipt(ticket, true, "", "CD", pre, post, Date,getDepositor ().getName () );
            writeReciept(receipt);
               
        }//close try
        catch(InvalidAmountException|InsufficientFundsException ex ) {
        	pre = balance;
            post = balance;
            receipt = new TransactionReceipt(ticket, false,ex.getMessage(),getAcctType(),pre, post,null,getDepositor ().getName ());
            writeReciept(receipt);
        }
        catch (AccountClosedException ex ) {
        	receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(),0.0, 0.0,null,getDepositor ().getName () );  
        	writeReciept(receipt);
        	
        }//close account close
        catch(CDMaturityDateException ex ) {
        	 pre = balance;
             post = balance;
             int YEAR = mtDate.get(Calendar.YEAR);
             int MONTH = mtDate.get(Calendar.MONTH) + 1;
             int DAY_OF_MONTH = mtDate.get(Calendar.DAY_OF_MONTH);
             setDate(MONTH +"/"+DAY_OF_MONTH+"/"+YEAR);
             receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(), pre, post,Date, getDepositor ().getName ());
             writeReciept(receipt);
        	
        }
        return receipt;
    }//close makewithdrawal  
            
    
    
    public void throwCDMaturityDate(TransactionTicket ticket)throws CDMaturityDateException {
    	 if (ticket.getdateOfTran().before(mtDate)) {
    		 throw new CDMaturityDateException();
    	 }
    }//close throwCDMaturityDate
    
	//toString() method - uses String static method .format()
	public String toString() {
		Name myName;
        myName = getDepositor().getName();
		
		String str = String.format("%-10s%-8s%-11s%-8d%-11s%-9.2f%-8s%-10s",
									myName.getFirst(),
									myName.getLast(),
									getDepositor().getSocSec(),
									getAcctNum(),
									getAcctType (),
									getBalance(),
									getAcctStatus(),
									getDate());
		return str;
	}
	//for write to dat file
	public String toString1() {
		Name myName;
        myName = getDepositor().getName();
		String str = String.format( myName.getFirst()+" "+
									myName.getLast()+" "+
									getDepositor().getSocSec()+" "+
									getAcctNum()+" "+
									getAcctType ()+" "+
									getBalance()+" "+
									getAcctStatus()+ " "+
									getDate()+ " " +
									getReceipts());
		return str;
	}
	
	//setters
	 private void setMtDate(String date) {
		mtDate = Calendar.getInstance();
		mtDate.clear();
		String[] mtdArray = date.split("/");
		mtDate.set(Integer.parseInt(mtdArray[2]),
				Integer.parseInt(mtdArray[0]) - 1,
				Integer.parseInt(mtdArray[1]));
	}
	
    private void setDate(String date){
	    Date = date;
	}
	
	//getters
	public Calendar getmtDate(){
        return mtDate;
        
    }
    public String getDate() {
        return Date;
	/*	String str;
		if(!getAcctType().equals("CD")) {
		   str = " "; 
		   return str;
		}
		str = String.format("%02d/%02d/%4d",
							mtDate.get(Calendar.MONTH) + 1,
							mtDate.get(Calendar.DAY_OF_MONTH),
							mtDate.get(Calendar.YEAR)
							);
		*/
		//return str;
	}
	
}
