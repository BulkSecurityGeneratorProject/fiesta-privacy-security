(function() {
    'use strict';

    angular
        .module('fiestaSecurityUiApp')
        .controller('AccessLogDialogController', AccessLogDialogController);

    AccessLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AccessLog'];

    function AccessLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AccessLog) {
        var vm = this;

        vm.accessLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.accessLog.id !== null) {
                AccessLog.update(vm.accessLog, onSaveSuccess, onSaveError);
            } else {
                AccessLog.save(vm.accessLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaSecurityUiApp:accessLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.accessTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
