package com.example.LibraryManagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.LibraryManagement.DTO.AddBook;
import com.example.LibraryManagement.DTO.BookResponse;
import com.example.LibraryManagement.entity.Library;
import com.example.LibraryManagement.service.LibraryService;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/library")
public class LibraryController {
    
    LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    //Borrow Book 
    @GetMapping("/book/{userId}")
    public ResponseEntity<Library> borrowBook(@PathVariable int userId, @RequestParam String title) {
        try{
            return ResponseEntity.ok(libraryService.borrowBook(title, userId));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Library());
        }
    }
    
    //get all books
    @GetMapping("/book/allBooks")
    public List<Library> getAllBooks() {
        try{
            return libraryService.getAllBooks();
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    //get all the available books
    @GetMapping("/book/availableBooks")
    public List<Library> getAvailableBooks() {
        try{
            return libraryService.getAvailableBooks();
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //Admin can add new Books to the System
    @PostMapping("/book")
    public ResponseEntity<BookResponse> addBook(@RequestBody AddBook book) {
        try{
            int bookId = libraryService.addBook(book);

            if(bookId > 0) {
                BookResponse res = new BookResponse(bookId, "Added Book Successfully"); 
                return ResponseEntity.ok(res);
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BookResponse(-1, "Failed to add Book"));
            }       
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BookResponse(-1, e.getMessage()));
        }
    }
    
    //Returning the borrowed, update the availability to  true
    @PutMapping("book/{userId}")
    public ResponseEntity<String> updateBook(@PathVariable int userId, @RequestParam String title) {
        try{
            if(libraryService.updateAvailability(userId, title)){
                return ResponseEntity.ok("Book is now Available");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to return the book");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Deleting a book
    @DeleteMapping("/book/{adminId}")
    public ResponseEntity<String> deleteBook(@PathVariable int adminId, @RequestParam String title){
        try{
            if(libraryService.deleteBook(adminId, title)){
                return ResponseEntity.ok("Deleted Book");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete book");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    
}
