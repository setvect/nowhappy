<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Dark layout w/sidebar drawer menu</title>
<meta name="generator" content="Bootply" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet">
   <link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
   <script src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
   <script src="<c:url value="/js/bootstrap.js"/>"></script>
	<!--[if lt IE 9]>
		<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	<script type="text/javascript">
		$(document).ready(function(){  $('[data-toggle=offcanvas]').click(function() {
		    $('.row-offcanvas').toggleClass('active');
		  });
		});		
	</script>    
</head>
<body>
	<div class="page-container">

		<!-- top navbar -->
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="offcanvas" data-target=".sidebar-nav">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Project Name</a>
				</div>
			</div>
		</div>

		<div class="container">
			<div class="row row-offcanvas row-offcanvas-left">

				<!-- sidebar -->
				<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
					<div data-spy="affix" data-offset-top="45" data-offset-bottom="90">
						<ul class="nav" id="sidebar-nav">
							<li><a href="#">Home</a></li>
							<li><a href="#section1">Section 1</a></li>
							<li><a href="#section2">Section 2</a></li>
							<li><a href="#section3">Section 3</a></li>
							<li><a href="#section4">Section 4</a></li>
						</ul>
					</div>
				</div>

				<!-- main area -->
				<div class="col-xs-12 col-sm-9" data-spy="scroll" data-target="#sidebar-nav">
					<h1 id="section1">Why Bootstrap?</h1>
					<p>Twitter Bootstrap is a front-end toolkit to rapidly build web applications. It is a framework that uses some
						of the latest browser techniques to provide you with stylish typography, navigation, buttons, tables, etc. One of
						the primary changes in Bootstrap 3 is an emphasis on Mobile-First responsive design. The goal is to elevate the
						mobile experience to a first-class citizen of the design process, because several billion mobile users is quite a
						large market to tap into. So, sites built with the current Bootstrap version target mobile devices and scale for
						larger screens depending on screen size.</p>

					<p>Vestibulum porttitor massa eget pellentesque eleifend. Suspendisse tempor, nisi eu placerat auctor, est erat
						tempus neque, pellentesque venenatis eros lorem vel quam. Nulla luctus malesuada porttitor. Fusce risus mi, luctus
						scelerisque hendrerit feugiat, volutpat gravida nisi. Quisque facilisis risus in lacus sagittis malesuada.
						Suspendisse non purus diam. Nunc commodo felis sit amet tortor adipiscing varius. Fusce commodo nulla quis
						fermentum hendrerit. Donec vulputate, tellus sed venenatis sodales, purus nibh ullamcorper quam, sit amet
						tristique justo velit molestie lorem.</p>

					<h1 id="section2">Section 2</h1>
					<p>Fusce sollicitudin lacus lacinia mi tincidunt ullamcorper. Aenean velit ipsum, vestibulum nec tincidunt eu,
						lobortis vitae erat. Nullam ultricies fringilla ultricies. Sed euismod nibh quis tincidunt dapibus. Nulla quam
						velit, porta sit amet felis eu, auctor fringilla elit. Donec convallis tincidunt nibh, quis pellentesque sapien
						condimentum a. Phasellus purus dui, rhoncus id suscipit id, ornare et sem. Duis aliquet posuere arcu a ornare.
						Pellentesque consequat libero id massa accumsan volutpat. Fusce a hendrerit lacus. Nam elementum ac eros eu
						porttitor. Phasellus enim mi, auctor sit amet luctus a, commodo fermentum arcu. In volutpat scelerisque quam, nec
						lacinia libero.</p>

					<h1 id="section3">Section 3</h1>
					<p>Aliquam a lacinia orci, iaculis porttitor neque. Nullam cursus dolor tempus mauris posuere, eu scelerisque
						sem tincidunt. Praesent blandit sapien at sem pulvinar, vel egestas orci varius. Praesent vitae purus at ante
						aliquet luctus vel quis nibh. Mauris id nulla vitae est lacinia rhoncus a vel justo. Donec iaculis quis sapien vel
						molestie. Aliquam sed elementum orci. Vestibulum tristique tempor risus et malesuada. Sed eget ligula sed quam
						placerat dapibus. Integer accumsan ac massa at tempus.</p>

					<h1 id="section4">Section 4</h1>
					<p>Aliquam a lacinia orci, iaculis porttitor neque. Nullam cursus dolor tempus mauris posuere, eu scelerisque
						sem tincidunt. Praesent blandit sapien at sem pulvinar, vel egestas orci varius. Praesent vitae purus at ante
						aliquet luctus vel quis nibh. Mauris id nulla vitae est lacinia rhoncus a vel justo. Donec iaculis quis sapien vel
						molestie. Aliquam sed elementum orci. Vestibulum tristique tempor risus et malesuada. Sed eget ligula sed quam
						placerat dapibus. Integer accumsan ac massa at tempus.</p>


				</div>
				<!-- /.col-xs-12 main -->
			</div>
			<!--/.row-->
		</div>
		<!--/.container-->
	</div>
	<!--/.page-container-->
</body>
</html>