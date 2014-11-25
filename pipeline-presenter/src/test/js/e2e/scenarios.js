'use strict';
var request = require('request');

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('Pipeline App', function() {
    beforeEach(function() {
      browser.get('index.html');
    });

    describe('Given no nothing', function() {
        it('should contain no pipelines to select', function() {
            var pipelineMenus = element.all(by.repeater('pipeline in pipelines'));
            expect(pipelineMenus.count()).toEqual(0);
        });

        it('should contain no pipeline builds', function() {
            var builds = element.all(by.repeater('build in currentPipeline.pipelineBuilds'));
            expect(builds.count()).toEqual(0);
        });
    });

     
    describe("Given a single pipeline", function() {
        beforeEach(function() {
            var jar = request.jar();
            var req = request.defaults({
                jar : jar
            });
             
            function post(url, params) {
                var defer = protractor.promise.defer();
                console.log("Calling");
                req.post(browser.baseUrl + '/emit', params, function(error, message) {
                    console.log("Done call emit");
                    if (error || message.statusCode >= 400) {
                        defer.reject({
                            error : error,
                            message : message
                        });
                    } else {
                        defer.fulfill(message);
                    }
                });
                return defer.promise;
            }

            function emitPipeline() {
                return post({
                    pipelines : {
                        name : "singlePipeline"
                    }
                });
            }
             
            var flow = protractor.promise.controlFlow();
            flow.execute(emitPipeline);
        });
     
        it('should contain no pipelines to select', function() {
            var pipelineMenus = element.all(by.repeater('pipeline in pipelines'));
            expect(pipelineMenus.count()).toEqual(1);
        });

    });
});
