package hw8;
/**
InvalidMenuSelectionException are thrown by the
main class when an invalid option from the menu 
is inputed.
*/
public class InvalidMenuSelectionException extends Exception {

	/**
	   This constructor uses a generic
	   error message.
	*/

	public InvalidMenuSelectionException(){
	   super("Error: Invalid menu selection - try again");
	}

	/**
	   This constructor specifies the bad starting
	   choice in the error message.
	   @param The bad starting balance.
	*/

	public InvalidMenuSelectionException(char choice){
	   super("Error: " + choice + " is an invalid selection -  try again");
	  
	}

	public String getMessage() {
		   return super.getMessage();
	}
}
