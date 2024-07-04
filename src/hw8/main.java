package hw8;
import java.io.*; 
import java.util.Scanner;
import java.util.Calendar;
/*



CDMaturityDateExceptio
 */
public class main {

	public static void main(String[] args) throws IOException, FileNotFoundException,InvalidAccountException,InvalidAmountException,AccountClosedException,
	InsufficientFundsException,InvalidMenuSelectionException,PostDatedCheckException,CheckTooOldException,CDMaturityDateException{
		// TODO Auto-generated method stub
		// Scanner kybd = new Scanner(System.in);
	    File newFile = new File ("testCases.txt");
	    //create Scanner object
	    Scanner kybd = new Scanner (newFile);
	    // open the output file
	    PrintWriter outFile = new PrintWriter ("pgmOutput.txt");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	    System.out.println ("");

	    Bank bank = new Bank ();
	    char choice;
	    boolean notDone = true;	//loop control flag
	    int numAccts = 0;

	      readAccts (bank, outFile);
	      printAccts (bank, outFile);

	    do{
		menu ();
		choice = kybd.next ().charAt (0);
		try { 
		switch (choice) {
		  case 'q':
		  case 'Q':
		      notDone = false;
		      printAccts (bank, outFile);
		    break;
	        case 'b':
	        case 'B':
	          balance(bank, outFile, kybd);
		    break;
		    case 'r':
		    case 'R':
		      reopenAcct(bank, outFile, kybd);
		    break;
		    case 's':
		    case 'S':
		      closeAcct(bank, outFile, kybd);
		    break;
		    case 'n':
		    case 'N':
		      newAcct(bank, outFile, kybd);
		    break;
		    case 'x':
		    case 'X':
		      deleteAcct(bank, outFile, kybd);
		    break;
		    case 'A':
		    case 'a':
		       acctInfo(bank, outFile, kybd);
		    break;
		    case 'h':
		    case 'H':
		       acctInfoHistory(bank, outFile, kybd);
		    break;
		    case 'd':
		    case 'D':
		       deposit(bank, outFile, kybd);
		    break;
		    case 'w':
		    case 'W':
		       withdrawal (bank, outFile, kybd);
		    break;
		    case 'C':
		    case 'c':
		       clearCheck (bank, outFile, kybd);
		    break;
		  
		    default:
		    	throw new InvalidMenuSelectionException(choice);
		  }//close switch
		}//close try
		catch(InvalidMenuSelectionException ex) {
			outFile.println ();
			outFile.println (ex.getMessage());
			outFile.println ();
	    	outFile.flush ();
		}//close catch

		
	 } while (notDone);
	    //close the output file
	    outFile.close ();

  }//close main
	  
	  /*     Method Add sum
	   * Input:
	   *  Parameter (Amount to add)
	   * Process:
	   *  checks acctype and add/subtract amount to the fitting static member of Back class
	   * Output:
	   * 
	   */
	  public static void addSum (Bank bank, String acctype, double amt, String operation){
	      if (acctype.equals("Savings")){
	          bank.totalSavings(amt,operation); //calls totalSavings method in bank
	        }
	      else if (acctype.equals("Checkings")){
	          bank.totalCheckings(amt,operation); //calls totalCheckings method in bank
	        }
	      else if (acctype.equals("CD")){
	          bank.totalCD(amt,operation);  //calls totalCd method in bank
	        }
	      else {
	          System.out.println("This is not an account type " + acctype);
	        }
	      
	      bank.totalAccts(amt,operation); //calls totalAccts method in bank
	      
	  }
	  
	  /*     Method Read Account()
	   * Input:
	   *  Strings :first and last name, Social security , account type,
	   *  CD maturity date, and Account status.
	   *  Integer : Account number
	   *  Double: Account balance
	   * Process:
	   *  Read inputs and uses Name,Depositor and, Account Constructors
	   * Output:
	   *  No output
	   */

