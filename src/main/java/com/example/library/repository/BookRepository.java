package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.publicationDate BETWEEN :start AND :end")
    List<Book> findAllByPublicationDateLessThanEqualAndPublicationDateGreaterThanEqual(@Param("start") LocalDate start,@Param("end") LocalDate end);
    @Query("SELECT b FROM Book b WHERE b.publicationDate > :start")
    List<Book> findAllByPublicationDateGreaterThan(@Param("start") LocalDate start);
    @Query("SELECT b FROM Book b WHERE b.publicationDate < :end")
    List<Book> findAllByPublicationDateLessThanEqual(@Param("end")LocalDate end);

//    Map<Integer, Book> findAll();
//    Book addBook(Book book);
//    void deleteBook(int id);
//    Book findBookById(int id);
//    Map<Integer, Book> findBookBetweenYears(Optional<Integer> startYear, Optional<Integer> endYear);
//    void deleteAllBooks();

}
