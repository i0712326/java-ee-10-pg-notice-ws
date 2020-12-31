<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Clients</title>
</head>
<body>
<h2>Message from PostgreSQL is below.</h2>
<div id="msg">
</div>
<script language="javascript" type="text/javascript">

	var wsUri = "ws://localhost:8080/java-ee-10-web/endpoint";
	var ws = new WebSocket(wsUri);
	ws.onopen = function() {
		console.log("Web Socket is connected!!");
		setInterval(function(){ws.send("keep alive .... !");},10000);
		
	};
	ws.onmessage = function(evt) {
		var msg = evt.data;
		console.log("Message received:" + msg);
		var tag = document.createElement("p");
		var text = document.createTextNode(msg);

		tag.appendChild(text);
		
		var element = document.getElementById("msg");
		element.appendChild(tag);
	};
	ws.onclose = function() {
		console.log("Connection is closed...");
	};

	
</script>
</body>
</html>