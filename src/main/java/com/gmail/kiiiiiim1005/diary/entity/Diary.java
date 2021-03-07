package com.gmail.kiiiiiim1005.diary.entity;

import javax.persistence.*;

@Entity
@Table
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User writer;

    @Column(nullable = false, columnDefinition="LONGTEXT")
    private String contents;

    @Column(nullable = false)
    private String title;

    private boolean isPublic = false;
    private long createdTime = System.currentTimeMillis();

    public Diary(){}

    public Diary(User writer, String title, String contents, boolean isPublic) {
        this.writer = writer;
        this.title=  title;
        this.contents = contents;
        this.isPublic = isPublic;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }



}