	  public static void readAccts (Bank bank, PrintWriter outFile) throws IOException,FileNotFoundException {
	    // open input test cases file
	    File testFile = new File ("initAccts.txt");
	    //create Scanner object
	    Scanner kybd = new Scanner (testFile);
	    String line;
	    String math = "add";
	    while (kybd.hasNext ()){

		line = kybd.nextLine ();
		String[] tokens = line.split (" ");
		Account myAccount = new Account();
		
		Name myName = new Name (tokens[0],	//set name component 
					tokens[1]);
		Depositor myDepositor = new Depositor (tokens[2],	//set Depositor component(socSec and name)
						       myName);
		System.out.println("Working!");
			//illustrate polymorphism 
		String type = tokens[4];
		 if (type.equals("CD")){
		     myAccount = new CDAccount (myDepositor,	//set Depositor component         
						 Integer.parseInt (tokens[3]),	//account number
						 tokens[4],	//acctype
						 Double.parseDouble (tokens[5]),	//acctbal
						 tokens[6], //satus 
						 tokens[7], //cd date
						 0);	//num of reciepts
		 }
		else if (type.equals("Savings")){
		     myAccount = new SavingsAccount(myDepositor,	//set Depositor component         
						 Integer.parseInt (tokens[3]),	//account number
						 tokens[4],	//acctype
						 Double.parseDouble (tokens[5]),	//acctbal
						 tokens[6], //satus 
						 0); //num reciepts
		 }
		 else{
		     if (type.equals("Checkings")){
		      myAccount = new CheckingAccount(myDepositor,	//set Depositor component         
						 Integer.parseInt (tokens[3]),	//account number
						 tokens[4],	//acctype
						 Double.parseDouble (tokens[5]),	//acctbal
						 tokens[6], //satus 
						 0); //num reciepts
		     }
		 }

	     bank.writeAccount(myAccount);
	     addSum(bank,myAccount.getAcctType(),myAccount.getBalance(),math);
	      }

	  }
	  
	  /*     Method Print Account
	   * Input:
	   *  none
	   * Process:
	   *  Goes though arrayList of accounts and prints each element
	   * Output:
	   *Prints name,social security,account balance/type/number,Maturity date for CD
	   */

	  public static void printAccts (Bank bank, PrintWriter outFile) throws IOException {
		  
	      outFile.println ("");
	      System.out.println("Size: "+bank.getAcctSize());
	      outFile.println("LastName\tFirstName\tSSN\tAcctNum\tAcctType\tBalance\t Status\t Maturity date");
	      outFile.println ("_______________________________________________________________________________");
	      for (int i = 0; i < bank.getAcctSize(); i++) { 
	          Account myAccount = bank.readAccount(i);
	          outFile.println("|" + myAccount + "|"); //using toString
	        
	      }
	      
	    //Be sure to print the values of all of these static variables when you print the database of accounts
	      outFile.println ("_______________________________________________________________________________");
	      outFile.printf("Total amount in all accounts: $%.2f          |",bank.getTotalAmountInAllAccts());
	      outFile.printf("\nTotal amount in Savings accounts: $%.2f       |",bank.geTotalAmountInSavingsAccts());
	      outFile.printf("\nTotal amount in Checkings accounts: $%.2f     |",bank.getTotalAmountInCheckingAccts());
	      outFile.printf("\nTotal amount in CD accounts: $%.2f            |",bank.getTotalAmountInCDAccts());
	      outFile.println ("\n_________________________________________________");
	      outFile.flush ();

	  }

