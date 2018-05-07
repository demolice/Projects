var gridPoints = 60;
var grid = new Array(gridPoints);

var openList = [];
var closeList = [];
var start;
var end;

var w = 800;
var h = 800;

var scaledW;
var scaledH;

var path = [];


function setup() {
  console.log("width:" + width);



  createCanvas(w, h);
  console.log('A*');

  w = width;
  h = height;

  scaledW = w / gridPoints;
  scaledH = h / gridPoints;

  //Basically making a 2D grid for points
  for (var i = 0; i < gridPoints; i++) {
    grid[i] = new Array(gridPoints);
  }

  //Filling arrays with points
  for (var i = 0; i < grid.length; i++) {
    for (var j = 0; j < grid[i].length; j++) {
      grid[i][j] = new Point(i, j);

      if (random(1) < 0.35) {
        grid[i][j].wall = true;
      }

    }
  }

  for (var i = 0; i < grid.length; i++) {
    for (var j = 0; j < grid[i].length; j++) {
      grid[i][j].calculateNeighbours(grid);
    }
  }

  start = grid[0][0];
  end = grid[grid.length - 1][grid.length - 1];

  start.wall = false;
  end.wall = false;

  openList.push(start);
}

function Point(i, j) {
  this.x = i;
  this.y = j;

  this.f = 0;
  this.g = 0;
  this.h = 0;

  this.p = undefined;

  this.wall = false;

  this.neighbours = [];

  this.redraw = function(color) {

    if (this.wall) {
      fill(0);
      noStroke();
      ellipse(this.x * scaledW + scaledW / 2, this.y * scaledH + scaledH / 2,
        scaledW / 2, scaledH / 2);
    }
    /*else if (color == color(0, 255, 0)) {
      fill(color(0, 0, 255));
    // */
  }

  this.calculateNeighbours = function(grid) {
    for (var i = -1; i < 2; i++) {
      for (var j = -1; j < 2; j++) {
        var rx = this.x + i;
        var ry = this.y + j;

        if (rx >= 0 && ry >= 0 && rx < gridPoints && ry < gridPoints) {
          this.neighbours.push(grid[rx][ry]);
        }
      }
    }
  }
}

function calculateHeuristic(a, b) {
  var d = dist(a.x, a.y, b.x, b.y);

  //var d = abs(a.x - b.x) + abs(a.y - b.y);
  return d;
}

//Loop; see p5.* for more info
function draw() {
  background(255);

  if (openList.length > 0) {

    var lowest = 0;

    for (var i = 0; i < openList.length; i++) {
      if (openList[i].f < openList[lowest].f) {
        lowest = i;
      }
    }

    var current = openList[lowest];

    if (openList[lowest] === end) {
      noLoop();
      console.log('done');
    }

    //Removes elemet from openList
    for (var i = openList.length - 1; i >= 0; i--) {

      if (openList[i] == current) {
        openList.splice(i, 1);
      }
    }

    closeList.push(current);

    var neighbours = current.neighbours;

    for (var i = 0; i < neighbours.length; i++) {
      var neighbour = neighbours[i];

      if (!closeList.includes(neighbour) && !neighbour.wall) {
        var newG = current.g + 1;

        var newP = false;

        if (openList.includes(neighbour)) {
          if (newG < neighbour.g) {
            neighbour.g = newG;
            newP = true;
          }
        } else {
          neighbour.g = newG;
          newP = true;
          openList.push(neighbour);
        }

        if (newP) {
          neighbour.h = calculateHeuristic(neighbour, end);
          neighbour.f = neighbour.g + neighbour.h;
          neighbour.p = current;
        }

      }
    }

  } else {

    console.log('not valid');
    noLoop();
    return;
  }


  for (var i = 0; i < gridPoints; i++) {
    for (var j = 0; j < gridPoints; j++) {
      grid[i][j].redraw(color(255));
    }
  }


  for (var i = 0; i < openList.length; i++) {
    openList[i].redraw(color(0, 255, 0));
  }

  for (var i = 0; i < closeList.length; i++) {
    closeList[i].redraw(color(255, 0, 0));
  }


  path = [];

  var temp = current;
  path.push(temp);

  while (temp.p) {
    path.push(temp.p);
    temp = temp.p;
  }

  stroke(51, 204, 51);
  strokeWeight(scaledW / 2);
  noFill();
  beginShape();
  for (var i = 0; i < path.length; i++) {
    vertex(path[i].x * scaledW + scaledW / 2, path[i].y * scaledH + scaledH / 2);
  }
  endShape();

  noStroke();
  fill(255, 0, 0);
  ellipse(end.x * scaledW + scaledW / 2, end.y * scaledH + scaledH / 2,
    scaledW / 2, scaledH / 2);
}