<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Example - example-example52-production</title>
  

 <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
  

  
</head>
<body ng-app="compileExample">
  <script>
  angular.module('compileExample', [], function($compileProvider) {
    // configure new 'compile' directive by passing a directive
    // factory function. The factory function injects the '$compile'
    $compileProvider.directive('compile', function($compile) {
      // directive factory creates a link function
      return function(scope, element, attrs) {
        scope.$watch(
          function(scope) {
             // watch the 'compile' expression for changes
            return scope.$eval(attrs.compile);
          },
          function(value) {
            // when the 'compile' expression changes
            // assign it into the current DOM
            element.html(value);

            // compile the new DOM and link it to the current
            // scope.
            // NOTE: we only compile .childNodes so that
            // we don't get into infinite loop compiling ourselves
            $compile(element.contents())(scope);
          }
        );
      };
    });
  })
  .controller('GreeterController', ['$scope', function($scope) {
    $scope.name = 'Angular';
    $scope.html = 'Hello {{name}}';
  }]);
</script>
<div ng-controller="GreeterController">
  <input ng-model="name"> <br/>
  <textarea ng-model="html"></textarea> <br/>
  <div compile="html"></div>
</div>
</body>
</html>