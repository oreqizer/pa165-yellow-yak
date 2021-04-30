package cz.fi.muni.pa165.yellow_yak.facade;

import cz.fi.muni.pa165.yellow_yak.dto.PlayerDTO;
import cz.fi.muni.pa165.yellow_yak.entity.Score;
import cz.fi.muni.pa165.yellow_yak.service.BeanMappingService;
import cz.fi.muni.pa165.yellow_yak.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author oreqizer
 */
@Service
@Transactional
public class PlayerFacadeImpl implements PlayerFacade {

    final static Logger log = LoggerFactory.getLogger(ScoreFacadeImpl.class);

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private PlayerService playerService;

    @Override
    public PlayerDTO create(String name, String email) {
        if (name == null || email == null) {
            throw new NullPointerException("arguments cannot be null");
        }
        log.info("creating player, name = {}, email = {}", name, email);
        return beanMappingService.mapTo(playerService.create(name, email), PlayerDTO.class);
    }

    @Override
    public void remove(Long id) {
        if (id == null) {
            throw new NullPointerException("arguments cannot be null");
        }
        log.info("removing player, id = {}", id);
        playerService.remove(id);
    }

    @Override
    public PlayerDTO findById(Long id) {
        if (id == null) {
            throw new NullPointerException("arguments cannot be null");
        }
        log.info("finding player by ID, id = {}", id);
        return beanMappingService.mapTo(playerService.findById(id), PlayerDTO.class);
    }

    @Override
    public List<PlayerDTO> findByUsername(String username) {
        if (username == null) {
            throw new NullPointerException("arguments cannot be null");
        }
        log.info("listing player by username, username = {}", username);
        return beanMappingService.mapTo(playerService.findByUsername(username), PlayerDTO.class);
    }

    @Override
    public List<PlayerDTO> findByTeam(Long teamId) {
        if (teamId == null) {
            throw new NullPointerException("arguments cannot be null");
        }
        log.info("listing player by team, teamId = {}", teamId);
        return beanMappingService.mapTo(playerService.findByTeam(teamId), PlayerDTO.class);
    }

    @Override
    public List<PlayerDTO> findAll() {
        log.info("Get all Player");
        return beanMappingService.mapTo(playerService.findAll(), PlayerDTO.class);
    }
}