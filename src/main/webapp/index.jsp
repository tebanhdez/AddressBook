<html>
<body>
<header>
<h2>Flatirons Address Book</h2>
</header>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script>
var app = angular.module("addressBook", []); 
app.controller("myCtrl", function($scope, $http) {
    $scope.addresses = [];
    $scope.addItem = function () {
        $scope.addresses.push($scope.addMe);
    }
    $scope.removeItem = function (address) {
    	$http.delete("/rest/address/"+address.id, address)
        .success(function (data, status, headers, config){
        	$scope.listAddresses();
        })
        .error(function (data, status, headers, config){
        	$scope.listAddresses();
        });
    }

    $scope.listAddresses = function() {
        $http.get('/rest/address').then(function(response) {
            $scope.addresses = response.data;
        });
    };
    $scope.listAddresses();

    $scope.updateAddress = function(address){
        $scope.address = address;
    }

    $scope.update = function(address){
    	$http.put("/rest/address", address)
        .success(function (data, status, headers, config){
        	$scope.listAddresses();
        	$scope.address = {personName:'', personLastName:'', streetName:'', zipCode:'', city:''}
        })
        .error(function (data, status, headers, config){
        	$scope.listAddresses();
        });
    };
    $scope.reset = function(){
    	$scope.address = {personName:'', personLastName:'', streetName:'', zipCode:'', city:''}
    };
});
</script>

<div ng-app="addressBook" ng-controller="myCtrl">

<section id="sidebar1">
    <h2>Add new contact:</h2>
    <form novalidate class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label" for="personName">Person Name:</label>
            <div class="col-sm-10">
                <input id="personName" type="text" ng-model="address.personName" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="personLastName">Person LastName:</label>
            <div class="col-sm-10">
                <input id="personLastName" type="text" ng-model="address.personLastName" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="streetName">Street Name:</label>
            <div class="col-sm-10">
                <input id="streetName" type="text" ng-model="address.streetName" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="ZipCode">ZipCode:       </label>
            <div class="col-sm-10">
                <input id="ZipCode" type="text" ng-model="address.zipCode" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="city">City:             </label>
            <div class="col-sm-10">
                <input id="city" type="text" ng-model="address.city" />
            </div>
        </div>
        <input type="button" ng-click="reset()" value="Reset" />
        <input type="submit" ng-click="update(address)" value="Save" />
  </form>
  </section>
<section id="main">
    </br>
    <ul>
        <li ng-repeat="address in addresses">
	        <div>
		        <dl class="dl-horizontal">
				  <address>
					  <strong>{{address.personLastName}}, {{address.personName}}</strong><br>
					  {{address.streetName}}<br>
					  {{address.city}}, {{address.zipCode}}<br>
					  <button ng-click="updateAddress(address)">Edit</button>
					  <button ng-click="removeItem(address)">Delete</button>
                  </address>
	        </div>
        </li>
    </ul>
</section>
</div>
</body>
<style>
html {
    margin: 0;
    padding: 0;
}
body {
    width: 940px;
    margin: 0 auto;
    font: 10pt/1.5em Helvetica,"Helvetica neue", Arial, sans-serif;
}

/*HTML 5 specific*/
header,section,article,aside,footer{
    display: block;
}
section{
    float: right;
    width: 428px;
    padding-left: 20px;
    margin: 0 0 20px 0px;
    border-left: 2px dotted #b2a497;

}
</style>
</html>
