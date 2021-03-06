package cz.fi.muni.pa165.yellow_yak.facade;

import cz.fi.muni.pa165.yellow_yak.dto.GameDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Facade for Game
 *
 * @author oreqizer
 */
public interface GameFacade {

    /**
     * Creates a new game
     * @param name game name
     * @return the created game
     */
    public GameDTO create(@NotNull String name);

    /**
     * Removes the game
     * @param id game id to remove
     */
    public boolean remove(@NotNull Long id);

    /**
     * Finds a game by id
     * @param id the ID to find
     * @return the game
     */
    public GameDTO findById(@NotNull Long id);

    /**
     * Lists games by name
     * @param name the name to find
     * @return the game list
     */
    public List<GameDTO> findByName(@NotNull String name);

    /**
     * Returns all games
     * @return list of games
     */
    public List<GameDTO> findAll();

}
