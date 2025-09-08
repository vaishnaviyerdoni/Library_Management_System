function UserTable({ users, onDelete }) {
  return (
    <table border="1">
      <thead>
        <tr>
          <th>User ID</th>
          <th>Name</th>
          <th>Role</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {users.map((u, i) => (
          <tr key={i}>
            <td>{u.userId}</td>
            <td>{u.userName}</td>
            <td>{u.userRole}</td>
            <td>
              {onDelete && <button onClick={() => onDelete(u.userId)}>Delete</button>}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default UserTable;
