var app = angular.module('nodeApp', ['btford.socket-io']);

app.factory('mySocket', function (socketFactory) {
    var myIoSocket = io.connect('http://localhost:9092');
    mySocket = socketFactory({
        ioSocket: myIoSocket
    });
    return mySocket;
});

app.controller("MainController", ['$scope','mySocket', function ($scope,mySocket) {
    $scope.moinz = 'MOINZ FROM ANGULAR!!!';
    $scope.emits = [];
    $scope.emitted = [];
    mySocket.on('buildevent', function (msg) {
        $scope.emits.push('Recieve'+$scope.emits.length+': '+msg);
    });
    $scope.emitFunction = function(){
        var emitted = 'Emit'+$scope.emitted.length
        mySocket.emit('buildevent', emitted);
        $scope.emitted.push(emitted);
    }
}]);
