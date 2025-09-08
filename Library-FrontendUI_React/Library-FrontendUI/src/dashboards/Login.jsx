import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../services/userService";

function Login() {
  const [userName, setUsername] = useState("");
  const [pass_word, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Call backend login
      const res = await loginUser({ userName, pass_word });
      const { userId, userRole } = res.data; // matches LoginResponse DTO

      // Save in localStorage
      localStorage.setItem("userId", userId);
      localStorage.setItem("role", userRole);
      localStorage.setItem("userName", userName);

      // Navigate based on role
      if (userRole === "admin") navigate("/admin");
      else navigate("/member");
    } catch (err) {
      console.error(err);
      alert("Login failed. Check username/password.");
    }
  };

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      {/* single parent div */}
      <form onSubmit={handleSubmit}>
        <h2>Login</h2>
        <input
          placeholder="Username"
          value={userName}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          placeholder="Password"
          type={pass_word ? "text" : "password"}
          value={pass_word}
          onChange={(e) => setPassword(e.target.value)}
        />
        <br /><br />
        <button type="submit">Login</button>
      </form>

      <div style={{ marginTop: "20px" }}>
        <span>Don't have an account? </span>
        <button onClick={() => navigate("/register")}>Register</button>
      </div>
    </div>
  );
}

export default Login;
