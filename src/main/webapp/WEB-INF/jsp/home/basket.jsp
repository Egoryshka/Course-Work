<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Basket</title>
</head>
<body>
<div ng-app="myApp" ng-controller="basketController">
    <div class="col-md-10 col-md-offset-1">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th class="cetered">Poster</th>
                <th class="cetered">Title</th>
                <th class="cetered">Year</th>
                <th class="cetered">Country</th>
                <th class="cetered">Cost</th>
                <th class="cetered">Action</th>
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
                    <img ng-click="removeMovie($index)"
                         src="${pageContext.request.contextPath}/static/images/remove.png"
                         alt="Remove">
                </td>
            </tr>
            <tr>
                <td colspan="4" style="text-align: center">
                    <h5 class="pull-right">Total:</h5>
                </td>
                <td style="text-align: center">
                    <h5>{{total}}</h5>
                </td>
                <td style="text-align: center">
                    <button class="btn btn-success btn-block" ng-click="order()">
                        Order It
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/app/home/basketController.js"></script>
</body>
</html>
