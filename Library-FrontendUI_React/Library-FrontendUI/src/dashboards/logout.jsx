import { useNavigate } from "react-router-dom";

function Logout() {
  const navigate = useNavigate();

  const handleLogin = () => navigate("/login");
  const handleRegister = () => navigate("/register");

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <h2>You have been logged out</h2>
      <div style={{ marginTop: "20px" }}>
        <button onClick={handleLogin} style={{ marginRight: "10px" }}>Login</button>
        <button onClick={handleRegister}>Register</button>
      </div>
    </div>
  );
}

export default Logout;
