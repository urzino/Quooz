package quooz.server.user;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import quooz.client.Client;
import quooz.shared.ClientInterface;
import quooz.shared.DatabaseProblemException;
import quooz.shared.UserAlreadyLoggedException;
import quooz.shared.user.Author;
import quooz.shared.user.AuthorHandlerInterface;

public class AuthorHandlerTest {
	
	public class DummyAuthorHandler implements AuthorHandlerInterface{
		
		public HashMap<String, ClientInterface> loggedAuthors = new HashMap<String, ClientInterface>();

		@Override
		public Author signIn(String user, String pwd, ClientInterface client)
				throws RemoteException, UserAlreadyLoggedException, DatabaseProblemException {
			Author author = null;
			if (loggedAuthors.containsKey(user)) {
				throw new UserAlreadyLoggedException(user, "player");
			}
			// Assumo per test che il player venga creato. In realtà con rmi
			// viene fatto tramite db
			author = new Author("TestAuthor", "TestAuthor", Date.valueOf(LocalDate.now()), user, "", "");
			ClientInterface client1 = new Client(user);
			loggedAuthors.put(user, client1);
			return author;
		}

		@Override
		public void signOutAuthor(Author author) throws RemoteException {
			// TODO Auto-generated method stub
			
		}

		
	}

	@Test
	public void testIsRealAuthor() throws DatabaseProblemException {
		Author author=new Author("TestAuthor", "TestAuthor", Date.valueOf(LocalDate.now()), "admin", DigestUtils.md5Hex("admin"), "admin@admin.com");
		boolean actual=AuthorHandler.getInstance().isRealAuthor(author);
		boolean expected=true;
		assertEquals("admin is a real author, true expected",expected, actual);
		author=new Author("TestAuthor", "TestAuthor ", Date.valueOf(LocalDate.now()), "fakeadmin", DigestUtils.md5Hex("admin"), "admin@admin.com");
		actual=AuthorHandler.getInstance().isRealAuthor(author);
		expected=false;
		assertEquals("fakeadmin isn't a real author, false expected",expected, actual);
		
	}
	
	@Test(expected = UserAlreadyLoggedException.class)
	public void testSignInAlreadyLogged() throws RemoteException, UserAlreadyLoggedException, DatabaseProblemException {
		ClientInterface client1=new Client("admin");
		ClientInterface client2=new Client("admin");
		AuthorHandlerInterface ah=new DummyAuthorHandler();
		ah.signIn("admin", "12345", client1);
		ah.signIn("admin", "12345", client2);
		fail("Should throw UserAlreadyLoggedException");
	}

}
