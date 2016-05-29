<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Basket</title>
</head>
<body>
<div ng-app="myApp" ng-controller="basketController">
    <div class="col-md-10 col-md-offset-1">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th style="text-align: center">Poster</th>
                <th style="text-align: center">Title</th>
                <th style="text-align: center">Amount</th>
                <th style="text-align: center">Cost</th>
                <th style="text-align: center">Action</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="movie in basket.movies">
                <td style="text-align: center">
                    <img width="90px" ng-src="{{movie.poster}}" alt="Poster">
                </td>
                <td>
                    <h4>{{movie.title}}</h4>
                </td>
                <td style="vertical-align: middle" align="center">
                    <input maxlength="2" min="1" type="number" class="form-control" style="width: 100px;"
                           ng-model="basket.count[$index]" ng-change="total()">
                </td>
                <td style="text-align: center; vertical-align: middle">
                    <h5>{{movie.cost}}</h5>
                </td>
                <td style="text-align: center; vertical-align: middle">
                    <img ng-click="removeFromBasket($index)"
                         src="${pageContext.request.contextPath}/static/images/remove.png"
                         alt="Remove">
                </td>
            </tr>
            <tr>
                <td colspan="3" style="text-align: center">
                    <h4 class="pull-right">Total:</h4>
                </td>
                <td style="text-align: center">
                    <h5>{{basket.summaryCost}}</h5>
                </td>
                <td style="text-align: center">
                    <a class="btn btn-success btn-block"
                       href="#"
                       data-toggle="modal"
                       data-target="#orderModal">
                        Order It
                    </a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="orderModal" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <form ng-submit="order();">
                    <fieldset>
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Client Information</h4>
                        </div>
                        <div class="modal-body">
                            <label style="width: 100%;">Receiver
                                <input type="text" class="form-control" ng-model="basket.name" required>
                            </label>

                            <label style="width: 100%;">Address
                                <input type="text" class="form-control" ng-model="basket.address" required>
                            </label>

                            <label style="width: 100%;">Phone
                                <input type="tel" class="form-control" ng-model="basket.phone" required>
                            </label>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-success">Make order</button>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/app/home/basketController.js"></script>
</body>
</html>
