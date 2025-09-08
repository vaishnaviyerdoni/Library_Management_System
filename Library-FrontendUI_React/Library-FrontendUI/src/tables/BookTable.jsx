function BookTable({ books, onDelete, onBorrow, onReturn }) {
  return (
    <table border="1">
      <thead>
        <tr>
          <th>Title</th>
          <th>Author</th>
          <th>Available</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {books.map((b, i) => (
          <tr key={i}>
            <td>{b.title}</td>
            <td>{b.author}</td>
            <td>{b.available ? "Yes" : "No"}</td>
            <td>
              {onDelete && <button onClick={() => onDelete(b.title)}>Delete</button>}
              {onBorrow && b.available && <button onClick={() => onBorrow(b.title)}>Borrow</button>}
              {onReturn && !b.available && <button onClick={() => onReturn(b.title)}>Return</button>}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default BookTable;
