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

    $scope.isPipelineRunSuccess = function(build){
        return $scope.isStepSuccess(build.steps[build.steps.length-1])
    };

    $scope.isPipelineRunNotAccepted = function(build){
        return $.grep(build.steps, function(step){ return $scope.isStepNotAccepted(step); }).length>0;

    };

    $scope.isPipelineRunFailed = function(build){
        return $.grep(build.steps, function(step){ return $scope.isStepFailed(step);}).length>0;
    };

    $scope.isPipelineRunActive = function(build){
    return $.grep(build.steps, function(step){ return $scope.isStepActive(step);}).length>0;
    };

    $scope.isPipelineRunPending = function(build){
        return !$scope.isPipelineRunSuccess(build) && !$scope.isPipelineRunActive(build) &&
        !$scope.isPipelineRunFailed(build) && !$scope.isPipelineRunNotAccepted(build);
    };

    $scope.isStepSuccess = function(step){
        return step.status === 'success';
    };

    $scope.isStepNotAccepted = function(step){
        return step.status === 'notAccepted';
    };

    $scope.isStepFailed = function(step){
        return step.status === 'failed';
    };

    $scope.isStepActive = function(step){
        return step.status === 'active';
    };

    $scope.isStepPending = function(step){
        return step.status == null;
    };

    $scope.findPipeline = function(name){
        return $.grep($scope.pipelines, function(pipeline){ return pipeline.name === name; })[0];
    };

    $scope.additionalInfo = function(buildIndex){
        if($scope.currentStep[buildIndex]){
            return $scope.currentStep[buildIndex].stacktrace;
        } else {
            return "";
        }
    };
    $scope.currentStep = {};
    $scope.setCurrentStep = function(buildNr, stepNr){
        $scope.currentStep[buildNr] = $scope.currentPipeline.pipelineBuilds[buildNr-1].steps[stepNr-1]
    };


    $scope.emits = [];
    $scope.emitted = [];


}]);
