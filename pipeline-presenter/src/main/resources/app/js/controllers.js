var app = angular.module('nodeApp', ['btford.socket-io']);

app.factory('mySocket', function (socketFactory) {
//    var myIoSocket = io.connect('/',9092);
//    mySocket = socketFactory({
//        ioSocket: myIoSocket
//    });
//    return mySocket;
});

app.controller("MainController", ['$scope','mySocket', function ($scope,mySocket) {
    $scope.moinz = 'MOINZ FROM ANGULAR!!!';
    $scope.buildevents = [];
    //mySocket.on('buildevent', function (ev, data) {
    //    $scope.buildevents.push(data)
    //})
}]);
