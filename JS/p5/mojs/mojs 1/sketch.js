/*
var rectStroke = new mojs.Shape({
  shape: 'rect',
  duration: 2500,
  delay: 500,
  radius: 70,
  stroke:'black',
  strokeWidth: 15,
  fill: 'none',
  top: '25%',

  //isYoyo: true,
  strokeDasharray: '100%',
  strokeDashoffset: '350',


  repeat: 999
}).play();
*/

var rectFill = new mojs.Shape({
  shape: 'rect',
  //scale: {1 : 0.1},
  duration: 1000,
  radius: 10,
  radiusX: 50,
  radiusY: 50,
  delay: 0,
  easing: 'cubic.inout',
  //backwarrdEasing: 'cubic.out',
  isYoyo: true,
  radius: 70,
  fill: 'purple',
  //left: {'25%' : '75%'},
  top: {'25%' : '75%'},
  //isShowStart: true
  repeat: 999

}).play();



function draw() {
}

function setup() {
  createCanvas(400, 400);

}
