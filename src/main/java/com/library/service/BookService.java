package com.library.service;

import com.library.entity.Book;
import com.library.exception.BookNotFoundException;
import com.library.exception.NoAvailableCopiesException;
import com.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.library.constant.ExceptionConstant.BOOK_NOT_FOUND;
import static com.library.constant.ExceptionConstant.NO_AVAILABLE_COPIES;
import static com.library.constant.ExceptionConstant.NO_BORROWED_COPIES;
import static com.library.util.BookUtils.getFormattedMessage;

@Service
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Constructs a new BookService with the given BookRepository.
     *
     * @param bookRepository the BookRepository used for interacting with the database
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books from the library.
     *
     * @return a list of all books
     */
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID. Throws a BookNotFoundException if no book is found.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the given ID
     * @throws BookNotFoundException if the book with the given ID is not found
     */
    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(getFormattedMessage(BOOK_NOT_FOUND, id)));
    }

    /**
     * Saves a new book or updates an existing book.
     *
     * @param book the book to save or update
     * @return the saved or updated book
     */
    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Deletes a book by its ID. Throws a BookNotFoundException if no book is found.
     *
     * @param id the ID of the book to delete
     * @throws BookNotFoundException if the book with the given ID is not found
     */
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(getFormattedMessage(BOOK_NOT_FOUND, id));
        }
        bookRepository.deleteById(id);
    }

    /**
     * Borrows a book by its ID. Increments the borrowedCopies if there are available copies.
     * Throws a NoAvailableCopiesException if there are no available copies.
     *
     * @param id the ID of the book to borrow
     * @throws NoAvailableCopiesException if no copies are available to borrow
     */
    @Transactional
    public void borrowBook(Long id) {
        Book book = findBookById(id);
        if (book.getBorrowedCopies() < book.getTotalCopies()) {
            book.setBorrowedCopies(book.getBorrowedCopies() + 1);
            bookRepository.save(book);
        } else {
            throw new NoAvailableCopiesException(getFormattedMessage(NO_AVAILABLE_COPIES, id));
        }
    }

    /**
     * Returns a borrowed book by its ID. Decrements the borrowedCopies if there are borrowed copies.
     * Throws a NoAvailableCopiesException if no copies have been borrowed.
     *
     * @param id the ID of the book to return
     * @throws NoAvailableCopiesException if no borrowed copies exist to return
     */
    @Transactional
    public void returnBook(Long id) {
        Book book = findBookById(id);
        if (book.getBorrowedCopies() > 0) {
            book.setBorrowedCopies(book.getBorrowedCopies() - 1);
            bookRepository.save(book);
        } else {
            throw new NoAvailableCopiesException(getFormattedMessage(NO_BORROWED_COPIES, id));
        }
    }

    /**
     * Updates the details of an existing book. If the book with the given ID is found, the book's fields
     * are updated and saved. If the book is not found, a 404 (Not Found) response is returned.
     *
     * @param id the ID of the book to update
     * @param updatedBook the updated book object with new details
     * @return a ResponseEntity containing the updated book if successful, or a 404 response if not found
     */
    public Book updateBook(Long id, Book updatedBook) {
        // Fetch the existing book
        Book existingBook = findBookById(id);

        // Update the fields
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setTotalCopies(updatedBook.getTotalCopies());

        // Save the updated book
        return saveBook(existingBook);
    }
}