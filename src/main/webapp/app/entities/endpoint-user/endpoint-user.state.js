(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('endpoint-user', {
            parent: 'entity',
            url: '/endpoint-user?page&sort&search',
            data: {
                ////authorities: ['ROLE_USER'],,
                pageTitle: 'EndpointUsers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/endpoint-user/endpoint-users.html',
                    controller: 'EndpointUserController',
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
        .state('endpoint-user-detail', {
            parent: 'endpoint-user',
            url: '/endpoint-user/{id}',
            data: {
                ////authorities: ['ROLE_USER'],,
                pageTitle: 'EndpointUser'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/endpoint-user/endpoint-user-detail.html',
                    controller: 'EndpointUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EndpointUser', function($stateParams, EndpointUser) {
                    return EndpointUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'endpoint-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('endpoint-user-detail.edit', {
            parent: 'endpoint-user-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint-user/endpoint-user-dialog.html',
                    controller: 'EndpointUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndpointUser', function(EndpointUser) {
                            return EndpointUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('endpoint-user.new', {
            parent: 'endpoint-user',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint-user/endpoint-user-dialog.html',
                    controller: 'EndpointUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sensorId: null,
                                endpointUrl: null,
                                originalSensorId: null,
                                userId: null,
                                visible: null,
                                allowAccess: null,
                                disallowAccess: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('endpoint-user', null, { reload: 'endpoint-user' });
                }, function() {
                    $state.go('endpoint-user');
                });
            }]
        })
        .state('endpoint-user.edit', {
            parent: 'endpoint-user',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint-user/endpoint-user-dialog.html',
                    controller: 'EndpointUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndpointUser', function(EndpointUser) {
                            return EndpointUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('endpoint-user', null, { reload: 'endpoint-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('endpoint-user.delete', {
            parent: 'endpoint-user',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/endpoint-user/endpoint-user-delete-dialog.html',
                    controller: 'EndpointUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EndpointUser', function(EndpointUser) {
                            return EndpointUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('endpoint-user', null, { reload: 'endpoint-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
