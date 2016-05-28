<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Movie</title>
</head>
<body>
<div ng-app="myApp" ng-controller="moviePageController" class=" col-md-9"
     style="margin-left: 12.5%;">
    <div class="panel panel-default">
        <div class="panel-body" style="padding-top: 0; padding-bottom: 30px;">
            <div class="col-md-12">
                <h3 align="center">
                    <strong>{{movie.title}}</strong>
                </h3>
            </div>

            <div class="col-md-4 pull-left">
                <img style="width: 100%; margin-bottom: 10px;" ng-src="{{movie.poster}}">
                <button class="btn btn-primary btn-block">
                    <span class="icon-plus"></span>
                    Добавить в корзину <strong>({{movie.cost}}BLR)</strong>
                </button>
            </div>

            <div class="pull-left col-md-8" style="padding-left: 0; margin-top: 0;">
                <h4 style="margin-top: 0;">
                    <strong>
                        <spring:message code="label.movie.year"/>:
                    </strong> {{movie.year}}
                </h4>
            </div>

            <div class="pull-left col-md-8" style="padding-left: 0;">
                <h4>
                    <strong>
                        <spring:message code="label.movie.country"/>:
                    </strong> {{movie.country}}
                </h4>
            </div>

            <div class="pull-left col-md-8" style="padding-left: 0;">
                <h4>
                    <strong>
                        <spring:message code="label.movie.genres"/>:
                    </strong> {{genres}}
                </h4>
            </div>

            <div class="pull-left col-md-8" style="padding-left: 0;">
                <h4>
                    <strong><spring:message code="label.movie.actors"/>:</strong> {{actors}}
                </h4>
            </div>

            <div class="pull-left col-md-8" style="padding-left: 0;">
                <h4>
                    <strong>
                        <spring:message code="label.movie.about"/>
                    </strong>
                </h4>
                <p align="justify" style="font-size: medium">{{movie.notice}}</p>
            </div>

            <div class="col-md-12" ng-controller="TrustController">
                <h4>
                    <strong>
                        <spring:message code="label.movie.trailer"/>
                    </strong>
                </h4>
                <div class="embed-responsive embed-responsive-16by9"
                     style="margin: 10px 0 0 0 ">
                    <iframe class="embed-responsive-item"
                            ng-src="{{trustSrc(trailer)}}"
                            frameborder="0" allowfullscreen></iframe>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/app/home/moviePageController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/TrustController.js"></script>
</body>
</html>
