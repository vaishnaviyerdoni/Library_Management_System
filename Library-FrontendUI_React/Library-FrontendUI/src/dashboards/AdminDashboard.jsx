import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllBooks, deleteBook, addBook } from "../services/libraryService";
import { getAllUsers, deleteUser } from "../services/userService";
import BookTable from "../tables/BookTable";
import UserTable from "../tables/UserTable";
import BookForm from "../tables/BookForm";

function AdminDashboard() {
  const [books, setBooks] = useState([]);
  const [users, setUsers] = useState([]);
  const adminId = localStorage.getItem("userId");
  const navigate = useNavigate(); 

  useEffect(() => {
    getAllBooks().then((res) => setBooks(res.data));
    getAllUsers(adminId).then((res) => setUsers(res.data));
  }, [adminId]);

  const handleDeleteBook = async (title) => {
    await deleteBook(adminId, title);
    setBooks(books.filter((b) => b.title !== title));
  };

  const handleDeleteUser = async (userId) => {
    await deleteUser(adminId, userId);
    setUsers(users.filter((u) => u.userId !== userId));
  };

  const handleAddBook = async (book) => {
    await addBook(book);
    const res = await getAllBooks();
    setBooks(res.data);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/logout");
  };

  return (
   <div className="admin-content">
    <div className="welcome-header">
      <span className="user-name">Hello {localStorage.getItem("userName")}, Welcome to Library</span>
      <button className="logout-btn" onClick={handleLogout}>Logout</button>
    </div>


  <div className="tables-container">
    <div className="add-book-form">
      <BookForm onAdd={handleAddBook} />
    </div>
    <div className="books-table">
      <h2>Books</h2>
      <BookTable books={books} onDelete={handleDeleteBook} />
    </div>
    <div className="users-table">
      <h2>Users</h2>
      <UserTable users={users} onDelete={handleDeleteUser} />
    </div>
  </div>
</div>

  )
}

export default AdminDashboard;
