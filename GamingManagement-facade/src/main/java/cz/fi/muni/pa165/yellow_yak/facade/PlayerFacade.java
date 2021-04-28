package cz.fi.muni.pa165.yellow_yak.facade;

import cz.fi.muni.pa165.yellow_yak.dto.PlayerDTO;

import java.util.List;

/**
 * @author oreqizer
 */
public interface PlayerFacade {

    /**
     * Creates a new player
     * @param name player name
     * @param email player email
     * @return the created player
     */
    public PlayerDTO create(String name, String email);

    /**
     * Removes the player
     * @param id id to remove
     */
    public void remove(Long id);

    /**
     * Finds a player by id
     * @param id the ID to find
     * @return the player
     */
    public PlayerDTO findById(Long id);

    /**
     * Finds a player by name
     * @param name the name to find
     * @return the player
     */
    public PlayerDTO findByName(String name);

    /**
     * Finds players by team
     * @param teamId the team's ID
     * @return the players found
     */
    public List<PlayerDTO> listByTeam(Long teamId);

}
