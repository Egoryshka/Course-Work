/**
 * Created by Егор on 29.05.2016.
 */

angular.module('myApp')
    .controller('basketController', function ($scope, $http) {
        $scope.basket = {};
        var isOrderable = false;

        $scope.getBasketContent = function () {
            $http({
                method: 'GET',
                url: '/prepareOrder'
            }).then(function successCallback(response) {
                $scope.basket = response.data;
                if ($scope.basket.movies.length != 0) {
                    isOrderable = true;
                }
                console.log($scope.basket);
            }, function errorCallback(response) {
                alert("Something went wrong!!!")
            });
        };
        $scope.getBasketContent();

        $scope.removeFromBasket = function (index) {
            $scope.basket.movies.splice(index, 1);
            $scope.basket.count.splice(index, 1);
            $scope.total();
        };

        $scope.total = function () {
            var total = 0;
            for (var i = 0; i < $scope.basket.movies.length; i++) {
                var cost = $scope.basket.movies[i].cost;
                var count = $scope.basket.count[i];
                total += cost * count;
            }
            $scope.basket.summaryCost = total;
        };

        $scope.order = function () {
            if (isOrderable) {
                $http({
                    method: 'POST',
                    url: '/makeOrder',
                    headers: {
                        'Content-Type': "application/json"
                    },
                    data: $scope.basket
                }).then(function successCallback(response) {
                    eraseBasket();
                    closeModal();
                    alert("Order successfully issued.");
                }, function errorCallback(response) {
                    alert("Your basket is empty!!!")
                });
            }
            else {
                closeModal();
                alert("Your basket is empty!!!");
            }

        };

        function closeModal() {
            $('#orderModal').modal('hide');
        }

        function eraseBasket() {
            $scope.basket.address = "";
            $scope.basket.count = [];
            $scope.basket.movies = [];
            $scope.basket.name = "";
            $scope.basket.phone = "";
            $scope.basket.summaryCost = 0;
            isOrderable = false;
        }


    });