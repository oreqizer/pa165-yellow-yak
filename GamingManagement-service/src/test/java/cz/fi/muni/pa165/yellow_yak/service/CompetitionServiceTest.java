package cz.fi.muni.pa165.yellow_yak.service;

import cz.fi.muni.pa165.yellow_yak.config.ServiceConfiguration;
import cz.fi.muni.pa165.yellow_yak.entity.Competition;
import cz.fi.muni.pa165.yellow_yak.entity.Game;
import cz.fi.muni.pa165.yellow_yak.persistance.CompetitionDao;
import cz.fi.muni.pa165.yellow_yak.persistance.GameDao;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Tests for competition service layer
 *
 * @author matho
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CompetitionServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CompetitionDao competitionDao;
    @Mock
    private GameDao gameDao;

    @Autowired
    @InjectMocks
    private CompetitionService competitionService;

    private Competition competition;

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setup() {
        Game game = new Game();
        game.setName("TestGame");

        competition = new Competition();
        competition.setId(1L);
        competition.setGame(game);
        competition.setName("TestNameCompetition");
        competition.setPrices("TestPrices");
        competition.setCreatedAt(LocalDate.now());
        competition.setStartedAt(LocalDate.now());
    }

    @Test
    public void createCompetition() {
        Game game = new Game();
        game.setId(1337L);
        game.setName("Battlefield 6");

        Competition c = new Competition();
        c.setName("ZergFest");
        c.setGame(game);
        c.setCreatedAt(LocalDate.now());
        c.setStartedAt(LocalDate.now());

        Competition res = competitionService.create(game.getId(), c.getName());

        Assert.assertEquals(res, c);
    }

    @Test
    public void removeCompetition() {
        competitionService.remove(competition.getId());
    }

    @Test
    public void findByIdCompetition() {
        Mockito.doReturn(competition).when(competitionDao).findById(competition.getId());

        Competition res = competitionService.findById(competition.getId());

        Assert.assertEquals(competition, res);
    }

    @Test
    public void findByGameTest() {
        Game game = new Game();
        game.setId(1337L);
        game.setName("Battlefield 6");

        when(gameDao.findById(game.getId())).thenReturn(game);
        when(competitionDao.findByGame(game)).thenReturn(Collections.singletonList(competition));

        List<Competition> competitionList = competitionService.findByGame(game.getId());

        Assert.assertNotNull(competitionList);
        Assert.assertEquals(competitionList.size(), 1);
        Competition result = competitionList.get(0);
        Assert.assertEquals(result, competition);
    }

    @Test
    public void findAll() {
        when(competitionDao.findAll()).thenReturn(Collections.singletonList(competition));
        List<Competition> competitionTestList = competitionService.findAll();

        Assert.assertNotNull(competitionTestList);
        Assert.assertEquals(competitionTestList.size(), 1);
        Competition competitionTest = competitionTestList.get(0);
        Assert.assertEquals(competitionTest, competition);
    }

    @Test
    public void findByGameTestNullId() {
        when(competitionDao.findByGame(null)).thenReturn(Collections.emptyList());

        List<Competition> competitionList = competitionService.findByGame(null);

        Assert.assertNotNull(competitionList);
        Assert.assertEquals(competitionList.size(), 0);
    }

    @Test
    public void findOldestTest() {
        when(competitionDao.findOldest()).thenReturn(competition);
        when(competitionDao.findAll()).thenReturn(Collections.singletonList(competition));

        LocalDate oldestCompetition = competitionService.findOldestCompetition();

        Assert.assertNotNull(oldestCompetition);
        Assert.assertEquals(oldestCompetition, competition.getCreatedAt());
    }

    @Test
    public void findOldestTestNullCompetitions() {
        when(competitionDao.findAll()).thenReturn(Collections.emptyList());

        LocalDate oldestCompetition = competitionService.findOldestCompetition();

        Assert.assertNull(oldestCompetition);
    }
}
