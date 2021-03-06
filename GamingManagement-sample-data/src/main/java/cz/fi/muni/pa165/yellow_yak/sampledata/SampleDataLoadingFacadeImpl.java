package cz.fi.muni.pa165.yellow_yak.sampledata;

import cz.fi.muni.pa165.yellow_yak.entity.*;
import cz.fi.muni.pa165.yellow_yak.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Loads some sample data to populate the eshop database.
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameService gameService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private TeamService teamService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() throws IOException {
        // TODO nacitanie dat

        Team team_1 = team("team_1");
        Team team_2 = team("team_2");
        Team team_3 = team("team_3");
        Team team_4 = team("team_4");
        Team team_5 = team("team_5");

        Player player_1 = player("player_1", "player_1@gmail.com", team_1.getId());
        Player player_2 = player("player_2", "player_2@gmail.com", team_1.getId());
        Player player_3 = player("player_3", "player_3@gmail.com", team_2.getId());
        Player player_4 = player("player_4", "player_4@gmail.com", team_3.getId());
        Player player_5 = player("player_5", "player_5@gmail.com", team_4.getId());

        Game game_1 = game("game_1");
        Game game_2 = game("game_2");
        Game game_3 = game("game_3");
        Game game_4 = game("game_4");
        Game game_5 = game("game_5");

        Competition competition_1 = competition(game_1.getId(), "competition_1");
        Competition competition_2 = competition(game_2.getId(), "competition_2");
        Competition competition_3 = competition(game_3.getId(), "competition_3");
        Competition competition_4 = competition(game_4.getId(), "competition_4");
        Competition competition_5 = competition(game_5.getId(), "competition_5");

        Score score_1 = score(competition_1.getId(), player_1.getId());
        score_1.setResult("1:3");
        Score score_2 = score(competition_2.getId(), player_2.getId());
        score_2.setResult("2:3");
        Score score_3 = score(competition_3.getId(), player_3.getId());
        score_3.setResult("1:6");
        Score score_4 = score(competition_4.getId(), player_4.getId());
        score_4.setResult("4:4");
        Score score_5 = score(competition_5.getId(), player_5.getId());
        score_5.setResult("1:0");
    }

    private Player player(String username, String email, Long teamId) {
        return playerService.create(username, email, teamId);
    }

    private Game game(String name){
        return gameService.create(name);
    }

    private Competition competition(Long gameId, String name){
        return competitionService.create(gameId, name);
    }
    
    private Team team(String name) {
        return teamService.create(name);
    }

    private Score score(Long competitionId, Long playerId) {
        return scoreService.create(competitionId, playerId);
    }
}