	  /*     Method menu()
	   * Input:
	   *  none
	   * Process:
	   *  Prints the menu of transaction choices
	   * Output:
	   *  Prints the menu of transaction choices
	   */
	  public static void menu (){
	    System.out.println ();
	    System.out.println ("Select one of the following transactions:");
	    System.out.println ("\t****************************");
	    System.out.println ("\t    List of Choices         ");
	    System.out.println ("\t****************************");
	    System.out.println ("\t     W -- Withdrawal");
	    System.out.println ("\t     D -- Deposit");
	    System.out.println ("\t     N -- New Account");
	    System.out.println ("\t     B -- Balance Inquiry");
	    System.out.println ("\t     S -- Close Account");
	    System.out.println ("\t     A -- Account Info");
	    System.out.println ("\t     H -- Account Info with History");
	    System.out.println ("\t     R -- Reopen Account");
	    System.out.println ("\t     X -- Delete Account");
	    System.out.println ("\t     Q -- Quit");
	    System.out.println ();
	    System.out.print ("\tEnter your selection: ");

	  }
	  /*     Method Balance()
	   * Input:
	   *  Account number
	   * Process:
	   *  process if condition for success indicator is true or false
	   * Output:
	   *  Prints reason for failure if applicable or prints account balance
	   */

	  public static void balance (Bank bank, PrintWriter outFile,Scanner kybd) throws IOException,InvalidAccountException,
	  AccountClosedException{
	    outFile.println (" ");
	    TransactionTicket ticket;
	    TransactionReceipt receipt = new TransactionReceipt();
	    int requestedAccount; String type; double amt; int termofCd = 0;
	    type = "Balance inquiry"; amt = 0;

	    System.out.println ("Enter account number");	//acctnum, typeoftran, amount of tran , term of cd
	    requestedAccount = kybd.nextInt();
	    ticket = new TransactionTicket (requestedAccount, type, amt, termofCd);
	    receipt = bank.Balance(ticket);
	    outFile.println ("Transaction Requested: " + ticket.getTypeOfTran());
	    outFile.println (ticket.toString());
	    
	    if (receipt.getSuccessIndicator() == false){
	    	outFile.print(receipt.toString());
	    }
	    if (receipt.getSuccessIndicator() == true) {
		    outFile.println(receipt.toString2());
	    }

	  }//closes Balance
	  
	  /*     Method Deposit()
	   * Input:
	   *  Account number,amount to deposit, or maturity date
	   * Process:
	   *  process if condition for receipt type
	   * Output:
	   *  Prints receipt or reason for failure
	   */
	    public static void deposit (Bank bank, PrintWriter outFile, Scanner kybd) throws IOException,InvalidAccountException,InvalidAmountException,
	    AccountClosedException {
	        outFile.println("");
	        int requestedAccount;
	        String type;
	        double amt;
	        int termofCd;
	        TransactionReceipt receipt = new TransactionReceipt();
	        TransactionTicket ticket;
	        //Name myName = new Name();

	        type = "Deposit";
	        System.out.print("Enter the account number: ");	//prompt for the account number
	        requestedAccount = kybd.nextInt ();	//read-in the account number
	        System.out.print("Enter amount to deposit: ");	//prompt for amount to deposit
	        amt = kybd.nextDouble();	//read-in the amount to deposit
	        System.out.println("Enter a new maturity date for CD: 6, 12, 18, 24 ");
	        termofCd = kybd.nextInt();
	        ticket = new TransactionTicket(requestedAccount, type, amt, termofCd);
	        receipt = bank.Deposit(ticket);
	        outFile.println("Transaction Requested: " + ticket.getTypeOfTran ());
	        outFile.println(ticket.toString());
	        
	        if (receipt.getSuccessIndicator() == false && receipt.getAcctType().equalsIgnoreCase("CD")) {
		        outFile.println(receipt.toString1());
	        }

	        if (receipt.getSuccessIndicator() == false && (!receipt.getAcctType().equalsIgnoreCase("CD"))) {
		        outFile.println(receipt.toString());
	        }
	        if (receipt.getSuccessIndicator() == true && receipt.getAcctType().equalsIgnoreCase("CD")) {
		        outFile.println(receipt.toString3());
	        }
	        if (receipt.getSuccessIndicator() == true && (!receipt.getAcctType().equalsIgnoreCase("CD"))) {
	            outFile.println(receipt.toString4());
	        }
	   
	        System.out.println();
	        outFile.flush();
	  }//closes deposit
	  
