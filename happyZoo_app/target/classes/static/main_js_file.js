var app = angular.module("AnimalManagement", ['ngDragDrop']);

//для модального окна
 app.directive('modalDialog', function() {
  return {
    restrict: 'E',
    scope: {
      show: '='
    },
    replace: true, 
    transclude: true, 
    link: function(scope, element, attrs) {
      scope.dialogStyle = {};
      if (attrs.width)
        scope.dialogStyle.width = attrs.width;
      if (attrs.height)
        scope.dialogStyle.height = attrs.height;
      scope.hideModal = function() {
        scope.show = false;
      };
    },
    template: "<div class='ng-modal' ng-show='show'><div class='ng-modal-overlay' ng-click='hideModal()'></div><div class='ng-modal-dialog' ng-style='dialogStyle'><div class='ng-modal-close' ng-click='hideModal()'>X</div><div class='ng-modal-dialog-content' ng-transclude></div></div></div>"
  };
});

// Контроллер
app.controller("AnimalController", function($scope, $http) {
	
	_refreshAnimalData();
	
    $scope.animals = [];
    $scope.animalForm = {
        id: "",
        name: "",
        kind: ""
        
    };
	$scope.animalLayout_generate = [];
	$scope.animalLayout = [];
	$scope.animalLayoutForm = {
        id: "",
        nameA: "",
        nameB: "",
        count: 0
    };

 	$scope._volerCount;

	$scope.modalShown = false;
    $scope.toggleModal = function() {
    $scope.modalShown = !$scope.modalShown;
   };


    $scope.generateAnimalsLayout = function() {
	
 		$http({
            method: 'GET',
            url: '/animals/generate'
        }).then(
            function(res) { // success
                $scope.animalLayout_generate = res.data;
				console.log($scope.animalLayout_generate);
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
	$scope._volerCount = "";
	$scope.volerCount();
		//$scope._volerCount
    };


 $scope.optionsList2 = {
		 
          accept: function(dragEl) {
		  console.log("Сработала функция: $scope.optionsList2");
 		 /* console.log("animalLayout:");
 		  console.log($scope.animalLayout);
 		  console.log("arr:");
 		  console.log($scope.arr);
 	      console.log("index:");
 		  console.log($scope.index);
 		  console.log("list2:");
 		  console.log($scope.list2);*/

		  for(var i = 0;i<$scope._volerCount; i++){
			
	 		 console.log("arr[i]:");
 		 	 console.log($scope.arr[i]);
		}
		  return true;
          }
       };


   $scope.volerCount = function() {
		console.log($scope._volerCount);
		$scope.arr = [];
		
		for(var i = 0;i<$scope._volerCount; i++){
			var list = [];
			$scope.arr[i] = list;
			console.log($scope.arr[i]);
		}
		
		
		console.log($scope.arr);
	};
	
	$scope.deleteAnimalFromV = function(item) {
		console.log("Hi from deleteAnimalFromV");
		console.log($scope.item);
    };

$scope.saveAnimalLayout = function(){
	
			
	
			method = "POST";
            url = '/animals/saveLayout';
            var f = 1;

			for(var i = 0; i<$scope._volerCount; i++){
				if ($scope.arr[i].length == 2){
					/*console.log($scope.arr[i][j]);*/
					$scope.animalLayoutForm.nameA = $scope.arr[i][0].name;
					console.log('animalLayoutForm.NameA: '+$scope.animalLayoutForm.nameA);
					$scope.animalLayoutForm.nameB = $scope.arr[i][1].name;
					$scope.animalLayoutForm.count = 1;
					console.log('animalLayoutForm.NameB: '+$scope.animalLayoutForm.nameB);
					console.log(angular.toJson($scope.animalLayoutForm));
					
					$http({
			            method: method,
			            url: url,
			            data: angular.toJson($scope.animalLayoutForm),
			            headers: {
			                'Content-Type': 'application/json'
			            }
			        }).then(_success, _error);
					}
			}
    };


    function _refreshAnimalData() {
        $http({
            method: 'GET',
            url: '/animals/findAll'
        }).then(
            function(res) { // success
                $scope.animals = res.data;
				console.log($scope.animals);
				
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    $scope.submitAnimal = function() {
 
 
            method = "POST";
            url = '/animals/save';
			console.log('animalForm:');
			console.log($scope.animalForm);
			
			/*for(var i = 0; i<$scope.animals.length; i++){
				if ($scope.animalForm.name === $scope.animals[i].name){
					alert($scope.animalForm.name + " уже есть в базе!");
					_clearFormData();
					return _error;
				}
			}0308*/
 
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.animalForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    };
 
    $scope.createAnimal = function() {
        _clearFormData();
    }
 

    $scope.deleteAnimal = function(animal) {
        $http({
            method: 'DELETE',
            url: '/animals/delete/' + animal.id
        }).then(_success, _error);
    };
 

    function _refreshAnimalData() {
        $http({
            method: 'GET',
            url: '/animals/findAll'
        }).then(
            function(res) { // success
                $scope.animals = res.data;
				console.log($scope.animals);
				
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }
 
    function _success(res) {
        _refreshAnimalData();
        _clearFormData();
    }
 
    function _error(res) {
        var data = res.data;
        var status = res.status;
        var header = res.header;
        var config = res.config;
        alert("Error: " + status + ":" + data);
    }
 
    // Clear the form
    function _clearFormData() {
        $scope.animalForm.name = "";
        $scope.animalForm.kind = ""
    };


});





