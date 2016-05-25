<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <style>
        .cetered {
            text-align: center;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <div class="col-md-10 col-md-offset-1">
        <a href="${pageContext.request.contextPath}/admin/addMoviePage">
            <button class="btn btn-primary">Add new Movie</button>
        </a>
    </div>
    <div class="col-md-10 col-md-offset-1" ng-app="myApp">
        <table class="table table-striped table-hover" ng-controller="adminMainController">
            <thead>
                <tr>
                    <th class="cetered">Poster</th>
                    <th class="cetered">Title</th>
                    <th class="cetered">Year</th>
                    <th class="cetered">Country</th>
                    <th class="cetered">Cost</th>
                    <th class="cetered">Remove</th>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="movie in movies">
                    <td style="text-align: center">
                        <img width="90px" ng-src="{{movie.poster}}" alt="Poster">
                    </td>
                    <td>
                        <h4>{{movie.title}}</h4>
                    </td>
                    <td style="text-align: center">
                        <h5>{{movie.year}}</h5>
                    </td>
                    <td style="text-align: center">
                        <h5>{{movie.country}}</h5>
                    </td>
                    <td style="text-align: center">
                        <h5>{{movie.cost}}</h5>
                    </td>
                    <td style="text-align: center">
                        <img ng-click="removeMovie($index)" src="${pageContext.request.contextPath}/static/images/remove.png" alt="Remove">
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/app/admin/adminMainController.js"></script>
</body>
</html>