	   /*     Method Withdrawl()
	   * Input:
	   *  Account number, amount to withdraw, or new month for CD maturity date
	   * Process:
	   *  Process if conditon for reciept (Success Indicator)
	   * Output:
	   *  Prints receipts or reason for failues
	   */
	  public static void withdrawal (Bank bank, PrintWriter outFile,Scanner kybd) throws IOException,InvalidAccountException ,InvalidAmountException,AccountClosedException,
	  InsufficientFundsException,CDMaturityDateException{
	    outFile.println ("");
	    int requestedAccount; String type; double amt; int termofCd;
	    TransactionReceipt receipt = new TransactionReceipt ();
	    TransactionTicket ticket;


	    type = "withdrawal";
	    System.out.print ("Enter the account number: ");	//prompt for the account number
	    requestedAccount = kybd.nextInt ();	//read-in the account number
	    System.out.print ("Enter amount to withdraw: ");	//prompt for amount to deposit
	    amt = kybd.nextDouble ();	//read-in the amount to deposit
	    System.out.println ("Enter a new maturity date for CD: 6, 12, 18, 24 "); //enter new cd date
	    termofCd = kybd.nextInt ();
	    ticket = new TransactionTicket (requestedAccount, type, amt, termofCd);
	    receipt = bank.Withdrawal (ticket); //calls bank withdrawal
	    outFile.println ("Transaction Requested: " + ticket.getTypeOfTran ());
	    outFile.println (ticket.toString());
	      
	    if (receipt.getSuccessIndicator() == false && receipt.getAcctType().equalsIgnoreCase("CD")) {
	    	outFile.println (receipt.toString1());
	    }

	    if (receipt.getSuccessIndicator() == false && (!receipt.getAcctType().equalsIgnoreCase("CD"))) {
	        outFile.println (receipt.toString());
	    }
	    if (receipt.getSuccessIndicator() == true && receipt.getAcctType().equalsIgnoreCase("CD")) {
	    	outFile.println (receipt.toString33());
	    }
	    if (receipt.getSuccessIndicator() == true && (!receipt.getAcctType().equalsIgnoreCase("CD"))) {
		   outFile.println (receipt.toString5());
	    }
	    outFile.flush();
	  }//withdrawal Method

	   /*     Method Clear check()
	   * Input:
	   *  Account number, amount to withdraw and date of check
	   * Process:
	   *  Process if condtion for if Success Indicator is true or false
	   * Output:
	   *  Prints succes  receipt or reason for failure receipt 
	   */
	  public static void clearCheck (Bank bank, PrintWriter outFile,Scanner kybd) throws IOException, FileNotFoundException,
	  InvalidAccountException,InvalidAmountException,AccountClosedException,InsufficientFundsException,PostDatedCheckException,
	  CheckTooOldException{
		  int requestedAccount; String date; double amt; String type;
	      TransactionReceipt receipt = new TransactionReceipt ();
	      Check check;
	      type = "Clear check";
	      outFile.println ("");
	      System.out.print ("Enter the account number: ");	//prompt for the account number
	      requestedAccount = kybd.nextInt ();	//read-in the account number
	      System.out.print ("Enter amount to withdraw: ");	//prompt for amount to deposit
	      amt = kybd.nextDouble ();	//read-in the amount to deposit
	      System.out.print ("Enter date of check: ");	//prompt date of check
	      date = kybd.next ();
	      check = new Check (requestedAccount, amt, date);	// call check Constructor
	      receipt = bank.Check (check);
	      outFile.println ("Transaction Requested: " + type);
	      outFile.println (receipt.getTransactionTicket().toString());
	      if (receipt.getSuccessIndicator () == false) {
	    	  outFile.println (receipt.toString6());
	      }
	     if (receipt.getSuccessIndicator () == true) {
	    	 outFile.println (receipt.toString5());
	     }
	    
	    System.out.println ();
	    outFile.flush();
	  }

	   
	    /*     Method Close Account()
	   * Input:
	   *  account number
	   * Process:
	   *  sends transaction ticket to bank
	   * and recives a receipt
	   * Output:
	   *  Prints Success or failure receipt
	   */
	  public static void closeAcct (Bank bank, PrintWriter outFile,Scanner kybd) throws IOException, FileNotFoundException,InvalidAccountException{
	      outFile.println ("");
	      TransactionTicket ticket;
	      TransactionReceipt receipt = new TransactionReceipt ();
	      int requestedAccount; String type; double amt; int termofCd = 0;

	      type = "Close Account"; amt = 0; int numAccts= 0;

	      System.out.println ("Enter account number");	//acctnum, typeoftran, amount of tran , term of cd
	      requestedAccount = kybd.nextInt ();
	      ticket = new TransactionTicket (requestedAccount, type, amt, termofCd);
	      receipt = bank.closeAct (ticket);
	      outFile.println ("Transaction Requested: " + ticket.getTypeOfTran ());
	      outFile.println (receipt.getTransactionTicket().toString());
	       if (receipt.getSuccessIndicator () == false){
	    	   outFile.println (receipt.toString ());
	        }				//closes if statement
	       if (receipt.getSuccessIndicator () == true) {
	    	   outFile.println("Name: " + receipt.getName().toString());  
	    	   outFile.println (ticket.toString1());
	      }
	       outFile.flush ();
	  }	//close closeAcct method

