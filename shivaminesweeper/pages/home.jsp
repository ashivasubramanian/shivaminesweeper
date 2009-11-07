<html>
	<head>
		<title> Minesweeper </title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/homePage.css" type="text/css"></link>
	</head>
	<body>
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
	</body>
</html>