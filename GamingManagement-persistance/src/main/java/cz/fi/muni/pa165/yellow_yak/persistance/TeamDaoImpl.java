package cz.fi.muni.pa165.yellow_yak.persistance;

import cz.fi.muni.pa165.yellow_yak.entity.Team;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Implemenation for team DAO
 *
 * @author Matej Knazik
 */
@Repository
@Transactional
public class TeamDaoImpl implements TeamDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Team team) {
        em.persist(team);
    }

    @Override
    public void remove(Long id) {
        em.remove(this.findById(id));
    }

    @Override
    public void update(Team team) {
        em.merge(team);
    }

    @Override
    public List<Team> findAll() {
        return em.createQuery("select team from Team team", Team.class).getResultList();
    }

    @Override
    public Team findById(Long id) {
        return em.find(Team.class, id);
    }

    @Override
    public List<Team> findByName(String name) {
        return em.createQuery("select team from Team team where name = :name", Team.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Team> findByCreatedAt(LocalDate createdAt) {
        return em.createQuery("select team from Team team where createdAt = :createdAt", Team.class)
                .setParameter("createdAt", createdAt)
                .getResultList();
    }
}
