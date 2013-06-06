<!DOCTYPE HTML>
<html>
  <head>
    <title>Traffic Lights</title>
    <style>
            body {
            	background-image:url('Traffic_light1.jpg');
            }
            image {
                border: 0;
            }
            a {
            	font-family: "Arial";
            	font-size: 40px;
            	text-decoration: none
            }
            .start {
            	color: Green;
            }
            .stop {
            	color: Red;
            }
            .form {
            	position:absolute; 
            	bottom:0;
            }
    </style>
  </head>
  <body>
	<p>
		<p><a class="start" href="/start">Start &gt;</a></p>
		<p><a class="stop" href="/stop">Stop [ ]</a></p>
		<div class="form">
        <form action="/setDelay" method="POST">
            <input type="text" name="delay"/>
            <input type="submit" value="Set Delay"/>
        </form>
        </div>
	</p>
  </body>
</html>