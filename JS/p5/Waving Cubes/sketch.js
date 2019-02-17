let angle = 0;

function setup() {
  //createCanvas(windowWidth, windowHeight);
  createCanvas(windowWidth, windowHeight);
}

function draw() {
  background(0);

  colorMode(HSB, 100);



  rectMode(CENTER);


  let offset = 0;

  var rowCount = 15;


  let sW = windowWidth / rowCount;
  let sH = windowHeight / rowCount;

  for (var i = 0; i < rowCount; i++) {
    for (var j = 0; j < 1; j++) {
      //fill(map(h, 0, 10, 0, 100), 80, 28);
      let a = angle + offset;
      let h = map(sin(a), -1, 1, 0, 200);
      fill(map(h, 0, 200, 100, 1), 80, 28);
      noStroke();
      //rect(i * sW + sW / 2, j * sH + sH / 2, sW - 1, h);
      rect(i * sW + sW / 2, windowHeight / 2, sW - 1, h);
      //rect(windowWidth / 2, i * sH + sH / 2, h, sH - 1);
      offset += 0.25;

    }
  }

  angle += 0.05;
}