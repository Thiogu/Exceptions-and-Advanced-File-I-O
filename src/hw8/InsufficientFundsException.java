package hw8;
/**
InsufficientFundsException exceptions are thrown by the
Bank class when the withdrawal amount is greater
than the balance.
*/
public class InsufficientFundsException extends Exception {
	/**
	   This constructor uses a generic
	   error message.
	*/

	public InsufficientFundsException(){
	   super("Error: Insufficient Funds Available - Bounce Fee ($2.50) Charged");
	}

	/**
	   This constructor specifies the bad starting
	   amount in the error message.
	   @param The bad starting balance.
	*/

	public InsufficientFundsException(String balance, String amount){
	   super("Balance: $" + balance+"\nError: Insufficient Funds Available - Transaction amount - $" +amount+ " .");
	}


	public String getMessage() {
		   return super.getMessage();
	}

}
