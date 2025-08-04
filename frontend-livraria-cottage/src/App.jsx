import { Outlet } from "react-router-dom";
import Navbar from "./components/Navbar"; 

export default function App() {
  return (
    <div
      style={{
        minHeight: "100vh",
        backgroundColor: "#fdfaf6",
        color: "#3b3a30",
        fontFamily: "'Quicksand', sans-serif",
      }}
    >
      <Navbar /> 

      <main style={{ padding: "2rem", maxWidth: "1000px", margin: "0 auto" }}>
        <Outlet />
      </main>
    </div>
  );
}