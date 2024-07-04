package hw8;
/**
CDMaturityDateException are thrown by the
CD Account class when a cd account has 
not reach its maturity date
*/
public class CDMaturityDateException extends Exception {

	/**
	   This constructor instantiates the error message.
	    no @param .
	*/

	public CDMaturityDateException(){
	   super("Error: CD Account has not attained its maturity date");
	}


	public String getMessage() {
		   return super.getMessage();
	}
}
