/**
 * Created by Егор on 25.05.2016.
 */

angular.module('myApp')
    .controller('adminMainController', function ($scope, $http) {
        $scope.movies = [];

        $scope.getMovies = function() {
            $http({
                method: 'GET',
                url: '/getAllMovies'
            }).then(function successCallback(response) {
                $scope.movies = response.data;
            }, function errorCallback(response) {
                alert("Something went wrong!!!")
            });
        };

        $scope.removeMovie = function(index) {
            var movie = $scope.movies[index];
            $http({
                method: 'POST',
                url: '/deleteMovie',
                headers: {'Content-Type': undefined},
                data: movie
            }).then(function successCallback(response) {
                alert("Success!!!");
                $scope.getMovies();
            }, function errorCallback(response) {
                alert("Failure!!!");
            });
        };

        $scope.getMovies();
    });