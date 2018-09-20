package pl.coderslab.bettingsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Role;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.entity.Wallet;
import pl.coderslab.bettingsite.repository.RoleRepository;
import pl.coderslab.bettingsite.repository.UserRepository;
import pl.coderslab.bettingsite.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private WalletRepository walletRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.walletRepository = walletRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        BigDecimal zeroBalance = new BigDecimal(0);
        Wallet wallet = new Wallet(zeroBalance, user);
//        walletRepository.save(wallet);
        user.setWallet(wallet);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public User isLoggedIn() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email);
    }

}
