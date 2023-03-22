
<%@ page import="model.Point" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="desctiption" content="ITMO 3 sem laboratory work Web 2">
        <meta name="keywords" content="ITMO, lab, web">
        <meta name="author" content="yt">
        <title>Lab 2 Web</title>
        <link rel="stylesheet" href="./views/main.css">
        <link rel="icon" href="./views/favicon.ico">
    </head>
    <body>
        <table>
            <tr id="header" class="gradient">
                <td id="group" class="shadow">
                    <h2>P32301<br></h2>
                    <h3>321200</h3>
                </td>
                <td id="name"  class="shadow">
                    <h1>Ovcharenko Aleksandr</h1>
                    <h2>
                    Laboratory Work Web 2
                    </h2>
                </td>
            </tr>
            <tr>
                <td id="menu">
                    <h3>Dark<br>mode</h3>
                    <label class="switch">
                        <input type="checkbox" id="switcher">
                        <span class="slider round"></span>
                    </label>
                </td>
                    <td id="graph-style">
                        <canvas id="graph" width="600%" alt="Graph" height="600%" style="border: 3px solid var(--border-color);"></canvas>
                    </td>
                        <tr>
                            <td></td>
                            <td>
                                <form id="form">
                                    <table class="select-table">
                                        <tr>
                                            <td>
                                                <label for="input_y">Y from -5 to 3</label>
                                            </td>
                                            <td colspan="3">
                                                <input type="text" name="y" id="input_y" placeholder="0"  maxlength="5">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label for="input_y">R from 2 to 5</label>
                                            </td>
                                            <td colspan="3">
                                                <input type="text" name="r" id="input_r" placeholder="0"  maxlength="5">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="select-text">select X</label>
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-1" value="-5">
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-2" value="-4">
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-3" value="-3">
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-4" value="-2">
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-5" value="-1">
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-6" value="0">
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-7" value="1">
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-8" value="2">
                                            </td>
                                            <td>
                                                <input type="submit" name="x" id="x-9" value="3">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="8">
                                                <p id="warning">
                                                    <%
                                                    Object warning = request.getAttribute("warning");
                                                    if (warning != null) {
                                                        out.println(warning);
                                                    }
                                                    %>
                                                </p>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </td>
                        </tr>
            </tr>
            <tr>
                <td style="padding:1%;text-align:center;">
                    <h2>Results</h2>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="padding:1%">
                    <table id="results">
                        <thead>
                            <tr>
                                <th>Attempt #</th>
                                <th>X</th>
                                <th>Y</th>
                                <th>R</th>
                                <th>Result</th>
                                <th>Attempt time</th>
                                <th>Processing time</th>
                            </tr>
                        </thead>
                        <tbody id="items">
                        </tbody>
                    </table>
                </td>
                <td>
                </td>
            </tr>
        </table>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script type="module" src="./views/main.js"></script>
    </body>
</html>