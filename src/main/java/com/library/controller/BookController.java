package com.library.controller;

import com.library.api.ResponseResult;
import com.library.constant.ApiConstant;
import com.library.entity.Book;
import com.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.library.util.BookUtils.getFormattedMessage;

/**
 * REST controller for handling requests related to books.
 * <p>
 * This controller provides endpoints for fetching, saving, updating, deleting, and borrowing/returning books.
 * </p>
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * Constructs a new BookController with the given BookService.
     *
     * @param bookService the BookService used to perform book-related operations
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Fetches all books in the library.
     *
     * @return a list of all books
     */
    @GetMapping("/get")
    public ResponseEntity<ResponseResult> getAllBooks() {
        List<Book> bookList = bookService.findAllBooks();
        ResponseResult result = new ResponseResult.Builder()
                .statusCode(HttpStatus.OK.value())
                .responseMessage(getFormattedMessage(ApiConstant.BOOK_QUERY_ALL, bookList.size()))
                .timestamp(LocalDateTime.now())
                .queryResult(bookList)
                .build();
        return ResponseEntity.ok(result);
    }

    /**
     * Saves a new book in the library.
     *
     * @param book the book to be added
     * @return a ResponseEntity containing the result of the operation
     */
    @PostMapping("/save")
    public ResponseEntity<ResponseResult> addBook(@RequestBody Book book) {
        bookService.saveBook(book);
        ResponseResult result = new ResponseResult.Builder()
                .statusCode(HttpStatus.OK.value())
                .responseMessage(ApiConstant.BOOK_CREATE_SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(result);
    }

    /**
     * Updates an existing book in the library.
     *
     * @param id          the ID of the book to be updated
     * @param updatedBook the updated book details
     * @return a ResponseEntity containing the result of the operation
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseResult> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        ResponseResult result = new ResponseResult.Builder()
                .statusCode(HttpStatus.OK.value())
                .responseMessage(getFormattedMessage(ApiConstant.BOOK_UPDATE_SUCCESS, id))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(result);
    }

    /**
     * Deletes a book from the library.
     *
     * @param id the ID of the book to be deleted
     * @return a ResponseEntity containing the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseResult> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        ResponseResult result = new ResponseResult.Builder()
                .statusCode(HttpStatus.OK.value())
                .responseMessage(getFormattedMessage(ApiConstant.BOOK_DELETED_SUCCESS, id))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(result);
    }

    /**
     * Marks a book as borrowed from the library.
     *
     * @param id the ID of the book to be borrowed
     * @return a ResponseEntity containing the result of the operation
     */
    @PostMapping("/{id}/borrow")
    public ResponseEntity<ResponseResult> borrowBook(@PathVariable Long id) {
        bookService.borrowBook(id);
        ResponseResult result = new ResponseResult.Builder()
                .statusCode(HttpStatus.OK.value())
                .responseMessage(getFormattedMessage(ApiConstant.BOOK_BORROW_SUCCESS, id))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(result);
    }

    /**
     * Marks a book as returned to the library.
     *
     * @param id the ID of the book to be returned
     * @return a ResponseEntity containing the result of the operation
     */
    @PostMapping("/{id}/return")
    public ResponseEntity<ResponseResult> returnBook(@PathVariable Long id) {
        bookService.returnBook(id);
        ResponseResult result = new ResponseResult.Builder()
                .statusCode(HttpStatus.OK.value())
                .responseMessage(getFormattedMessage(ApiConstant.BOOK_RETURN_SUCCESS, id))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(result);
    }
}
