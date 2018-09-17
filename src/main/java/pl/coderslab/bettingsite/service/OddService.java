package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Odd;

@Service
public interface OddService {
    void addNewOdd(Odd odd);
}
