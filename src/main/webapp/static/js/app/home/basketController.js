/**
 * Created by Егор on 29.05.2016.
 */

angular.module('myApp')
    .controller('basketController', function ($scope, $http) {
        $scope.basket = [];

        $scope.getBasketContent = function() {
                $http({
                        method: 'GET',
                        url: '/prepareOrder'
                }).then(function successCallback(response) {
                        $scope.basket = response.data;
                        console.log($scope.basket);
                }, function errorCallback(response) {
                        alert("Something went wrong!!!")
                });
        };
        $scope.getBasketContent();

        $scope.total = function() {

        };

        $scope.order = function() {
                $http({
                        method: 'POST',
                        url: '/makeOrder',
                        headers: {
                                'Content-Type': "application/json"
                        },
                        data: $scope.basket
                }).then(function successCallback(response) {
                        alert("Order was made successfully!!!");
                }, function errorCallback(response) {
                        alert("Something went wrong!!!")
                });
        }
    });