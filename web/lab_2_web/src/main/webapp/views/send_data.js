export function bindDataSendingButtons(clickSentCallback, BASE_URL) {
    $("form").submit(function(e) {
        document.getElementById('warning').innerHTML = "";
        e.preventDefault();
        if (validateTextNumber($("#input_y").val()) && validateNumber(parseFloat($("#input_y").val()), -5, 3)
            && validateTextNumber($("#input_r").val()) && validateNumber(parseFloat($("#input_r").val()), 2, 5)) {
            let x = parseFloat(document.activeElement['value']);
        $.ajax({
            url: BASE_URL + "?" +  $("#form").serialize() + "&x=" + x,
            type: "get",
            success: function(response) {
                clickSentCallback(JSON.parse(response));
            },
            error: function(xhr) {
                console.log("Error");
            }
          });
        } else {
            if (!(validateTextNumber($("#input_y").val()) && validateNumber(parseFloat($("#input_y").val()), -5, 3))) {
                document.getElementById("warning").innerHTML = "Please, enter rigth value for Y";
            } else {
                document.getElementById("warning").innerHTML = "Please, enter rigth value for R";
            }
        }
        });

    const canvas = (document.getElementById("graph"));
    const width = canvas.width;
    const height = canvas.height;
    const canvasR = width / 5;

    canvas.onmousedown = function(event) {
        document.getElementById('warning').innerHTML = "";

        if (!validateTextNumber($("#input_r").val()) || !validateNumber(parseFloat($("#input_r").val()), 2, 5)) {
            document.getElementById("warning").innerHTML = "Please, enter rigth value for R";
            return;
        }

        const r = parseFloat($("#input_r").val());
        const x = convertXToRadiusOf(event.offsetX, r).toFixed(3);
        const y = convertYToRadiusOf(event.offsetY, r).toFixed(3);

        if (!validateNumber(parseFloat(y), -5, 3)) {
            document.getElementById("warning").innerHTML = "Please, click on rigth value for Y";
            return;
        } else if (!validateNumber(parseFloat(x), -5, 3)) {
            document.getElementById("warning").innerHTML = "Please, click on rigth value for X";
            return;
        }

        $.ajax({
            url: BASE_URL + "?" + "x=" + x + "&y=" + y + "&r=" + r,
            type: "get",
            success: function(response) {
                clickSentCallback(JSON.parse(response));
            },
            error: function(xhr) {
                console.log("Error");
            }
          });
    };

    function convertXToRadiusOf(x, r) {
        return ((x - width / 2) / canvasR) * r;
    }

    function convertYToRadiusOf(y, r) {
        return ((height / 2 - y) / canvasR) * r;
    }

    function validateTextNumber(text) {
        const numberPattern = /^[+-]?(\d*[.])?\d+$/;
    
        const number = parseFloat(text);
        if (Number.isNaN(number)
            || !numberPattern.test(text)) {
            return false;
        } else {
            return true;
        }
    }
    
    function validateNumber(number, start, finish) {
        return start <= number && number <= finish;
    }
}
