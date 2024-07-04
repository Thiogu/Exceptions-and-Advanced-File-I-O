package hw8;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SavingsAccount extends Account{
    
    //no-arg constructor
    public SavingsAccount(){
        super();
    }
    
    //parametized constructor
    public SavingsAccount(Depositor dep, int x, String str, double xx, String status, int y){
       super(dep,x,str,xx,status,y);
    }
    
    //copy constructor
    public SavingsAccount(SavingsAccount acct){
        super(acct);
        //super = acct.super();
    }
      /*     Method Deposit()
   * Input:
   *  Transaction ticket is a parameter
   * Process:
   *  process if condition for if account status is close
   *  if amount to deposit is a zero or negative
   * Output:
   *  returns reciept
   */
     public TransactionReceipt makeDeposit(TransactionTicket ticket) throws FileNotFoundException, IOException,InvalidAmountException,
     AccountClosedException{
        TransactionReceipt receipt = new TransactionReceipt(); 
        double pre; double post; double amt; 
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
        catch (AccountClosedException ex ) {
        	receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(),0.0, 0.0,null, getDepositor().getName());  
            writeReciept(receipt);      
        	
        }//close catch
        return receipt;
    } // closes deposit
    
          
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
    AccountClosedException,InsufficientFundsException,CDMaturityDateException{
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
        catch(InvalidAmountException | InsufficientFundsException ex) {
        	 pre = balance;
             post = balance;
             receipt = new TransactionReceipt(ticket, false, ex.getMessage(),getAcctType(), pre, post,null,getDepositor ().getName () );
             writeReciept(receipt);
        }
        catch (AccountClosedException ex ) {
        	receipt = new TransactionReceipt(ticket, false,  ex.getMessage(),getAcctType(),0.0, 0.0,null,getDepositor ().getName () );  
        	writeReciept(receipt);
        	
        }//close account close

        return receipt;
    }  
            
           
    
    
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