package hw8;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
public class CheckingAccount extends Account{
    //no-arg constructor
    public CheckingAccount(){
        super();
    }
    
    //parametized constructor
    public CheckingAccount(Depositor dep, int x, String str, double xx, String status, int y){
       super(dep,x,str,xx,status, y);
    }
    
     //copy constructor
    public CheckingAccount(CheckingAccount acct){
        super(acct);
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
     public TransactionReceipt makeDeposit(TransactionTicket ticket) throws FileNotFoundException, IOException,InvalidAmountException,
     AccountClosedException{
       // System.out.println("DEP: size = "+receipts.size() + " " + acctNum);
        TransactionReceipt receipt = new TransactionReceipt(); 
        double pre; double post; double amt; int term;
        amt = ticket.getAmountofTran();
        try {
        	throwInvalidAmount(amt);
        	throwAccountClosed();
            pre = balance;
            post = balance + amt;
            setBalance(post);
            receipt = new TransactionReceipt(ticket, true, "",getAcctType() ,pre, post,null, getDepositor().getName());
            writeReciept(receipt);
        
        }//close try
        catch(InvalidAmountException ex) {
        	 pre = balance;
             post = balance;
             receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(),pre, post,null, getDepositor().getName());
             writeReciept(receipt);    
        }
        catch(AccountClosedException ex ) {
        	receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(),0.0, 0.0,null, getDepositor().getName());  
        	writeReciept(receipt);     
        }
        return receipt;
    }//closes deposit
    
         
      /*     Method Withdrawal()
   * Input:
   *  Transaction ticket is a parameter
   * Process:
   *  Process if condition for if account status is close
   *  if amount is valid(not negative or greater than balance)
   * Output:
   *  returns reciept
   */ 
    public TransactionReceipt makeWithdrawal(TransactionTicket ticket)throws IOException, FileNotFoundException,InvalidAmountException,
    AccountClosedException,InsufficientFundsException{
        TransactionReceipt receipt = new TransactionReceipt(); 
        double pre; double post; double amt; 
        amt = ticket.getAmountofTran();
        try {
        	throwInvalidAmount(amt);
        	throwAccountClosed();
        	throwInsufficientFunds(amt);
        	pre = balance;
            post = balance - amt;
            setBalance(post);
            receipt = new TransactionReceipt(ticket, true, "",getAcctType() ,pre, post,null,getDepositor ().getName () );
            writeReciept(receipt);       
       
        }//close try
        catch (InvalidAmountException | InsufficientFundsException ex) {
        	 pre = balance;
             post = balance;
             receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(), pre, post,null,getDepositor ().getName () );
             writeReciept(receipt);
        	
        }
        catch ( AccountClosedException ex) {
        	receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(),0.0, 0.0,null,getDepositor ().getName () );  
        	writeReciept(receipt);
        }//close account close

        return receipt;
    }  
            
           
      
   /*     Method Clear check()
   * Input:
   *  Check class is a parameter
   * Process:
   *  checks if condition for if account status is close
   *  and if account type is a checking account and proceed to compute withdrawal
   * Output:
   *  returns reciept
   */
    public TransactionReceipt clearCheck(Check check)throws IOException, FileNotFoundException,InvalidAmountException,AccountClosedException,
    InsufficientFundsException,PostDatedCheckException{
        //this is where you will check acctype and do withdrawal
            TransactionReceipt receipt = new TransactionReceipt();
            double pre; double post;double amt; String chdate;
            amt = check.getCheckAmount();
            chdate = check.getDate();
            Calendar sixMonthsAgo =  Calendar.getInstance();
            sixMonthsAgo.add(Calendar.MONTH, - 6);
            Calendar today = Calendar.getInstance();
            Calendar dot =  check.getDateOfCheck();
            int x = check.getAcctNum();
            String str = "Clear Check";
            int term = 0;
            TransactionTicket ticket = new TransactionTicket(x, str, amt, term );
        	
          try {
        	  throwInvalidAmount(amt);
        	  throwAccountClosed();
        	  throwInsufficientFundsCheck(amt);
        	  throwPostDatedCheck(check );
        	  throwCheckToOld(check);
              pre = balance;
              post = balance - amt;
              setBalance(post);
              receipt = new TransactionReceipt(ticket,true," ",getAcctType(),pre,post," ", getDepositor ().getName ());   
              writeReciept(receipt);
                
            
          }//close try
          catch(InvalidAmountException ex) {
        	  pre = balance; post = balance;
        	  receipt = new TransactionReceipt(ticket,false,ex.getMessage(),getAcctType(),pre,post," ",getDepositor ().getName () ); 
    	  	  writeReciept(receipt);
          }//close catch invalid ammount
          catch (InsufficientFundsException ex) {
        	  pre = balance + 2.50;
        	  receipt = new TransactionReceipt(ticket,false,ex.getMessage(),getAcctType(), pre,balance," ",getDepositor ().getName () ); 
    	  	  writeReciept(receipt);
          }
          catch(AccountClosedException ex ) {
        	  receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(),0.0, 0.0,null,getDepositor ().getName ()  );  
        	  writeReciept(receipt);
        	  
          }
          catch (PostDatedCheckException | CheckTooOldException  ex ) {
        	  receipt = new TransactionReceipt(ticket,false,ex.getMessage(),getAcctType(),balance,balance," ", getDepositor ().getName ()); 
              writeReciept(receipt);
        	  
          }
          return receipt;
    } //close check
 
    
    public void throwPostDatedCheck( Check check ) throws PostDatedCheckException { //future date
    	 Calendar today = Calendar.getInstance();
    	 String chdate = check.getDate();
    	 Calendar dot =  check.getDateOfCheck();
    	 if (today.before(dot) == true){ 
    		 throw new PostDatedCheckException(chdate);
    		 
    	 }
    }//close throwPostDatedCheck
    
    public void throwCheckToOld(Check check )throws CheckTooOldException {
    	 String chdate = check.getDate();
         Calendar sixMonthsAgo =  Calendar.getInstance();
         sixMonthsAgo.add(Calendar.MONTH, - 6);
         Calendar dot =  check.getDateOfCheck();
         if (dot.before(sixMonthsAgo) == true){
        	 throw new CheckTooOldException(chdate);
	  }
    	
    }//close throwCheckToOld
    
    //toString() method - uses String static method .format()
	public String toString() {
		Name myName;
        myName = getDepositor().getName();
		
		String str = String.format("%-10s%-8s%-11s%-8d%-11s%-9.2f%-8s",
									myName.getFirst(),
									myName.getLast(),
									getDepositor().getSocSec(),
									getAcctNum(),
									getAcctType (),
									getBalance(),
									getAcctStatus());
		return str;
	}
	 //for reading to dat file
	public String toString1() {
		Name myName;
        myName = getDepositor().getName();
		String str = String.format( myName.getFirst()+" "+
									myName.getLast()+" "+
									getDepositor().getSocSec()+" "+
									getAcctNum()+" "+
									getAcctType ()+" "+
									getBalance()+" "+
									getAcctStatus()+ " " +
									getReceipts());
		return str;
	}
	
	
}