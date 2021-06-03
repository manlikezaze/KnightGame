package Game.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Class representing the top result of games played by a specific player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TopPlayer {

    /**
     * The name of the player.
     */

    @Id
    private String name;


    /**
     * The number of games won by the player.
     */
    @Column(nullable = false)
    private Integer numOfGames;





}
