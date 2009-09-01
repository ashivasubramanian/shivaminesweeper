<%@ page errorPage="/pages/exception.jsp" %> 
<html>
	<head>
		<title> Minesweeper </title>
		<script language="javascript">
			function hideCells() {
				var gamerows = document.getElementsByTagName("table")[1].rows;
				for (i = 0; i < 8; i++) {
					var row = gamerows[i];
					for (j = 0; j < 8; j++) {
						row.cells[j].style.color = "cyan";
					}
				}
			}
			
			function unHide(cell) {
				cell.style.color = "#000000";
				/*gameTable = document.getElementById("gametable");
				count = 0;
				alert(gameTable.rows[0].cells.length);
				alert(gameTable.rows[0].cells[0].innerText);
				for (i = 0; i < gameTable.rows.length; i++) {
					row = gameTable.rows[i];
					for (j = 0 ; j < row.cells.length; j++) {
						if (row.cells[j].innerText == 'B')
					}
					
				}*/
				if (cell.innerText == 'B') {
					alert("game over..!!");
					location.reload(true);
				}
			}
		</script>
	</head>
	<body onload="javascript:hideCells();" style="font-family:Arial;font-size:10px;">
		<center>
			<table>
				<tr>
					<td style="text-align:center;">Minesweeper</td>
				</tr>
				
				<!-- Game part. This shall be a 8 * 8 puzzle. -->
				<tr>
					<td>
						<table border=1 width=300>
						<tbody style="background-color:cyan;" id=gametable>
							<tr> <!-- Row 1 -->
							<%
								char row[] = ((char[][])session.getAttribute("gameboard"))[0];
							%>
								<td onclick="javascript:unHide(this);"><%=row[0]%></td>
								<td onclick="javascript:unHide(this);"><%=row[1]%></td>
								<td onclick="javascript:unHide(this);"><%=row[2]%></td>
								<td onclick="javascript:unHide(this);"><%=row[3]%></td>
								<td onclick="javascript:unHide(this);"><%=row[4]%></td>
								<td onclick="javascript:unHide(this);"><%=row[5]%></td>
								<td onclick="javascript:unHide(this);"><%=row[6]%></td>
								<td onclick="javascript:unHide(this);"><%=row[7]%></td>
							</tr>
							<tr> <!-- Row 2 -->
							<%
								row = ((char[][])session.getAttribute("gameboard"))[1];
							%>
								<td onclick="javascript:unHide(this);"><%=row[0]%></td>
								<td onclick="javascript:unHide(this);"><%=row[1]%></td>
								<td onclick="javascript:unHide(this);"><%=row[2]%></td>
								<td onclick="javascript:unHide(this);"><%=row[3]%></td>
								<td onclick="javascript:unHide(this);"><%=row[4]%></td>
								<td onclick="javascript:unHide(this);"><%=row[5]%></td>
								<td onclick="javascript:unHide(this);"><%=row[6]%></td>
								<td onclick="javascript:unHide(this);"><%=row[7]%></td>
							</tr>
							<tr> <!-- Row 3 -->
							<%
								row = ((char[][])session.getAttribute("gameboard"))[2];
							%>
								<td onclick="javascript:unHide(this);"><%=row[0]%></td>
								<td onclick="javascript:unHide(this);"><%=row[1]%></td>
								<td onclick="javascript:unHide(this);"><%=row[2]%></td>
								<td onclick="javascript:unHide(this);"><%=row[3]%></td>
								<td onclick="javascript:unHide(this);"><%=row[4]%></td>
								<td onclick="javascript:unHide(this);"><%=row[5]%></td>
								<td onclick="javascript:unHide(this);"><%=row[6]%></td>
								<td onclick="javascript:unHide(this);"><%=row[7]%></td>
							</tr>
							<tr> <!-- Row 4 -->
							<%
								row = ((char[][])session.getAttribute("gameboard"))[3];
							%>
								<td onclick="javascript:unHide(this);"><%=row[0]%></td>
								<td onclick="javascript:unHide(this);"><%=row[1]%></td>
								<td onclick="javascript:unHide(this);"><%=row[2]%></td>
								<td onclick="javascript:unHide(this);"><%=row[3]%></td>
								<td onclick="javascript:unHide(this);"><%=row[4]%></td>
								<td onclick="javascript:unHide(this);"><%=row[5]%></td>
								<td onclick="javascript:unHide(this);"><%=row[6]%></td>
								<td onclick="javascript:unHide(this);"><%=row[7]%></td>
							</tr>
							<tr> <!-- Row 5 -->
							<%
								row = ((char[][])session.getAttribute("gameboard"))[4];
							%>
								<td onclick="javascript:unHide(this);"><%=row[0]%></td>
								<td onclick="javascript:unHide(this);"><%=row[1]%></td>
								<td onclick="javascript:unHide(this);"><%=row[2]%></td>
								<td onclick="javascript:unHide(this);"><%=row[3]%></td>
								<td onclick="javascript:unHide(this);"><%=row[4]%></td>
								<td onclick="javascript:unHide(this);"><%=row[5]%></td>
								<td onclick="javascript:unHide(this);"><%=row[6]%></td>
								<td onclick="javascript:unHide(this);"><%=row[7]%></td>
							</tr>
							<tr> <!-- Row 6 -->
							<%
								row = ((char[][])session.getAttribute("gameboard"))[5];
							%>
								<td onclick="javascript:unHide(this);"><%=row[0]%></td>
								<td onclick="javascript:unHide(this);"><%=row[1]%></td>
								<td onclick="javascript:unHide(this);"><%=row[2]%></td>
								<td onclick="javascript:unHide(this);"><%=row[3]%></td>
								<td onclick="javascript:unHide(this);"><%=row[4]%></td>
								<td onclick="javascript:unHide(this);"><%=row[5]%></td>
								<td onclick="javascript:unHide(this);"><%=row[6]%></td>
								<td onclick="javascript:unHide(this);"><%=row[7]%></td>
							</tr>
							<tr> <!-- Row 7 -->
							<%
								row = ((char[][])session.getAttribute("gameboard"))[6];
							%>
								<td onclick="javascript:unHide(this);"><%=row[0]%></td>
								<td onclick="javascript:unHide(this);"><%=row[1]%></td>
								<td onclick="javascript:unHide(this);"><%=row[2]%></td>
								<td onclick="javascript:unHide(this);"><%=row[3]%></td>
								<td onclick="javascript:unHide(this);"><%=row[4]%></td>
								<td onclick="javascript:unHide(this);"><%=row[5]%></td>
								<td onclick="javascript:unHide(this);"><%=row[6]%></td>
								<td onclick="javascript:unHide(this);"><%=row[7]%></td>
							</tr>
							<tr> <!-- Row 8 -->
							<%
								row = ((char[][])session.getAttribute("gameboard"))[7];
							%>
								<td onclick="javascript:unHide(this);"><%=row[0]%></td>
								<td onclick="javascript:unHide(this);"><%=row[1]%></td>
								<td onclick="javascript:unHide(this);"><%=row[2]%></td>
								<td onclick="javascript:unHide(this);"><%=row[3]%></td>
								<td onclick="javascript:unHide(this);"><%=row[4]%></td>
								<td onclick="javascript:unHide(this);"><%=row[5]%></td>
								<td onclick="javascript:unHide(this);"><%=row[6]%></td>
								<td onclick="javascript:unHide(this);"><%=row[7]%></td>
							</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>