<html>
	<head>
		<title> Minesweeper </title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/homePage.css" type="text/css"></link>
		<script src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/homePage.js"></script>
	</head>
	<body>
		<!-- header table --> 
		<table class=headerTable>
			<tr>
				<td class=newGameCell>
					<a href="javascript:alert(1);" title="Click to start a game in the same mode. Access key is n." accesskey=n>
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
					<select id=selectMode title="Select a mode to generate a new game in that mode.">
						<option>Beginner</option>
						<option>Intermediate</option>
						<option>Advanced</option>
					</select>
				</td>
			</tr>
		</table>
		<!-- end of header table -->
		
		<!-- game board -->
		<center>
			<table width=300 id=board cellspacing=0>
				<tr> <!-- Row 1 -->
					<td>1</td>
					<td>2</td>
					<td>3</td>
					<td>4</td>
					<td>5</td>
					<td>6</td>
					<td>7</td>
					<td>8</td>
				</tr>
				<tr> <!-- Row 2 -->
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr> <!-- Row 3 -->
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr> <!-- Row 4 -->
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr> <!-- Row 5 -->
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr> <!-- Row 6 -->
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr> <!-- Row 7 -->
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr> <!-- Row 8 -->
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
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
	</body>
</html>