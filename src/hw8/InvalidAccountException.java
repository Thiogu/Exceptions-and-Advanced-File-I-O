package hw8;
/**
InvalidAccountException exceptions is thrown by the
Bank class when an account cannot be found by the findAcct 
method.
*/

public class InvalidAccountException extends Exception {
	/**
	   This constructor uses a generic
	   error message.
	*/

	public InvalidAccountException(){
	   super("Error: Account number not found");
	   
	}

	/**
	   This constructor specifies the invalid account
	   in the error message.
	   @param The bad starting balance.
	*/

	public InvalidAccountException(int acctNum)	{
	   super("Error: Account number- " +acctNum +" - not found");
	}


	public String getMessage() { 
		  return super.getMessage();
	}

}
