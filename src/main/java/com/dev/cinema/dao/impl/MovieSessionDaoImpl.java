package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl implements MovieSessionDao {

    private static final Logger logger = Logger.getLogger(MovieSessionDaoImpl.class);
    private final SessionFactory sessionFactory;

    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from MovieSession ms where "
                    + "ms.id = :movieId and ms.showTime between :start and :end");
            query.setParameter("movieId", movieId);
            query.setParameter("start", date.atTime(LocalTime.MIN));
            query.setParameter("end", date.atTime(LocalTime.MAX));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Cant get movie sessions by movie id: " + movieId + " and date: " + date, e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            logger.info("Movie session was created" + movieSession);
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Cant add movie session with movie" + movieSession.getMovie()
                            + " to the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
