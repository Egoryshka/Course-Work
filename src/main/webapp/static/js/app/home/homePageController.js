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


        function genresComparator(a, b){
            if(a.text < b.text)
                return -1;
            if(a.text >b.text)
            return 1;
            return 0
        }

        $scope.getGenresList = function () {
            $http({
                method: 'GET',
                url: '/getGenres'
            }).then(function successCallback(response) {
                $scope.genresList = response.data;
                $scope.genresList.sort(genresComparator);
            }, function errorCallback(response) {
                alert("Something went wrong while loading genres!!!")
            });
        };
        $scope.getGenresList();

        $scope.searchMovies = function () {
            $http({
                method: 'POST',
                url: '/search',
                headers: {
                    'Content-Type': "application/json"
                },
                data: $scope.search
            }).then(function successCallback(response) {
                $scope.movies = response.data;
                console.log($scope.movies)
            }, function errorCallback(response) {
            });
        };

        $scope.addMovieToBasket = function(index) {
            var id = $scope.movies[index].id;
            $http({
                method: 'POST',
                url: '/addMovieToBasket',
                headers: {
                    'Content-Type': "application/json"
                },
                data: id
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

        $scope.getMoviesByGenre = function(index) {
            var genre  = $scope.genresList[index].text;
            $http({
                method: 'POST',
                url: '/getMoviesByGenre',
                headers: {
                    'Content-Type': "application/json"
                },
                data: genre
            }).then(function successCallback(response) {
                $scope.movies = response.data;
            }, function errorCallback(response) {
                alert("Something went wrong!!!");
            });
        }
    });