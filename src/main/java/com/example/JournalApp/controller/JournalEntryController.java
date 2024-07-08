package com.example.JournalApp.controller;


import com.example.JournalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Journal")
public class JournalEntryController {

    private Map<String, JournalEntry> journalEntryMap = new HashMap<>();

@GetMapping("/GetAll")
    public List<JournalEntry> getAll() {
    return new ArrayList<>(journalEntryMap.values());
    }

    @PostMapping("/CreateData")
    public boolean Create(@RequestBody JournalEntry journalEntry) {
    journalEntryMap.put(String.valueOf(journalEntry.getId()),journalEntry);
    return true;
    }

    @GetMapping("/Get/{id}")
    public JournalEntry getById(@PathVariable Long id) {
    return journalEntryMap.get(id);
    }

    @DeleteMapping("/Delete/{id}")
    public boolean delete(@PathVariable Long id) {
    journalEntryMap.remove(id);
    return true;
    }

    @PutMapping("/update/{id}")
    public JournalEntry update(@RequestBody JournalEntry journalEntry, @PathVariable Long id) {
    return journalEntryMap.put(String.valueOf(id),journalEntry);
    }

}
