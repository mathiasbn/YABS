'use strict';
var request = require('request');

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('Pipeline App', function () {
    beforeEach(function () {
        browser.get('index.html');
    });

    describe('Given no nothing', function () {
        it('should contain no pipelines to select', function () {
            var pipelineMenus = element.all(by.repeater('pipeline in pipelines'));
            expect(pipelineMenus.count()).toEqual(0);
        });

        it('should contain no pipeline builds', function () {
            var builds = element.all(by.repeater('build in currentPipeline.pipelineBuilds'));
            expect(builds.count()).toEqual(0);
        });
    });


    describe("Given a single pipeline", function () {
        beforeEach(function () {
            var jar = request.jar();
            var req = request.defaults({
                jar: jar
            });

            function post(json) {
                var options = {
                    uri: 'http://localhost:8015/emit',
                    method: 'POST',
                    json: json
                };
//
//                request(options, function (error, response, body) {
//                    if (!error && response.statusCode == 200) {
//                        console.log(body.id); // Print the shortened url.
//                    }
//                });


                var defer = protractor.promise.defer();
                console.log("Calling");
                var url = 'http://localhost:8015/emit';
                req.post(url, options, function (error, message) {
                    console.log("Done call emit to: " + url);
                    if (error || message.statusCode >= 400) {
                        defer.reject({
                            error: error,
                            message: message
                        });
                    } else {
                        defer.fulfill(message);
                    }
                });
                return defer.promise;
            }

            function emitPipeline() {
                return post({
                    pipelineCreated: {
                        name: "singlePipeline"
                    }
                });
            }

            var flow = protractor.promise.controlFlow();
            flow.execute(emitPipeline);
        });

        it('should contain a single pipeline', function () {
            var pipelineMenus = element.all(by.repeater('pipeline in pipelines'));
            expect(pipelineMenus.count()).toEqual(1);
        });
    });
});
