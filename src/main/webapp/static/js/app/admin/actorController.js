/**
 * Created by Егор on 14.05.2016.
 */

angular.module('myApp')
    .controller('actorController', function ($scope, $http) {
        $scope.autocompleteActors = function ($query) {
            return $http.get('/autocompleteActors', {cache: true}).then(function (response) {
                var actors = response.data;
                return actors.filter(function (actor) {
                    return actor.name.toLowerCase().indexOf($query.toLowerCase()) != -1;
                });
            });
        };
    });
