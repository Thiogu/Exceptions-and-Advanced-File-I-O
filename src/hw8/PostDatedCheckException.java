package hw8;
/**
PostDatedCheckException exceptions are thrown by the
checking class when a check with a future date
is passed to the constructor.
*/
public class PostDatedCheckException extends Exception{
	public PostDatedCheckException(){
	   super("Error:Check not cleared - Post-dated check: ");
	}

	/**
	   This constructor specifies the date of the check in 
	   the error message
	   @param The bad starting balance.
	*/

	public PostDatedCheckException(String chdate){
	   super("Error:Check not cleared - Post-dated check: " + chdate +"");
	}


	public String getMessage() {
		   return super.getMessage();
	}

}