	  /*     Method reopen Account()
	   * Input:
	   *  Account number
	   * Process:
	   *  sends transaction ticket to bank
	   * and recives a receipt
	   * Output:
	   *  Prints Success or failure receipt
	   */
	  public static void reopenAcct (Bank bank, PrintWriter outFile, Scanner kybd) throws IOException,FileNotFoundException ,InvalidAccountException,
	  AccountClosedException{
	      outFile.println ("");
	      TransactionTicket ticket;
	      TransactionReceipt receipt = new TransactionReceipt ();
	      int requestedAccount; String type; double amt; int termofCd = 0;

	      type = "Reopen Account"; amt = 0;

	      System.out.println ("Enter account number");	//acctnum, typeoftran, amount of tran , term of cd
	      requestedAccount = kybd.nextInt ();
	      ticket = new TransactionTicket (requestedAccount, type, amt, termofCd);
	      receipt = bank.reopenAct (ticket);
	      outFile.println ("Transaction Requested: " + ticket.getTypeOfTran ());
	      outFile.println (receipt.getTransactionTicket().toString());
	    
	      if (receipt.getSuccessIndicator () == false){
	    	  outFile.println (receipt.toString ());
	      }
	      if (receipt.getSuccessIndicator () == true) {
	    	  outFile.println("Name: " + receipt.getName().toString());
	    	  outFile.println (ticket.toString2());
	      }
	      outFile.println("");
	      outFile.flush ();
	  }	//closes reopen account
	  
