/**
 * Created by Егор on 28.05.2016.
 */

angular.module('myApp')
    .controller('moviePageController', function ($scope, $http, $location) {
        $scope.movie = {};
        $scope.genres = "";
        $scope.actors = "";
        $scope.trailer = "";

        function getMovieId() {
            var url = $location.absUrl().substring(0,$location.absUrl().indexOf('?'));
            if(url === "")
                url = $location.absUrl();
            url = url.split('\/');
            return url[url.length-1];
        }

        function getMovieGenres(genres) {
            var genresString = "";
            genres.forEach(function (item) {
                genresString += item.text + ", ";
            });
            var length = genresString.length - 2;
            genresString = genresString.substr(0, length) + ".";
            return genresString;
        }

        function getMovieActors(actors) {
            var actorsString = "";
            actors.forEach(function (item) {
                actorsString += item.name + ", ";
            });
            var length = actorsString.length - 2;
            actorsString = actorsString.substr(0, length) + ".";
            return actorsString;
        }

        function handleYouTube(video) {
            if (typeof video !== 'undefined') {
                var readyYouTube = video.replace('https://www.youtube.com/watch?v=','');
                readyYouTube = readyYouTube.replace('https://www.youtube.com/embed/','');
                readyYouTube.replace('&index=18&', '?');
                readyYouTube.replace('&', '?');
                readyYouTube = "https://www.youtube.com/embed/" + readyYouTube;
                return readyYouTube;
            }
            return "";
        }

        $scope.getMovie = function() {
            var id = getMovieId();
            $http({
                method: 'POST',
                url: '/getSingleMovie',
                headers: {
                    'Content-Type': "application/json"
                },
                data: id
            }).then(function successCallback(response) {
                $scope.movie = response.data;
                $scope.genres = getMovieGenres($scope.movie.genres);
                $scope.actors = getMovieActors($scope.movie.actors);
                $scope.trailer = handleYouTube($scope.movie.trailer);
                console.log($scope.trailer);
            }, function errorCallback(response) {
            });
        };
        $scope.getMovie();

        $scope.addMovieToBasket = function() {
            $http({
                method: 'POST',
                url: '/addMovieToBasket',
                headers: {
                    'Content-Type': "application/json"
                },
                data: $scope.movie.id
            }).then(function successCallback(response) {
                if (response.data == "OK") {
                    alert("Movie added to Basket");
                }
                else{
                    alert("Error while adding movie to basket! Try log in!");
                }
            }, function errorCallback(response) {
                alert("Something went wrong!!!");
            });
        };
    });