package pl.coderslab.bettingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.bettingsite.entity.Ticket;
import pl.coderslab.bettingsite.entity.User;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByUserAndActiveTrueAndWinFalse(User user);

    Ticket findOneById(int id);
}
