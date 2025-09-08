import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { updateUserPassword } from "../services/userService";

function UpdatePassword() {
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const userId = localStorage.getItem("userId");
  const userName = localStorage.getItem("userName"); 
  const userRole = localStorage.getItem("role");   

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await updateUserPassword(userId, {
        userName,
        userRole,
        password,
      });
      alert("Password updated successfully!");
      navigate("/member"); // back to dashboard
    } catch (err) {
      console.error(err);
      alert("Failed to update password. Try again.");
    }
  };

  return (
    <form onSubmit={handleUpdate}>
      <h2>Update Password</h2>
      <input
        type="password"
        placeholder={password ? "text" : "password"}
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button type="submit">Update</button>
    </form>
  );
}

export default UpdatePassword;
