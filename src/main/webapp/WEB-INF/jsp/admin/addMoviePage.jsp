<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/admin/adminMovieController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/admin/genreController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/admin/actorController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/TrustController.js"></script>

<div ng-app="myApp" ng-controller="adminMovieController" style="height: 100%">

    <div class="col-md-12" style="height: 100%">
        <div class="col-md-9" style="margin-left: 12.5%">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4 class="panel-title" style="display: inline-block">Add new Movie</h4>
                </div>
                <form class="panel-body" style="padding: 15px 0 15px 0;" name="editingArticle">
                    <fieldset>
                        <div class="col-md-3 pull-left" style="padding: 0 0 0 15px;">
                            <div class="dropzone" style="height: 328px; display: table-cell; vertical-align: middle;"
                                 file-dropzone="[image/png, image/jpeg, image/gif]"
                                 file="poster" file-name="fileName" data-max-file-size="3">
                                <img ng-show="poster" ng-src="{{poster}}" width="100%"/>
                                <h4 ng-hide="poster"><spring:message code="label.post.dropzone"/></h4>
                            </div>
                        </div>

                        <div class="pull-left col-md-9">
                            <h4 style="margin: 0 0 2px 0;">Movie Title</h4>
                            <input maxlength="150" class="form-control" id="title" ng-model="title"
                                   required>
                        </div>

                        <div class="pull-left col-md-4" style="padding-right: 0;">
                            <h4 style="margin: 10px 0 2px 0;">Year</h4>
                            <input maxlength="4" class="form-control" id="year" ng-model="year"
                                   required>
                        </div>

                        <div class="pull-left col-md-5">
                            <h4 style="margin: 10px 0 2px 0;">Cost</h4>
                            <input maxlength="10" class="form-control" id="cost" ng-model="cost"
                                   required>
                        </div>

                        <div class="pull-left col-md-9">
                            <h4 style="margin: 10px 0 2px 0;">Country</h4>
                            <input maxlength="150" class="form-control" id="country" ng-model="country">
                        </div>

                        <div class="pull-left col-md-9">
                            <h4>Genres</h4>
                            <div ng-controller="genreController">
                                <tags-input ng-model="genres"
                                            display-property="text"
                                            placeholder="Enter the genre"
                                            replace-spaces-with-dashes="false"
                                            min-length="0"
                                            max-length="15"
                                            max-tags="5">
                                    <auto-complete source="autocompleteGenres($query)"
                                                   load-on-focus="true"
                                                   max-tags="10"
                                                   load-on-empty="true"
                                                   max-results-to-show="32"
                                                   template="genre-template">
                                    </auto-complete>
                                </tags-input>
                                <script type="text/ng-template" id="genre-template">
                                    <div class="right-panel">
                                        <span ng-bind-html="$highlight($getDisplayText())"></span>
                                    </div>
                                </script>
                            </div>
                        </div>

                        <div class="pull-left col-md-12">
                            <h4>Actors</h4>
                            <div ng-controller="actorController">
                                <tags-input ng-model="actors"
                                            display-property="name"
                                            placeholder="Enter the actor"
                                            replace-spaces-with-dashes="false"
                                            min-length="0"
                                            max-length="30"
                                            max-tags="10">
                                    <auto-complete source="autocompleteActors($query)"
                                                   load-on-focus="true"
                                                   max-tags="10"
                                                   load-on-empty="true"
                                                   max-results-to-show="32"
                                                   template="actor-template">
                                    </auto-complete>
                                </tags-input>
                                <script type="text/ng-template" id="actor-template">
                                    <div class="right-panel">
                                        <span ng-bind-html="$highlight($getDisplayText())"></span>
                                    </div>
                                </script>
                            </div>
                        </div>

                        <div class="pull-left col-md-12">
                            <h4 style="margin: 10px 0 2px 0;">Description</h4>
                            <textarea ng-model="notice" id="notice" rows="5" class="form-control"
                                      style="width: 100%;">
                            </textarea>
                        </div>

                        <div class="pull-left col-md-12">
                            <h4 style="margin: 15px 0 2px 0;">Trailer URL</h4>
                            <input maxlength="150" type="url" class="form-control" ng-model="trailer">
                            <div ng-show="trailer" ng-controller="TrustController">
                                <div class="embed-responsive embed-responsive-16by9"
                                     style="margin: 10px 0 0 0 ">
                                    <iframe class="embed-responsive-item"
                                            ng-src="{{trustSrc(handleYouTube(trailer))}}"
                                            frameborder="0" allowfullscreen></iframe>
                                </div>
                            </div>
                        </div>

                        <div class="pull-left col-md-12">
                            <div class="col-md-6 pull-left" style="padding: 20px 7.5px 0 0">
                                <button ng-click="newMovie();"
                                        class="btn btn-primary btn-block" type="submit"
                                        ng-disabled="editingArticle.$invalid">
                                    Save
                                </button>
                            </div>
                            <div class="col-md-6  pull-left" style="padding: 20px 0 0 7.5px">
                                <a href="${pageContext.request.contextPath}/admin/mainAdminPage"
                                   class="btn btn-default btn-block">
                                    Cancel
                                </a>
                            </div>
                            <div ng-show="isUploading" class="progress progress-striped active pull-left">
                                <div class="progress-bar" style="width: 100%"></div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
