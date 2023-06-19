package com.zanchenko.alexey.spring5webappguru.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity // So the first thing they will do is annotate the class with the @Entity annotation.
public class Book {
    //So now this tells Hibernate that this is an entity and we can see that this is upset because we don't have a primary key.
    //Now what we want to do is annotate this as the ID That's how the Javax persistence package.

     // we need to tell Hibernate how it is getting generated.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//And what this means is that the underlying database is going to be providing the generation of this.
    private Long id;
    private String title;
    private String isbn;

    @ManyToOne
    private Publisher publisher;

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name= "author_id"))
    private Set<Author> authors = new HashSet<>();
//    So underneath in the database so we can envision, we're going to have a table for author and table
//
//for book and we are going to use a join table called author book that is going to hold the relationship
//
//between records and the author table and records and the book table.
    // Итак, внизу в базе данных, чтобы мы могли представить,
// у нас будет таблица для автора и таблица для книги,
// и мы собираемся использовать таблицу соединений, называемую авторской книгой,
// которая будет поддерживать отношения между записями и таблицей author и записями и таблицей book.


    // And what thiss mapping is doing this is setting up the properties within the JoinTable.
    // И то, что делает это сопоставление, - это настройка свойств в JoinTable.
    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
