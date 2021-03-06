package cz.fi.muni.pa165.yellow_yak.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.fi.muni.pa165.yellow_yak.ApiUris;
import cz.fi.muni.pa165.yellow_yak.dto.GameDTO;
import cz.fi.muni.pa165.yellow_yak.dto.PlayerDTO;
import cz.fi.muni.pa165.yellow_yak.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.yellow_yak.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.yellow_yak.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.yellow_yak.facade.GameFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * REST layer for Game
 *
 * @author Lukas Mikula
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_GAMES)
public class GameController {

    final static Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameFacade gameFacade;

    /**
     * returns all games according to as JSON
     *
     * @return list of GameDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<GameDTO> findGames() throws JsonProcessingException {
        logger.debug("rest findGames()");
        return gameFacade.findAll();
    }

    /**
     * getting game according to id
     *
     * @param id game identifier
     * @return GameDTO
     * @throws ResourceNotFoundException
     * @throws InvalidParameterException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final GameDTO findGame(@PathVariable("id") Long id) throws ResourceNotFoundException, InvalidParameterException {
        logger.debug("rest findGame({})", id);
        GameDTO game = null;
        try {
            game = gameFacade.findById(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException();
        }
        if (game == null) {
            throw new ResourceNotFoundException();
        }
        return game;
    }

    /**
     * remove game according to id
     *
     * @param id game identifier
     * @return if operation was successful
     * @throws ResourceNotFoundException
     * @throws InvalidParameterException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean removeGame(@PathVariable("id") Long id) throws ResourceNotFoundException, InvalidParameterException {
        logger.debug("rest removeGame({})", id);
        try {
            return gameFacade.remove(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException();
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * getting game according to name
     *
     * @param name player name
     * @return GameDTO
     * @throws InvalidParameterException
     */
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<GameDTO> findGameByName(@PathVariable("name") String name) throws InvalidParameterException {
        logger.debug("rest findGameByName({})", name);
        List<GameDTO> games = new ArrayList<>();
        try {
            games = gameFacade.findByName(name);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException();
        }
        return games;
    }

    /**
     * create game
     *
     * @param game game's name
     * @return GameDTO
     * @throws ResourceAlreadyExistingException
     * @throws InvalidParameterException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final GameDTO createGame(@RequestBody GameDTO game) throws ResourceAlreadyExistingException, InvalidParameterException {
        logger.debug("rest createGame()");
        try {
            return gameFacade.create(game.getName());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException();
        } catch (Exception e) {
            throw new ResourceAlreadyExistingException();
        }
    }
}
