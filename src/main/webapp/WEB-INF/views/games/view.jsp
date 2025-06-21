<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Game - TokenMint</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .detail-container {
            max-width: 800px;
            margin: 0 auto;
        }
        .game-logo-large {
            max-width: 300px;
            max-height: 300px;
            object-fit: cover;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .navbar-brand {
            font-weight: bold;
        }
        .detail-card {
            border: none;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .detail-label {
            font-weight: 600;
            color: #495057;
        }
        .detail-value {
            color: #212529;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#"><i class="fas fa-gamepad me-2"></i>TokenMint Admin</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/games">Games</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="detail-container">
            <!-- Breadcrumb -->
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/admin/games">Games</a></li>
                    <li class="breadcrumb-item active">View Game</li>
                </ol>
            </nav>

            <!-- Game Details Card -->
            <div class="card detail-card">
                <div class="card-header bg-info text-white">
                    <h5 class="card-title mb-0">
                        <i class="fas fa-eye me-2"></i>Game Details
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <!-- Game Logo Section -->
                        <div class="col-md-4 text-center mb-4">
                            <c:choose>
                                <c:when test="${not empty gameInfo.gameLogo}">
                                    <img src="${gameInfo.gameLogo}" alt="Game Logo" class="game-logo-large img-fluid">
                                </c:when>
                                <c:otherwise>
                                    <div class="bg-secondary text-white d-flex align-items-center justify-content-center game-logo-large" style="height: 200px;">
                                        <i class="fas fa-gamepad fa-4x"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <!-- Game Information Section -->
                        <div class="col-md-8">
                            <div class="row">
                                <div class="col-12 mb-3">
                                    <h3 class="text-primary">${gameInfo.gameName}</h3>
                                    <p class="text-muted">Game ID: <strong>${gameInfo.gameId}</strong></p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6 mb-3">
                                    <div class="p-3 bg-light rounded">
                                        <div class="detail-label">Game ID</div>
                                        <div class="detail-value h6">${gameInfo.gameId}</div>
                                    </div>
                                </div>
                                <div class="col-sm-6 mb-3">
                                    <div class="p-3 bg-light rounded">
                                        <div class="detail-label">Game Name</div>
                                        <div class="detail-value h6">${gameInfo.gameName}</div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6 mb-3">
                                    <div class="p-3 bg-light rounded">
                                        <div class="detail-label">Parent Company</div>
                                        <div class="detail-value h6">
                                            <c:choose>
                                                <c:when test="${not empty gameInfo.gameParentCompany}">
                                                    ${gameInfo.gameParentCompany}
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">Not specified</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 mb-3">
                                    <div class="p-3 bg-light rounded">
                                        <div class="detail-label">Logo Status</div>
                                        <div class="detail-value h6">
                                            <c:choose>
                                                <c:when test="${not empty gameInfo.gameLogo}">
                                                    <span class="badge bg-success">
                                                        <i class="fas fa-check me-1"></i>Available
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-warning">
                                                        <i class="fas fa-exclamation-triangle me-1"></i>Not set
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <c:if test="${not empty gameInfo.gameLogo}">
                                <div class="row">
                                    <div class="col-12 mb-3">
                                        <div class="p-3 bg-light rounded">
                                            <div class="detail-label">Logo URL</div>
                                            <div class="detail-value">
                                                <a href="${gameInfo.gameLogo}" target="_blank" class="text-decoration-none">
                                                    ${gameInfo.gameLogo}
                                                    <i class="fas fa-external-link-alt ms-1"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="card mt-4">
                <div class="card-body">
                                         <div class="d-flex gap-2 justify-content-center">
                         <a href="/admin/games" class="btn btn-secondary">
                             <i class="fas fa-arrow-left me-2"></i>Back to List
                         </a>
                     </div>
                </div>
            </div>

            <!-- Additional Information Card -->
            <div class="card mt-4">
                <div class="card-header">
                    <h6 class="card-title mb-0">
                        <i class="fas fa-info-circle me-2"></i>Additional Information
                    </h6>
                </div>
                <div class="card-body">
                    <div class="row text-center">
                        <div class="col-md-4">
                            <div class="p-3">
                                <i class="fas fa-database fa-2x text-primary mb-2"></i>
                                <h6>Database Entries</h6>
                                <p class="text-muted">This game may have associated configurations and data in the system.</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="p-3">
                                <i class="fas fa-cogs fa-2x text-success mb-2"></i>
                                <h6>Configuration</h6>
                                <p class="text-muted">Game settings and configurations can be managed through the admin panel.</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="p-3">
                                <i class="fas fa-users fa-2x text-info mb-2"></i>
                                <h6>Player Management</h6>
                                <p class="text-muted">Player data and token transactions are tracked for this game.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="mt-5 py-4 bg-light text-center">
        <div class="container">
            <p class="text-muted mb-0">
                <i class="fas fa-copyright me-1"></i>2024 TokenMint Game Management System
            </p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 