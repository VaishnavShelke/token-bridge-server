<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Game Management - TokenMint</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .game-logo {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 8px;
        }
        .table-actions {
            min-width: 200px;
        }
        .navbar-brand {
            font-weight: bold;
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
                        <a class="nav-link active" href="/admin/games">Games</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <!-- Header -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-list me-2 text-primary"></i>Game Management</h2>
                    <a href="/admin/games/create" class="btn btn-success">
                        <i class="fas fa-plus me-2"></i>Add New Game
                    </a>
                </div>

                <!-- Success/Error Messages -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle me-2"></i>${successMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-circle me-2"></i>${errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Games Table Card -->
                <div class="card shadow">
                    <div class="card-header bg-light">
                        <h5 class="card-title mb-0">
                            <i class="fas fa-table me-2"></i>Games List
                            <span class="badge bg-primary ms-2">${fn:length(games)} games</span>
                        </h5>
                    </div>
                    <div class="card-body p-0">
                        <c:choose>
                            <c:when test="${empty games}">
                                <div class="text-center py-5">
                                    <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
                                    <h5 class="text-muted">No games found</h5>
                                    <p class="text-muted">Start by creating your first game.</p>
                                    <a href="/admin/games/create" class="btn btn-primary">
                                        <i class="fas fa-plus me-2"></i>Create First Game
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover mb-0">
                                        <thead class="table-dark">
                                            <tr>
                                                <th scope="col">Logo</th>
                                                <th scope="col">Game ID</th>
                                                <th scope="col">Game Name</th>
                                                <th scope="col">Parent Company</th>
                                                <th scope="col" class="table-actions">Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="game" items="${games}">
                                                <tr>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty game.gameLogo}">
                                                                <img src="${game.gameLogo}" alt="Game Logo" class="game-logo">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="game-logo bg-secondary d-flex align-items-center justify-content-center">
                                                                    <i class="fas fa-gamepad text-white"></i>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td><strong>${game.gameId}</strong></td>
                                                    <td>${game.gameName}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty game.gameParentCompany}">
                                                                ${game.gameParentCompany}
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-muted">-</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <div class="btn-group" role="group">
                                                            <a href="/admin/games/view/${game.gameId}" class="btn btn-outline-info btn-sm" title="View">
                                                                <i class="fas fa-eye"></i>
                                                            </a>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
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