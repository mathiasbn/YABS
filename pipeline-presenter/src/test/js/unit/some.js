describe('Lad os se om scope faar det rigtige', function(){
    beforeEach(module('nodeApp'));

    it('Der skulle gerne staa noget med moinz', inject(function($controller) {
        var scope = {},
            ctrl = $controller('MainController', {$scope:scope});
    }));
});