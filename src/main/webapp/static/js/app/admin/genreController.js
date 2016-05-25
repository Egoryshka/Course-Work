/**
 * Created by Alex on 13.02.2016.
 */
angular.module('myApp')
    .controller('genreController', function ($scope, $http) {
        $scope.autocompleteGenres = function ($query) {
            return $http.get('/autocompleteGenres', {cache: true}).then(function (response) {
                var genres = response.data;
                return genres.filter(function (genre) {
                    return genre.text.toLowerCase().indexOf($query.toLowerCase()) != -1;
                });
            });
        };
    });

