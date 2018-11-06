(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sensor-explorer', {
            parent: 'entity',
            url: '/sensor-explorer?page&sort&search',
            data: {
               //http://localhost:8081
                pageTitle: 'SensorExplorers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sensor-explorer/sensor-explorers.html',
                    controller: 'SensorExplorerController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('sensor-explorer-detail', {
            parent: 'sensor-explorer',
            url: '/sensor-explorer/{id}',
            data: {
               //http://localhost:8081
                pageTitle: 'SensorExplorer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sensor-explorer/sensor-explorer-detail.html',
                    controller: 'SensorExplorerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SensorExplorer', function($stateParams, SensorExplorer) {
                    return SensorExplorer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sensor-explorer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sensor-explorer-detail.edit', {
            parent: 'sensor-explorer-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensor-explorer/sensor-explorer-dialog.html',
                    controller: 'SensorExplorerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SensorExplorer', function(SensorExplorer) {
                            return SensorExplorer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sensor-explorer.new', {
            parent: 'sensor-explorer',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensor-explorer/sensor-explorer-dialog.html',
                    controller: 'SensorExplorerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                endp: null,
                                unit: null,
                                qk: null,
                                sensor: null,
                                hashedSensor: null,
                                displaySensor: null,
                                type: null,
                                shortQk: null,
                                shortUnit: null,
                                lng: null,
                                lat: null,
                                sensorData: null,
                                created: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sensor-explorer', null, { reload: 'sensor-explorer' });
                }, function() {
                    $state.go('sensor-explorer');
                });
            }]
        })
        /*.state('sensor-explorer.edit', {
            parent: 'sensor-explorer',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensor-explorer/sensor-explorer-dialog.html',
                    controller: 'SensorExplorerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SensorExplorer', function(SensorExplorer) {
                            return SensorExplorer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sensor-explorer', null, { reload: 'sensor-explorer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })*/

        .state('sensor-explorer.edit', {
                    parent: 'sensor-explorer',
                    url: '/{id}/edit',
                    data: {
                        //authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/entities/request-access/request-access-dialog-experimenter.html',
                            controller: 'RequestAccessDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                                entity: function () {
                                    return {
                                         content: null,
                                                                        sensorHashId: null,
                                                                        sensorOriginalId: null,
                                                                        sensorEndpoint: null,
                                                                        requesterId: null,
                                                                        receiverId: null,
                                                                        seenByRequester: null,
                                                                        seenByReceiver: null,
                                                                        approved: null,
                                                                        requestDate: null,
                                                                        approvedDate: null,
                                                                        seenByRequesterDate: null,
                                                                        seenByReceiverDate: null,
                                                                        requestSensorId: $stateParams.id,
                                                                        id: null
                                    };
                                }
                            }
                        }).result.then(function() {
                            $state.go('sensor-explorer', null, { reload: 'sensor-explorer' });
                        }, function() {
                            $state.go('sensor-explorer');
                        });
                    }]
                })
        .state('sensor-explorer.delete', {
            parent: 'sensor-explorer',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensor-explorer/sensor-explorer-delete-dialog.html',
                    controller: 'SensorExplorerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SensorExplorer', function(SensorExplorer) {
                            return SensorExplorer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sensor-explorer', null, { reload: 'sensor-explorer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
