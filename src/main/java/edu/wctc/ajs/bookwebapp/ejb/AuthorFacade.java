/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.bookwebapp.ejb;

import edu.wctc.ajs.bookwebapp.model.Author;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alyson
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "edu.wctc.ajs_bookWebApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }

    public void deleteAuthorById(String id) {
        Author author = this.find(new Integer(id));
        this.remove(author);
    }

    public void updateAuthor(String id, String name, String dateAdded) {
        Author author = new Author();
        if (id == null) {
            author.setAuthorName(name);
            author.setDateAdded(new Date());
        } else {
            //update
            author.setAuthorId(new Integer(id));
            author.setAuthorName(name);
            author.setDateAdded(new Date(dateAdded));
        }
            this.getEntityManager().merge(author);
        

    }
}
