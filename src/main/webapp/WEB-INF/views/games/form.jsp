<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Game - TokenMint</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .form-container {
            max-width: 600px;
            margin: 0 auto;
        }
        .image-preview {
            max-width: 200px;
            max-height: 200px;
            object-fit: cover;
            border-radius: 8px;
            margin-top: 10px;
        }
        .navbar-brand {
            font-weight: bold;
        }
        .required {
            color: red;
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
        <div class="form-container">
            <!-- Breadcrumb -->
            <nav aria-label="breadcrumb">
                                 <ol class="breadcrumb">
                     <li class="breadcrumb-item"><a href="/admin/games">Games</a></li>
                     <li class="breadcrumb-item active">Create Game</li>
                 </ol>
            </nav>

            <!-- Form Card -->
            <div class="card shadow">
                                 <div class="card-header bg-primary text-white">
                     <h5 class="card-title mb-0">
                         <i class="fas fa-plus me-2"></i>Create Game
                     </h5>
                 </div>
                <div class="card-body">
                                         <c:url var="formAction" value="/admin/games/create" />
                    
                    <form:form modelAttribute="gameInfo" action="${formAction}" method="post" id="gameForm">
                        <!-- Game ID Field -->
                        <div class="mb-3">
                            <label for="gameId" class="form-label">
                                Game ID <span class="required">*</span>
                            </label>
                                                         <form:input path="gameId" class="form-control" id="gameId" placeholder="Enter unique game ID"/>
                             <form:errors path="gameId" cssClass="text-danger" />
                             <div class="form-text">Must be unique and cannot be changed after creation.</div>
                        </div>

                        <!-- Game Name Field -->
                        <div class="mb-3">
                            <label for="gameName" class="form-label">
                                Game Name <span class="required">*</span>
                            </label>
                            <form:input path="gameName" class="form-control" id="gameName" placeholder="Enter game name"/>
                            <form:errors path="gameName" cssClass="text-danger" />
                        </div>

                        <!-- Parent Company Field -->
                        <div class="mb-3">
                            <label for="gameParentCompany" class="form-label">Parent Company</label>
                            <form:input path="gameParentCompany" class="form-control" id="gameParentCompany" placeholder="Enter parent company name"/>
                            <form:errors path="gameParentCompany" cssClass="text-danger" />
                            <div class="form-text">Optional field for the company that owns this game.</div>
                        </div>

                        <!-- Game Logo Field -->
                        <div class="mb-3">
                            <label for="gameLogo" class="form-label">Game Logo URL</label>
                            <form:input path="gameLogo" class="form-control" id="gameLogo" placeholder="Enter logo image URL" onchange="previewImage()"/>
                            <form:errors path="gameLogo" cssClass="text-danger" />
                            <div class="form-text">Enter a valid URL for the game logo image.</div>
                            
                            <!-- Image Preview -->
                            <div id="imagePreview" style="display: none;">
                                <img id="previewImg" class="image-preview" alt="Logo Preview">
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="d-flex gap-2 justify-content-end">
                            <a href="/admin/games" class="btn btn-secondary">
                                <i class="fas fa-times me-2"></i>Cancel
                            </a>
                                                         <button type="submit" class="btn btn-success">
                                 <i class="fas fa-plus me-2"></i>Create Game
                             </button>
                        </div>
                    </form:form>
                </div>
            </div>

            <!-- Help Card -->
            <div class="card mt-4">
                <div class="card-body">
                    <h6 class="card-title"><i class="fas fa-info-circle me-2"></i>Guidelines</h6>
                    <ul class="mb-0">
                        <li><strong>Game ID:</strong> Must be unique and follow naming conventions (e.g., GAME_001)</li>
                        <li><strong>Game Name:</strong> Display name for the game</li>
                        <li><strong>Parent Company:</strong> Optional field for the owning company</li>
                        <li><strong>Logo URL:</strong> Should be a valid image URL (PNG, JPG, JPEG, GIF)</li>
                    </ul>
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
    <script>
        function previewImage() {
            const url = document.getElementById('gameLogo').value;
            const preview = document.getElementById('imagePreview');
            const img = document.getElementById('previewImg');
            
            if (url && url.trim() !== '') {
                img.src = url;
                img.onerror = function() {
                    preview.style.display = 'none';
                };
                img.onload = function() {
                    preview.style.display = 'block';
                };
            } else {
                preview.style.display = 'none';
            }
        }

        // Show preview on page load if URL exists
        document.addEventListener('DOMContentLoaded', function() {
            previewImage();
        });

        // Form validation
        document.getElementById('gameForm').addEventListener('submit', function(e) {
            const gameId = document.getElementById('gameId').value.trim();
            const gameName = document.getElementById('gameName').value.trim();
            
            if (!gameId) {
                e.preventDefault();
                alert('Game ID is required!');
                document.getElementById('gameId').focus();
                return false;
            }
            
            if (!gameName) {
                e.preventDefault();
                alert('Game Name is required!');
                document.getElementById('gameName').focus();
                return false;
            }
        });
    </script>
</body>
</html> 