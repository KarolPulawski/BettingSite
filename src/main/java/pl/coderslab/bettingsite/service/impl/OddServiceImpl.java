package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Odd;
import pl.coderslab.bettingsite.repository.OddRepository;
import pl.coderslab.bettingsite.service.OddService;

@Service
public class OddServiceImpl implements OddService {

    @Autowired
    private OddRepository oddRepository;

    @Override
    public void addNewOdd(Odd odd) {
        oddRepository.save(odd);
    }
}
