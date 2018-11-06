'use strict';

describe('Controller Tests', function() {

    describe('EndpointsConfig Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEndpointsConfig, MockEndpoint, MockFiestaUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEndpointsConfig = jasmine.createSpy('MockEndpointsConfig');
            MockEndpoint = jasmine.createSpy('MockEndpoint');
            MockFiestaUser = jasmine.createSpy('MockFiestaUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EndpointsConfig': MockEndpointsConfig,
                'Endpoint': MockEndpoint,
                'FiestaUser': MockFiestaUser
            };
            createController = function() {
                $injector.get('$controller')("EndpointsConfigDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fiestaSecurityUiApp:endpointsConfigUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
