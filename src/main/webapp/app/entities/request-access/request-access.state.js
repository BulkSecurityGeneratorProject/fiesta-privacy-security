(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('request-access', {
            parent: 'entity',
            url: '/request-access?page&sort&search',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'RequestAccesses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-access/request-accesses.html',
                    controller: 'RequestAccessController',
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
        .state('request-access-detail', {
            parent: 'request-access',
            url: '/request-access/{id}',
            data: {
                //authorities: ['ROLE_USER'],
                pageTitle: 'RequestAccess'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-access/request-access-detail.html',
                    controller: 'RequestAccessDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RequestAccess', function($stateParams, RequestAccess) {
                    return RequestAccess.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'request-access',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('request-access-detail.edit', {
            parent: 'request-access-detail',
            url: '/detail/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-access/request-access-dialog.html',
                    controller: 'RequestAccessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RequestAccess', function(RequestAccess) {
                            return RequestAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request-access.new', {
            parent: 'request-access',
            url: '/new',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-access/request-access-dialog.html',
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
                                requestSensorId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('request-access', null, { reload: 'request-access' });
                }, function() {
                    $state.go('request-access');
                });
            }]
        })
        .state('request-access.edit', {
            parent: 'request-access',
            url: '/{id}/edit',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-access/request-access-dialog.html',
                    controller: 'RequestAccessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RequestAccess', function(RequestAccess) {
                            return RequestAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-access', null, { reload: 'request-access' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request-access.delete', {
            parent: 'request-access',
            url: '/{id}/delete',
            data: {
                //authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-access/request-access-delete-dialog.html',
                    controller: 'RequestAccessDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RequestAccess', function(RequestAccess) {
                            return RequestAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-access', null, { reload: 'request-access' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
