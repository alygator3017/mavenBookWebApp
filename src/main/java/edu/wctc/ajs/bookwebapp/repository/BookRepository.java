package edu.wctc.ajs.bookwebapp.repository;


import edu.wctc.ajs.bookwebapp.entity.Book;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jlombardo
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
     @Query("SELECT b FROM Book b  WHERE b.authorId = (:id)")
    public List<Book> findBookByAuthor(@Param("id") Integer id);
}
