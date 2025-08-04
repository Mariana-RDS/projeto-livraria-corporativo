// main.jsx

import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import App from "./App";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import Catalogo from "./pages/Catalogo";
import Vendas from "./pages/Vendas";
import Estoque from "./pages/Estoque";
import Sobre from "./pages/Sobre";

import PrivateRoute from "./components/PrivateRoute";

import "./index.css";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        {/* Agrupa as rotas públicas que não precisam do layout principal do App */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Agrupa as rotas privadas que usarão o PrivateRoute e o layout do App */}
        <Route
          path="/"
          element={
            <PrivateRoute>
              <App />
            </PrivateRoute>
          }
        >
          <Route index element={<Home />} />
          <Route path="catalogo" element={<Catalogo />} />
          <Route path="vendas" element={<Vendas />} />
          <Route path="estoque" element={<Estoque />} />
          <Route path="sobre" element={<Sobre />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);