package Game.state;

import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the cells in the board where whiteKnight = 2 , BlackKnight =3.
 */
public class cell {

    /**
     *  fields of the cell class.
     *  row field represents the the row of the cell in the board
     *  col field represents the the column of the cell in the board
     *  type represents cell where Cream = 0 Brown = -1 OccupiedBefore = 1  whiteKnight = 2 , BlackKnight =3.
     */


     private int col;
     private int row;
     private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.type = 0;
    }

    public String toString() {

       if (this.type == 2) {
           return "whiteKnight";
       }
       else if (this.type == 0){
           return "CREAM";
       } else if (this.type == -1){
           return "BROWN";
       }
       else if (this.type == 1) {
              return "OccupiedBefore";
              }
       else {
             return "blackKnight";
       }

    }
}
