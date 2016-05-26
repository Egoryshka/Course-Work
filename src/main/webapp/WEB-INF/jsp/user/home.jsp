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
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/postDirective.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/startPageController.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/tagCloudController.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/TrustController.js"></script>--%>


<!--removed by integration-->
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
                                    <h4 style="margin-top: 15px;">{{movie.title}}</h4>
                                </div>
                                <div class="col-md-6" style="padding: 0; margin-top: 15px;">
                                    <button class="btn btn-primary pull-right">
                                        <span class="icon-plus"></span>
                                        Добавить в корзину
                                    </button>
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
                        <form class="navbar-form navbar-left" role="search">
                        <%--<form ng-submit="getSearchResults()" class="navbar-form navbar-left" role="search">--%>
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
                                <li ng-repeat="genre in genresList">
                                    <a ng-click="" href="javascript:void(0)"
                                       style="padding: 10px 15px;" class="nav-tabs">
                                        {{genre.text}}
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <%--<div class="col-md-12">--%>
                            <%--<h4 class="sidebar-block-header nav-tabs">--%>
                                <%--Popular Movies--%>
                            <%--</h4>--%>
                            <%--<div class="popular-container row">--%>
                                <%--&lt;%&ndash;<div ng-repeat="pop in popArticles" class="populars nav-tabs col-md-12 col-xs-12">&ndash;%&gt;--%>
                                <%--<div class="populars nav-tabs col-md-12 col-xs-12">--%>
                                    <%--<div class="col-md-3" style="padding: 0 0 0 0;">--%>
                                        <%--<a href="#">--%>
                                            <%--<img width="100%" src="${pageContext.request.contextPath}/static/images/orel.jpg" alt=""/>--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                    <%--<div class="col-md-9">--%>
                                        <%--<div style="font-size: 0.92308em; line-height: 2;">--%>
                                            <%--<a href="#">Eddi Eagle</a>--%>
                                        <%--</div>--%>
                                        <%--<div style="font-size: 0.84615em; line-height: 1.63636; font-style: italic; font-weight: lighter;">--%>
                                            <%--2016--%>
                                        <%--</div>--%>

                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="populars nav-tabs col-md-12 col-xs-12">--%>
                                    <%--<div class="col-md-3" style="padding: 0 0 0 0;">--%>
                                        <%--<a href="#">--%>
                                            <%--<img width="100%" src="${pageContext.request.contextPath}/static/images/orel.jpg" alt=""/>--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                    <%--<div class="col-md-9">--%>
                                        <%--<div style="font-size: 0.92308em; line-height: 2;">--%>
                                            <%--<a href="#">Eddi Eagle</a>--%>
                                        <%--</div>--%>
                                        <%--<div style="font-size: 0.84615em; line-height: 1.63636; font-style: italic; font-weight: lighter;">--%>
                                            <%--2016--%>
                                        <%--</div>--%>

                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/home/homePageController.js"></script>
</body>
</html>