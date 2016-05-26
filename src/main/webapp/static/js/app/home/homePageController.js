/**
 * Created by Егор on 26.05.2016.
 */

angular.module('myApp')
    .controller('homePageController', function ($scope, $http) {
        $scope.movies = [];
        $scope.genresList = [];

        $scope.getMovies = function() {
            $http({
                method: 'GET',
                url: '/getAllMovies'
            }).then(function successCallback(response) {
                $scope.movies = response.data;
            }, function errorCallback(response) {
                alert("Something went wrong while loading movies!!!")
            });
        };
        $scope.getMovies();

        $scope.getMovieGenres = function (index) {
            var genres = $scope.movies[index].genres;
            var genresString = "";
            genres.forEach(function (item, genres) {
                genresString += item.text + ", ";
            });
            var length = genresString.length - 2;
            genresString = genresString.substr(0, length) + ".";
            return genresString;
        };

        $scope.getMovieActors = function (index) {
            var actors = $scope.movies[index].actors;
            var actorsString = "";
            actors.forEach(function (item, actors) {
                actorsString += item.name + ", ";
            });
            var length = actorsString.length - 2;
            actorsString = actorsString.substr(0, length) + ".";
            return actorsString;
        };


        $scope.getGenresList = function () {
            $http({
                method: 'GET',
                url: '/getGenres'
            }).then(function successCallback(response) {
                $scope.genresList = response.data;
                console.log($scope.genresList);
            }, function errorCallback(response) {
                alert("Something went wrong while loading genres!!!")
            });
        };
        $scope.getGenresList();
    });