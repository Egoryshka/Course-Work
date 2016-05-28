<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title></title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
</head>
<body>
<div ng-app="myApp">
    <div ng-controller="homePageController" class="page-container">
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-md-push-3">
                    <div>
                        <div ng-repeat="movie in movies" class="panel panel-default col-md-12">
                            <div class="col-md-3" style="padding: 15px 15px 0 0; float: left;">
                                <img width="100%" ng-src="{{movie.poster}}" alt="">
                                <%--<h5 style="text-align: center;">Рейтинг: XX</h5>--%>
                            </div>
                            <div class="col-md-9" style="padding: 0;">
                                <div class="col-md-6" style="padding: 0;">
                                    <a href="${pageContext.request.contextPath}/home/movie/{{movie.id}}"
                                       style="text-decoration: none;">
                                        <h4 style="margin-top: 15px;">{{movie.title}}</h4>
                                    </a>
                                </div>
                                <div class="col-md-6" style="padding: 0; margin-top: 15px;">
                                    <sec:authorize access="hasRole('ROLE_USER')">
                                        <button class="btn btn-primary pull-right" ng-click="addMovieToBasket($index)">
                                            <span class="icon-plus"></span>
                                            Добавить в корзину
                                        </button>
                                    </sec:authorize>
                                </div>
                            </div>
                            <p>Год: {{movie.year}}</p>
                            <p>Страна: {{movie.country}}</p>
                            <p>Жанр: {{getMovieGenres($index)}}</p>
                            <p>В ролях: {{getMovieActors($index)}}</p>
                            <p>{{movie.notice}}</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-md-pull-9">
                    <div class="row sidebar">
                        <form ng-submit="searchMovies()" class="navbar-form navbar-left" role="search">
                            <div class="form-group">
                                <input ng-model="search" type="text" class="form-control" placeholder="Search">
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <i class="icon-search" style="font-size: 1.5em;"> </i>
                            </button>
                        </form>
                        <div class="col-md-12">
                            <h4 class="sidebar-block-header nav-tabs">
                                Genres
                            </h4>

                            <ul class="nav" style="margin-top: -10px">
                                <li>
                                    <a ng-click="getMovies()" href="javascript:void(0)"
                                       style="padding: 10px 15px;" class="nav-tabs">
                                        Все
                                    </a>
                                </li>
                                <li ng-repeat="genre in genresList">
                                    <a ng-click="getMoviesByGenre($index)" href="javascript:void(0)"
                                       style="padding: 10px 15px;" class="nav-tabs">
                                        {{genre.text}}
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/app/home/homePageController.js"></script>
</body>
</html>