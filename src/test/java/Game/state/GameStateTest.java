package Game.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameStateTest {


    @Test
    void testGameStateConstructor() {

        GameState KnightGame = new GameState();
        assertEquals(new GameState((new int[][]{
                {2,-1,0,-1,0,-1,0,-1},
                {-1,0,-1,0,-1,0,-1,0},
                {0,-1,0,-1,0,-1,0,-1},
                {-1,0,-1,0,-1,0,-1,0},
                {0,-1,0,-1,0,-1,0,-1},
                {-1,0,-1,0,-1,0,-1,0},
                {0,-1,0,-1,0,-1,0,-1},
                {-1,0,-1,0,-1,0,-1,3}
        })).toString(),KnightGame.toString());
    }

    @Test
    void testIsGameFinished() {
        assertFalse(new GameState().isGameFinished(0,0));
        assertTrue(new GameState(new int[][] {
                {0, 2, 0,-1,0,-1,0,-1},
                {1, 0, -1,1,-1,0,-1,0},
                {1, 1, 1,1,1,-1,0,-1},
                {1, 0, 1,1,-1,0,-1,0},
                {0, 1, 1,1,0,-1,0,-1},
                {-1, 3, 1,0,-1,0,-1,0},
                {0, -1, 1,-1,0,-1,0,-1},
                {-1, 0, 1,0,-1,0,-1,0}}).isGameFinished(0,1));
        assertTrue(new GameState(new int[][] {
                {0, 2, 0,-1,0,-1,0,-1},
                {1, 0, -1,1,-1,0,-1,0},
                {1, 1, 1,1,1,-1,0,-1},
                {1, 0, 1,1,-1,0,-1,0},
                {0, 1, 1,1,0,-1,0,-1},
                {-1, 3, 1,0,-1,0,-1,0},
                {0, -1, 1,1,0,-1,0,-1},
                {1, 0, 1,0,-1,0,-1,0}}).isGameFinished(5,1));
        assertFalse(new GameState(new int[][] {
                {0, -1, 0,-1,0,-1,0,-1},
                {1, 0, -1,1,-1,0,-1,0},
                {1, 1, 1,1,1,-1,0,-1},
                {1, 2, 1,1,-1,0,-1,0},
                {0, 1, 1,1,0,-1,0,-1},
                {-1, 3, 1,0,-1,0,-1,0},
                {0, -1, 1,1,0,-1,0,-1},
                {1, 0, 1,0,-1,0,-1,0}}).isGameFinished(3,1));
        assertFalse(new GameState(new int[][] {
                {0, -1, 0,-1,0,-1,0,-1},
                {1, 0, -1,1,-1,0,-1,0},
                {1, 1, 1,1,1,-1,0,-1},
                {1, 2, 1,1,-1,0,-1,0},
                {0, 1, 1,1,0,-1,0,-1},
                {-1, 3, 1,0,-1,0,-1,0},
                {0, -1, 1,1,0,-1,0,-1},
                {-1, 0, -1,0,-1,0,-1,0}}).isGameFinished(5,1));

    }





    @Test
    void testmoveKnight(){
        GameState KnightGame = new GameState();
        KnightGame.moveKnight(2,1,0,0);
        assertEquals(new GameState(
                       new int[][] {
                               {1,-1,0,-1,0,-1,0,-1},
                               {-1,0,-1,0,-1,0,-1,0},
                               {0,2,0,-1,0,-1,0,-1},
                               {-1,0,-1,0,-1,0,-1,0},
                               {0,-1,0,-1,0,-1,0,-1},
                               {-1,0,-1,0,-1,0,-1,0},
                               {0,-1,0,-1,0,-1,0,-1},
                               {-1,0,-1,0,-1,0,-1,3}}).toString(),KnightGame.toString());

        KnightGame.moveKnight(5,6,7,7);
        assertEquals(new GameState(
                new int[][] {
                        {1,-1,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,0},
                        {0,2,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,0},
                        {0,-1,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,3,0},
                        {0,-1,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,1}}).toString(),KnightGame.toString());

        KnightGame.moveKnight(0,2,2,1);
        assertEquals(new GameState(
                new int[][] {
                        {1,-1,2,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,0},
                        {0,1,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,0},
                        {0,-1,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,3,0},
                        {0,-1,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,1}}).toString(),KnightGame.toString());

        KnightGame.moveKnight(6,4,5,6);
        assertEquals(new GameState(
                new int[][] {
                        {1,-1,2,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,0},
                        {0,1,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,0},
                        {0,-1,0,-1,0,-1,0,-1},
                        {-1,0,-1,0,-1,0,1,0},
                        {0,-1,0,-1,3,-1,0,-1},
                        {-1,0,-1,0,-1,0,-1,1}}).toString(),KnightGame.toString());
    }

    @Test
    void testcanKnightMoveTo() {


        GameState KnightGame =new GameState(
                new int[][] {
                        {0, 2, 0,-1,1,-1,0,-1},
                        {1, 0,-1 ,1,-1,0,-1,0},
                        {1, 1, 1,-1,1,-1,0,-1},
                        {1, 1, 1,1,-1,0,-1,0},
                        {0, 1, 1,1,0,1,0,-1},
                        {-1, 3, -1,0,-1,0,-1,0},
                        {0, -1, 1,1,0,-1,0,-1},
                        { -1, 0,-1,0,-1,0,-1,0}});
        // testing white knight (invalid moves)
        assertFalse(KnightGame.canKnightMoveTo(0,0,0,1));
        assertFalse(KnightGame.canKnightMoveTo(2,2,0,1));
        assertFalse(KnightGame.canKnightMoveTo(1,3,0,1));
        assertFalse(KnightGame.canKnightMoveTo(2,0,0,1));

        // testing black knight (invalid moves)
        assertFalse(KnightGame.canKnightMoveTo(4,3,5,1));
        assertFalse(KnightGame.canKnightMoveTo(5,3,5,1));
        assertFalse(KnightGame.canKnightMoveTo(3,0,5,1));

        // testing black knights (valid moves)
        assertTrue(KnightGame.canKnightMoveTo(7,2,5,1));
        assertTrue(KnightGame.canKnightMoveTo(7,0,5,1));

    }

    @Test
    void testToString() {

        GameState KnightGameWithSomeValue = new GameState(new int[][] {
                {0, -1, 0,-1,0,-1,0,-1},
                { -1, 0,2,1,-1,0,-1,0},
                {0, -1, 0,-1,1,-1,0,-1},
                {-1, 0, -1,1,-1,0,-1,0},
                {0, 3, 0,1,0,-1,0,-1},
                {-1, 0, 1,0,-1,0,-1,0},
                {0, -1, 1,-1,0,-1,0,-1},
                {-1, 0, 1,0,-1,0,-1,0}});
        assertEquals("""
                CREAM BROWN CREAM BROWN CREAM BROWN CREAM BROWN\s
                BROWN CREAM whiteKnight OccupiedBefore BROWN CREAM BROWN CREAM\s
                CREAM BROWN CREAM BROWN OccupiedBefore BROWN CREAM BROWN\s
                BROWN CREAM BROWN OccupiedBefore BROWN CREAM BROWN CREAM\s
                CREAM blackKnight CREAM OccupiedBefore CREAM BROWN CREAM BROWN\s
                BROWN CREAM OccupiedBefore CREAM BROWN CREAM BROWN CREAM\s
                CREAM BROWN OccupiedBefore BROWN CREAM BROWN CREAM BROWN\s
                BROWN CREAM OccupiedBefore CREAM BROWN CREAM BROWN CREAM\s
                """, KnightGameWithSomeValue.toString());
    }
}

