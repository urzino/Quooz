package quooz.server.system;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;

import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Answer;
import quooz.shared.question.Level;
import quooz.shared.question.Option;
import quooz.shared.user.Player;

public class GameHandlerTest {

	@Test
	public void testCreateFeedback() {
		Option option=new Option(1, "Useless", true, null);
		Answer answer= new Answer(option);
		GameHandler gh=GameHandler.getInstance();
		boolean actual=gh.createFeedback(answer);
		boolean expected=true;
		assertEquals("Asnwer is correct: should be true",expected,actual);
		option=new Option(1, "Useless", false, null);
		answer= new Answer(option);
		actual=gh.createFeedback(answer);
		expected=false;
		assertEquals("Asnwer is wrong: should be false",expected,actual);
	}

	/**
	 * Test update stats.
	 * @throws DatabaseProblemException 
	 */
	@Test
	public void testUpdateStats() throws DatabaseProblemException {
		Player p=new Player("TestName", "TestSurname",Date.valueOf(LocalDate.now()), "RoccoBay", "e10adc3949ba59abbe56e057f20f883e", "test@gmail.com", Level.ONE, 0, 0);
		GameHandler gh=GameHandler.getInstance();
		gh.updateStats(p, p.getLevel(), true);
		Level actualLevel=p.getLevel();
		Level expectedLevel=Level.ONE;
		int actualCorrectSequence=p.getCorrectSequence();
		int expectedCorrectSequence=1;
		int actualScore=p.getScore();
		int expectedScore=2;
		assertEquals("Expected level should be ONE",expectedLevel,actualLevel);
		assertEquals("Expected correctSequence should be 1",expectedCorrectSequence,actualCorrectSequence);
		assertEquals("Expected score should be 2",expectedScore,actualScore);
		gh.updateStats(p, p.getLevel(), true);
		gh.updateStats(p, p.getLevel(), true);
		actualLevel=p.getLevel();
		expectedLevel=Level.TWO;
		actualCorrectSequence=p.getCorrectSequence();
		expectedCorrectSequence=0;
		actualScore=p.getScore();
		expectedScore=6;
		assertEquals("Expected level should be TWO",expectedLevel,actualLevel);
		assertEquals("Expected correctSequence should be 0",expectedCorrectSequence,actualCorrectSequence);
		assertEquals("Expected score should be 6",expectedScore,actualScore);
		for(int i=0;i<6;i++){
			gh.updateStats(p, p.getLevel(), false);
		}
		actualLevel=p.getLevel();
		expectedLevel=Level.ONE;
		actualCorrectSequence=p.getCorrectSequence();
		expectedCorrectSequence=-1;
		actualScore=p.getScore();
		expectedScore=0;
		assertEquals("Expected level should be ONE",expectedLevel,actualLevel);
		assertEquals("Expected correctSequence should be -1",expectedCorrectSequence,actualCorrectSequence);
		assertEquals("Expected score should be 0",expectedScore,actualScore);
		p.setLevel(Level.THREE);
		p.setCorrectSequence(2);
		p.setScore(0);
		gh.updateStats(p, p.getLevel(), true);
		actualLevel=p.getLevel();
		expectedLevel=Level.THREE;
		actualCorrectSequence=p.getCorrectSequence();
		expectedCorrectSequence=0;
		actualScore=p.getScore();
		expectedScore=6;
		assertEquals("Expected level should be THREE",expectedLevel,actualLevel);
		assertEquals("Expected correctSequence should be 0",expectedCorrectSequence,actualCorrectSequence);
		assertEquals("Expected score should be 6",expectedScore,actualScore);
	}

}
