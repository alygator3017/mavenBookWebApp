package edu.wctc.ajs.bookwebapp.model;

import java.util.Date;

/**
 *
 * @author Alyson
 */
public class Author {
    private int authorId;
    private String authorName;
    private Date dateAdded;

    public Author() {
    }
    public Author(int authorId, String authorName){
        if(authorId <= 0 || authorName.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateAdded = new Date();
    }
    public Author(int authorId) {
        if(authorId <= 0){
            throw new IllegalArgumentException();
        }
        this.authorId = authorId;
    }
    
    public final int getAuthorId() {
        return authorId;
    }

    public final void setAuthorId(int authorId) {
        if(authorId <= 0){
            throw new IllegalArgumentException();
        }
        this.authorId = authorId;
    }

    public final String getAuthorName() {
        return authorName;
    }

    public final void setAuthorName(String authorName) {
        if(authorName.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.authorName = authorName;
    }

    public final Date getDateAdded() {
        return dateAdded;
    }

    public final void setDateAdded(Date dateAdded) {
        if(dateAdded == null){
            throw new IllegalArgumentException();
        }
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.authorId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.authorId != other.authorId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorId=" + authorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }

    
    
}
