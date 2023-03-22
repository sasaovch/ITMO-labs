var graphColor = "";
var graphPointColor = "";

var graphCanvas = document.getElementById('graph');
var graph = graphCanvas.getContext('2d');
var toggleSwitch = document.querySelector('.switch input[type="checkbox"]');

var width = graphCanvas.width;
var height = graphCanvas.height;

var heightLine = 5;
var coef = 2.5;
var canvasR = width / coef;
var metrik= graphCanvas.width / 2;
var r;

var getCookie = name => {
    var cookies = document.cookie.split(';')
    for (let i = 0; i < cookies.length; i++) {
        let c = cookies[i].trim().split('=')
        if (c[0] === name) {
          return decodeURIComponent(c[1])
        }
    }
    return ''
}

function validateForm(form) {
    document.getElementById('warning').innerHTML = "";
    let y = document.getElementById("form:input_y_hinput").getAttribute("value");
    if (validateNumber(y, -5, 3)) {
        return true;
    } else {
        document.getElementById('warning').innerHTML = "Please, enter correct values";
        return false;
    }
}

function validateNumber(text, start, finish) {
    const numberPattern = /^[+-]?(\d*[.])?\d+$/;

    const number = parseFloat(text);
    if (Number.isNaN(number)
        || !numberPattern.test(text)) {
        return false;
    } else {
        return start <= number && number <= finish;
    }
}

function checkTheme() {
    if (getCookie('theme') == 'dark-theme') {
        document.body.className = 'dark-theme';
        graphColor = 'white'
    } else {
        graphColor = 'blue';
        document.body.className = 'light-theme';
    }
}

function switchTheme() {
    if (getCookie('theme') == 'light-theme') {
        document.body.className = 'dark-theme';
        graphColor = 'white'
        document.cookie = 'theme=dark-theme';
    }
    else {
        document.body.className = 'light-theme';
        graphColor = 'blue';
        document.cookie = 'theme=light-theme';
    }    
    drawGraph();
}

checkTheme();
document.getElementById('form:input_x').value = 0;
toggleSwitch.addEventListener('change', switchTheme, false);
document.getElementById("form").onsubmit = validateForm;
window.onload = initialilzeGraph();

function convertXToRadiusOf(x, r) {
    return ((x - width / 2) / canvasR) * r;
}

function convertYToRadiusOf(y, r) {
    return ((height / 2 - y) / canvasR) * r;
}

function drawLine(graph, startX, startY, endX, endY) {
    graph.moveTo(startX, startY);
    graph.lineTo(endX, endY);
    graph.stroke(); 
}

function fillText(graph, text, coordX, coordY) {
    graph.fillText(text, coordX, coordY);
}

function drawGraph() {

    graph.strokeStyle = graphColor;
    graph.fillStyle = graphColor;
    graph.clearRect(-metrik, -metrik, metrik * 2, metrik * 2);
    graph.globalAlpha = 1;
    graph.beginPath();

    // draw x and y
    drawLine(graph, -metrik, 0, metrik, 0);
    drawLine(graph, 0, -metrik, 0, metrik);
    
    // draw strokes on x
    drawLine(graph, -(canvasR), -heightLine, -(canvasR), heightLine);
    drawLine(graph, -(canvasR / 2), -heightLine, -(canvasR / 2), heightLine);
    drawLine(graph, (canvasR), -heightLine, (canvasR), heightLine);
    drawLine(graph, (canvasR / 2), -heightLine, (canvasR / 2), heightLine);
    drawLine(graph, -(canvasR * 2), -heightLine, -(canvasR * 2), heightLine);
    drawLine(graph, -(canvasR * 1.5), -heightLine, -(canvasR * 1.5), heightLine);
    drawLine(graph, (canvasR * 2), -heightLine, (canvasR * 2), heightLine);
    drawLine(graph, (canvasR * 1.5), -heightLine, (canvasR * 1.5), heightLine);
    
    // draw strokes on y
    drawLine(graph, -heightLine, -(canvasR), heightLine, -(canvasR));
    drawLine(graph, -heightLine, -(canvasR / 2), heightLine, -(canvasR / 2));
    drawLine(graph, -heightLine, (canvasR), heightLine, (canvasR));
    drawLine(graph, -heightLine, (canvasR / 2), heightLine, (canvasR / 2));
    drawLine(graph, -heightLine, -(canvasR * 2), heightLine, -(canvasR * 2));
    drawLine(graph, -heightLine, -(canvasR * 1.5), heightLine, -(canvasR * 1.5));
    drawLine(graph, -heightLine, (canvasR * 2), heightLine, (canvasR * 2));
    drawLine(graph, -heightLine, (canvasR * 1.5), heightLine, (canvasR * 1.5));
    
    //draw arrows
    drawLine(graph, metrik, 0, metrik * 0.9, -heightLine * 2);
    drawLine(graph, metrik, 0, metrik * 0.9, heightLine * 2);
    drawLine(graph, 0, -metrik, heightLine * 2, -metrik * 0.9);
    drawLine(graph, 0, -metrik, -heightLine * 2, -metrik * 0.9);
    
    graph.beginPath();
    graph.font = "16px Arial blod";
    fillText(graph, "x", (metrik * 0.9), -heightLine * 3);
    fillText(graph, "y", heightLine * 3, -(metrik * 0.9));
    
    fillText(graph, "-R", -(canvasR), heightLine * 4);
    fillText(graph, "-R/2", -(canvasR / 2), heightLine * 4);
    fillText(graph, "R", (canvasR), heightLine * 4);
    fillText(graph, "R/2", (canvasR / 2), heightLine * 4);
    
    fillText(graph, "R", -heightLine * 6, -(canvasR));
    fillText(graph, "R/2", -heightLine * 6, -(canvasR / 2));
    fillText(graph, "-R", -heightLine * 6, (canvasR));
    fillText(graph, "-R/2", -heightLine * 6, (canvasR / 2));

    // draw rectangle
    graph.beginPath();
    graph.globalAlpha = 0.3;
    graph.fillStyle = "blue";
    graph.fillRect(0, 0, (canvasR), -(canvasR / 2)); 
    
    // draw sphere
    graph.arc(0, 0, (canvasR), Math.PI / 2, Math.PI);
    graph.lineWidth = 0;
    graph.fill();
    graph.stroke();
    
    graph.beginPath();
    graph.moveTo(0, 0);
    graph.lineTo(-(canvasR), 0);
    graph.lineTo(0, (canvasR));
    graph.fill();

    // draw triangle
    graph.beginPath();
    graph.moveTo(0, 0);
    graph.lineTo(-(canvasR) / 2, 0);
    graph.lineTo(0, -(canvasR) / 2);
    graph.fill();

    graph.globalAlpha = 0.5;
    drawDots();
}

