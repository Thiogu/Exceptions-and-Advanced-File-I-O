package hw8;

import java.io.*;

public class Bank{
  private String fileName = "bank.dat";
  private RandomAccessFile bankFile;
  private final int RECORD_SIZE = 78;
  private static int activeAcct = 0;  //actual active account = activeAcct+1
  private static double totalAmountInSavingsAccts = 0.0;
  private static double totalAmountInCheckingAccts = 0.0;
  private static double totalAmountInCDAccts = 0.0;
  private static double totalAmountInAllAccts = 0.0;
  protected static int indexFound = -1;
   
   //no-arg constructor 
    public Bank(){
    	//do nothing
    }
  
    //static method
    
     /*     Method total balance of savings accounts()
   * Input:
   * none
   * Process:
   *  add or subtract amount to savings
   * Output:
   *  none
   */
	public static void totalSavings(double amt, String s){
	    if (s.equals("add")) {
	        totalAmountInSavingsAccts += amt;
	    }
	    if (s.equals("minus")) {
	        totalAmountInSavingsAccts = totalAmountInSavingsAccts - amt;
	    }
	}
	
	/*     Method total balance of checkings accounts()
   * Input:
   * none
   * Process:
   *  add or subtract amount to checking
   *  
   * Output:
   *  none
   */
	public static void totalCheckings(double amt, String s){
	     if (s.equals("add")) {
	         totalAmountInCheckingAccts += amt;
	       }
	      if (s.equals("minus")) {
	          totalAmountInCheckingAccts = totalAmountInCheckingAccts - amt;
	    }
	}
	
	/*     Method total balance of CD accounts()
   * Input:
   * none
   * Process:
   *  add or subtract amount to CD
   * Output:
   *  none
   */
	public static void totalCD(double amt,String s){
	    if (s.equals("add")) {
	        totalAmountInCDAccts += amt;
	    }
	     if (s.equals("minus")) {
	          totalAmountInCDAccts = totalAmountInCDAccts - amt;
	    }
	}
	
	/*     Method total balance of all accounts()
   * Input:
   * none
   * Process:
   *  
   *  
   * Output:
   *  none
   */
		public static void totalAccts(double amt, String s){
	     if (s.equals("add")) {
	         totalAmountInAllAccts += amt;
	     }
	      if (s.equals("minus")) {
	          totalAmountInAllAccts = totalAmountInAllAccts - amt;
	    }
	}
	/*     Method Add sum
   * Input:
   *  Parameter (Amount to add)
   * Process:
   *  checks acctype and add/subtract amount to the fitting static member of Back class
   * Output:
   * 
   */
	public static void addSum (String acctype, double amt, String operation){
      if (acctype.equals("Savings")){
          totalSavings(amt,operation); //calls totalSavings method in bank
      }
      else if (acctype.equals("Checkings")){
           totalCheckings(amt,operation); //calls totalCheckings method in bank
      }
      else if (acctype.equals("CD")){
           totalCD(amt,operation);  //calls totalCd method in bank
      }
      else {
          System.out.println("This is not an account type: " + acctype);
      }
      
        totalAccts(amt,operation); //calls totalAccts method in bank
      
  }
   
    /*   Method Balance()
   * Parameter:
   *  Transaction ticket(Account number, type of transaction, )
   * Process:
   *  calls find account to checks if account exist it it does not
   * instatiate receipt and sends it appropriate content or send it account
   * to get balance
   * Output:
   *  returns reciept
   */
    public TransactionReceipt Balance(TransactionTicket ticket)throws IOException, FileNotFoundException,InvalidAccountException,AccountClosedException{
        TransactionReceipt receipt = new TransactionReceipt();
        Name myName = new Name();
        int requestedAccount = ticket.getAcctNum();
        try {
        	Account acct = findAcct1 (requestedAccount);  //call findAcct to search if requestedAccount exists
        	System.out.println("Index found: " +indexFound );
        	receipt = acct.getBalance(ticket); 
        	System.out.println("acct num reciept: " +acct.getReceipts() );
        	String newAccount = acct.toString1();
            writeAccount(newAccount,indexFound);
        	
       
        }//close try
        catch (InvalidAccountException ex) {
        	 receipt = new TransactionReceipt(ticket,false,ex.getMessage()," ",0.0,0.0," ", myName); 
        }
        return receipt;
       
    }
    
