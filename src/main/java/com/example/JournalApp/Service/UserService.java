package com.example.JournalApp.Service;



import com.example.JournalApp.Repository.UserRepository;
import com.example.JournalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


   // private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public boolean save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER", "ADMIN"));
            userRepository.save(user);
           // logger.info("aaaaaa");
            log.error("Error");
            log.warn("Warn");
            log.info("Info");
            log.debug("Debug");
            log.trace("Trace");
            return true;
        }catch (Exception e){
            //logger.error("Error occurred for {} : ",user.getUserName(),e);
            log.error("Error occurred for {} : ",user.getUserName(),e);
            return false;
        }

    }

    public boolean saveUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
           // logger.info("abc");
            log.error("Error");
            log.warn("Warn");
            log.info("Info");
            log.debug("Debug");
            log.trace("Trace");
            return true;
        }catch (Exception e){
            //logger.info("abc");
            log.error("Error occurred for {} : ",user.getUserName(),e);
            return false;
        }
    }



    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);

    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }


    public User findByUsername(String username) {
        return userRepository.findByuserName(username);
    }
}
