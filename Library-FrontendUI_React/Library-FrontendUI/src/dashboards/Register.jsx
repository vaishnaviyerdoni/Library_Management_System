import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../services/userService";

function Register() {
  const [userName, setUsername] = useState("");
  const [pass_word, setPassword] = useState();
  const [userRole, setRole] = useState("")
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await registerUser({userName, pass_word, userRole });
      alert("Registered successfully");
    } catch (err) {
      alert("Registration failed", err);
    }
  };

  const goToLogin = () => {
    navigate("/login");
  }

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <form onSubmit={handleSubmit}>
      <h2>Register</h2>
      <input placeholder="Username" value={userName} onChange={(e) => setUsername(e.target.value)} />
      <input placeholder="Password" type={pass_word ? "text" : "password"} value={pass_word} onChange={(e) => setPassword(e.target.value)} />
      <input placeholder="userRole" value={userRole} onChange={(e) => setRole(e.target.value)} />
      <button type="submit">Register</button>
    </form>

      <div style={{ marginTop: "20px" }}>
        <span>Already have an account? </span>
        <button onClick={goToLogin}>Go to Login</button>
      </div>
    </div>
  );
}

export default Register;
