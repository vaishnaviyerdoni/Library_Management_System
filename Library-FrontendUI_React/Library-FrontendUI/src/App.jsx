import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./dashboards/Login";
import Register from "./dashboards/Register";
import AdminDashboard from "./dashboards/AdminDashboard";
import MemberDashboard from "./dashboards/MemberDashboard";
import Logout from "./dashboards/logout";
import UpdatePassword from "./dashboards/UpdatePassword";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/member" element={<MemberDashboard />} />
        <Route path="/update-password" element={<UpdatePassword />} />
        <Route path="/logout" element={<Logout />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
