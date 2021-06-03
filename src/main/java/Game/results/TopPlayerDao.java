package Game.results;

import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;


import javax.persistence.Query;
import java.util.List;

/**
 * DAO class for the {@link TopPlayer} entity.
 */
public class TopPlayerDao extends GenericJpaDao<TopPlayer> {


    public TopPlayerDao() {
        super(TopPlayer.class);
    }






    @Transactional
    public  TopPlayer find(String name) {
        try{
            return entityManager.find(TopPlayer.class,name);}
        catch (Exception e){
            return null;
        }
    }

    /**
     *
     * @param name is  the player name.
     * @param numOfGames is the updated number of games won by the player.
     */
    @Transactional
    public void update(String name, Integer numOfGames) {
        Query updateQuery = entityManager.createQuery("UPDATE TopPlayer set numOfGames =:numOfGames where name = :name")
                .setParameter("numOfGames", numOfGames)
                .setParameter("name", name);
        updateQuery.executeUpdate();
        TopPlayer UpdatedPlayer = find(name);
        entityManager.refresh(UpdatedPlayer);
    }

    /**
     * Returns the list of {@code n} best results with respect to
     * number of games won.
     * @param n the maximum number of results to be returned
     * @return the list of {@code n} best results with respect to
     *   number of games won
     */
    @Transactional
    public List<TopPlayer> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM TopPlayer r ORDER BY r.numOfGames DESC", TopPlayer.class)
                .setMaxResults(n)
                .getResultList();
    }

}


