package hw8;
/**
InvalidAmountException are thrown by the
Bank class when transaction amount
is a negative amount.
*/
public class InvalidAmountException extends Exception {

	/**
	   This constructor uses a generic
	   error message.
	*/

	public InvalidAmountException(){
	   super("Error: Invalid amount");
	}

	/**
	   This constructor specifies the Invalid
	   amount in the error message.
	   @param The bad starting balance.
	*/

	public InvalidAmountException(double amount){
	   super("Error: " + amount + " is an invalid amount");
	}

	public String getMessage() {
		   return super.getMessage();
	}
}
