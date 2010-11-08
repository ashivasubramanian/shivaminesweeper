<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title> Minesweeper </title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/homePage.css" type="text/css"></link>
		<script src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/homePage.js"></script>
	</head>
	<body>
		<form method=post>
			<!-- header table --> 
			<table class=headerTable>
				<tr>
					<td class=newGameCell>
						<a href="createBoard?mode=<s:property value="#parameters['mode']"/>" title="Click to start a game in the same mode. Access key is n." accesskey=n>
							New Game <!-- No need to under line the access key. It's already a link. -->
						</a>
						<br>
						<span class=sameModeText>
							(in same mode)
						</span>
					</td>
					<td>
						<label accesskey=C for=selectMode>
							<span class=accessKey>C</span>hange mode:
						</label>
						<s:select value="#parameters['mode']" list="#request['modes']" id="selectMode"/>
					</td>
				</tr>
			</table>
			<!-- end of header table -->
			
			<!-- game board -->
			<center>
				<s:hidden id="sessionId" value="%{#request['sessionId']}"/>
				<table width=300 id=board cellspacing=0>
					<s:iterator value="%{#session[#request['sessionId']].rows}" var="singleRow">
						<tr>
						<s:iterator value="#singleRow" var="cell">
							<td>
								<s:if test="cell.visible == true">
									<s:property value="cell.tileValue"/>
								</s:if>
								<s:else>
									&nbsp;
								</s:else>
							</td>
						</s:iterator>
						</tr>
					</s:iterator>
				</table>
	
				<table width=300>
					<!-- Time counter and mine counter fields -->
					<tr>
						<td class=fieldSpacer>&nbsp;</td>
						<td>Mines:</td>
						<td class=field>5</td>
						<td class=fieldSpacer>&nbsp;</td>
						<td>Time:</td>
						<td class=field>5</td>
						<td class=fieldSpacer>&nbsp;</td>
					</tr>
					<!-- Time counter and mine counter fields end here -->
				</table>
			</center>
			<!-- end of game board -->
	
			<div id=usage_div>
				<h3>Usage</h3>
				<ul>
					<li>
						<span>Select a tile</span> - Mouse over it or use the arrow keys
					</li>
					<li>
						<span>Mark/unmark a tile as suspect</span> - right click on it or Shift + spacebar 
					</li>
					<li>
						<span>Reveal a tile</span> - click on it or press the spacebar.
					</li>
				</ul>
			</div>
		</form>
	</body>
</html>