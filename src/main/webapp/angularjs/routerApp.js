angular.module('routerApp', [ 'ngRoute' ]).config(function($routeProvider) {
	console.log("aaaaa");
	$routeProvider.when('/home', {
		templateUrl : 'views/home.html'
	}).when('/userList', {
		templateUrl : 'views/userList.html',
		controller : 'userListCtrl'
	}).otherwise({
		redirectTo : '/home'
	});
}).controller('userListCtrl', function($scope) {
	console.log("bbbbb");
	$scope.userList = [ { 
		name : '미나',
		email : 'mina@gmail.com',
		regDate : '2012-03-12'
	}, {
		name : '제시카',
		email : 'jess@gmail.com',
		regDate : '2012-03-12'
	} ];
});