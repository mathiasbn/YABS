var app = angular.module('nodeApp', ['btford.socket-io']);

app.factory('mySocket', function (socketFactory) {
    var myIoSocket = io.connect('http://localhost:9092');
    mySocket = socketFactory({
        ioSocket: myIoSocket
    });
    return mySocket;
});

app.controller("MainController", ['$scope','mySocket','$http', function ($scope,mySocket,$http) {
$scope.slide = true;
    mySocket.on('buildevent', function (msg) {
        $scope.emits.push('Recieve'+$scope.emits.length+': '+msg);
    });
    $scope.emitFunction = function(){
        var emitted = 'Emit'+$scope.emitted.length
        mySocket.emit('buildevent', emitted);
        $scope.emitted.push(emitted);
    };

    $http.get('testdata/pipelines.json').success(function(data) {
        $scope.pipelines = data.pipelines;
        if(data.pipelines.length > 0){
            $scope.currentPipeline = data.pipelines[0];
            $scope.tab = data.pipelines[0].name;
        }
    });

    $scope.setTab = function(name){
        $scope.tab = name;
        $scope.currentPipeline = this.findPipeline(name);
    };

    $scope.isTabSelected = function(name){
        return $scope.tab === name;
    };

    $scope.isSuccess = function(step){
        return step.status === 'success';
    };

    $scope.isNotAccepted = function(step){
        return step.status === 'notAccepted';
    };

    $scope.isFailed = function(step){
        return step.status === 'failed';
    };

    $scope.isActive = function(step){
        return step.status === 'active';
    };

    $scope.isPending = function(step){
        return step.status == null;
    };

    $scope.findPipeline = function(name){
        return $.grep($scope.pipelines, function(pipeline){ return pipeline.name === name; })[0];
    };

    $scope.moinz = 'MOINZ FROM ANGULAR!!!';
    $scope.emits = [];
    $scope.emitted = [];


}]);