    /*     Method Deposit()
   * Input:
   *  transaction ticket
   * Process:
   *  calls find account method to check if account exist, if it does
   *  it calls deposit method in either Checking Account class for checkings Account
   * Savings Account class for Savings account
   * CDAccount class for CD Account
   * Output:
   *  returns reciept
   */
    public TransactionReceipt Deposit(TransactionTicket ticket)throws IOException, FileNotFoundException,InvalidAccountException,InvalidAmountException,
    AccountClosedException{
        TransactionReceipt receipt = new TransactionReceipt();
        Name myName = new Name();
        String math = "add";
        double amt; amt = ticket.getAmountofTran();
        int requestedAccount = ticket.getAcctNum();
        try {
        	Account acct = findAcct1 (requestedAccount);  //call findAcct to search if requestedAccount exists
            receipt = acct.makeDeposit(ticket);
            System.out.println("Deposit works"); //testing-Deposit
            if (receipt.getSuccessIndicator() == true){
            	addSum(acct.getAcctType() ,amt,math); //call method to change static variables
            }
            System.out.println("Index found: " +indexFound );
            System.out.println("acct num reciept: " +acct.getReceipts());
        	String newAccount = acct.toString1();
            writeAccount(newAccount,indexFound); //update account after deposting
       
        }//close try
        catch(InvalidAccountException ex) {
        	receipt = new TransactionReceipt(ticket,false,ex.getMessage()," ",0.0,0.0," ", myName);   
        }
        return receipt;
    }//close deposit
    
    /*     Method withdrawal ()
   * Input:
   *  Transaction ticket()
   * Process:
   *   calls find account method to check if account exist, if it does
   *  it calls withdrawal method in either Checking Account class for checkings Account
   * Savings Account class for Savings account
   * CDAccount class for CD Account
   * Output:
   *  returns reciept
   */
    
    public TransactionReceipt Withdrawal(TransactionTicket ticket)throws IOException, FileNotFoundException,InvalidAccountException,InvalidAmountException,
    AccountClosedException,InsufficientFundsException,CDMaturityDateException{
        TransactionReceipt receipt = new TransactionReceipt();
        Name myName = new Name();
        String math = "minus";
        double amt; amt = ticket.getAmountofTran();
        //call findAcct to search if requestedAccount exists
        int requestedAccount = ticket.getAcctNum();
        try {
        	Account acct = findAcct1 (requestedAccount);  //call findAcct to search if requestedAccount exists
        	String type = acct.getAcctType();
        	receipt = acct.makeWithdrawal(ticket);
        	System.out.println("Withdrawal works");
        	if (receipt.getSuccessIndicator () == true){
        		addSum(acct.getAcctType () ,amt,math); //call method to change static variables
        	}
        	System.out.println("Index found: " +indexFound );
            System.out.println("acct num reciept: " +acct.getReceipts());
         	String newAccount = acct.toString1();
            writeAccount(newAccount,indexFound); //update account after deposting
       
        }//close try
        catch (InvalidAccountException ex) {
        	receipt = new TransactionReceipt(ticket,false,ex.getMessage()," ",0.0,0.0," ", myName);   
        }
        return receipt;
    }//close withdrawal
    
    
     /*     Method check()
   * Input:
   *  none
   * Process:
   *   calls find account method to check if account exist, if it does
   *  it calls Clear check method in Account class
   * Output:
   *  returns reciept
   */
    public TransactionReceipt Check (Check check)throws IOException, FileNotFoundException,InvalidAccountException,InvalidAmountException,
    AccountClosedException,InsufficientFundsException,PostDatedCheckException,CheckTooOldException{
        TransactionReceipt receipt = new TransactionReceipt();
        Name myName = new Name();
        String math = "minus";
        double amt; amt = check.getCheckAmount();
        int requestedAccount = check.getAcctNum();
        int x = check.getAcctNum();
        String str = "Clear Check";
        int term = 0;
        TransactionTicket ticket = new TransactionTicket(x, str, amt, term );
        try {
        	Account acct = findAcct1 (requestedAccount);  //call findAcct to search if requestedAccount exists
            String type = acct.getAcctType();
            if (type.equals("Checkings")){
	            receipt = acct.clearCheck(check);
	         }
	         else{
	             receipt = new TransactionReceipt(ticket,false,"Error: Account is not a Checkings account ",type,0.0,0.0," ",acct.getDepositor().getName());   
	             acct.writeReciept(receipt); 
	         }
            System.out.println("Clear checks work");
            if (receipt.getSuccessIndicator() == true){
            	addSum(acct.getAcctType () ,amt,math); //call method to change static variables
            }
        }//close try
        catch(InvalidAccountException ex) {
        	 receipt = new TransactionReceipt(ticket,false,ex.getMessage()," ",0.0,0.0," ",myName );   
        }//close catch
        
           return receipt;
    }//close check
    
