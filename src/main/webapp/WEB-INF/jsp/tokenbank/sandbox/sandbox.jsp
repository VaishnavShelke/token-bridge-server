<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Token Bridge Sandbox</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #fff176 0%, #ffb74d 100%);
            min-height: 100vh;
            padding: 20px 0;
        }
        .main-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(255, 235, 59, 0.4);
            overflow: hidden;
        }
        .header-section {
            background: linear-gradient(135deg, #ffeb3b 0%, #ffc107 100%);
            color: #5d4037;
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
            background: #ffd54f;
            border-radius: 50%;
            margin-right: 8px;
        }
        .info-card {
            border-left: 4px solid #ffeb3b;
            transition: transform 0.2s;
            background: linear-gradient(135deg, #fffde7 0%, #fff8e1 100%);
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
        .sandbox-badge {
            background: linear-gradient(45deg, #ffeb3b, #ffc107);
            color: #5d4037;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: bold;
            display: inline-block;
            margin-bottom: 15px;
            box-shadow: 0 3px 10px rgba(255, 235, 59, 0.5);
        }
        .card-header.bg-light {
            background: linear-gradient(135deg, #fff9c4 0%, #fff176 100%) !important;
            border-bottom: 2px solid #ffeb3b;
        }
        .btn-warning-custom {
            background: linear-gradient(45deg, #ffeb3b, #ffc107);
            border: none;
            color: #5d4037;
            box-shadow: 0 3px 15px rgba(255, 235, 59, 0.4);
            font-weight: 600;
        }
        .btn-warning-custom:hover {
            background: linear-gradient(45deg, #fdd835, #ff8f00);
            color: #4e342e;
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(255, 235, 59, 0.5);
        }
        .btn-outline-warning-custom {
            border: 2px solid #ffeb3b;
            color: #f57f17;
            background: transparent;
        }
        .btn-outline-warning-custom:hover {
            background: #ffeb3b;
            color: #5d4037;
            box-shadow: 0 3px 15px rgba(255, 235, 59, 0.4);
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
                        <div class="sandbox-badge">
                            <i class="bi bi-flask"></i> SANDBOX ENVIRONMENT
                        </div>
                        <div class="logo">⚙️</div>
                        <h1 class="display-4 fw-bold mb-3">Welcome to ${serverName}</h1>
                        <p class="lead">${description}</p>
                    </div>
                    
                    <!-- Content Section -->
                    <div class="p-4">
                        <!-- Status Cards -->
                        <div class="row g-4 mb-4">
                            <div class="col-sm-6">
                                <div class="card info-card h-100">
                                    <div class="card-body text-center">
                                        <i class="bi bi-gear-wide-connected text-warning fs-2 mb-2"></i>
                                        <h6 class="card-title text-muted">Environment</h6>
                                        <p class="card-text fw-bold">
                                            <span class="status-indicator"></span>${environment}
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="card info-card h-100">
                                    <div class="card-body text-center">
                                        <i class="bi bi-clock text-warning fs-2 mb-2"></i>
                                        <h6 class="card-title text-muted">Current Time</h6>
                                        <p class="card-text fw-bold">${currentTime}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Sandbox Features -->
                        <div class="card mb-4">
                            <div class="card-header bg-light">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-tools"></i> Sandbox Features
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <a href="/tokenmint/create" class="btn btn-warning-custom btn-custom w-100">
                                            <i class="bi bi-plus-circle"></i> Create Test Tokens
                                        </a>
                                    </div>
                                    <div class="col-md-6">
                                        <a href="/tokenmint/transfer" class="btn btn-outline-warning-custom btn-custom w-100">
                                            <i class="bi bi-arrow-left-right"></i> Test Transfers
                                        </a>
                                    </div>
                                </div>
                                <div class="row g-3 mt-2">
                                    <div class="col-md-6">
                                        <a href="/tokenmint/monitor" class="btn btn-outline-warning-custom btn-custom w-100">
                                            <i class="bi bi-activity"></i> Monitor Activity
                                        </a>
                                    </div>
                                    <div class="col-md-6">
                                        <a href="/tokenmint/settings" class="btn btn-outline-warning-custom btn-custom w-100">
                                            <i class="bi bi-sliders"></i> Sandbox Settings
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Important Notice -->
                        <div class="alert alert-warning d-flex align-items-center" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>
                            <div>
                                <strong>Sandbox Environment:</strong> This is a development and testing environment. 
                                All transactions and data are for testing purposes only and do not represent real blockchain operations.
                            </div>
                        </div>
                        
                        <!-- Quick Actions -->
                        <div class="text-center mt-4">
                            <a href="/" class="btn btn-outline-secondary me-3">
                                <i class="bi bi-house"></i> Back to Main
                            </a>
                            <a href="/test/health" class="btn btn-outline-success">
                                <i class="bi bi-heart-pulse"></i> Health Check
                            </a>
                        </div>
                        
                        <!-- Footer -->
                        <div class="text-center mt-4 pt-4 border-top">
                            <p class="text-muted mb-1">
                                <strong>Token Bridge Sandbox</strong> - Development & Testing Platform
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
</body>
</html>
