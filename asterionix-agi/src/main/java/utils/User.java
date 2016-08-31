package utils;

public class User {

	private String user;
	
/*   WARNING!!  WARNING!!  WARNING!! WARNING!!  WARNING!! WARNING!! 
 * 
 *   user's password MUST be hidden from anyone due to security reasons 
 *  
 *   password MUST not coincide with usual password from login/password pair used for program access
 * 	
 *   password MUST be assigned by Asterisk admin and MUST be invisible for anyone
 *    	
 */
	private String password;
	
	public User(String user, String password){
		this.user = user;
		this.password = password;
	}
	
	public String getUser(){
		
		return this.user;
	}
	
	public String getPassword(){
		
		return this.password;
		
	}
	
}
