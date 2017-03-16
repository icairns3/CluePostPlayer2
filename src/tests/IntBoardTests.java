package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	
	private IntBoard board;

	@Before
	public void init() {
		board = new IntBoard();
	}

	//Tests assertTrue using remove, which returns true if they find the element.
	//This is so they can test that the list is empty at the end.
	@Test
	public void testAdj0_0(){
		Set<BoardCell> adj = board.getAdjList(0, 0);
		assertTrue(adj.remove(board.getCellAt(0, 1)));
		assertTrue(adj.remove(board.getCellAt(1, 0)));
		assertTrue(adj.isEmpty());
	}

	@Test
	public void testAdj3_3() {
		Set<BoardCell> adj = board.getAdjList(3, 3);
		assertTrue(adj.remove(board.getCellAt(3, 2)));
		assertTrue(adj.remove(board.getCellAt(2, 3)));
		assertTrue(adj.isEmpty());
	}

	@Test
	public void testAdj1_3() {
		Set<BoardCell> adj = board.getAdjList(1, 3);
		assertTrue(adj.remove(board.getCellAt(1, 2)));
		assertTrue(adj.remove(board.getCellAt(0, 3)));
		assertTrue(adj.remove(board.getCellAt(2, 3)));
		assertTrue(adj.isEmpty());
	}

	@Test
	public void testAdj3_1() {
		Set<BoardCell> adj = board.getAdjList(3, 1);
		assertTrue(adj.remove(board.getCellAt(2, 1)));
		assertTrue(adj.remove(board.getCellAt(3, 0)));
		assertTrue(adj.remove(board.getCellAt(3, 2)));
		assertTrue(adj.isEmpty());
	}

	@Test
	public void testAdj1_1() {
		Set<BoardCell> adj = board.getAdjList(1, 1);
		assertTrue(adj.remove(board.getCellAt(1, 2)));
		assertTrue(adj.remove(board.getCellAt(2, 1)));
		assertTrue(adj.remove(board.getCellAt(0, 1)));
		assertTrue(adj.remove(board.getCellAt(1, 0)));
		assertTrue(adj.isEmpty());
	}

	@Test
	public void testAdj2_2() {
		Set<BoardCell> adj = board.getAdjList(2, 2);
		assertTrue(adj.remove(board.getCellAt(1, 2)));
		assertTrue(adj.remove(board.getCellAt(2, 1)));
		assertTrue(adj.remove(board.getCellAt(3, 2)));
		assertTrue(adj.remove(board.getCellAt(2, 3)));
		assertTrue(adj.isEmpty());
	}

	@Test
	/**
	 * Tests that all targets 3 moves away from (0,0) are accounted for.
	 */
	public void testTargets0_3() {
		BoardCell cell = board.getCellAt(0, 0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 3)));
		assertTrue(targets.contains(board.getCellAt(1, 2)));
		assertTrue(targets.contains(board.getCellAt(3, 0)));
		assertTrue(targets.contains(board.getCellAt(2, 1)));
		assertTrue(targets.contains(board.getCellAt(0, 1)));
		assertTrue(targets.contains(board.getCellAt(1, 0)));
	}
	
	@Test
	/**
	 * Tests that all targets 2 moves away from (0,0) are accounted for.
	 */
	public void testTargets0_2() {
		BoardCell cell = board.getCellAt(0, 0);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 0)));
		assertTrue(targets.contains(board.getCellAt(1, 1)));
		assertTrue(targets.contains(board.getCellAt(0, 2)));
	}
	
	@Test
	/**
	 * Tests that all targets 1 move away from (0,0) are accounted for.
	 */
	public void testTargets0_1() {
		BoardCell cell = board.getCellAt(0, 0);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 1)));
		assertTrue(targets.contains(board.getCellAt(1, 0)));
	}
	
	@Test
	/**
	 * Tests that all targets 3 moves away from (3,3) are accounted for.
	 */
	public void testTargets3_3() {
		BoardCell cell = board.getCellAt(3, 3);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 3)));
		assertTrue(targets.contains(board.getCellAt(1, 2)));
		assertTrue(targets.contains(board.getCellAt(3, 0)));
		assertTrue(targets.contains(board.getCellAt(2, 1)));
		assertTrue(targets.contains(board.getCellAt(3, 2)));
		assertTrue(targets.contains(board.getCellAt(2, 3)));
	}
	
	@Test
	/**
	 * Tests that all targets 2 moves away from (3,3) are accounted for.
	 */
	public void testTargets3_2() {
		BoardCell cell = board.getCellAt(3, 3);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 1)));
		assertTrue(targets.contains(board.getCellAt(2, 2)));
		assertTrue(targets.contains(board.getCellAt(1, 3)));
	}
	
	@Test
	/**
	 * Tests that all targets 1 move away from (3,3) are accounted for.
	 */
	public void testTargets3_1() {
		BoardCell cell = board.getCellAt(3, 3);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 2)));
		assertTrue(targets.contains(board.getCellAt(2, 3)));
	}
}
