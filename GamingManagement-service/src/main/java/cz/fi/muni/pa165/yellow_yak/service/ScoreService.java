package cz.fi.muni.pa165.yellow_yak.service;

import cz.fi.muni.pa165.yellow_yak.dto.ScoreDTO;
import cz.fi.muni.pa165.yellow_yak.entity.Competition;
import cz.fi.muni.pa165.yellow_yak.entity.Score;
import cz.fi.muni.pa165.yellow_yak.entity.Score;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Score service layer interface
 *
 * @author Matej Horniak
 */
public interface ScoreService {
    /**
     * Find Score by specific Score, specific Competition and specific Date
     * @param playerId id of player
     * @param competitions competitions
     * @param createdAt specific date
     */
    public List<Score> findByPlayerAndCompetitionAndDate(Long playerId,
                                                         List<Competition> competitions,
                                                         LocalDate createdAt);

    /**
     * Creates a new score
     * @param competitionId competition id
     * @param playerId player id
     * @return the created score
     */
    public Score create(Long competitionId, Long playerId);

    /**
     * Removes the score
     * @param id id to remove
     */
    public boolean remove(Long id);

    /**
     * Finds a score by id
     * @param id the ID to find
     * @return the score
     */
    public Score findById(Long id);

    /**
     * Sets the score's result and recalculates rank
     * @param id score ID
     * @param result result
     * @return updated score
     */
    public Score setResult(Long id, String result);

    /**
     * Lists scores by username
     * @param playerId the player's ID
     * @param gameId the game's ID
     * @return the score
     */
    public List<Score> findByPlayerAndGame(Long playerId, Long gameId);

    /**
     * Finds scores by team
     * @param competitionId the competition's ID
     * @return the players found
     */
    public List<Score> findByCompetition(Long competitionId);

}
