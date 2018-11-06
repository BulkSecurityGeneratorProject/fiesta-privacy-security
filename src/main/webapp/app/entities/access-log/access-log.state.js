(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('access-log', {
            parent: 'entity',
            url: '/access-log?page&sort&search',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'AccessLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/access-log/access-logs.html',
                    controller: 'AccessLogController',
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
//        .state('access-log-one', {
//                    parent: 'entity',
//                    url: '/access-log-one?page&sort&search',
//                    data: {
//                        //authorities: ['ROLE_USER'],
//                        pageTitle: 'AccessLogs'
//                    },
//                    views: {
//                        'content@': {
//                            templateUrl: 'app/entities/access-log/access-log-one.html',
//                            controller: 'AccessLogOneController',
//                            controllerAs: 'vm'
//                        }
//                    },
//                    params: {
//                        page: {
//                            value: '1',
//                            squash: true
//                        },
//                        sort: {
//                            value: 'id,asc',
//                            squash: true
//                        },
//                        search: null
//                    },
//                    resolve: {
//                        pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
//                            return {
//                                page: PaginationUtil.parsePage($stateParams.page),
//                                sort: $stateParams.sort,
//                                predicate: PaginationUtil.parsePredicate($stateParams.sort),
//                                ascending: PaginationUtil.parseAscending($stateParams.sort),
//                                search: $stateParams.search
//                            };
//                        }],
//                    }
//                })
        .state('access-log-detail', {
            parent: 'access-log',
            url: '/access-log/{id}',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'AccessLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/access-log/access-log-detail.html',
                    controller: 'AccessLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AccessLog', function($stateParams, AccessLog) {
                    return AccessLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'access-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('access-log-detail.edit', {
            parent: 'access-log-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/access-log/access-log-dialog.html',
                    controller: 'AccessLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AccessLog', function(AccessLog) {
                            return AccessLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('access-log.new', {
            parent: 'access-log',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/access-log/access-log-dialog.html',
                    controller: 'AccessLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                sensorId: null,
                                originalSensorId: null,
                                endpointUrl: null,
                                accessStatus: null,
                                accessTime: null,
                                testbedId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('access-log', null, { reload: 'access-log' });
                }, function() {
                    $state.go('access-log');
                });
            }]
        })
        .state('access-log.edit', {
            parent: 'access-log',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/access-log/access-log-dialog.html',
                    controller: 'AccessLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AccessLog', function(AccessLog) {
                            return AccessLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('access-log', null, { reload: 'access-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('access-log.delete', {
            parent: 'access-log',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/access-log/access-log-delete-dialog.html',
                    controller: 'AccessLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AccessLog', function(AccessLog) {
                            return AccessLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('access-log', null, { reload: 'access-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
