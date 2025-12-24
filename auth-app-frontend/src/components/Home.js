import React from 'react';
import { Link } from 'react-router-dom';
import authService from '../services/authService';

const Home = () => {
  const isAuthenticated = authService.isAuthenticated();
  const user = authService.getCurrentUser();

  return (
    <div className="container">
      <div className="dashboard">
        <div className="dashboard-header">
          <h1 className="dashboard-title">🔐 Authentication System</h1>
          <p className="dashboard-subtitle">
            Secure, modern authentication with Spring Boot & React
          </p>
        </div>

        {isAuthenticated ? (
          <div className="dashboard-grid">
            <div className="dashboard-card">
              <h3>✅ Authenticated</h3>
              <p>Welcome back, <strong>{user?.name || user?.email}</strong>!</p>
              <p>You are successfully logged in.</p>
              <div style={{ marginTop: '1rem' }}>
                <Link to="/dashboard" className="nav-link" style={{ 
                  background: 'linear-gradient(45deg, #00ff88, #00ccff)',
                  color: '#000',
                  textDecoration: 'none',
                  padding: '0.5rem 1rem',
                  borderRadius: '5px',
                  fontWeight: 'bold'
                }}>
                  Go to Dashboard →
                </Link>
              </div>
            </div>
          </div>
        ) : (
          <div className="dashboard-grid">
            <div className="dashboard-card">
              <h3>🔑 Login</h3>
              <p>Sign in to access your dashboard and protected features.</p>
              <div style={{ marginTop: '1rem' }}>
                <Link to="/login" className="nav-link" style={{ 
                  background: 'linear-gradient(45deg, #00ff88, #00ccff)',
                  color: '#000',
                  textDecoration: 'none',
                  padding: '0.5rem 1rem',
                  borderRadius: '5px',
                  fontWeight: 'bold'
                }}>
                  Sign In →
                </Link>
              </div>
            </div>

            <div className="dashboard-card">
              <h3>📝 Register</h3>
              <p>Create a new account to get started with our authentication system.</p>
              <div style={{ marginTop: '1rem' }}>
                <Link to="/register" className="nav-link" style={{ 
                  background: 'rgba(255, 255, 255, 0.1)',
                  color: '#fff',
                  textDecoration: 'none',
                  padding: '0.5rem 1rem',
                  borderRadius: '5px',
                  fontWeight: 'bold',
                  border: '1px solid rgba(255, 255, 255, 0.2)'
                }}>
                  Sign Up →
                </Link>
              </div>
            </div>

            <div className="dashboard-card">
              <h3>🚀 Features</h3>
              <p>• JWT Token Authentication</p>
              <p>• Secure Password Encryption</p>
              <p>• Role-based Access Control</p>
              <p>• Protected Routes</p>
              <p>• Modern Black Theme UI</p>
            </div>

            <div className="dashboard-card">
              <h3>🛠️ Tech Stack</h3>
              <p>• <strong>Backend:</strong> Spring Boot 3.x</p>
              <p>• <strong>Frontend:</strong> React 18</p>
              <p>• <strong>Database:</strong> MySQL</p>
              <p>• <strong>Security:</strong> Spring Security + JWT</p>
              <p>• <strong>UI:</strong> Custom CSS with Dark Theme</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;