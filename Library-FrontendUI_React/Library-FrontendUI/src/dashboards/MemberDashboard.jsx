import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllBooks, borrowBook, returnBook } from "../services/libraryService";
import BookTable from "../tables/BookTable";

function MemberDashboard() {
  const [availableBooks, setAvailableBooks] = useState([]);
  const [borrowedBooks, setBorrowedBooks] = useState([]);
  const userId = parseInt(localStorage.getItem("userId"), 10);
  const navigate = useNavigate();

  const fetchBooks = async () => {
    const res = await getAllBooks();
    const books = res.data;

    // Split into available vs borrowed
    setAvailableBooks(books.filter((b) => b.available));
    setBorrowedBooks(books.filter((b) => !b.available));
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  const handleBorrow = async (title) => {
    await borrowBook(userId, title);
    fetchBooks();
  };

  const handleReturn = async (title) => {
    await returnBook(userId, title);
    fetchBooks();
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/logout");
  };

  const handleUpdatePassword = () => {
    navigate("/update-password");
  };

  return (
    <div>
      <div className="welcome-header">
        <span className="user-name">Hello {localStorage.getItem("userName")}</span>
        <span className="welcome-text">, Welcome to Library</span>
    </div>

      <h2>Member Dashboard</h2>
      
      {/* Action buttons */}
      <div style={{ marginBottom: "20px" }}>
        <button onClick={handleLogout}>Logout</button>
        <button onClick={handleUpdatePassword} style={{ marginLeft: "10px" }}>
          Update Password
        </button>
      </div>

      <h3>Available Books</h3>
      <BookTable books={availableBooks} onBorrow={handleBorrow} />

      {borrowedBooks.length > 0 && (
            <>
            <h3>Borrowed Books</h3>
            <BookTable books={borrowedBooks} onReturn={handleReturn} />
        </>
        )}
    </div>
  );
}

export default MemberDashboard;
