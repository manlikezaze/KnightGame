package Game.results;

import util.jpa.GenericJpaDao;



/**
 * DAO class for the {@link GameResult} entity.
 */
public class GameResultDao extends GenericJpaDao<GameResult> {


    public GameResultDao() {
        super(GameResult.class);
    }


}
