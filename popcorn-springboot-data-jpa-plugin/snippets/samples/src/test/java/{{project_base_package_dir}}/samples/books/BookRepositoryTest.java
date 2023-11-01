package {{project_base_package}}.samples.books;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.iterable;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should save a book")
    void t1() {
        // scenario
        Book book = new Book("9788550800653", "Domain-Driven Design", "DDD - The blue book");

        // action
        repository.save(book);

        // validation
        assertThat(repository.findAll())
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(book)
                ;
    }

    @Test
    @DisplayName("should not save a book with invalid parameters")
    void t2() {
        // scenario
        Book book = new Book("97885-invalid", "a".repeat(121), "");

        // action and validation
        assertThatThrownBy(() -> {
            repository.save(book);
        })
                .isInstanceOf(TransactionSystemException.class)
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .getRootCause()
                .extracting("constraintViolations", as(iterable(ConstraintViolation.class)))
                .extracting(
                        t -> t.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )
                .containsExactlyInAnyOrder(
                        tuple("isbn", "invalid ISBN"),
                        tuple("title", "size must be between 0 and 120"),
                        tuple("description", "must not be blank")
                )
        ;
        // Tip: Try always to verify the side effects
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("should not save a book when a book with same isbn already exists")
    void t3() {
        // scenario
        String isbn = "9788550800653";
        Book ddd = new Book(isbn, "Domain-Driven Design", "DDD - The blue book");

        // action
        repository.save(ddd);

        // validation
        assertThrows(DataIntegrityViolationException.class, () -> {
            Book cleanCode = new Book(isbn, "Clean Code", "Learn how to write clean code with Uncle Bob");
            repository.save(cleanCode);
        });
        // Tip: Try always to verify the side effects
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("should find a book by isbn")
    void t4() {
        // scenario
        String isbn = "9788550800653";
        Book book = new Book(isbn, "Domain-Driven Design", "DDD - The blue book");
        repository.save(book);

        // action
        Optional<Book> optionalBook = repository.findByIsbn(isbn);

        // validation
        assertThat(optionalBook)
                .isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(book)
                ;
    }

    @Test
    @DisplayName("should not find a book by isbn")
    void t5() {
        // scenario
        Book book = new Book("9788550800653", "Domain-Driven Design", "DDD - The blue book");
        repository.save(book);

        // action
        String notExistingIsbn = "1234567890123";
        Optional<Book> optionalBook = repository.findByIsbn(notExistingIsbn);

        // validation
        assertThat(optionalBook).isEmpty();
    }

}