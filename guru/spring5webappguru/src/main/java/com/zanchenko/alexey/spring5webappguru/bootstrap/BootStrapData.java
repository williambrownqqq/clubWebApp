package com.zanchenko.alexey.spring5webappguru.bootstrap;

import com.zanchenko.alexey.spring5webappguru.model.Author;
import com.zanchenko.alexey.spring5webappguru.model.Book;
import com.zanchenko.alexey.spring5webappguru.model.Publisher;
import com.zanchenko.alexey.spring5webappguru.repositories.AuthorRepository;
import com.zanchenko.alexey.spring5webappguru.repositories.BookRepository;
import com.zanchenko.alexey.spring5webappguru.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    // dependency injection in conctructor
    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootstrap");
        Publisher publisher = new Publisher();
        publisher.setAddress("Ua");
        publisher.setCity("Kiev");
        publisher.setZip("54345");
        publisher.setName("alexPublic");

        publisherRepository.save(publisher);
        System.out.println("Publisher count: " + publisherRepository.count());

        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Adventure","5434543");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        ddd.setPublisher(publisher);
        publisher.getBooks().add(ddd);


        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(publisher);

        Author alex = new Author("Alex", "Zanchenko");
        Book bbb = new Book("Love","98765445");
        alex.getBooks().add(bbb);
        bbb.getAuthors().add(alex);

        bbb.setPublisher(publisher);
        publisher.getBooks().add(bbb);

        authorRepository.save(alex);
        bookRepository.save(bbb);
        publisherRepository.save(publisher);

        System.out.println("Number of Books: " + bookRepository.count());
        System.out.println("Publisher Number of Books: " + publisher.getBooks().size());
    }
}
