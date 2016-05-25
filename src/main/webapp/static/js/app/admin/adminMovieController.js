/**
 * Created by Егор on 14.05.2016.
 */

angular.module('myApp')
    .controller('adminMovieController', function ($scope, $http) {
        $scope.isUploading = false;
        $scope.readyYouTube = "";
        $scope.genres = [];
        $scope.actors = [];

        $scope.handleYouTube = function (video) {
            if (typeof video !== 'undefined') {
                $scope.readyYouTube = video.replace('https://www.youtube.com/watch?v=','');
                $scope.readyYouTube = $scope.readyYouTube.replace('https://www.youtube.com/embed/','');
                $scope.readyYouTube.replace('&index=18&', '?');
                $scope.readyYouTube.replace('&', '?');
                $scope.readyYouTube = "https://www.youtube.com/embed/" + $scope.readyYouTube;
                return $scope.readyYouTube;
            }
            return "";
        };

        $scope.newMovie = function () {
            $scope.isUploading = true;
            var movie = {};
            movie.title = $scope.title;
            movie.year = $scope.year;
            movie.country = $scope.country;
            movie.trailer = $scope.trailer;
            movie.poster = "";
            movie.notice = $scope.notice;
            movie.genres = $scope.genres;
            movie.actors = $scope.actors;
            movie.cost = $scope.cost;
            if (movie.notice.length > 3072) {
                alert("Description too long!!!")
                return;
            }
            $scope.saveImage()
                .then(function (response) {
                    movie.poster = response.data.poster;
                }, function () {
                    console.log("empty image");
                    $scope.isUpdating = false;
                }).then(function () {
                    return $http({
                        method: 'POST',
                        url: '/saveMovie',
                        headers: {'Content-Type': undefined},
                        data: movie
                })
            }).then(function () {
                $scope.isUploading = false;
            });
        };

        $scope.saveImage = function () {
            return $http({
                method: 'POST',
                url: '/saveImage',
                headers: {
                    'Content-Type': "application/json"
                },
                data: $scope.poster
            });
        };
    });