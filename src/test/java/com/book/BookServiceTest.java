package com.book;

import com.library.entity.Book;
import com.library.exception.BookNotFoundException;
import com.library.exception.NoAvailableCopiesException;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.library.constant.ExceptionConstant.BOOK_NOT_FOUND;
import static com.library.constant.ExceptionConstant.NO_AVAILABLE_COPIES;
import static com.library.constant.ExceptionConstant.NO_BORROWED_COPIES;
import static com.library.constant.TestConstant.BOOK_TITLE_MATCH;
import static com.library.constant.TestConstant.RETURN_BOOK_SHOULD_NOT_NULL;
import static com.library.util.BookUtils.getFormattedMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void testFindBookById_Success() {
        // Arrange
        String bookTitle = "Test Book";
        Book book = new Book();
        book.setId(1L);
        book.setTitle(bookTitle);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Simulate
        Book result = bookService.findBookById(1L);

        // Assertions
        assertNotNull(result, RETURN_BOOK_SHOULD_NOT_NULL);
        assertEquals(bookTitle, result.getTitle(), BOOK_TITLE_MATCH);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testFindBookById_ThrowsResourceNotFoundException() {
        // Arrange
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Simulate
        Exception exception = assertThrows(BookNotFoundException.class, () -> bookService.findBookById(id));

        // Assertions
        assertEquals(getFormattedMessage(BOOK_NOT_FOUND, id), exception.getMessage());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void testBorrowBook_Success() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTotalCopies(5);
        book.setBorrowedCopies(3);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Simulate
        bookService.borrowBook(1L);

        // Assertions
        assertEquals(4, book.getBorrowedCopies());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testBorrowBook_ThrowsBookNotFoundException() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTotalCopies(2);
        book.setBorrowedCopies(2);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Simulate
        Exception exception = assertThrows(NoAvailableCopiesException.class, () -> bookService.borrowBook(1L));

        // Assertions
        assertEquals(getFormattedMessage(NO_AVAILABLE_COPIES, book.getId()), exception.getMessage());
        verify(bookRepository, never()).save(book);
    }

    @Test
    void testReturnBook_Success() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTotalCopies(5);
        book.setBorrowedCopies(3);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Simulate
        bookService.returnBook(1L);

        // Assertions
        assertEquals(2, book.getBorrowedCopies());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testReturnBook_ThrowsBookNotFoundException() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTotalCopies(5);
        book.setBorrowedCopies(0);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Simulate
        Exception exception = assertThrows(NoAvailableCopiesException.class, () -> bookService.returnBook(1L));

        // Assertions
        assertEquals(getFormattedMessage(NO_BORROWED_COPIES, book.getId()), exception.getMessage());
        verify(bookRepository, never()).save(book);
    }
}