package Game.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Class representing the result of a game played by a specific player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GameResult {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * The number of steps made by the player one.
     */
    private int stepsFirstPlayer;

    /**
     * The number of steps made by the player two.
     */
    private int stepsSecondPlayer;
    /**
     * The name of the winner.
     */
    @Column(nullable = false)
    private String winner;

    /**
     * The name of the player one.
     */
    @Column(nullable = false)
    private String FirstPlayer;

    /**
     * The name of the player two.
     */
    @Column(nullable = false)
    private String SecondPlayer;





    /**
     * The duration of the game.
     */
    @Column(nullable = false)
    private Duration duration;


    /**
     * The timestamp when the result was saved.
     */
    @Column(nullable = false)
    private ZonedDateTime created;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }

}