       /*     Method close Account()
   * Parameter:
   *  TransactionTicket reference
   * Process:
   *  calls find account method to check if account exist, if it does
   *  it calls close account method in Account class
   * Output:
   *  returns reciept
   */
      public TransactionReceipt closeAct(TransactionTicket ticket)throws IOException, FileNotFoundException,InvalidAccountException{
        TransactionReceipt receipt = new TransactionReceipt();
        Name myName = new Name();
        int requestedAccount = ticket.getAcctNum();
        try {
        	Account acct = findAcct1 (requestedAccount);  //call findAcct to search if requestedAccount exists
        	receipt = acct.closeAcct(ticket);
        	System.out.println("Index found: " +indexFound );
            System.out.println("acct num reciept: " +acct.getReceipts());
         	String newAccount = acct.toString1();
            writeAccount(newAccount,indexFound); //update account after updating num reciepts 
       
        }//close try
        catch( InvalidAccountException ex) {
        	 receipt = new TransactionReceipt(ticket,false,ex.getMessage()," ",0.0,0.0," ", myName);   
        }
        return receipt; 
      } //close closeAct
      
      /*     Method reopen Account()
   * Parameter:
   *  transactionticket reference
   * Process:
   *   calls find account method to check if account exist, if it does
   *  it calls reopen account method in Account class
   * Output:
   *  returns reciept
   */
    public TransactionReceipt reopenAct(TransactionTicket ticket)throws IOException, FileNotFoundException,InvalidAccountException,AccountClosedException{
        TransactionReceipt receipt = new TransactionReceipt();
        Name myName = new Name();
        int requestedAccount = ticket.getAcctNum();
        try {
        	Account acct = findAcct1 (requestedAccount);  //call findAcct to search if requestedAccount exists
        	receipt = acct.reopenAcct(ticket);
        	System.out.println("Index found: " +indexFound );
            System.out.println("acct num reciept: " +acct.getReceipts());
         	String newAccount = acct.toString1();
            writeAccount(newAccount,indexFound); //update account after updating num reciepts 
        
        }//close try
        catch (InvalidAccountException ex) {
        	  receipt = new TransactionReceipt(ticket,false,ex.getMessage()," ",0.0,0.0," ",myName );   
        	
        }
        return receipt; 
     } //close reopenact
     
     /*     Method delete account()
   * Parameter:
   *  transactionticket reference
   * Process:
   *   calls find account method to check if account exist, if it does
   *  it checks if the balance is greate than 0 and does not delete account
   *  calls remove item from ArrayList method
   * Output:
   * returns reciept
   */ 
     public TransactionReceipt deleteAcct(TransactionTicket ticket)throws IOException, FileNotFoundException,InvalidAccountException{
        TransactionReceipt receipt = new TransactionReceipt();
        Name myName = new Name();
        //call findAcct to search if requestedAccount exists
        int index; double balance;
        int requestedAccount = ticket.getAcctNum();
        try {
        	Account acct = findAcct1 (requestedAccount);  //call findAcct to search if requestedAccount exists
            balance = acct.getBalance ();
            if (balance > 0){
                 receipt = new TransactionReceipt(ticket,false,"Error: Unable to delete account due to present balances\nAccount balance: " +  balance +
			                                      " "," ",balance,balance," ", acct.getDepositor ().getName ());   
                 acct.writeReciept(receipt); //make a method in main just to send reciept to add it
            }
           else {
        	   /*When an account is to be deleted, the deleted Account record in the file is to be replaced by the last active Account record of the
				file and the number of accounts currently active is decremented. The file is to be truncated using the RandomAccessFile
				method setLength(long newLength)
        	    */
       		  Account myAcct = readAccount(activeAcct - 1);
       		  String newAccount = myAcct.toString1();
       		  System.out.println("index Found: " + indexFound);
       		  delAccount(newAccount,indexFound);
              receipt = new TransactionReceipt(ticket,true,"", " ",0.0,0.0," ",myName );
             
             
            }
        
        }//close try
        catch (InvalidAccountException ex) {
        	receipt = new TransactionReceipt(ticket,false,ex.getMessage(), " ",0.0,0.0," ",myName);   
        }//close catch
        return receipt;
     }
     
