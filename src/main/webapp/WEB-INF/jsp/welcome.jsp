<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome - Token Bridge Server</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px 0;
        }
        .main-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            overflow: hidden;
        }
        .header-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px 0;
            text-align: center;
        }
        .logo {
            font-size: 4rem;
            margin-bottom: 15px;
        }
        .status-indicator {
            display: inline-block;
            width: 12px;
            height: 12px;
            background: #28a745;
            border-radius: 50%;
            margin-right: 8px;
        }
        .info-card {
            border-left: 4px solid #667eea;
            transition: transform 0.2s;
        }
        .info-card:hover {
            transform: translateY(-2px);
        }
        .btn-custom {
            transition: all 0.3s ease;
        }
        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        }
        .login-form .input-group {
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .login-form .input-group-text {
            border: 1px solid #dee2e6;
        }
        .login-form .form-control {
            border: 1px solid #dee2e6;
            padding: 12px;
        }
        .login-form .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .login-form .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            font-weight: 600;
        }
        .login-form .btn-primary:hover {
            background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
        }
        .card-header.bg-gradient {
            border: none;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-lg-10 col-xl-8">
                <div class="main-container">
                    <!-- Header Section -->
                    <div class="header-section">
                        <div class="logo">🌉</div>
                        <h1 class="display-4 fw-bold mb-3">Welcome to ${serverName}</h1>
                        <p class="lead">Blockchain Token Bridge & Management Platform</p>
                    </div>
                    
                    <!-- Content Section -->
                    <div class="p-4">
                        <!-- Error Message -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                <i class="bi bi-info-circle"></i>
                                <strong>Notice:</strong> ${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>
                        

                        
                        <!-- TokenMint Login Section -->
                        <div class="card mb-4 shadow-sm">
                            <div class="card-header bg-gradient text-white" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                                <h5 class="card-title mb-0 text-center">
                                    <i class="bi bi-coin me-2"></i>Login to TokenMint
                                </h5>
                            </div>
                            <div class="card-body p-4">
                                <!-- Success/Error Messages -->
                                <c:if test="${not empty successMessage}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <i class="bi bi-check-circle"></i>
                                        <strong>Success:</strong> ${successMessage}
                                        <c:if test="${not empty username}">
                                            <br><small>Welcome back, ${username}!</small>
                                        </c:if>
                                        <c:if test="${not empty apiInfo}">
                                            <br><small class="text-muted">${apiInfo}</small>
                                        </c:if>
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </c:if>
                                
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <i class="bi bi-exclamation-triangle"></i>
                                        <strong>Error:</strong> ${errorMessage}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </c:if>
                                
                                <form action="/auth/login" method="post" class="login-form">
                                    <div class="mb-3">
                                        <label for="username" class="form-label fw-semibold">
                                            <i class="bi bi-person me-2 text-primary"></i>Username
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="bi bi-person-circle text-muted"></i>
                                            </span>
                                            <input type="text" class="form-control border-start-0 ps-0" id="username" name="username"
                                                   placeholder="Enter your username" required>
                                        </div>
                                    </div>
                                    <div class="mb-4">
                                        <label for="password" class="form-label fw-semibold">
                                            <i class="bi bi-lock me-2 text-primary"></i>Password
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="bi bi-key text-muted"></i>
                                            </span>
                                            <input type="password" class="form-control border-start-0 ps-0" id="password" name="password"
                                                   placeholder="Enter your password" required>
                                            <button class="btn btn-outline-secondary border-start-0" type="button" id="togglePassword">
                                                <i class="bi bi-eye" id="eyeIcon"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="row g-3">
                                        <div class="col-md-8">
                                            <button type="submit" class="btn btn-primary btn-custom w-100 py-2 fw-semibold">
                                                <i class="bi bi-box-arrow-in-right me-2"></i>Login to TokenMint
                                            </button>
                                        </div>
                                        <div class="col-md-4">
                                            <a href="/signup" class="btn btn-outline-primary btn-custom w-100 py-2">
                                                <i class="bi bi-person-plus me-1"></i>Sign Up
                                            </a>
                                        </div>
                                    </div>
                                    <div class="text-center mt-3">
                                        <small class="text-muted">
                                            <a href="#" class="text-decoration-none">Forgot your password?</a>
                                        </small>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <!-- Launch Sandbox Section -->
                        <div class="card mb-4">
                            <div class="card-body text-center py-3">
                                <p class="text-muted mb-2 small">Development & Testing Environment</p>
                                <a href="/sandbox" target="_blank" class="btn btn-warning btn-custom">
                                    <i class="bi bi-arrow-left-right me-1"></i> Launch Sandbox
                                </a>
                            </div>
                        </div>
                        
                        <!-- Footer -->
                        <div class="text-center mt-4 pt-4 border-top">
                            <p class="text-muted mb-1">
                                <strong>Token Bridge Server</strong> - Blockchain Integration Platform
                            </p>
                            <small class="text-muted">
                                Generated at: <c:out value="${currentTime}"/>
                            </small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom JavaScript -->
    <script>
        // Password toggle functionality
        document.getElementById('togglePassword').addEventListener('click', function() {
            const passwordField = document.getElementById('password');
            const eyeIcon = document.getElementById('eyeIcon');
            
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                eyeIcon.className = 'bi bi-eye-slash';
            } else {
                passwordField.type = 'password';
                eyeIcon.className = 'bi bi-eye';
            }
        });
        
        // Form validation and animation
        document.querySelector('.login-form').addEventListener('submit', function(e) {
            const username = this.querySelector('#username').value.trim();
            const password = this.querySelector('#password').value.trim();
            
            if (!username || !password) {
                e.preventDefault();
                alert('Please enter both username and password.');
                return false;
            }
            
            // Add loading state
            const submitBtn = this.querySelector('button[type="submit"]');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="bi bi-hourglass-split me-2"></i>Logging in...';
            submitBtn.disabled = true;
            
            // Allow form to submit to server
            return true;
        });
    </script>
</body>
</html>
