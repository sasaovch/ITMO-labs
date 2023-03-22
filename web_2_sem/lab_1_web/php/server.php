<?php
const X_VALUES = array(-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2);
const R_VALUES = array(1, 2, 3, 4, 5);
function checkX() {
    if (isset($_GET['x'])) {
        $x = floatval($_GET['x']);
        if (in_array($x, X_VALUES)) {
            return true;
        }
    }
    return false;
}

function checkY() {
    if (isset($_GET['y'])) {
        $y = $_GET['y'];
        if (is_numeric($y)) {
            if ($y >= -3 && $y <= 5) {
                return true;
            }
        }
    }
    return false;
}

function checkR() {
    if (isset($_GET['r'])) {
        $r = floatval($_GET['r']);
        if (in_array($r, R_VALUES)) {
            return true;
        }
    }
    return false;
}

$start_time = microtime(true);
session_start();
unset($_SESSION['hit']);

if (checkX() && checkY() && checkR()) {
    $r = floatval($_GET['r']);
    $x = floatval($_GET['x']);
    $y = floatval($_GET['y']);
    $hit = false;
    if ($x >= 0 && $y >= 0) {
        $hit = $r/2 - $r/2 * $x >= $y; 
    } else if ($x > 0 && $y < 0) {
        $hit = false;
    }  else if ($x < 0 && $y <= 0) {
        $hit = ($x >= -$r && $y >= -$r/2);
    } else if ($x <= 0 && $y > 0) {
        $hit = $r >= sqrt($x*$x + $y*$y);
    }
    if ($hit) {
        $_SESSION['hit'] = 'Successful hit';
    } else {
        $_SESSION['hit'] = 'You missed';
    }
    $attempt = ["x" => $x, "y" => $y, "r" => $r, "hit" => $hit, "attempt_time" => $start_time, "process_time" => round((microtime(true) - $start_time), 5)];
    if (isset($_SESSION['attempts'])) {
        array_push($_SESSION['attempts'], $attempt);
    } else {
        $_SESSION['attempts'] = array($attempt);
    }
    header('Location: ./index.php');
} else {
    header('HTTP/1.0 400 Data is wrong');
}