   //to update account already in the file
 	protected void delAccount(String newAccount, int index)throws IOException,FileNotFoundException,InvalidAccountException {
 		bankFile =  new RandomAccessFile(fileName,"rw"); //open file to read and write
 		long pos = RECORD_SIZE * index; //the index of the account
 		bankFile.seek(pos); //go to line of where to write
 		//String newAccount = acct.toString1(); //get account to string
 		bankFile.writeBytes(newAccount); //each char of the string is a byte
 		int strLenght = newAccount.length(); //fixed length record so make sure each record has 78 characters
 		if (strLenght < RECORD_SIZE ) {
 			int SpaceToPad = RECORD_SIZE - strLenght;
 			for(int i = 0;  i < SpaceToPad; i++) {
 				bankFile.writeBytes(" ");
 			}
 		}//close if
 	    long newLength = bankFile.length() - 78; //remove a record
  	    activeAcct--; //reduce active acct
  	    bankFile.setLength(newLength);
 		System.out.println("Active account:" +  activeAcct+ "\n File lennght:"+ bankFile.length());
 	    bankFile.close(); //close bankFile	
 	}
     
     /*     Method open new account()
   * Input:
   *  none
   * Process:
   *   calls find account method to check if account exist, if it does
   *  it calls open new account method in Account class
   * Output:
   * returns reciept
   */
    public TransactionReceipt openNewAcct(Account myAccount)throws IOException, FileNotFoundException,InvalidAccountException{
        TransactionReceipt receipt = new TransactionReceipt();
        TransactionTicket ticket = new TransactionTicket();
        //call findAcct to search if requestedAccount exists
        String math = "add"; int index;
        int requestedAccount = myAccount.getAcctNum();
        double bal = myAccount.getBalance();
        String type = myAccount.getAcctType();
        String str = "Open new Account";
        ticket = new TransactionTicket(requestedAccount, str, bal, 0);
        try {
        	Account acct = findAcct1 (requestedAccount);  //call findAcct to search if requestedAccount exists
        	receipt = new TransactionReceipt(ticket,false,"Error: The account number you entered already exists: " +
                 requestedAccount +	 " ", " ",0.0,0.0," ",myAccount.getDepositor ().getName ());   
     
        }//close try
        catch(InvalidAccountException ex) {
        	writeAccount(myAccount);
            receipt = new TransactionReceipt(ticket,true, "",type,0.0, bal, " ",myAccount.getDepositor ().getName ());
            myAccount.writeReciept(receipt); //make a method in main just to send reciept to add it
            addSum(myAccount.getAcctType () ,bal,math); //call method to change static variables
        	
        }//close catch
        return receipt;
    }//close opennewaccount
      

    
    
	//using Random AccessFile
	protected void writeAccount(Account acct)throws IOException,FileNotFoundException {
		bankFile =  new RandomAccessFile(fileName,"rw"); //open file to read and write
		long pos = RECORD_SIZE * activeAcct; //the index of the account
		bankFile.seek(pos); //go to line of where to write
		String newAccount = acct.toString1(); //get account to string
		bankFile.writeBytes(newAccount); //each char of the string is a byte
		int strLenght = newAccount.length(); //fixed length record so make sure each record has 78 characters
		if (strLenght < RECORD_SIZE ) {
			int SpaceToPad = RECORD_SIZE - strLenght;
			for(int i = 0;  i < SpaceToPad; i++) {
				bankFile.writeBytes(" ");
			}
		}//close if
		System.out.println("Active account:" +  activeAcct+ "\n File lennght:"+ bankFile.length());
		activeAcct++;
	    bankFile.close(); //close bankFile	
	}
	
