package com.example.LibraryManagement.service;

import java.util.*;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import com.example.LibraryManagement.DTO.AddBook;
import com.example.LibraryManagement.Exceptions.BookNotAvailableException;
import com.example.LibraryManagement.Exceptions.UserNotFoundException;
import com.example.LibraryManagement.entity.Library;
import com.example.LibraryManagement.entity.User;
import com.example.LibraryManagement.entity.UserBooks;
import com.example.LibraryManagement.repository.LibraryDAO;
import com.example.LibraryManagement.repository.UserBookDAO;
import com.example.LibraryManagement.repository.UserDAO;

import jakarta.transaction.Transactional;

@Service
public class LibraryService {
    
    private UserDAO userDAO;
    private LibraryDAO libDAO;
    private UserBookDAO userBookDAO;
    Logger logger = LoggerFactory.getLogger(LibraryService.class);

    public LibraryService(UserDAO userDAO, LibraryDAO libDAO, UserBookDAO userBookDAO) {
        this.libDAO = libDAO;
        this.userDAO = userDAO;
        this.userBookDAO = userBookDAO;
    }

    //Create Method: Add new Book to library //Only admin can add new books
    public int addBook(AddBook book) {
        try{
            String title = book.getTitle();
            String author = book.getAuthor();
            String ISBN = book.getISBN();
            System.out.println(ISBN);
            boolean isAvailable = true; //default is true, it'll be changed if book is borrowed or returned

            Library libBook = new Library(title, author, ISBN, isAvailable);
            Library newBook = libDAO.save(libBook);
            return newBook.getBookId();
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Failed to add new book to lib", e);
            return -1;
        }
    }

    @Transactional
    //Borrow book: User takes book, book is marked taken(Availability = false) and User table has bookID as foreign to indicate which book they took
    public Library borrowBook(String title, int userId) {
        try{
            UserBooks userBooks = new UserBooks();
            //get book by title, update the status and save that
            Library book = libDAO.findByTitle(title);
            if(book.isAvailable()){
                Optional<User> optionalUser = userDAO.findById(userId);
                if(optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    //Now set fk in userBooks and save it
                    userBooks.setBorrowedBook(book);
                    userBooks.setBorrowerUser(user);
                    userBookDAO.save(userBooks);

                    //set the availability to false in borrowed book and save it.
                    book.setAvailable(false);
                    libDAO.save(book);

                    //return borrowed Book(Library Obj)
                    return book;
                }
                else{
                    throw new UserNotFoundException("user not found");
                }
            }
            else{
                throw new BookNotAvailableException("Book is already borrowed, try again later");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while fetching book", e);
            return new Library();
        }
    }

    //User might want see all Books in System
    public List<Library> getAllBooks() {
        try{
            List<Library> allBooks = libDAO.findAll();
            return allBooks;
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while fetch all Books", e);
            return Collections.emptyList();
        }
    }

    //User might need list of available books
    public List<Library> getAvailableBooks() {
        try{
            List<Library> availableBooks = new ArrayList<>();
            List<Library> allbooks = libDAO.findAll();
            for (int i = 0; i < allbooks.size(); i++) {
                Library book = allbooks.get(i);
                if(book.isAvailable()){     
                    availableBooks.add(book);
                }
            }
            if(availableBooks.isEmpty()){
                throw new BookNotAvailableException("No books are available");
            }
            else{
                return availableBooks;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while fetching books.", e);
            return Collections.emptyList();
        }
    }

    //return a borrowed book: update availability to true, make User(bookId) = null
    public boolean updateAvailability(int userId, String title) {
        try{
            Library book = libDAO.findByTitle(title);
            if(!book.isAvailable()){
                //book is returned, hence the availabilty is true and save the changes
                book.setAvailable(true);
                libDAO.save(book);

                //find the user for given userId
                Optional<User> optionalUser = userDAO.findById(userId);
                if(optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    //set the fk in userbooks to null and save changes
                    UserBooks userBooks =  new UserBooks();
                    userBooks.setBorrowedBook(book);
                    userBooks.setBorrowerUser(user);

                    userBookDAO.save(userBooks);

                    return true;
                }
                else{
                    throw new UserNotFoundException("user not found for this userid");
                }             
            }
            else{
                throw new BookNotAvailableException("Book is already returned");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while updating availability", e);
            return false;
        }
    }

    //Delete book: Admin can decide if they want to delete a book from System
    public boolean deleteBook(int adminId, String title){
        try{
            Library book = libDAO.findByTitle(title);
            Optional<User> optionalUser = userDAO.findById(adminId);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                String role = user.getUserRole();
                if(role.equalsIgnoreCase("admin")) {
                    libDAO.delete(book);
                    return true;
                }
                else{
                    throw new UserNotFoundException("Only admin can delete books");
                }
            }
            else{
                throw new UserNotFoundException("User not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while deleting book", e);
            return false;
        }
    }
}
