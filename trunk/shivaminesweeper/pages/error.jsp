<%@ page isErrorPage="true" import="java.util.logging.Logger,java.util.logging.Level"%>
<%
	Logger logger = Logger.getLogger("error.jsp");
	String errorStr = "404 error!! The URL requested was : " + request.getAttribute("javax.servlet.forward.servlet_path");
	logger.log(Level.SEVERE, errorStr, exception);
%>
<html>
	<head>
		<title> Error 404 </title>
	</head>
	<body>
	<!-- Comment added to increase size of file beyond 512 bytes. Otherwise, IE
	doesn't render the same, though other browsers do so.
	fhlhfshflsadljdlkajflsjflsjdfljslfjasldjflsajdflasjfdlasjdflaks
	fhlhfshflsadljdlkajflsjflsjdfljslfjasldjflsajdflasjfdlasjdflaks
	 -->
		<center>
			The particular resource was not found!! Either the URL you have entered is wrong
			or there is a programming error. The error has been logged
			and it should be fixed very soon. Why not check after some time?
		</center>
	</body>
</html>