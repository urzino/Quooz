package quooz.server.question;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import quooz.server.database.DatabaseConnection;
import quooz.server.user.AuthorHandler;
import quooz.server.user.PlayerHandler;
import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Category;
import quooz.shared.question.Level;
import quooz.shared.question.Option;
import quooz.shared.question.Question;
import quooz.shared.question.QuestionHandlerInterface;
import quooz.shared.user.Author;
import quooz.shared.user.Player;

/**
 * The Class QuestionHandler manages everything related to Question from the
 * creation passing through the withdrawing from the Database to the editing
 * (modification/elimination)
 */
public class QuestionHandler extends UnicastRemoteObject implements QuestionHandlerInterface {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The unique instance. */
	private static QuestionHandler instance;

	/**
	 * Instantiates a new question handler.
	 *
	 * @throws RemoteException
	 *             the remote exception
	 */
	protected QuestionHandler() throws RemoteException {
		super();
	}

	/**
	 * Gets the single instance of QuestionHandler, it implements the singleton
	 * pattern.
	 *
	 * @return single instance of QuestionHandler
	 */
	public static QuestionHandler getInstance() {
		if (instance == null) {
			try {
				instance = new QuestionHandler();
			} catch (RemoteException e) {
				e.printStackTrace();
				return null;
			}
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quooz.shared.question.QuestionHandlerInterface#getQuestionList()
	 */
	public LinkedHashMap<Integer, String> getQuestionList(Author author) throws RemoteException, DatabaseProblemException {
		if(!AuthorHandler.getInstance().isRealAuthor(author)){
			//TODO
			return null;
		}
		LinkedHashMap<Integer, String> questions = new LinkedHashMap<>();
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT id,text FROM Question");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// put the question id and its text in the hasmap
				questions.put(rs.getInt("id"), rs.getString("text"));
			}
			return questions;
		} catch (SQLException e) {
			System.out.println("Sqlexception caught: " + e.getMessage());
			throw new DatabaseProblemException();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.question.QuestionHandlerInterface#getGameQuestion(quooz.
	 * shared.user.Player)
	 */
	public int getGameQuestion(Player player) throws RemoteException, DatabaseProblemException {
		if(!PlayerHandler.getInstance().isRealPlayer(player)){
			//TODO
			return -1;
		}
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();

			// the query which selects randomly an available question for the
			// user
			PreparedStatement ps = c.prepareStatement("SELECT id FROM playeravailablequestions "
					+ "WHERE username=? and difficulty=? " + "order by rand() limit 1");
			ps.setString(1, player.getUsername());
			ps.setInt(2, player.getLevel().toInt());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			} else {
				return -1;
			}
		} catch (SQLException e) {
			System.out.println("Sqlexception caught: " + e.getMessage());
			throw new DatabaseProblemException();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quooz.shared.question.QuestionHandlerInterface#getQuestionById(int)
	 */
	public Question getQuestionById(int id) throws RemoteException, DatabaseProblemException {

		Question question = null;
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT * FROM question WHERE id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ArrayList<Option> options = getOptions(rs.getInt("id"));
				Level level = Level.getLevelFromInt(rs.getInt("difficulty"));
				Category category = Category.valueOf(rs.getString("category"));
				ImageIcon imageIcon = null;
				byte[] imgBytes = rs.getBytes("image");

				if (imgBytes != null)
					imageIcon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imgBytes)));

				QuestionCreator qc = QuestionCreator.getInstance();
				question = qc.createQuestion(rs.getInt("id"), rs.getString("text"), level, category, options,
						rs.getString("userAuthor"), imageIcon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseProblemException();
		} catch (IOException e) {
			System.out.println("E' stato riscontrato un problema nel leggere una immagine");
		
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}

		return question;
	}

	/**
	 * Gets the options. it supports getQuestionById withdrawing the question's
	 * options from the Database
	 * 
	 * @param questionId
	 *            the question id
	 * @return the options
	 * @throws DatabaseProblemException 
	 */
	private ArrayList<Option> getOptions(int questionId) throws DatabaseProblemException {
		ArrayList<Option> options = new ArrayList<Option>();
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT * FROM `option` WHERE questionId=?");
			ps.setInt(1, questionId);
			ResultSet rs = ps.executeQuery();
			OptionCreator oc = OptionCreator.getInstance();
			while (rs.next()) {
				byte[] imgBytes = rs.getBytes("image");
				ImageIcon imageIcon = null;
				if (imgBytes != null)
					imageIcon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imgBytes)));
				boolean opt = rs.getInt("correct") != 0;
				Option option = oc.createOption(rs.getInt("id"), rs.getString("text"), opt, imageIcon);

				options.add(option);
			}
			return options;

		} catch (SQLException e) {
			System.out.println("Sqlexception caught: " + e.getMessage());
			throw new DatabaseProblemException();
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}

	}

	/**
	 * This method is charged to effectively add a new question into the
	 * Database.
	 *
	 * @param txt
	 *            the question's txt
	 * @param level
	 *            the question's level
	 * @param category
	 *            the question's category
	 * @param wrongOptionsTextual
	 *            the question's wrong options textual
	 * @param correctOptionTextual
	 *            the question's correct option textual
	 * @param wrongOptionsImage
	 *            the question's wrong options image
	 * @param correctOptionImage
	 *            the question's correct option image
	 * @param author
	 *            the question's author
	 * @param questionImage
	 *            the question's image
	 * @throws RemoteException
	 *             the remote exception
	 * @throws DatabaseProblemException 
	 */
	private void createNewQuestion(String txt, Level level, Category category, ArrayList<String> wrongOptionsTextual,
			String correctOptionTextual, ArrayList<File> wrongOptionsImage, File correctOptionImage, Author author,
			File questionImage) throws RemoteException, DatabaseProblemException {
		
		if (!AuthorHandler.getInstance().isRealAuthor(author)) {
			// TODO
			return;
		}

		Connection c = null;

		PreparedStatement ps;
		try {
			c = DatabaseConnection.getConnection();
			ps = c.prepareStatement(
					"INSERT INTO question (text,difficulty,userAuthor,image,category) VALUES (?,?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, txt);
			ps.setInt(2, level.ordinal() + 1);
			ps.setString(3, author.getUsername());
			if (questionImage != null) { // if the question has an image in the
											// text
				ps.setBytes(4, Files.readAllBytes(questionImage.toPath())); // read
																			// image
			} else {
				ps.setBytes(4, null);
			}

			ps.setString(5, category.toString());
			ps.executeUpdate();

			ResultSet keys = ps.getGeneratedKeys();
			keys.next();

			if (correctOptionImage == null) { // check if options are textual or
												// image
				addTextualOptionsToDb(keys.getInt(1), wrongOptionsTextual, correctOptionTextual);
			} else {
				addImageOptionsToDb(keys.getInt(1), wrongOptionsImage, correctOptionImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.question.QuestionHandlerInterface#createNewQuestion(java.
	 * lang.String, quooz.shared.question.Level, quooz.shared.question.Category,
	 * java.util.ArrayList, java.lang.String, java.lang.String)
	 */
	public void createNewQuestion(String text, Level level, Category category, ArrayList<String> wrongOptionsTextual,
			String correctTextualOption, Author userAuthor) throws RemoteException, DatabaseProblemException {
		createNewQuestion(text, level, category, wrongOptionsTextual, correctTextualOption, null, null, userAuthor,
				null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.question.QuestionHandlerInterface#createNewQuestion(java.
	 * lang.String, quooz.shared.question.Level, quooz.shared.question.Category,
	 * java.util.ArrayList, java.io.File, java.lang.String, java.io.File)
	 */
	public void createNewQuestion(String text, Level level, Category category, ArrayList<File> wrongOptionsImage,
			File correctImageOption, Author userAuthor, File questionImage) throws RemoteException, DatabaseProblemException {
		createNewQuestion(text, level, category, null, null, wrongOptionsImage, correctImageOption, userAuthor,
				questionImage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.question.QuestionHandlerInterface#createNewQuestion(java.
	 * lang.String, quooz.shared.question.Level, quooz.shared.question.Category,
	 * java.util.ArrayList, java.lang.String, java.lang.String, java.io.File)
	 */
	public void createNewQuestion(String text, Level level, Category category, ArrayList<String> wrongOptionsTextual,
			String correctTextualOption, Author userAuthor, File questionImage) throws RemoteException, DatabaseProblemException {
		createNewQuestion(text, level, category, wrongOptionsTextual, correctTextualOption, null, null, userAuthor,
				questionImage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.question.QuestionHandlerInterface#createNewQuestion(java.
	 * lang.String, quooz.shared.question.Level, quooz.shared.question.Category,
	 * java.util.ArrayList, java.io.File, java.lang.String)
	 */
	public void createNewQuestion(String text, Level level, Category category, ArrayList<File> wrongOptionsImage,
			File correctImage, Author userAuthor) throws RemoteException, DatabaseProblemException {
		createNewQuestion(text, level, category, null, null, wrongOptionsImage, correctImage, userAuthor, null);
	}

	/**
	 * Adds the textual options to the Database supporting createNewQuestion
	 *
	 * @param questionId
	 *            the question's id whose options needs to be added
	 * @param wrongOptionsTextual
	 *            the wrong textual options
	 * @param correctOptionTextual
	 *            the correct textual option
	 * @throws DatabaseProblemException 
	 */
	private void addTextualOptionsToDb(int questionId, ArrayList<String> wrongOptionsTextual,
			String correctOptionTextual) throws DatabaseProblemException {
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps2 = c
					.prepareStatement("INSERT INTO `option` (text,correct,questionId,image) VALUES (?,?,?,?);");
			// Inserts the wrong options
			for (int i = 0; i < wrongOptionsTextual.size(); i++) {
				ps2.setString(1, wrongOptionsTextual.get(i));
				ps2.setInt(2, 0);
				ps2.setInt(3, questionId);
				ps2.setBytes(4, null); // Textual option hasn't bytes for the
										// image
				ps2.executeUpdate();

			}
			// Inserts the correct one
			ps2.setString(1, correctOptionTextual);
			ps2.setInt(2, 1);
			ps2.setInt(3, questionId);
			ps2.setBytes(4, null);
			ps2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseProblemException();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {				
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}

	}

	/**
	 * Adds the image options to the Database supporting createNewQuestion
	 *
	 * @param questionId
	 *            the question's id whose options needs to be added
	 * @param wrongOptionsImage
	 *            the wrong image options
	 * @param correctOptionImage
	 *            the correct image option
	 * @throws DatabaseProblemException 
	 */
	private void addImageOptionsToDb(int questionId, ArrayList<File> wrongOptionsImage, File correctOptionImage) throws DatabaseProblemException {
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();

			PreparedStatement ps2 = c
					.prepareStatement("INSERT INTO `option` (text,correct,questionId,image) VALUES (?,?,?,?);");
			// Inserts the wrong options
			for (int i = 0; i < wrongOptionsImage.size(); i++) {
				ps2.setString(1, null); // Image option hasn't text
				ps2.setInt(2, 0);
				ps2.setInt(3, questionId);
				ps2.setBytes(4, Files.readAllBytes(wrongOptionsImage.get(i).toPath())); // Bytes
																						// of
																						// the
																						// image
				ps2.executeUpdate();
			}

			// Inserts the correct one
			ps2.setString(1, null);
			ps2.setInt(2, 1);
			ps2.setInt(3, questionId);
			ps2.setBytes(4, Files.readAllBytes(correctOptionImage.toPath()));
			ps2.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();
			throw new DatabaseProblemException();
		} catch (IOException e) {
			System.out.println("E' stato riscontrato un problema nel leggere una immagine");
		} finally {
			try {
				c.close();
			} catch (SQLException e) {				
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quooz.shared.question.QuestionHandlerInterface#editQuestion(int,
	 * java.lang.String, quooz.shared.question.Level,
	 * quooz.shared.question.Category, java.util.LinkedHashMap,
	 * java.util.LinkedHashMap, java.lang.String, java.io.File, boolean)
	 */
	public void editQuestion(int questionId, String newTxt, Level newLevel, Category newCategory,
			LinkedHashMap<Integer, String> newOptionsTextual, LinkedHashMap<Integer, File> newOptionsImage,
			Author newAuthor, File newImage, boolean isImage) throws RemoteException, DatabaseProblemException {
		
		if (!AuthorHandler.getInstance().isRealAuthor(newAuthor)) {
			// TODO
			return;
		}
		Connection c = null;
		try {

			// Update new fields
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("UPDATE question SET text=? WHERE question.id=?");
			ps.setString(1, newTxt);
			ps.setInt(2, questionId);

			PreparedStatement ps1 = c.prepareStatement("UPDATE question SET userAuthor=? WHERE question.id=?");
			ps1.setString(1, newAuthor.getUsername());
			ps1.setInt(2, questionId);

			PreparedStatement ps2 = c.prepareStatement("UPDATE question SET difficulty=? WHERE question.id=?");
			ps2.setInt(1, newLevel.toInt());
			ps2.setInt(2, questionId);

			PreparedStatement ps3 = c.prepareStatement("UPDATE question SET category=? WHERE question.id=?");
			ps3.setString(1, newCategory.toString());
			ps3.setInt(2, questionId);

			if (newImage != null) {// check if the question has image
				PreparedStatement ps4 = c.prepareStatement("UPDATE question SET image=? WHERE question.id=?");
				ps4.setBytes(1, Files.readAllBytes(newImage.toPath()));
				ps4.setInt(2, questionId);
				ps4.executeUpdate();
			}

			ps.executeUpdate();
			ps1.executeUpdate();
			ps2.executeUpdate();
			ps3.executeUpdate();

			if (!isImage) { // check if the options are textual or image
				for (int i = 0; i < newOptionsTextual.size(); i++) {
					PreparedStatement ps5 = c.prepareStatement("UPDATE `option` set text=? WHERE `option`.id=?");
					int idq = (int) newOptionsTextual.keySet().toArray()[i];
					ps5.setString(1, newOptionsTextual.get(idq));
					ps5.setInt(2, idq);
					ps5.executeUpdate();
				}
			} else if (newOptionsImage != null) {
				for (int i = 0; i < newOptionsImage.size(); i++) {
					PreparedStatement ps5 = c.prepareStatement("UPDATE `option` set image=? WHERE `option`.id=?");
					int idq = (int) newOptionsImage.keySet().toArray()[i];
					ps5.setBytes(1, Files.readAllBytes(newOptionsImage.get(idq).toPath()));
					ps5.setInt(2, idq);
					ps5.executeUpdate();
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseProblemException();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {				
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.question.QuestionHandlerInterface#deleteQuestion(java.lang.
	 * Integer)
	 */
	public void deleteQuestion(Integer questionId, Author author) throws RemoteException, DatabaseProblemException {
		if(!AuthorHandler.getInstance().isRealAuthor(author)){
			//TODO
			return;
		}
		
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("DELETE FROM `question` WHERE `id`=?;");
			ps.setInt(1, questionId);
			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}

	}

}
