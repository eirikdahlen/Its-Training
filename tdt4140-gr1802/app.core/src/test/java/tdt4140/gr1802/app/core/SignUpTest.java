package tdt4140.gr1802.app.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class SignUpTest {
	
	SignUp signup = new SignUp("andrekm", "Andreas Melzer", "123test", "123test", true);
	
	@Test
	public void testSignUpUsernameOnlyLetters() {
		assertTrue(signup.checkNameOnlyLetters(signup.username));
	}
	
	public void testPasswordEquality() {
		signup = new SignUp(null, null, "123TEST", "123test", false);
		assertFalse(signup.checkEqualPasswords("123TEST",  "123test"));
	}
}
