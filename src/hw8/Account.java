package hw8;


import java.util.Calendar;
import java.util.ArrayList; 
import java.io.*;

public class Account{
   private Depositor myDepositor;
   private int acctNum;
   private String acctType;
   protected double balance;
   private String acctStatus;
   protected ArrayList<TransactionReceipt > receipts;  //arraylist of receipt
   protected String file;
   protected RandomAccessFile recieptFile;
   private final int R_SIZE = 250;
   private int numReciepts;
   //: add a copy constructor and implements both the toString() and the equals() methods
   
   //no-arg Parameter
   public Account (){
       myDepositor = new Depositor();
       acctNum = 0;
       acctType = "";
       balance = 0.0;
       acctStatus = "";
       numReciepts = 0;
       receipts = new ArrayList<TransactionReceipt >(30);
   } 
   
   
   //parameter with Constructor
   	public Account(Depositor dep, int x, String str, double xx, String status, int y) {
		//System.out.println("Account: 4-parameter Constructor running");
		myDepositor = dep;
		acctNum = x;
		acctType = str;
		balance = xx;
		acctStatus = status;
	    receipts = new ArrayList<TransactionReceipt >();
	    numReciepts = y;
	}
	
	
	//Copy Constructor
	public Account(Account acct) {
		//System.out.println("Account: 4-parameter Constructor running");
		myDepositor = new Depositor(acct.myDepositor); //nested copy-construtor
		acctNum = acct.acctNum;
		acctType = acct.acctType; 
	    balance = acct.balance;
	    acctStatus = acct.acctStatus;
	    receipts = new ArrayList<TransactionReceipt >(acct.receipts);
	    numReciepts = acct.numReciepts;
	}
	
       /*     Method Balance(),
   * Input:
   *  Transaction ticket is a parameter
   * Process:
   *  Checks if account status is close or intialize a local 
   *  vaiable to balance and sends it in receipt
   * Output:
   *  returns reciept
   */
    public TransactionReceipt getBalance(TransactionTicket ticket) throws FileNotFoundException, IOException,AccountClosedException {
    	
        TransactionReceipt receipt = new TransactionReceipt(); 
        double pre; double post;
        try {
        	throwAccountClosed();
        	pre = balance;
            post = balance;
            receipt = new TransactionReceipt(ticket, true, " ",acctType,pre, post,null, getDepositor ().getName ());   
            writeReciept(receipt);
        	
        }//close try
        catch( AccountClosedException ex) {
        	receipt = new TransactionReceipt(ticket, false,  ex.getMessage(),acctType,0.0, 0.0,null, getDepositor ().getName ()); 
            writeReciept(receipt); 
        }//close catch
       
         return receipt;  
     }//close balance 
        
        
        /*
          null Deposit class
        
        */
         public TransactionReceipt makeDeposit(TransactionTicket ticket)throws FileNotFoundException, IOException, InvalidAmountException,
         AccountClosedException{
             
              TransactionReceipt receipt = null;
              return receipt;
             
         }
         
         /* 
          null withdrawal
          
         */
         public TransactionReceipt makeWithdrawal(TransactionTicket ticket) throws IOException, FileNotFoundException,InvalidAmountException,
         AccountClosedException,InsufficientFundsException,CDMaturityDateException{
            TransactionReceipt receipt = null;
            return receipt;
         }
         
        
         /*
           null clear Check
           
         */
        public TransactionReceipt clearCheck(Check check)throws IOException, FileNotFoundException,InvalidAmountException,AccountClosedException,
        InsufficientFundsException,PostDatedCheckException,CheckTooOldException{
        //this is where you will check acctype and do withdrawal
            TransactionReceipt receipt = null;
            return receipt;
        }
    
