function drawLine(graph, startX, startY, endX, endY) {
    graph.moveTo(startX, startY);
    graph.lineTo(endX, endY);
    graph.stroke(); 
}

function fillText(graph, text, coordX, coordY) {
    graph.fillText(text, coordX, coordY);
}

function drawGraph(fillStyle) {
    graph.strokeStyle = graphColor;
    graph.fillStyle = graphColor;
    graph.clearRect(-200, -200, 400, 400);
    graph.globalAlpha = 1;
    graph.beginPath();
    drawLine(graph, -200, 0, 200, 0);
    drawLine(graph, 0, -200, 0, 200);
    
    drawLine(graph, -160, -5, -160, 5);
    drawLine(graph, -80, -5, -80, 5);
    drawLine(graph, 80, -5, 80, 5);
    drawLine(graph, 160, -5, 160, 5);
    
    drawLine(graph, -5, -160, 5, -160);
    drawLine(graph, -5, -80, 5, -80);
    drawLine(graph, -5, 80, 5, 80);
    drawLine(graph, -5, 160, 5, 160);
    
    drawLine(graph, 200, 0, 190, -10);
    drawLine(graph, 200, 0, 190, 10);
    drawLine(graph, 0, -200, 10, -190);
    drawLine(graph, 0, -200, -10, -190);
    
    graph.beginPath();
    graph.font = "14px Arial blod";
    fillText(graph, "x", 180, -10);
    fillText(graph, "y", 10, -180);
    
    fillText(graph, "-R", -160, 20);
    fillText(graph, "-R/2", -80, 20);
    fillText(graph, "R/2", 80, 20);
    fillText(graph, "R", 160, 20);
    
    fillText(graph, "R", -30, -160);
    fillText(graph, "R/2", -30, -80);
    fillText(graph, "-R", -30, 160);
    fillText(graph, "-R/2", -30, 80);

    graph.beginPath();
    graph.globalAlpha = 0.3;
    graph.fillStyle = "blue";
    graph.fillRect(-160, 80, 160, -80); 
    
    graph.arc(0, 0, 160, Math.PI, Math.PI * 3 / 2);
    graph.lineWidth = 0;
    graph.fill();
    graph.stroke();
    
    graph.beginPath();
    graph.moveTo(0, 0);
    graph.lineTo(-160, 0);
    graph.lineTo(0, -160);
    graph.fill();
    
    graph.beginPath();
    graph.moveTo(0, 0);
    graph.lineTo(80, 0);
    graph.lineTo(0, -80);
    graph.fill();
}

function checkTheme() {
    if (document.body.className == 'dark-theme') {
        graphColor = 'white';
    } else {
        graphColor = 'blue';
    }
}

function switchTheme(e) {
    if (document.body.className == 'dark-theme') {
        document.body.className = 'light-theme';
        graphColor = 'blue';
        document.cookie = 'theme=' + 'light-theme';
        drawGraph();
    }
    else {
        document.body.className = 'dark-theme';
        graphColor = 'white'
        document.cookie = 'theme=' + 'dark-theme';
        drawGraph();
    }    
}

function checkAndSendForm(el) {
    var x = el.x.value;
    var y = el.y.value;
    var r = getCheckboxValue();
    console.log(x + " " + y + " " + r);
    if (x == "") {
        document.getElementById("warning").innerHTML = "Please, select X";
    } else if (y == "") {
        document.getElementById("warning").innerHTML = "Please, enter rigth value for Y";
    } else if (r == 0) {
        document.getElementById("warning").innerHTML = "Please, choose only ONE box for R";
    } else {
        document.getElementById("warning").innerHTML = "";
        return true;
    }
    return false;
}

function getCheckboxValue() {  
  
    var l1 = document.getElementById("r-1");  
    var l2 = document.getElementById("r-2");  
    var l3 = document.getElementById("r-3");  
    var l4 = document.getElementById("r-4");  
    var l5 = document.getElementById("r-5");  

    var countTrue = 0;
    var res = 0;

    if (l1.checked == true){  
        countTrue += 1;
        res = parseInt(document.getElementById("r-1").value);
    }   
    if (l2.checked == true){  
        countTrue += 1;
        res = parseInt(document.getElementById("r-2").value);
    }  
    if (l3.checked == true){  
        countTrue += 1;
        res = parseInt(document.getElementById("r-3").value);
    }  
    if (l4.checked == true){  
        countTrue += 1;
        res = parseInt(document.getElementById("r-4").value);  
    }  
    if (l5.checked == true){  
        countTrue += 1;
        res = parseInt(document.getElementById("r-5").value);  
    }
    if (countTrue == 1) {
        return res;
    } else {
        return 0;
    }
}

var graphColor = "";
checkTheme();
const toggleSwitch = document.querySelector('.switch input[type="checkbox"]');
const graphCanvas = document.getElementById('graph');
const graph = graphCanvas.getContext('2d');
graph.translate(200, 200);
const height = graphCanvas.height;
const width = graphCanvas.width;
toggleSwitch.addEventListener('change', switchTheme, false);
window.onload = drawGraph();

// graphCanvas.onmousemove = (e) => {
//     drawGraph();
//     graph.fillStyle = graphColor;
//     graph.strokeStyle = graphColor;
//     graph.beginPath();
//     graph.arc(e.offsetX - 200, e.offsetY - 200, 5, 0, Math.PI*2);
//     graph.fill();
// }

graphCanvas.onmouseleave = drawGraph;

// graphCanvas.onmousedown = (e) => {
//     /** @type {HTMLFormElement} */
//     const form = document.getElementById("form");
//     let r = getCheckboxValue();
//     if (r == 0) {
//         document.getElementById('warning').innerHTML = "Please, select ONE R";
//         return;
//     }
//     let x = Math.round(((e.offsetX - 200) / width / 0.6 * r * 1.5) * 100) / 100;
//     let y = Math.round(((e.offsetY - 200) / width / 0.6 * r * 1.5 * 100)) / 100;
//     document.getElementById('x-10').disabled = false;
//     document.getElementById('x-10').value = x;
//     form['x'].value = x;
//     form['y'].value = y;
//     form.submit();
// }
