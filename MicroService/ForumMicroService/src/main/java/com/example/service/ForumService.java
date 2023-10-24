package com.example.service;

import com.example.entity.Forum;
import com.example.repository.ForumRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ForumService implements IForumService{
    @Autowired
    ForumRepository forumRepository;

    //ajout forum
    @Override
    public Forum addForum(Forum forum) {
        if (!containsProfanity(forum.getPublication())) {
            forum.setDate(LocalDateTime.now());
            return forumRepository.save(forum);
        } else {
            // Handle the case where profanity is found in the publication
            throw new IllegalArgumentException("Publication contains profanity.");
        }
    }
    //supprimer forum
    @Override
    public void deleteForum(Integer id) {

        forumRepository.deleteById(id);
    }

    //afficher forum
    @Override
    public List<Forum> fetchForumList() {
        return (List<Forum>)
                forumRepository.findAll();
    }
    @Override
    public Optional<Forum> getPostById(Integer id) {

        return forumRepository.findById(id);
    }

    //modif forum
    @Override
    public void updateForum(Forum forum) {
        if (!containsProfanity(forum.getPublication())) {
            forumRepository.save(forum);
        } else {
            // Handle the case where profanity is found in the updated publication
            throw new IllegalArgumentException("Publication contains profanity.");
        }
    }


    private final Set<String> badWords;

    public ForumService() {
        this.badWords = new HashSet<>();
        loadBadWords();
    }

    private void loadBadWords() {
        try {
            URL url = new URL("https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv");
            InputStreamReader reader = new InputStreamReader(url.openStream());

            CSVParser csvParser = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : csvParser) {
                badWords.add(record.get(0).toLowerCase());
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., log or throw an error)
            e.printStackTrace();
        }
    }
    private boolean containsProfanity(String text) {
        String lowerText = text.toLowerCase();
        for (String badWord : badWords) {
            if (lowerText.contains(badWord)) {
                return true;
            }
        }
        return false;
    }
}
