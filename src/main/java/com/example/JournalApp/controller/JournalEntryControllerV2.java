package com.example.JournalApp.controller;



import com.example.JournalApp.Service.JournalEntrySerive;
import com.example.JournalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntrySerive journalEntrySerive;

    @GetMapping("/GetAll1")
    public ResponseEntity<?> getAll() {
      List<JournalEntry>list =  journalEntrySerive.GetAll();
      if(list.isEmpty())
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      else
          return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/Create")
    public ResponseEntity <JournalEntry> Create(@RequestBody JournalEntry journalEntry) {
       try{
           journalEntrySerive.Save(journalEntry);
           return ResponseEntity.ok(journalEntry);

       }
        catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/Get1/{id}")
    public ResponseEntity <JournalEntry> getById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry =journalEntrySerive.GetById(id);
        if (journalEntry.isPresent()) {
            return ResponseEntity.ok(journalEntry.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/Update1/{id}")
    public ResponseEntity<?> update(@RequestBody JournalEntry journalEntry, @PathVariable ObjectId id) {
        JournalEntry journalEntry1 = journalEntrySerive.GetById(id).orElse(null);

        if(journalEntry1 != null) {
            journalEntry1.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().equals("")?journalEntry1.getTitle():journalEntry.getTitle());
            journalEntry1.setContent(journalEntry.getContent() != null && !journalEntry.getContent().equals("")?journalEntry1.getContent():journalEntry.getContent());
            journalEntrySerive.Save(journalEntry1);
            return ResponseEntity.ok(journalEntry);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/Delete1/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id) {
        journalEntrySerive.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/DeleteAll")
    public boolean deleteAll() {
        journalEntrySerive.DeleteAll();
        return true;
    }

}
