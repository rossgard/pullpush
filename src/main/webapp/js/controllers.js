'use strict';

/* Controllers */

var app = angular.module('myApp.controllers', []);

/** WebSocket Service **/
app.factory('MyWebSocketService', function () {
    var service = {}

    service.connect = function () {
        if (service.ws) {
            return;
        }

        var ws = new WebSocket("ws://localhost:8080/websocket");

        ws.onopen = function () {
            service.callback("Succeeded to open a connection");
        };

        ws.onerror = function () {
            service.callback("Failed to open a connection");
        }

        ws.onmessage = function (message) {
            service.callback(message.data);
        };

        service.ws = ws;
    }

    service.send = function (message) {
        console.log("Sending request...")
        service.ws.send(message);
    }

    service.subscribe = function (callback) {
        console.log("Subscribe: " + callback)
        service.callback = callback;
    }

    return service;
});

app.controller('MyCtrl1', function ($scope, $timeout, $http, $q, MyWebSocketService) {
    /** Polling **/
    $scope.polling = function tick() {
        $http.get('/webapi/myresource').success(function (data) {
            $scope.polling = data
            $timeout(tick, 3000)
        });
    }

    /** SSE **/
    var source = new EventSource('/events');

    source.addEventListener('open', function (e) {
        console.log("SSE connection opened")
    }, false);

    source.addEventListener('open', function (e) {
        $scope.$apply(function () {
            //$scope.sse = e.data;
        });
    }, false);

    source.onmessage = function (e) {
        $scope.$apply(function () {
            $scope.sse = e.data;
        });
    };


    /** WebSockets **/
    MyWebSocketService.subscribe(function (message) {
        $scope.websocket = message;
        $scope.$apply();
    });

    $scope.connectWebSocket = function () {
        MyWebSocketService.connect();
    }

    $scope.sendWebSocket = function () {
        MyWebSocketService.send("Text");
    }

});
app.controller('MyCtrl2', [function () {

}]);