     /*     Method close Account()
   * Input:
   *  Transaction ticket is a parameter
   * Process:
   *  sets account status to close
   * Output:
   *  returns reciept
   */
     public TransactionReceipt closeAcct(TransactionTicket ticket)throws IOException, FileNotFoundException{
          TransactionReceipt receipt = new TransactionReceipt();
          acctStatus= "close";
          System.out.println("account status"+ getAcctStatus());
          double pre = balance;
          double post = balance;
          receipt = new TransactionReceipt(ticket,true," ",acctType,pre,post," ",getDepositor ().getName () );   
          writeReciept(receipt);
          return receipt;
      }
      
      
      /*     Method reopen Account()
   * Input:
   *  Transaction ticket is a parameter
   * Process:
   *  sets account status to open
   * Output:
   *  returns reciept
   */
    public TransactionReceipt reopenAcct(TransactionTicket ticket)throws IOException, FileNotFoundException,AccountClosedException{
        TransactionReceipt receipt = new TransactionReceipt();
        
        try {
        	
        	throwAccountClosed();
        	double pre = balance;
            double post = balance;
            receipt =  new TransactionReceipt(ticket,false,"Account status is already open",acctType,pre,post," ",getDepositor ().getName ());   
            writeReciept(receipt);
        }//close try
        catch(AccountClosedException ex ) {
        	setAcctStatus("Open");
            double pre = balance;
            double post = balance;
            receipt = new TransactionReceipt(ticket,true," ",acctType,pre,post," ",getDepositor ().getName () );   
            writeReciept(receipt);
        	
        }//close catch
           return receipt;
    }//close reopenAcct
    
    
    //write reciept into binaryFile
    protected void writeReciept(TransactionReceipt reciept) throws IOException, FileNotFoundException{
    	System.out.println("number of reciepts: "+ getReceipts());
    	file =""+getAcctNum()+".dat";
    	recieptFile = new RandomAccessFile(file, "rw"); //open file in "rw" to write to it
    	int size =  getReceipts(); //number of reciepts
    	long pos =  R_SIZE * size; //record size  * num of reciepts record in the file
    	recieptFile.seek(pos); //go to pos
    	String nReciept = reciept.getTransactionTicket().toString3(); //Convert reciept to string
    	String status = "Done";
    	if(reciept.getSuccessIndicator() == false) {
    		status = "Failed";
    	}
    	String bal = String.format("%.2f",	getBalance()); //convert balance to string with 2 decimals
    	String failure = reciept.getreasonForFailure();
    	nReciept += " "+status+"\t"+bal+"\t"+failure;
    	int strLength = nReciept.length();
    	System.out.println("Reciepts string length: "+ strLength);
    	recieptFile.writeBytes(nReciept);
    	if (strLength <  R_SIZE) {
    		int SpaceToPad =  R_SIZE - strLength;
    		for(int i = 0; i < SpaceToPad; i++) {
    			recieptFile.writeBytes(" ");
    		}//close for-loop
    	}//close if 
    	numReciepts++;
    	System.out.println("num reciepts after increase: " +numReciepts );
    	System.out.println("Name of file: "+ file);
    	int x =   (int) (recieptFile.length() / R_SIZE);
    	System.out.println("Num of reciepts for account: " + getAcctNum()+" is "+ x+ "\nReciept File lennght:"+ recieptFile.length());
    	//deleteReciepts();
		recieptFile.close(); //close bankFile	
    	
    }//close write REciepts
    
    /*
     * Read reciepts 
     */
    protected String readReciepts(int index)throws IOException, FileNotFoundException {
    	String strReciepts = "";
    	if (index < 0 || index > numReciepts + 1) { //check for invalid index
			System.out.println("invalid index");
			return strReciepts;
		}
    	else {
    		file =""+getAcctNum()+".dat";
        	recieptFile = new RandomAccessFile(file, "rw"); //open file in "rw" to read from it
        	long pos =  R_SIZE * index; //record size  * index to read from
        	recieptFile.seek(pos); //go to pos
        	strReciepts = recieptFile.readLine();
        	recieptFile.close();
        	//strReciepts = strReciepts.substring(0, 250);
        	strReciepts.trim(); //trims any trailing space
    	}//close else
    	return strReciepts;
    }//close read Receipts
   
