/**
 * 
 */
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
import quooz.shared.question.Level;
import quooz.shared.user.Player;
import quooz.shared.user.PlayerHandlerInterface;

/**
 * @author Matteo
 *
 */
public class PlayerHandlerTest {

	public class DummYPlayerHandler implements PlayerHandlerInterface {

		public HashMap<String, ClientInterface> loggedPlayers = new HashMap<String, ClientInterface>();

		@Override
		public Player signInPlayer(String user, String pwd, ClientInterface client)
				throws RemoteException, UserAlreadyLoggedException, DatabaseProblemException {

			Player player = null;
			if (loggedPlayers.containsKey(user)) {
				throw new UserAlreadyLoggedException(user, "player");
			}
			// Assumo per test che il player venga creato. In realtà con rmi
			// viene fatto tramite db
			player = new Player("TestPlayer", "TestPlayer", Date.valueOf(LocalDate.now()), user, pwd, "", Level.ONE, 0,
					0);
			ClientInterface client1 = new Client(user);
			loggedPlayers.put(user, client1);
			return player;

		}

		@Override
		public void signUpNewPlayer(String name, String surname, Date date, String username, String password,
				String email) throws RemoteException, DatabaseProblemException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isFieldAvailabe(String field, String value) throws RemoteException, DatabaseProblemException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void signOutPlayer(Player player) throws RemoteException {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendVerificationCode(String email, String verificationCode) throws RemoteException {
			// TODO Auto-generated method stub

		}

		@Override
		public void unregisterPlayer(Player player) throws RemoteException, DatabaseProblemException {
			// TODO Auto-generated method stub

		}

		@Override
		public String generateVerificationCode() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Player getUpdatedPlayer(Player player) throws RemoteException, DatabaseProblemException {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/**
	 * Test method for
	 * {@link quooz.server.user.PlayerHandler#isRealPlayer(quooz.shared.user.Player)}
	 * .
	 * 
	 * @throws DatabaseProblemException
	 */
	@Test
	public void testIsRealPlayer() throws DatabaseProblemException {
		Player player = new Player("TestPlayer", "TestPlayer", Date.valueOf(LocalDate.now()), "RoccoBay",
				DigestUtils.md5Hex("123456"), "test@test.com", Level.ONE, 0, 0);
		boolean actual = PlayerHandler.getInstance().isRealPlayer(player);
		boolean expected = true;
		assertEquals("RoccoBay is a real player, true expected ", expected, actual);
		player = new Player("TestPlayer", "TestPlayer", Date.valueOf(LocalDate.now()), "fakeplayer",
				DigestUtils.md5Hex("admin"), "admin@admin.com", Level.ONE, 0, 0);
		actual = PlayerHandler.getInstance().isRealPlayer(player);
		expected = false;
		assertEquals("fakeplayer isn't a real player, false expected ", expected, actual);
	}

	@Test(expected = UserAlreadyLoggedException.class)
	public void testSignInAlreadyLogged() throws RemoteException, UserAlreadyLoggedException, DatabaseProblemException {
		ClientInterface client1=new Client("RoccoBay");
		ClientInterface client2=new Client("RoccoBay");
		PlayerHandlerInterface ph=new DummYPlayerHandler();
		ph.signInPlayer("RoccoBay", "12345", client1);
		ph.signInPlayer("RoccoBay", "12345", client2);
		fail("Should throw UserAlreadyLoggedException");
	}
}
