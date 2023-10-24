package com.example.service;

import com.example.entity.Forum;

import java.util.List;
import java.util.Optional;

public interface IForumService {
    //ajout forum
    Forum addForum(Forum forum);

    //supprimer forum
    void deleteForum(Integer id);

    //afficher forum
    List<Forum> fetchForumList();

    Optional<Forum> getPostById(Integer id);

    //modif forum
    void updateForum(Forum forum);
}
