package cz.fi.muni.pa165.yellow_yak.facade;

import cz.fi.muni.pa165.yellow_yak.dto.ScoreDTO;
import cz.fi.muni.pa165.yellow_yak.entity.Competition;
import cz.fi.muni.pa165.yellow_yak.entity.Score;
import cz.fi.muni.pa165.yellow_yak.service.BeanMappingService;
import cz.fi.muni.pa165.yellow_yak.service.CompetitionService;
import cz.fi.muni.pa165.yellow_yak.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation for score facade
 *
 * @author Matej Horniak
 */
@Service
@Transactional
public class ScoreFacadeImpl implements ScoreFacade {

    final static Logger log = LoggerFactory.getLogger(ScoreFacadeImpl.class);

    private final ScoreService scoreService;

    private final CompetitionService competitionService;

    private final BeanMappingService beanMappingService;

    @Autowired
    public ScoreFacadeImpl(BeanMappingService beanMappingService, CompetitionService competitionService, ScoreService scoreService) {
        this.beanMappingService = beanMappingService;
        this.competitionService = competitionService;
        this.scoreService = scoreService;
    }

    @Override
    public List<ScoreDTO> findByGamePlayerDate(Long playerId, Long gameId, LocalDate oldest) {
        if (playerId == null) {
            throw new IllegalArgumentException("playerId is null");
        }
        if (gameId == null) {
            throw new IllegalArgumentException("gameId is null");
        }
        if (playerId <= 0 || gameId <= 0) {
            throw new IllegalArgumentException("invalid argument");
        }
        List<Competition> competitions = competitionService.findByGame(gameId);
        log.info("Get competition by gameId(gameId=" + gameId +
                ", competition size=" + competitions.size() + ")");
        LocalDate createAt = (oldest == null ) ?  competitionService.findOldestCompetition() : oldest;
        List<Score> scores = scoreService.findByPlayerAndCompetitionAndDate(
                playerId,
                competitions,
                createAt);
        log.info("Find all scores with PlayerId(playerId=" + playerId +
                ", scores size=" + scores.size() + ")");
        return beanMappingService.mapTo(scores, ScoreDTO.class);
    }

    @Override
    public ScoreDTO create(Long competitionId, Long playerId) {
        if (playerId == null) {
            throw new IllegalArgumentException("playerId is null");
        }
        if (competitionId == null) {
            throw new IllegalArgumentException("competitionId is null");
        }
        if (competitionId <= 0 || playerId <= 0) {
            throw new IllegalArgumentException("invalid argument");
        }
        log.info("create score, competitionId = {}, playerId = {}", competitionId, playerId);
        return beanMappingService.mapTo(scoreService.create(competitionId, playerId), ScoreDTO.class);
    }

    @Override
    public boolean remove(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("id cannot be zero or negative value");
        }
        log.info("removing score, id = {}", id);
        return scoreService.remove(id);
    }

    @Override
    public ScoreDTO setResult(Long id, String result) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        if (id <= 0 || result.isEmpty()) {
            throw new IllegalArgumentException("invalid argument");
        }
        log.info("setting result, id = {}, result = {}", id, result);
        return beanMappingService.mapTo(scoreService.setResult(id, result), ScoreDTO.class);
    }

    @Override
    public ScoreDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("id cannot be zero or negative value");
        }
        log.info("finding player by ID, id = {}", id);
        return beanMappingService.mapTo(scoreService.findById(id), ScoreDTO.class);
    }

    @Override
    public List<ScoreDTO> findByPlayerGame(Long playerId, Long gameId) {
        if (playerId == null) {
            throw new IllegalArgumentException("playerId is null");
        }
        if (gameId == null) {
            throw new IllegalArgumentException("gameId is null");
        }
        if (gameId <= 0 || playerId <= 0) {
            throw new IllegalArgumentException("invalid argument");
        }
        log.info("finding score by ID, playerId = {}, gameId = {}", playerId, gameId);
        return beanMappingService.mapTo(scoreService.findByPlayerAndGame(playerId, gameId), ScoreDTO.class);
    }

    @Override
    public List<ScoreDTO> findByCompetition(Long competitionId) {
        if (competitionId == null) {
            throw new IllegalArgumentException("competitionId is null");
        }
        if (competitionId <= 0) {
            throw new IllegalArgumentException("id cannot be zero or negative value");
        }
        log.info("finding score by ID, competitionId = {}", competitionId);
        return beanMappingService.mapTo(scoreService.findByCompetition(competitionId), ScoreDTO.class);
    }
}
