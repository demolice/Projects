var r1 = 200;
var r2 = 200;
var m1 = 40;
var m2 = 40;
var a1 = Math.PI / 2;
var a2 = Math.PI / 2;
var a1_v = 0;
var a2_v = 0;
var g = 1;

var px2 = -1;
var py2 = -1;

var cx, cy;

var pathX = [];
var pathY = [];

function setup() {
  createCanvas(windowWidth, windowHeight);

  cx = windowWidth / 2;
  cy = 200;

  colorMode(HSB);
}

function draw() {
  background(0, 0, 13);



  var num1 = -g * (2 * m1 + m2) * sin(a1);
  var num2 = -m2 * g * sin(a1 - 2 * a2);
  var num3 = -2 * sin(a1 - a2) * m2;
  var num4 = a2_v * a2_v * r2 + a1_v * a1_v * r1 * cos(a1 - a2);
  var den = r1 * (2 * m1 + m2 - m2 * cos(2 * a1 - 2 * a2));
  var a1_a = (num1 + num2 + num3 * num4) / den;

  num1 = 2 * sin(a1 - a2);
  num2 = (a1_v * a1_v * r1 * (m1 + m2));
  num3 = g * (m1 + m2) * cos(a1);
  num4 = a2_v * a2_v * r2 * m2 * cos(a1 - a2);
  den = r2 * (2 * m1 + m2 - m2 * cos(2 * a1 - 2 * a2));
  var a2_a = (num1 * (num2 + num3 + num4)) / den;


  translate(cx, cy);

  var x1 = r1 * sin(a1);
  var y1 = r1 * cos(a1);

  var x2 = x1 + r2 * sin(a2);
  var y2 = y1 + r2 * cos(a2);

  fill(255);
  stroke(255);

  line(0, 0, x1, y1);
  ellipse(x1, y1, m1, m1);

  line(x1, y1, x2, y2);
  ellipse(x2, y2, m2, m2);

  a1_v += a1_a;
  a2_v += a2_a;
  a1 += a1_v;
  a2 += a2_v;

  pathX.push(x2);
  pathY.push(y2);

  beginShape();

  noFill();
  stroke(130, 70, 70);

  for (var i = 0; i < pathX.length; i++) {
    vertex(pathX[i], pathY[i]);
  }

  endShape();
}