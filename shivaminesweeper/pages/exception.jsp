<%@ page isErrorPage="true" import="java.util.logging.Logger,java.util.logging.Level"%>
<%
	Logger logger = Logger.getLogger("exception.jsp");
	logger.log(Level.SEVERE, "Exception thrown!!", exception);
%>
<html>
	<head>
		<title> An exception has occurred...</title>
	</head>
	<body>
		<!-- Comment added to increase size of file beyond 512 bytes. Otherwise,
		IE doesn't render the same, though other browsers do so.
		fhlhfshflsadljdlkajflsjflsjdfljslfjasldjflsajdflasjfdlasjdflaks
		 -->
		<center>
			The exception has been logged and it should be fixed very soon.
			<br>Meanwhile, why not check other parts of the site
			<br> or why not check back after some time?
		</center>
	</body>
</html>