function convertXToCanvasCoordinate(x, r) {
    return (x / r * canvasR);
}

function convertYToCanvasCoordinate(y, r) {
    return (-y / r * canvasR);
}

function drawDots() {
    let dots = document.getElementById("results").tBodies[0];
    r = document.getElementById("form:input_r_input").getAttribute("aria-valuenow");
    for (let i = 0; i < dots.rows.length; i++) {
        let dot = dots.rows.item(i).cells;
        const x = convertXToCanvasCoordinate(dot.item(1).innerHTML, r);
        const y = convertYToCanvasCoordinate(dot.item(2).innerHTML, r);
        if (dot.item(4).innerHTML === "true") {
            graph.fillStyle = "#7CFC00";
            graph.strokeStyle = "#7CFC00";
        } else {
            graph.fillStyle = "#FF0000";
            graph.strokeStyle = "#FF0000";
        }    
        graph.beginPath();
        graph.arc(x, y, heightLine, 0, Math.PI * 2);
        graph.fill();
    }
}

function setOnMouseMove() {
    graphCanvas.onmousemove = (e) => {
        drawGraph();
        graph.fillStyle = "#00BFFF";
        graph.strokeStyle = "#00BFFF";
        graph.beginPath();
        graph.arc(e.offsetX - metrik, e.offsetY - metrik, heightLine, 0, Math.PI*2);
        graph.fill();
    };

    graphCanvas.onmouseleave = (e) => {
        drawGraph();
    };

    graphCanvas.onmousedown = function(event) {
        document.getElementById('warning').innerHTML = "";
    
        let r = parseFloat(document.getElementById("form:input_r_input").getAttribute("aria-valuenow"));
        let x = convertXToRadiusOf(event.offsetX, r).toFixed(3);
        let y = convertYToRadiusOf(event.offsetY, r).toFixed(3);

        if (!validateNumber(y, -5, 3)) {
            document.getElementById("warning").innerHTML = "Please, click on rigth value for Y";
            return;
        } else if (!validateNumber(parseFloat(x), -5, 3)) {
            document.getElementById("warning").innerHTML = "Please, click on rigth value for X";
            return;
        }
    
        document.getElementById('form:input_x').value = x;
        document.getElementById("form:input_y_hinput").setAttribute("value", y);
    
        document.getElementById("form:submit").disabled = false;
        document.getElementById("form:submit").click();
        document.getElementById("form").reset();
    };
}

function initialilzeGraph() {
    graph.translate(metrik, metrik);
    drawGraph();
    setOnMouseMove();
}

function setColors(colorGraph, colorPoint) {
    graphColor = colorGraph;
    graphPointColor = colorPoint;
    drawGraph();
}

function changeValueForR(some) {
    r = document.getElementById(some).getAttribute("aria-valuenow");
    drawGraph();
}

function changeValueForX(id) {
    document.getElementById('form:input_x').value = getValueFromRadio();
}

function getValueFromRadio(){
    var searchType = document.getElementsByName("form:x");
    var a = (searchType[0].checked);
    var b = (searchType[1].checked);
    var c = (searchType[2].checked);
    var d = (searchType[3].checked);
    var e = (searchType[4].checked);
    var f = (searchType[5].checked);
    var g = (searchType[6].checked);
    var h = (searchType[7].checked);
    var i = (searchType[8].checked);
    if (a){
        return -3;
    }
    if (b){
        return -2;
    }
    if (c){
        return -1;
    }
    if (d){
        return 0;
    }
    if (e){
        return 1;
    }
    if (f){
        return 2;
    }
    if (g){
        return 3;
    }
    if (h){
        return 4;
    }
    if (i){
        return 5;
    }
    return 0;
} 