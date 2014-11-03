'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('Pipeline App', function() {

  describe('Header thingy', function() {

    beforeEach(function() {
      browser.get('index.html');
    });

    it('Should find header', function() {
      var header = element(by.binding('moinz')).getText();
      expect(header).toBe('MOINZ FROM ANGULAR!!!');
    });


  });
});
