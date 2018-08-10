var app = angular.module('payvision', []);

// Table controller. Get data from external service and print out on the table
app.controller('payvisionController', function($scope, $http, $location) {
	$scope.listTransactions = [];
	$scope.listActions = [];
	$scope.listCurrencyCodes = [];
	$scope.listOrderBys = [];
    $scope.search = function() {
    	getTransactions();
    };
    
    $scope.orderByMe = function(x) {
        $scope.myOrderBy = x;
      }
	
	function getTransactions(){
		var url = new URL($location.absUrl() + "/transactions");
		if(!isEmpty($scope.selectedAction)) {
			url.searchParams.append('action',$scope.selectedAction);
		}
		if(!isEmpty($scope.selectedCurrencyCode)) {
			url.searchParams.append('currencyCode',$scope.selectedCurrencyCode);
		}
		if(!isEmpty($scope.selectedOrderBy)) {
			url.searchParams.append('orderBy',$scope.selectedOrderBy);
		}
		
		// do getting
		$http.get(url.toString()).then(function (response) {
			$scope.getDivAvailable = true;
			$scope.listTransactions = response.data;
		}, function error(response) {
			$scope.postResultMessage = "getTransactions Error Status: " +  response.statusText;
		});
	}
	
	function getActions(){
		var url = $location.absUrl() + "/filter?name=action";
		
		// do getting
		$http.get(url).then(function (response) {
			$scope.getDivAvailable = true;
			$scope.listActions = response.data;
		}, function error(response) {
			$scope.postResultMessage = "getActions Error Status: " +  response.statusText;
		});
	}
	
	function getCurrencyCodes(){
		var url = $location.absUrl() + "/filter?name=currencyCode";
		
		// do getting
		$http.get(url).then(function (response) {
			$scope.getDivAvailable = true;
			$scope.listCurrencyCodes = response.data;
		}, function error(response) {
			$scope.postResultMessage = "getCurrencyCodes Error Status: " +  response.statusText;
		});
	}
	
	function getOrderBys(){
		var url = $location.absUrl() + "/filter?name=orderBy";
		
		// do getting
		$http.get(url).then(function (response) {
			$scope.getDivAvailable = true;
			$scope.listOrderBys = response.data;
		}, function error(response) {
			$scope.postResultMessage = "getOrderBys Error Status: " +  response.statusText;
		});
	}
	
	function isEmpty(str) {
	    return (!str || 0 === str.length);
	}	
	
	getTransactions();
	getActions();
	getCurrencyCodes();
	getOrderBys();
});

// Expandable table row controller
app.controller('RowCtrl', function ($scope) {

    $scope.toggleRow = function () {
      $scope.selected = !$scope.selected;
    };

    $scope.isSelected = function (i) {
      return $scope.selected;
    };
});