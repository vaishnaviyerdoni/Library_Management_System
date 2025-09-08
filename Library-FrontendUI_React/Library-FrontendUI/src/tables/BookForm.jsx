import { useState } from "react";

function BookForm({ onAdd }) {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [ISBN, setISBN] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onAdd({ title, author, ISBN });
    setTitle("");
    setAuthor("");
    setISBN("");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Add Book</h3>
      <input placeholder="Title" value={title} onChange={(e) => setTitle(e.target.value)} />
      <input placeholder="Author" value={author} onChange={(e) => setAuthor(e.target.value)} />
      <input placeholder="ISBN" value={ISBN} onChange={(e) => setISBN(e.target.value)} />
      <button type="submit">Add Book</button>
    </form>
  );
}

export default BookForm;
