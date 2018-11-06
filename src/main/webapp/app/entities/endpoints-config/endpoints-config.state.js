(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('endpoints-config', {
            parent: 'entity',
            url: '/endpoints-config?page&sort&search',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'EndpointsConfigs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/endpoints-config/endpoints-configs.html',
                    controller: 'EndpointsConfigController',
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
        .state('endpoints-config-detail', {
            parent: 'endpoints-config',
            url: '/endpoints-config/{id}',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'EndpointsConfig'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/endpoints-config/endpoints-config-detail.html',
                    controller: 'EndpointsConfigDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EndpointsConfig', function($stateParams, EndpointsConfig) {
                    return EndpointsConfig.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'endpoints-config',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('endpoints-config-detail.edit', {
            parent: 'endpoints-config-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoints-config/endpoints-config-dialog.html',
                    controller: 'EndpointsConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndpointsConfig', function(EndpointsConfig) {
                            return EndpointsConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('endpoints-config.new', {
            parent: 'endpoints-config',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoints-config/endpoints-config-dialog.html',
                    controller: 'EndpointsConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                checkAll: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('endpoints-config', null, { reload: 'endpoints-config' });
                }, function() {
                    $state.go('endpoints-config');
                });
            }]
        })
        .state('endpoints-config.edit', {
            parent: 'endpoints-config',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoints-config/endpoints-config-dialog.html',
                    controller: 'EndpointsConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndpointsConfig', function(EndpointsConfig) {
                            return EndpointsConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('endpoints-config', null, { reload: 'endpoints-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('endpoints-config.delete', {
            parent: 'endpoints-config',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoints-config/endpoints-config-delete-dialog.html',
                    controller: 'EndpointsConfigDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EndpointsConfig', function(EndpointsConfig) {
                            return EndpointsConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('endpoints-config', null, { reload: 'endpoints-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