	   /*     Method New account()
	   * Input:
	   *  name, social security,account type,balance
	   * Process:
	   *  calls name, depositor and account Constructor to set information is given acoount
	   * is not already in the database
	   * Output:
	   *  Prints Success receipt or reason for failure receipt
	   */
	  public static void newAcct (Bank bank, PrintWriter outFile, Scanner kybd) throws IOException,FileNotFoundException,InvalidAccountException {
		  int requestedAccount;String type; double amt;String date;
		  type = "Open New Account"; amt = 0; double bal = 0;
		  String name1; String name2; String socialsec; String actype;
		  String status = "Open";
		  Account myAccount = new Account();
	  
		  TransactionTicket ticket;
		  TransactionReceipt receipt = new TransactionReceipt ();
		  System.out.println("Transaction Requested: " + type );
		  System.out.println ("Enter first name");
		  name1 = kybd.next ();
		  System.out.println ("Enter last name");
		  name2 = kybd.next ();
		  Name myName = new Name (name1, name2); //set name component 
					            
		  System.out.println ("Enter social security");
		  socialsec = kybd.next ();
		  Depositor myDepositor = new Depositor (socialsec, myName); //set Depositor component(socSec and name)
						                        
		  System.out.print ("Enter the account number: ");
		  requestedAccount = kybd.nextInt ();	//recieves account number
		  System.out.println ("Deposit an amount");
		  bal = kybd.nextDouble (); 
		  if (bal < 0){
		    System.out.println("Invalid Amount");
		    bal = 0;
		  }
		  System.out.println ("Enter Account type");
		  actype = kybd.next();
		  System.out.println ("Enter Maturity date: 6, 12, 24" + "/n" +"Enter 'null' if not CD Account");
		  date = kybd.next (); //string date 
		    
		 if (actype.equals ("CD")){
		    myAccount = new CDAccount (myDepositor,	//set Account component         
						                 requestedAccount,	//account number
						                 actype,	//acctype
						                 bal,	//acctbal
						                 status, //set status to open
			     		                 date,	//cd date
			     		                 0); //num reciepts
		 }
		 else if (actype.equals("Savings")){
		      myAccount = new SavingsAccount(myDepositor,	//set Account component         
						                 requestedAccount,	//account number
						                 actype,	//acctype
						                 bal,	//acctbal
						                 status,//set status to open
						                 0); //num reciepts
		 }
		 else{
		     if (actype.equals("Checkings")){
		     myAccount = new CheckingAccount(myDepositor,	//set Account component         
						                 requestedAccount,	//account number
						                 actype,	//acctype
						                 bal,	//acctbal
						                 status,//set status to open
						                 0); //num reciepts
				                 
		     }
		 }
	    
	    
	    receipt = bank.openNewAcct(myAccount);
	    outFile.println ("\nTransaction Requested: " + type);
	    outFile.println (receipt.getTransactionTicket().toString());
	    if (receipt.getSuccessIndicator () == false) {				//given account already exist
		     outFile.println (receipt.toString ());
	    }
	    else  {
	    	outFile.println (receipt.toString7());
		    if (actype.equals ("CD")){
		    	outFile.println ("CD New Maturity Date: " + date);
		    } //close if
		    outFile.println (" ");
	    }//close else
	    outFile.flush ();
	  } //close open new account

	  /*     Method delete account()
	   * Input:
	   *  account number
	   * Process:
	   *  sends ticket to back and recieves a receipt from bank
	   * Output:
	   *  Prints Success receipt or reason for failure receipt
	   */
	  public static void deleteAcct (Bank bank, PrintWriter outFile,Scanner kybd) throws IOException, FileNotFoundException ,InvalidAccountException{
		  outFile.println ("");			     
		  TransactionTicket ticket;
		  TransactionReceipt receipt = new TransactionReceipt ();
		  int requestedAccount; String type; double amt; int termofCd = 0;

		  type = "Delete Account"; amt = 0;

		  System.out.println ("Enter account number");	//acctnum, typeoftran, amount of tran , term of cd
		  requestedAccount = kybd.nextInt ();
		  ticket = new TransactionTicket (requestedAccount, type, amt, termofCd);
		  receipt = bank.deleteAcct (ticket);
		  outFile.println ("\nTransaction Requested: " + ticket.getTypeOfTran ());

		  if (receipt.getSuccessIndicator () == false){
			  outFile.println (receipt.toString ());
		  }
		  if (receipt.getSuccessIndicator () == true){
			  outFile.println ("Success: Account number " + requestedAccount + " has been deleted");
			  outFile.println ("");
		  }

		  outFile.flush ();
	  }//closes delete Account
	  