   /*
    * delete all receiptss by setting length = 0 
    */
    public void deleteReciepts() throws IOException,FileNotFoundException{
    	file =""+getAcctNum()+".dat";
    	recieptFile = new RandomAccessFile(file, "rw"); 
    	recieptFile.setLength(0); 
    	int size =   (int) (recieptFile.length() / R_SIZE);
    	System.out.println("Num of reciepts for account: " + getAcctNum()+" is "+ size+ "\nReciept File lennght:"+ recieptFile.length());
    	recieptFile.close();
    	
    }
     //throws method
    public void throwInvalidAmount(double transactionAmt) throws InvalidAmountException{//for deposit
    	if (transactionAmt <= 0) {
    		throw new InvalidAmountException(transactionAmt);
    	}
    }//close throwInvalidAmount
    
    public void throwInvalidAmountWithDrawal(double transactionAmt) throws InvalidAmountException{
    	if (transactionAmt <= 0 || transactionAmt > balance ) {
    		throw new InvalidAmountException(transactionAmt);
    	}
    }//close throwInvalidAmountWithDrawal
    
    
    public void throwAccountClosed() throws AccountClosedException{
    	 if (getAcctStatus().equals("close")){
    		 throw new AccountClosedException();
    	 }
    }//close throwAccountClosed
    
    public void throwInsufficientFunds(double amt) throws InsufficientFundsException{
    	String bal = ""; bal += balance;
    	String amount = ""; amount += amt;
    	if (amt > balance ) {
    		throw new InsufficientFundsException(bal,amount);
    	}
    	
    }//close insuffiecint funds
    
    public void throwInsufficientFundsCheck(double amt) throws InsufficientFundsException{
    	if (amt > balance ) {
    		double pre = balance;
    	  	double post = pre - 2.50; 
    	  	setBalance(post);
    		throw new InsufficientFundsException();
    	}
    	
    }//close insuffiecint funds for check
    
  //toString() method - uses String static method .format()
	public String toString() {
		Name myName;
        myName = getDepositor().getName();
		String str = String.format("%-10s%-8s%-11s%-8d%-11s%-9.2f%-8s",
									myName.getFirst(),
									myName.getLast(),
									myDepositor.getSocSec(),
									getAcctNum(),
									getAcctType (),
									getBalance(),
									getAcctStatus());
		return str;
	}
    
	public String toString1() {
		Name myName;
        myName = getDepositor().getName();
		String str = String.format( myName.getFirst()+" "+
									myName.getLast()+" "+
									myDepositor.getSocSec()+" "+
									getAcctNum()+" "+
									getAcctType ()+" "+
									getBalance()+" "+
									getAcctStatus() + " " +
									getReceipts());
		return str;
	}
//Tom Sawyer 456778901 234567 CD 500.00 open 52
	
	//equals() method
	public boolean equals(Account myAcct) {
		if(myDepositor.equals(myAcct.myDepositor))
			return true;			//depositor found
		else
			return false;			//depositor not found
	}
   
   
	//setters
    private void setDepositor(Depositor depositors) {
    myDepositor = depositors;
    }
    private void setAcctNum(int num){
        acctNum = num;
    }
    private void setAcctType(String str){
        acctType = str;
    }
    protected void setBalance(double num){
        balance = num;
    }
   
	private void setAcctStatus(String status){
	    acctStatus = status;
	}
	protected void addReceipts(TransactionReceipt newReceipt) {
		receipts.add(newReceipt);			//uses ArrayList method .add()
	}
  
    
    
    //getters
    public Depositor getDepositor() {
    return new Depositor(myDepositor); //returning a copy
    }
    public int getAcctNum(){
        return acctNum;
    }
    public String getAcctType(){
        return acctType;
    }
    public double getBalance(){
        return balance;
    }
	public String getAcctStatus(){
	    return acctStatus;    
	}
    public TransactionReceipt getReceipts(int index) { //uses ArrayList method .get()
    	return new TransactionReceipt(receipts.get(index));
    	//	return (receipts.get(index));	//reference copy constructor		
	}                                                           //returns copy
	
	public int getReceipts() { //return num of reciepts
		return 	numReciepts;		
	}                                                               //returns copy
   
}
