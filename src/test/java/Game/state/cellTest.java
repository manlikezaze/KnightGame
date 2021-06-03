package Game.state;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class cellTest {


    @Test
    void testToString() {
        cell cell =new cell(2,2);
        assertEquals("CREAM", cell.toString());
        cell.setType(1);
        assertEquals("OccupiedBefore", cell.toString());
        cell.setType(2);
        assertEquals("whiteKnight", cell.toString());
        cell.setType(3);
        assertEquals("blackKnight", cell.toString());
        cell.setType(-1);
        assertEquals("BROWN",cell.toString());
    }
}
