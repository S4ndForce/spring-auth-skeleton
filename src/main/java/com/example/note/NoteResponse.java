package com.example.note;

import com.example.tag.Tag;
import com.example.user.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public class NoteResponse {

    private Long id;
    private String content;
    private String userName;
    private Long folderId;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<String> tags;



    public NoteResponse(){

    }

    public Long getId() {
        return id;
    }
    public Long getFolderId() {
        return folderId;
    }
    public String getContent() {
        return content;
    }
    public String getUserName() {
        return userName;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public Set<String> getTags() {
        return tags;
    }




    public static NoteResponse fromEntity(Note note) {
        NoteResponse r = new NoteResponse();
        r.id = note.getId();
        r.content = note.getContent();
        r.userName = note.getOwner().getEmail();
        r.folderId = note.getFolder().getId();
        r.createdAt = note.getCreatedAt();
        r.updatedAt = note.getUpdatedAt();
        r.tags = note.getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
        return r;
    }


    public void setId(Long id) {
        this.id = id;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setUserName(User user) {
        userName = user.getEmail();
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }


}
