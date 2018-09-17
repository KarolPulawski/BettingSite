package pl.coderslab.bettingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.bettingsite.entity.Odd;

@Repository
public interface OddRepository extends JpaRepository<Odd, Integer> {

}
