package cz.fi.muni.pa165.yellow_yak;

import cz.fi.muni.pa165.yellow_yak.entity.Team;
import cz.fi.muni.pa165.yellow_yak.persistance.TeamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * Tests for team DAO
 *
 * @author Matej Knazik
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TeamDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private Team testTeam;

    @Autowired
    private TeamDao teamDao;

    @BeforeMethod
    private void beforeEach() {
        EntityManager em = entityManagerFactory.createEntityManager();

        Team testTeam1 = new Team();
        testTeam1.setCreatedAt(LocalDate.now());
        testTeam1.setName("Test Team" + new Random().nextInt());
        Team testTeam2 = new Team();
        testTeam2.setCreatedAt(LocalDate.now().minusDays( 1 ));
        testTeam2.setName("Test Team" + + new Random().nextInt());

        teamDao.create(testTeam1);
        teamDao.create(testTeam2);
        testTeam = testTeam1;
    }

    @AfterMethod
    private void remove() {
        teamDao.remove(testTeam.getId());
    }


    @Test(expectedExceptions = PersistenceException.class)
    public void createTeamTest() {
        Assert.assertTrue(teamDao.findAll().size()>0);
        teamDao.create(testTeam);
    }

    @Test
    public void updateTeamTest() {
        String nameBefore = testTeam.getName();
        String nameAfter = "Awesome team";

        Assert.assertEquals(teamDao.findById(testTeam.getId()).getName(), nameBefore);
        testTeam.setName(nameAfter);
        teamDao.update(testTeam);

        Team changedTestTeam = teamDao.findById(testTeam.getId());
        Assert.assertNotNull(changedTestTeam);
        Assert.assertEquals(changedTestTeam.getName(), nameAfter);
        Assert.assertEquals(testTeam, changedTestTeam);
    }

    @Test
    public void removeTeamTest() {
        Team team = new Team();
        team.setCreatedAt(LocalDate.now());
        team.setName("TestTeamRemove");
        teamDao.create(team);

        int teamsCountBefore = teamDao.findAll().size();
        teamDao.remove(team.getId());
        Assert.assertEquals(teamsCountBefore - 1,teamDao.findAll().size());

        Team removedTestTeam = teamDao.findById(team.getId());
        Assert.assertNull(removedTestTeam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeTeamTestNull() {
        teamDao.remove(null);
    }

    @Test
    public void getAllTeamsTest() {
        List<Team> teamList = teamDao.findAll();
        Assert.assertNotNull(teamList);
        Assert.assertTrue(teamList.size() >= 2);

    }

    @Test
    public void getTeamByIdTest() {
        Team desiredTeam = teamDao.findById(testTeam.getId());
        Assert.assertNotNull(desiredTeam);
        Assert.assertEquals(testTeam, desiredTeam);
    }

    @Test
    public void getTeamByNameTest() {
        List<Team> teamList = teamDao.findByName(testTeam.getName());
        Assert.assertNotNull(teamList);
        Assert.assertEquals(teamList.size(), 1);

        Assert.assertEquals(teamList.get(0), testTeam);
    }

    @Test
    public void getTeamByCreatedAtTest() {
        Team newTeam = new Team();
        newTeam.setCreatedAt(testTeam.getCreatedAt());
        newTeam.setName("Test Team3");
        teamDao.create(newTeam);

        List<Team> teamList = teamDao.findByCreatedAt(testTeam.getCreatedAt());
        Assert.assertNotNull(teamList);
        Assert.assertEquals(teamList.size(), 2);
        Assert.assertEquals(teamList, List.of(testTeam,newTeam));
    }


}