	   /*     Method Account information()
	   * Input:
	   *  Social security
	   * Process:
	   *  Linear search for all the account with the given social security 
	   * Output:
	   *  Prints all accounts with the given social security
	   */
	  public static void acctInfo (Bank bank, PrintWriter outFile,Scanner kybd) throws IOException,FileNotFoundException {
		  outFile.println ("");
	    //create local reference variables
		  Name myName;
		  Depositor myDepositor;
		  Account myAccount;
		  String SocialSec;
		  int found = 0;

	      System.out.println ();
	      System.out.print ("Enter Social Security number: ");
	      SocialSec = kybd.next ();	//enter Social Security

	      outFile.println ("Transaction Requested: Account info");
	      outFile.println ("SSN: " + SocialSec);
	      outFile.println("LastName\tFirstName\tSSN\tAcctNum\tAcctType\tBalance\t Status\t Maturity date");
	      outFile.println ("_______________________________________________________________________________");
	      for (int i = 0; i < bank.getAcctSize(); i++){
	    	  myAccount = bank.readAccount (i); 
		      myDepositor = myAccount.getDepositor ();
		      if (myDepositor.getSocSec ().equals (SocialSec)) {
		    	  outFile.println("|" + myAccount.toString() + "|");
		    	  found++;
		      }//close if
	     } //close for loop
	     outFile.println ("_______________________________________________________________________________");
	     if (found == 0){
		    outFile.println ("null\t\tnull\t\tnull\tnull\tnull\tnull\tnull\tnull");
		    outFile.println ("_______________________________________________________________________________");
		    outFile.println ("No account with this Social Security exists");
	     }
	     else{
		    outFile.println (found + " accounts with this social security found");
	     }
	     System.out.println ();
	     outFile.flush ();
	  } //closes Account info
	  
	   /*     Method Account info History()
	   * Input:
	   *  Social Security
	   * Process:
	   *  search for all account with the social security given and then Prints
	   * the array of receipt attached with each account
	   * Output:
	   *  Prints name, social security, account number, account balance and maturity
	   * date for CD and prints their receipt(date of transaction, type of transaction
	   * amount of transaction, status of transaction, balance,and reason for failure
	   * when applicable)
	   */
	  public static void acctInfoHistory (Bank bank, PrintWriter outFile,Scanner kybd) throws IOException,FileNotFoundException {
	    outFile.println ("");
	    //create local reference variables
	   
	    Depositor myDepositor;
	    Account myAccount = new Account();
	    String SocialSec;
	    int found = 0;
	   

	    System.out.println ();
	    System.out.print ("Enter Social Security number: ");
	    SocialSec = kybd.next ();	//enter Social Security
	    
	    outFile.println("__________________________________________________________________"); 
	    outFile.println("Transaction Requested: Account Info With Transaction History      |");
	    outFile.println("SSN: "+SocialSec+"                                                    |");
	    
	    for (int i = 0; i < bank.getAcctSize(); i++) {
		      myAccount = bank.readAccount (i); //reading from file
		      myDepositor = myAccount.getDepositor ();
		   if (myDepositor.getSocSec ().equals (SocialSec)){
			   outFile.println("\nLastName\tFirstName\tSSN\tAcctNum\tAcctType\tBalance\t Status\t Maturity date");
	           outFile.println ("_______________________________________________________________________________"); 
		       found++;
		       outFile.println("" + myAccount.toString() + " ");
		    
			   outFile.println ("\n***** Account Transactions *****");
			   outFile.println("Date \t\tTransaction\t\tAmount\t\tStatus\tBalance\t\tReason for failure");
		       for (int j = 0; j < myAccount.getReceipts(); j++) {
		    	   String str = myAccount.readReciepts(j); 
	               outFile.println (str);
		       }//inner for-loop
		       outFile.println ("_______________________________________________________________________________"); 
		       outFile.println (" ");
		   } //if for if socail sec is found close here
	    } //outer for-loop
	    if (found == 0){
	        outFile.println ("LastName\tFirstName\tSSN\t\tAcctNum\tAcctType\tBalance\tMaturity Date");   
		    outFile.println ("null\t\tnull\t\tnull\t\tnull\tnull\tnull");
		    outFile.println ("No account with this Social Security exists");
	    }
	    else{
		   outFile.println (found + " accounts with this social security found");
	    }
	    System.out.println ();
	    outFile.flush ();
	  }//closes account info History


}//closes class
	/*
	    Name myName = bank.getAcct(0).getDepositor ().getName ();
	    outFile.println ("\n myName: " + myName);
	    myName.setFirst("Jane");
		myName.setLast("Eyre");
		outFile.println ("\n myName: " + myName);
		*/


		

	