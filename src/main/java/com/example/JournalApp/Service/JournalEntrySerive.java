package com.example.JournalApp.Service;


import com.example.JournalApp.Repository.JournalEntryRepository;
import com.example.JournalApp.Repository.UserRepository;
import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Service
@Slf4j
public class JournalEntrySerive {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntrySerive.class);


    public void Save(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }



    @Transactional
    //this method JournalUserController
    public void Save1(JournalEntry journalEntry,String userName) {

        try {
            User user = userService.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry journalEntry1 = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(journalEntry1);
            userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Something went wrong",e);
        }

    }


    public List<JournalEntry> GetAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> GetById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

   public void DeleteById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

    @Transactional

    //this method JournalUserController
    public void DeleteById1(ObjectId id,String userName) {
        try {
             User user = userService.findByUsername(userName);
             boolean remove = user.getJournalEntries().remove(journalEntryRepository.findById(id).get());
             if (remove) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch (Exception e){
            log.error("Error deleting journal entry",e);
             throw new RuntimeException("Something went wrong",e);
    }
    }


    public void DeleteAll() {
        journalEntryRepository.deleteAll();
    }

    public List<JournalEntry> FindByUserName(String userName) {
        return userService.findByUsername(userName).getJournalEntries();
    }
}
