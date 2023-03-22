var metrik;
const heightLine = 5;
const coef = 2.5;


var graphCanvas;
var graph;
var graphColor;
var graphPointColor;
var dots = [];
var canvasR;
export {dots};

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

    graph.beginPath();
    graph.globalAlpha = 0.3;
    graph.fillStyle = "blue";
    graph.fillRect(-(canvasR), (canvasR), (canvasR), -(canvasR)); 
    
    graph.arc(0, 0, (canvasR), Math.PI, Math.PI * 3 / 2);
    graph.lineWidth = 0;
    graph.fill();
    graph.stroke();
    
    graph.beginPath();
    graph.moveTo(0, 0);
    graph.lineTo(-(canvasR), 0);
    graph.lineTo(0, -(canvasR));
    graph.fill();
    
    graph.beginPath();
    graph.moveTo(0, 0);
    graph.lineTo((canvasR / 2), 0);
    graph.lineTo(0, (canvasR));
    graph.fill();

    drawDots();
}

function convertXToCanvasCoordinate(x, r) {
    return (x / r * canvasR);
}

function convertYToCanvasCoordinate(y, r) {
    return (-y / r * canvasR);
}

function drawDots() {
    dots.forEach((dot) => {
        const x = convertXToCanvasCoordinate(dot.x, dot.r);
        const y = convertYToCanvasCoordinate(dot.y, dot.r);
        graph.fillStyle = graphPointColor;
        graph.strokeStyle = graphPointColor;
        graph.beginPath();
        graph.arc(x, y, heightLine, 0, Math.PI * 2);
        graph.fill();
    })
}

function setOnMouseMove() {
    graphCanvas.onmousemove = (e) => {
        drawGraph();
        graph.fillStyle = graphPointColor;
        graph.strokeStyle = graphPointColor;
        graph.beginPath();
        graph.arc(e.offsetX - metrik, e.offsetY - metrik, heightLine, 0, Math.PI*2);
        graph.fill();
    }

    graphCanvas.onmouseleave = (e) => {
        drawGraph();
    };
}

function initialilzeGraph(colorGraph, colorPoint) {
    graphCanvas = document.getElementById('graph');
    graph = graphCanvas.getContext('2d');
    metrik = graphCanvas.width / 2;
    graph.translate(metrik, metrik);
    graphColor = colorGraph;
    graphPointColor = colorPoint;
    canvasR = metrik / coef;
    drawGraph();
    setOnMouseMove();
}

function setColors(colorGraph, colorPoint) {
    graphColor = colorGraph;
    graphPointColor = colorPoint;
    drawGraph();
}

function paintPoints(points) {
    while(dots.length > 0) {
        dots.pop();
    }
    points.forEach((point) => {dots.push(point)});
    drawGraph();
}

export {setColors, initialilzeGraph, paintPoints};