package hw8;
/**
CheckTooOldException exceptions are thrown by the
checking class when a check older than 6 months is passed 
to the constructor.
*/
public class CheckTooOldException extends Exception{

	/**
	   This constructor uses a generic
	   error message.
	*/

	public CheckTooOldException(){
	   super("Error: Checks older than 6 months will not be cleared");
	}

	/**
	   This constructor specifies the check
	   date in the error message.
	   @param The bad starting balance.
	*/

	public CheckTooOldException(String chdate) {
	   super("Error: Checks older than 6 months will not be cleared: " +chdate +"");
	}

	public String getMessage() {
		   return super.getMessage();
	}
}
