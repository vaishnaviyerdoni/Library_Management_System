import { Link, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear(); // remove userId, role, etc.
    navigate("/logout");  // go to Logout/Landing page
  };

  return (
    <nav style={{ padding: "1rem", background: "#ddd" }}>
      <Link to="/" style={{ marginRight: "1rem" }}>Home</Link>
      <Link to="/admin" style={{ marginRight: "1rem" }}>Admin Dashboard</Link>
      <Link to="/member" style={{ marginRight: "1rem" }}>Member Dashboard</Link>
      <button onClick={handleLogout}>Logout</button>
    </nav>
  );
}

export default Navbar;
