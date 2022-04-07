package net.frazionz.android.auth;


public class UserComputerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public UserComputerNotFoundException(String message) {
		this.setMessage(message);
	}

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}