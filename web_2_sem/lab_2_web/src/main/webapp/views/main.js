import {setColors, initialilzeGraph, paintPoints} from "./drawGraph.js";
import {bindDataSendingButtons} from "./send_data.js";
import { BASE_URL } from "./constants.js";

var graphColor = "";
var graphPointColor = "";

const getCookie = name => {
    const cookies = document.cookie.split(';')
    for (let i = 0; i < cookies.length; i++) {
      let c = cookies[i].trim().split('=')
      if (c[0] === name) {
        return decodeURIComponent(c[1])
      }
    }
    return ''
}

function checkTheme() {
    if (getCookie('theme') == 'dark-theme') {
        document.body.className = 'dark-theme';
        graphColor = 'white'
        graphPointColor = 'red';
    } else {
        graphColor = 'blue';
        graphPointColor = '#2ff1c1';
        document.body.className = 'light-theme';
    }
}

function switchTheme() {
    if (getCookie('theme') == 'light-theme') {
        document.body.className = 'dark-theme';
        graphColor = 'white'
        graphPointColor = 'red';
        document.cookie = 'theme=' + 'dark-theme';
    }
    else {
        document.body.className = 'light-theme';
        graphColor = 'blue';
        graphPointColor = '#2ff1c1';
        document.cookie = 'theme=' + 'light-theme';
    }    
    setColors(graphColor, graphPointColor);
}

function fillTable(tableData) {
    const tbody = document.getElementById("items");
    tbody.innerHTML = "";
    tableData.forEach((row) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `<td>${row.attampt}</td><td>${row.x}</td><td>${row.y}</td><td>${row.r}</td><td>${row.result}</td><td>${row.time}</td><td>${row.duration}</td>`;
        tbody.appendChild(tr);
    });
}

checkTheme();
initialilzeGraph(graphColor, graphPointColor);
bindDataSendingButtons((tableData) => {
    paintPoints(tableData);
    fillTable(tableData);
}, BASE_URL);
$.ajax({
    url: BASE_URL,
    type: "get",
    success: function(response) {
        paintPoints(JSON.parse(response));
        fillTable(JSON.parse(response));
    },
    error: function(xhr) {
        console.log("Error");
    }
});
const toggleSwitch = document.querySelector('.switch input[type="checkbox"]');
toggleSwitch.addEventListener('change', switchTheme, false);
