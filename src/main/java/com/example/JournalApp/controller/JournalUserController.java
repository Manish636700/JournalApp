package com.example.JournalApp.controller;



import com.example.JournalApp.Service.JournalEntrySerive;
import com.example.JournalApp.Service.UserService;
import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/JournalUser")
public class JournalUserController {

    @Autowired
    private JournalEntrySerive journalEntrySerive;

    @Autowired
    private UserService userService;

    @GetMapping("/GetAll")
    public ResponseEntity<?> getAllJournalEntriesofUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
      List<JournalEntry>list = user.getJournalEntries();
      if(list.isEmpty())
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      else
          return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/Create")
    public ResponseEntity <JournalEntry> CreateEntry(@RequestBody JournalEntry journalEntry) {
       try{
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String UserName= authentication.getName();
           journalEntrySerive.Save1(journalEntry,UserName);
           return ResponseEntity.ok(journalEntry);

       }
        catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/Get/{id}")
    public ResponseEntity <JournalEntry> getById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName= authentication.getName();
        User user = userService.findByUsername(UserName);
        List<JournalEntry>list = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!list.isEmpty())
        {
            Optional<JournalEntry> journalEntry =journalEntrySerive.GetById(id);
            if (journalEntry.isPresent()) {
                return ResponseEntity.ok(journalEntry.get());
            }
        }

        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName= authentication.getName();
        journalEntrySerive.DeleteById1(id,UserName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/Update/{id}")
    public ResponseEntity<?> update(@RequestBody JournalEntry journalEntry, @PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName= authentication.getName();
        User user = userService.findByUsername(UserName);
        List<JournalEntry>list = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!list.isEmpty())
        {
            Optional<JournalEntry> journalEntry1 =journalEntrySerive.GetById(id);
            if (journalEntry1.isPresent()) {
                JournalEntry old = journalEntry1.get();
                old.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().equals("")?journalEntry.getTitle():old.getTitle());
                old.setContent(journalEntry.getContent() != null && !journalEntry.getContent().equals("")?journalEntry.getContent():old.getContent());
                journalEntrySerive.Save(old);
                return ResponseEntity.ok(old);

            }
        }
        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/DeleteAll")
    public boolean deleteAll() {
        journalEntrySerive.DeleteAll();
        return true;
    }

}
