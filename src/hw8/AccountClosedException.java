package hw8;
/**
AccountClosedException are thrown by the
Bank class when the account status is closed.
*/
public class AccountClosedException extends Exception {

	/**
	   This constructor uses a generic
	   error message.
	*/

	public AccountClosedException(){
	   super("Error: Account status is close");
	}

	public String getMessage() {
		   return super.getMessage();
	}
	
	
}
