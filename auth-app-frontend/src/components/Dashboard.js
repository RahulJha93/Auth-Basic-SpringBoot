import React, { useState, useEffect } from 'react';
import authService from '../services/authService';

const Dashboard = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const user = authService.getCurrentUser();

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      const usersData = await authService.getAllUsers();
      setUsers(Array.isArray(usersData) ? usersData : []);
    } catch (error) {
      console.error('Error loading users:', error);
      setError('Failed to load users. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <div className="dashboard">
        <div className="dashboard-header">
          <h1 className="dashboard-title">Welcome to Dashboard</h1>
          <p className="dashboard-subtitle">
            Hello {user?.name || user?.email}, you're successfully authenticated! 🎉
          </p>
        </div>

        <div className="dashboard-grid">
          {/* User Profile Card */}
          <div className="dashboard-card">
            <h3>👤 Your Profile</h3>
            <p><strong>Name:</strong> {user?.name}</p>
            <p><strong>Email:</strong> {user?.email}</p>
            <p><strong>Provider:</strong> {user?.provider}</p>
            <p><strong>Status:</strong> {user?.enable ? '✅ Active' : '❌ Inactive'}</p>
            <p><strong>Roles:</strong> {user?.roles?.map(role => role.name).join(', ')}</p>
          </div>

          {/* Users List Card */}
          <div className="dashboard-card">
            <h3>👥 All Users</h3>
            {loading ? (
              <p>Loading users... <span className="loading"></span></p>
            ) : error ? (
              <p style={{ color: '#ff4757' }}>{error}</p>
            ) : (
              <div>
                <p><strong>Total Users:</strong> {users.length}</p>
                {users.length > 0 ? (
                  <div style={{ marginTop: '1rem' }}>
                    {users.slice(0, 3).map((userData, index) => (
                      <div key={index} style={{ 
                        marginBottom: '0.5rem', 
                        padding: '0.5rem', 
                        background: 'rgba(255, 255, 255, 0.05)',
                        borderRadius: '5px' 
                      }}>
                        <small>
                          {userData.name} ({userData.email})
                        </small>
                      </div>
                    ))}
                    {users.length > 3 && (
                      <small>...and {users.length - 3} more users</small>
                    )}
                  </div>
                ) : (
                  <p>No users found</p>
                )}
              </div>
            )}
          </div>

          {/* Features Card */}
          <div className="dashboard-card">
            <h3>🚀 Features</h3>
            <p>✅ JWT Authentication</p>
            <p>✅ User Registration & Login</p>
            <p>✅ Protected Routes</p>
            <p>✅ Role-based Access</p>
            <p>✅ Responsive Design</p>
            <p>✅ Modern Black Theme</p>
          </div>

          {/* API Status Card */}
          <div className="dashboard-card">
            <h3>🔗 API Connection</h3>
            <p><strong>Backend URL:</strong> http://localhost:8001</p>
            <p><strong>Status:</strong> ✅ Connected</p>
            <p><strong>Environment:</strong> Development</p>
            <p><strong>Auth Method:</strong> Bearer Token</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;