package cz.fi.muni.pa165.yellow_yak.persistance;

import cz.fi.muni.pa165.yellow_yak.entity.Competition;
import cz.fi.muni.pa165.yellow_yak.entity.Score;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author oreqizer
 */
public interface ScoreDao {
    /**
     * Creates a new score
     * @param s score to create
     */
    void create(Score s);

    /**
     * Lists all scores
     * @return list of all scores
     */
    List<Score> findAll();

    /**
     * Finds a score by ID
     * @param id the score's ID
     * @return the found score
     */
    Score findById(Long id);

    /**
     * Removes a score
     * @param s score to remove
     */
    void remove(Score s);

    /**
     * Updates a score
     * @param s score to update
     */
    void update(Score s);

    /**
     * Finds a score by player and competition
     * @param playerId the player's ID
     * @param competitions the competitions
     * @return the found score
     */
    List<Score> findByPlayerAndCompetitionAndDate(Long playerId,
                                                  List<Competition> competitions,
                                                  LocalDateTime createdAt);

    /**
     * Finds a score by player and game
     * @param playerId the player's ID
     * @param gameId the game's Id
     * @return the found score
     */
    List<Score> findByPlayerAndGame(Long playerId, Long gameId);

    /**
     * Finds a score competition
     * @param competitionId the competition's ID
     * @return the found score
     */
    List<Score> findByCompetition(Long competitionId);
}
