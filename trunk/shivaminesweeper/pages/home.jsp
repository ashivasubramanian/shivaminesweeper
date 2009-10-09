<html>
	<head>
		<title> Minesweeper </title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/homePage.css" type="text/css"></link>
	</head>
	<body>
		<table class=headerTable>
			<tr>
				<td class=newGameCell>
					<a href="#">New Game</a>
					<br>
					<span class=sameModeText>
						(in same mode)
					</span>
				</td>
				<td>
					<label abbr="S" for=selectMode>Change mode:</span>
					<select id=selectMode>
						<option>Beginner</option>
						<option>Intermediate</option>
						<option>Advanced</option>
					</select>
				</td>
			</tr>
		</table>
	</body>
</html>