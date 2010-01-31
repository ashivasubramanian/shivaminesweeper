$(document).ready(doEventBinding);
var row = 0;
var column = 0;
var mouseOverControl;

function doEventBinding() {
	$("#board td").bind("click", reveal);
	$("#board td").bind("mouseover", highlight);
	$("#board td").bind("mouseout", unhighlight);
	$(document).bind("keydown", determineAction);
	$("#selectMode").bind("change", changeMode); 
}

function reveal() {
	revealCell(this);
}

function highlight() {
	mouseOverControl = this;
	highlightCell(this);
}

function unhighlight() {
	unhighlightCell(this);
}

function revealCell(control) {
	$(control).css("background-color", "white");
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
	if (event.keyCode == 32) { //space
		revealCell(document.getElementById("board").rows[row].cells[column]);
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


function changeMode() {
	document.forms[0].action = "createBoard?mode=" + this.value;
	document.forms[0].submit();
}