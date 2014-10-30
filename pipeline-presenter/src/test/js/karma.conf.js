module.exports = function(config){
  config.set({

    basePath : '../',

    files : [
      '../main/resources/app/bower_components/angular/angular.js',
      '../main/resources/app/bower_components/angular-route/angular-route.js',
      '../main/resources/app/bower_components/angular-mocks/angular-mocks.js',
      '../main/resources/app/js/**/*.js',
      'js/unit/**/*.js'
    ],

    autoWatch : true,

    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine'
            ],

    junitReporter : {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    }

  });
};