	//to update account already in the file
	protected void writeAccount(String newAccount, int index)throws IOException,FileNotFoundException {
		bankFile =  new RandomAccessFile(fileName,"rw"); //open file to read and write
		long pos = RECORD_SIZE * index; //the index of the account
		bankFile.seek(pos); //go to line of where to write
		//String newAccount = acct.toString1(); //get account to string
		bankFile.writeBytes(newAccount); //each char of the string is a byte
		int strLenght = newAccount.length(); //fixed length record so make sure each record has 78 characters
		if (strLenght < RECORD_SIZE ) {
			int SpaceToPad = RECORD_SIZE - strLenght;
			for(int i = 0;  i < SpaceToPad; i++) {
				bankFile.writeBytes(" ");
			}
		}//close if
		System.out.println("Active account:" +  activeAcct+ "\n File lennght:"+ bankFile.length());
	    bankFile.close(); //close bankFile	
	}
	/*
	 * 
	 * data are all on the same line
	 */
	protected Account readAccount(int index) throws IOException, FileNotFoundException{
		Account acct = new Account();
		if (index < 0 || index > activeAcct + 1) { //check for invalid index
			System.out.println("invalid index");
			return acct;
		}
		else {
	
			bankFile =  new RandomAccessFile(fileName,"rw"); //open file to read and write
			long pos = RECORD_SIZE * index;
			bankFile.seek(pos); //go to index of account
			String data = bankFile.readLine();
			bankFile.close(); //close random access file
			data = data.substring(0, 78); //the data file is writing the record on the same line with space in-between
			data.trim(); //trims any trailing space
			String [] tokens = data.split(" ");
			Name myName = new Name (tokens[0], tokens[1]); //set name component 
		    Depositor myDepositor = new Depositor (tokens[2], myName); //set Depositor component(socSec and name)
			//illustrate polymorphism 
		    String type = tokens[4];
		    if (type.equals("CD")){
		    	CDAccount act = new CDAccount (myDepositor,	//set Depositor component         
						 				   Integer.parseInt (tokens[3]),	//account number
						 				   tokens[4],	//acctype
						 				   Double.parseDouble (tokens[5]),	//acctbal
						 				   tokens[6], //satus 
						 				   tokens[7],	//cd date
						 				   Integer.parseInt (tokens[8])); //num reciept
		    	return act;
		    }
		    else if (type.equals("Savings")){
		    	SavingsAccount accts = new SavingsAccount(myDepositor,	//set Depositor component         
						 	                 Integer.parseInt (tokens[3]),	//account number
						 	                 tokens[4],	//acctype
						 	                 Double.parseDouble (tokens[5]),	//acctbal
						 	                 tokens[6], //satus 
						 	                 Integer.parseInt (tokens[7])); //num reciept
		    	return accts;
		    }
		    else{
		      if (type.equals("Checkings")){
		    	 CheckingAccount acctt = new CheckingAccount(myDepositor,	//set Depositor component         
		    			 						Integer.parseInt (tokens[3]),	//account number
		    			 						tokens[4],	//acctype
		    			 						Double.parseDouble (tokens[5]),	//acctbal
		    			 						tokens[6], //satus 
		    			                        Integer.parseInt (tokens[7])); //num reciept
		    	 return acctt;
		     } //close if
		      System.out.println("read account  working");
		      return acct;
		   }//close else for if type equals checking	
			
		}//close else at the very top
		
		
  }//close readAccount
	                                
	 

	public int getAcctSize() {
		return activeAcct; 
	}
	//static method
    public static double geTotalAmountInSavingsAccts() { 
          return totalAmountInSavingsAccts; //returns value of the static field
    }
	public static double getTotalAmountInCheckingAccts() { 
          return totalAmountInCheckingAccts; //returns value of the static field
    }
    public static double getTotalAmountInCDAccts() { 
          return totalAmountInCDAccts; //returns value of the static field
    }
    public static double getTotalAmountInAllAccts() { 
          return totalAmountInAllAccts; //returns value of the static field
    } 
	

	//for binary file
	private Account findAcct1(int requestedAccount) throws FileNotFoundException, IOException,InvalidAccountException {
	    Account myAccount;
		System.out.println("In find Account");
		for (int index = 0; index < getAcctSize(); index++) {
		     myAccount = readAccount(index);
		     int x = myAccount.getAcctNum();
			if (x == requestedAccount){ 
			    indexFound = index;
			    return myAccount;    
		     }
	    }
		indexFound = -1;
		//so when you call findAccount thats when you will do the try and catch
		throw new  InvalidAccountException(requestedAccount);
    }
	
    //method findAccount - Note: this is a private method uses arraylist
	private int findAccount(Account myAccount)throws FileNotFoundException, IOException {
		Account acct;
		for(int index = 0; index < getAcctSize(); index++) {
			acct = readAccount(index);
			if(acct.equals(myAccount))
				return index;		//account found
		}
		
		return -1;	
	}
}