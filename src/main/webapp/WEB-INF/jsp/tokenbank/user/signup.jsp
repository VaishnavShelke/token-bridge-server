<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - TokenMint</title>
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
        .signup-form .input-group {
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .signup-form .input-group-text {
            border: 1px solid #dee2e6;
        }
        .signup-form .form-control {
            border: 1px solid #dee2e6;
            padding: 12px;
        }
        .signup-form .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .signup-form .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            font-weight: 600;
            padding: 12px;
        }
        .signup-form .btn-primary:hover {
            background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
        }
        .signup-form .btn-outline-secondary {
            border: 2px solid #6c757d;
            color: #6c757d;
        }
        .signup-form .btn-outline-secondary:hover {
            background: #6c757d;
            color: white;
        }
        .card-header.bg-gradient {
            border: none;
        }
        .api-info {
            background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
            border-left: 4px solid #2196f3;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-xl-5">
                <div class="main-container">
                    <!-- Header Section -->
                    <div class="header-section">
                        <div class="logo">👤</div>
                        <h1 class="display-5 fw-bold mb-3">Join TokenMint</h1>
                        <p class="lead">Create your account to access blockchain token services</p>
                    </div>
                    
                    <!-- Content Section -->
                    <div class="p-4">
                        <!-- Error/Success Messages -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle"></i>
                                <strong>Error:</strong> ${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>
                        
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <i class="bi bi-check-circle"></i>
                                <strong>Success:</strong> ${successMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <!-- API Information -->
                        <div class="api-info">
                            <h6 class="mb-2"><i class="bi bi-info-circle me-2"></i>API Integration</h6>
                            <p class="mb-1"><strong>Endpoint:</strong> <code>POST /tokenbank/user</code></p>
                            <p class="mb-0"><small>This form will integrate with the existing TokenBank user registration API</small></p>
                        </div>
                        
                        <!-- Signup Form -->
                        <div class="card shadow-sm">
                            <div class="card-header bg-gradient text-white" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                                <h5 class="card-title mb-0 text-center">
                                    <i class="bi bi-person-plus me-2"></i>Create Account
                                </h5>
                            </div>
                            <div class="card-body p-4">
                                <form action="/auth/signup" method="post" class="signup-form">
                                    <div class="mb-3">
                                        <label for="username" class="form-label fw-semibold">
                                            <i class="bi bi-person me-2 text-primary"></i>Username *
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="bi bi-person-circle text-muted"></i>
                                            </span>
                                            <input type="text" class="form-control border-start-0 ps-0" id="username" name="username"
                                                   placeholder="Choose a username" required>
                                        </div>
                                    </div>
                                    
                                    <div class="mb-3">
                                        <label for="email" class="form-label fw-semibold">
                                            <i class="bi bi-envelope me-2 text-primary"></i>Email Address
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="bi bi-envelope text-muted"></i>
                                            </span>
                                            <input type="email" class="form-control border-start-0 ps-0" id="email" name="email"
                                                   placeholder="your.email@example.com">
                                        </div>
                                    </div>
                                    
                                    <div class="mb-3">
                                        <label for="password" class="form-label fw-semibold">
                                            <i class="bi bi-lock me-2 text-primary"></i>Password *
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="bi bi-key text-muted"></i>
                                            </span>
                                            <input type="password" class="form-control border-start-0 ps-0" id="password" name="password"
                                                   placeholder="Create a secure password" required>
                                            <button class="btn btn-outline-secondary border-start-0" type="button" id="togglePassword">
                                                <i class="bi bi-eye" id="eyeIcon"></i>
                                            </button>
                                        </div>
                                    </div>
                                    
                                    <div class="mb-4">
                                        <label for="confirmPassword" class="form-label fw-semibold">
                                            <i class="bi bi-lock-fill me-2 text-primary"></i>Confirm Password *
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="bi bi-shield-check text-muted"></i>
                                            </span>
                                            <input type="password" class="form-control border-start-0 ps-0" id="confirmPassword" name="confirmPassword"
                                                   placeholder="Confirm your password" required>
                                        </div>
                                    </div>
                                    
                                    <div class="row g-3">
                                        <div class="col-12">
                                            <button type="submit" class="btn btn-primary btn-custom w-100 py-2 fw-semibold">
                                                <i class="bi bi-person-plus me-2"></i>Create TokenMint Account
                                            </button>
                                        </div>
                                    </div>
                                    
                                    <div class="text-center mt-3">
                                        <small class="text-muted">
                                            Already have an account? <a href="/" class="text-decoration-none">Sign in here</a>
                                        </small>
                                    </div>
                                </form>
                            </div>
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
        
        // Form validation
        document.querySelector('.signup-form').addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Passwords do not match. Please check and try again.');
                return false;
            }
            
            if (password.length < 6) {
                e.preventDefault();
                alert('Password must be at least 6 characters long.');
                return false;
            }
            
            // Add loading state
            const submitBtn = this.querySelector('button[type="submit"]');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="bi bi-hourglass-split me-2"></i>Creating Account...';
            submitBtn.disabled = true;
            
            // Allow form to submit
            return true;
        });
    </script>
</body>
</html>
