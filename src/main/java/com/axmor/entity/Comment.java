package com.axmor.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "COMMENT", schema = "PUBLIC")
public class Comment {
    private Integer id;
    private Date creationdate;
    private String text;
    private String author;
    private String transition;
    private Issue issuesByIssueid;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CREATIONDATE", nullable = false)
    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    @Basic
    @Column(name = "TEXT", nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "AUTHOR", nullable = false)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "TRANSITION")
    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    @ManyToOne
    @JoinColumn(name = "ISSUEID", referencedColumnName = "ID", nullable = false)
    public Issue getIssuesByIssueid() {
        return issuesByIssueid;
    }

    public void setIssuesByIssueid(Issue issuesByIssueid) {
        this.issuesByIssueid = issuesByIssueid;
    }

    @Transient
    public String getFormattedDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format( getCreationdate() );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment that = (Comment) o;

        if (id != that.id) return false;
        if (creationdate != null ? !creationdate.equals(that.creationdate) : that.creationdate != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (creationdate != null ? creationdate.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", creationdate=" + creationdate +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", transition='" + transition + '\'' +
                '}';
    }
}
