
function drag(ev) {
    ev.dataTransfer.setData("dragged-id", ev.target.id);
    var rowIndex = ev.target.parentNode.parentNode.parentNode.rowIndex;
    ev.dataTransfer.setData("rowIndex", rowIndex);
}

function drop(ev) {
    if (ev.target.parentNode.parentNode.nodeName == "TR"
            && ev.target.parentNode.parentNode.rowIndex == ev.dataTransfer.getData("rowIndex")) {
        ev.preventDefault();
        var draggedID = ev.dataTransfer.getData("dragged-id");
        ev.target.appendChild(document.getElementById(draggedID));
    }
}
