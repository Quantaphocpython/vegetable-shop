package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.ecommerce.spring.boot.vegetable.project.entity.VerificationToken;
import org.ecommerce.spring.boot.vegetable.project.repository.UserRepository;
import org.ecommerce.spring.boot.vegetable.project.repository.VerificationTokenRepository;
import org.ecommerce.spring.boot.vegetable.project.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class VerificationTokenServiceImp implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public String validateVerificationToken(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isEmpty())
            return "invalid";
        User user = verificationToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if(verificationToken.get().getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken.get());
            return "expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}
