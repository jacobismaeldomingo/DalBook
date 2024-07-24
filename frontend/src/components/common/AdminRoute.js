import React from 'react';
import { Navigate } from 'react-router-dom';

const AdminRoute = ({ children }) => {
  const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
  const userRole = localStorage.getItem('userRole'); // Assuming you store user role in localStorage

  if (!isLoggedIn) {
    return <Navigate to="/login" />;
  }

  if (userRole !== 'System_Admin') {
    return <Navigate to="/home" />;
  }

  return children;
};

export default AdminRoute;
