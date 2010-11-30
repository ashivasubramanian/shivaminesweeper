$(document).ready(doEventBinding);
var row = 0;
var column = 0;
var mouseOverControl;
var isFirstRevealAMine = false;

function doEventBinding() {
	$("#board td").bind("click", reveal);
	$("#board td").bind("mouseover", highlight);
	$("#board td").bind("mouseout", unhighlight);
	$(document).bind("keydown", determineAction);
	$("#selectMode").bind("change", changeMode); 
	$(document).bind("contextmenu", function(event) { return false; });
	$("#board td").bind("mouseup", markTile);
}

function reveal() {
	$.getJSON("http://localhost:8080/mine/getMineCount", {"row":row, "column":column}, revealCell);
}	

function highlight() {
	mouseOverControl = this;
	findRowAndColumnValues();
	highlightCell(this);
}

function unhighlight() {
	unhighlightCell(this);
}

function revealCell(data) {
	if (data.status && data.status == "game_over") {
		if (!isFirstRevealAMine) {
			alert("You clicked first on a mine!! Presenting a new board for you...");
			document.forms[0].action = "http://localhost:8080/mine";
			document.forms[0].submit();
		}
		$("#board td").unbind("click");
		$("#board td").unbind("mouseover");
		$("#board td").unbind("mouseout");
		$(document).unbind("keydown");
		$("#board td").unbind("mouseup");
		alert("You stepped on a mine!! \nGAME OVER!!");
	} else if (data.contiguous) {
		if (!isFirstRevealAMine) {
			isFirstRevealAMine = true;
		} 
		for (i = 0; i < data.contiguous.length; i++) {
			var table = document.getElementById("board");
			table.rows[data.contiguous[i].x].cells[data.contiguous[i].y].style.backgroundColor = "white";
		}
	} else {
		if (!isFirstRevealAMine) {
			isFirstRevealAMine = true;
		}
		$(mouseOverControl).css("background-color", "white");
		$(mouseOverControl).text(data.mineCount);
		$(mouseOverControl).css("color", data.colour);
	}
}

function highlightCell(control) {
	var current_control = document.getElementById("board").rows[row].cells[column];
	unhighlightCell(current_control);
	$(control).css("border", "1px white solid");
}

function unhighlightCell(control) {
	$(control).css("border", "1px black solid");
}

function determineAction(event) {
	findRowAndColumnValues();
	if (event.keyCode == 32) { //space
		if(event.shiftKey) {
			var control = document.getElementById("board").rows[row].cells[column];
			if (control.innerHTML.indexOf("<img") >= 0) {
				$.get("http://localhost:8080/mine/unmarkTile", {"row":row, "column":column}, showAsUnmarkedTile);
			} else {
				$.get("http://localhost:8080/mine/markTile", {"row":row, "column":column}, showAsMarkedTile);
			}
			return;
		}
		reveal();
	} else if (event.keyCode == 37) { //Left arrow
		unhighlightCell(document.getElementById("board").rows[row].cells[column]);
		column--;
		highlightCell(document.getElementById("board").rows[row].cells[column]);
	} else if (event.keyCode == 39) { // Right arrow
		unhighlightCell(document.getElementById("board").rows[row].cells[column]);
		column++;
		highlightCell(document.getElementById("board").rows[row].cells[column]);
	} else if (event.keyCode == 38) { // Up arrow
		unhighlightCell(document.getElementById("board").rows[row].cells[column]);
		row--;
		highlightCell(document.getElementById("board").rows[row].cells[column]);
	} else if (event.keyCode == 40) { //Down arrow
		unhighlightCell(document.getElementById("board").rows[row].cells[column]);
		row++;
		highlightCell(document.getElementById("board").rows[row].cells[column]);
	}
	mouseOverControl = document.getElementById("board").rows[row].cells[column];
}

function findRowAndColumnValues() {
	if (mouseOverControl != undefined) {
		for (i = 0; i < document.getElementById("board").rows.length; i++) {
			var single_row = document.getElementById("board").rows[i];
			for (j = 0 ; j < single_row.cells.length; j++) {
				var single_cell = single_row.cells[j];
				if (single_cell == mouseOverControl) {
					row = i;
					column = j;
					break;
				}
			}
		}
	}
}
function changeMode() {
	document.forms[0].action = "createBoard?mode=" + this.value;
	document.forms[0].submit();
}

function markTile(event) {
	if (event.button == 2) {
			$.get("http://localhost:8080/mine/markTile", {"row":row, "column":column}, showAsMarkedTile);
	}
}

function unmarkTile(event) {
	if (event.button == 2) {
		$.get("http://localhost:8080/mine/unmarkTile", {"row":row, "column":column}, showAsUnmarkedTile);
	}
}
function showAsMarkedTile(data) {
	$(mouseOverControl).html("<img src='http://localhost:8080/mine/images/flag.jpg'/>");
	var currentFlaggedCount = parseInt($("#mineCount").text()); 
	$("#mineCount").text(currentFlaggedCount - 1);
	$(mouseOverControl).unbind("mouseup");
	$(document).unbind("keyup");
	$(mouseOverControl).bind("mouseup", unmarkTile);
	currentFlaggedCount = parseInt($("#mineCount").text()); 
	if (currentFlaggedCount == 0) {
		$.getJSON("http://localhost:8080/mine/validateBoard", handleValidationResult);
	}
}

function showAsUnmarkedTile(data) {
	$(mouseOverControl).html("&nbsp");
	var currentFlaggedCount = parseInt($("#mineCount").text()); 
	$("#mineCount").text(currentFlaggedCount + 1);
	$(mouseOverControl).unbind("mouseup");
	$(document).unbind("keyup");
	$(mouseOverControl).bind("mouseup", markTile);
	currentFlaggedCount = parseInt($("#mineCount").text()); 
	if (currentFlaggedCount == 0) {
		$.getJSON("http://localhost:8080/mine/validateBoard", handleValidationResult);
	}
}

function handleValidationResult(data) {
	if (data.validity) {
		$("#board td").unbind("click");
		$("#board td").unbind("mouseover");
		$("#board td").unbind("mouseout");
		$(document).unbind("keydown");
		$("#board td").unbind("mouseup");
		alert("CONGRATULATIONS!!\nYou have marked all mines!!");
	} else if (!data.validity) {
		alert("Validation fails..!!");
	}
}
