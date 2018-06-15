package com.axmor.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "ISSUE", schema = "PUBLIC")
public class Issue {

    private Integer id;
    private String title;
    private String state;
    private Date creationdate;
    private String description;
    private String author;
    private Collection<Comment> commentsById;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "STATE", nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "AUTHOR", nullable = false)
    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATIONDATE", nullable = false)
    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "issuesByIssueid", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Collection<Comment> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<Comment> commentsById) {

        commentsById.forEach(comment -> comment.setIssuesByIssueid(this));
        this.commentsById = commentsById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue that = (Issue) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (creationdate != null ? !creationdate.equals(that.creationdate) : that.creationdate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (creationdate != null ? creationdate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssuesEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", creationdate=" + creationdate +
                ", description='" + description + '\'' +
                ", commentsById=" + commentsById +
                '}';
    }
}
