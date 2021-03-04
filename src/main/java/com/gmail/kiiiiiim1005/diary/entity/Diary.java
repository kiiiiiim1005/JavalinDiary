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

    @Column(nullable = false)
    private String contents;

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

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                '}';
    }
}
