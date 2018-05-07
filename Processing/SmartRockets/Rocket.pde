class Rocket {
  PVector pos = new PVector(30, height  / 2);
  PVector vel = new PVector();
  PVector acc = new PVector();
  float fitness = 0.00;
  Dna dna;
  boolean completed;
  boolean crashed;
  boolean hitbarrier;
  float paint;
  int time;

  PImage rocketImage;

  Rocket() {
    this.rocketImage = loadImage("rocket.png");
    this.dna = new Dna(null);
    // Checks if rocket has reached target
    this.completed = false;
    // Checks if rocket had crashed
    this.crashed = false;
    this.hitbarrier = false;
    this.paint = random(256);
  }

  Rocket(Dna dna) {
    
      this.rocketImage = loadImage("rocket.png");
    
    this.dna = dna;
    // Checkes rocket has reached target
    this.completed = false;
    // Checks if rocket had crashed
    this.crashed = false;
  }

  void applyForce(PVector force) {
    this.acc.add(force);
  }

  void calcFitness() {
    float d = dist(this.pos.x, this.pos.y, target.x, target.y);

    //println("1 " + fitness);
    // Flip following 2 lines:
    this.fitness *= this.fitness;
    //println("2 " + fitness);

    this.fitness = map(d, 0, width, width, 0);


    this.fitness += (height - this.pos.x)/3;

    //this.fitness += this.pos.x / 3;

    if (this.completed) {
      this.fitness *= 10;
      this.fitness *= 5*(lifespan/time);
    }

    // If rocket does not get to target decrease fitness
    if (this.crashed) {
      this.fitness /= 6;
    }


    // hitting the barrier is even worse than hitting the wall
    if (this.hitbarrier == true) {
      this.fitness /= 2;
    }


    if (this.pos.y > height*0.7) {
      //this.fitness /=10;
    }
  }

  void update(int frame) {

    // Checks distance from rocket to target
    float d = dist(this.pos.x, this.pos.y, target.x, target.y);
    // If distance less than 10 pixels, then it has reached target
    if (d <= 10) {
      this.completed = true;
      totalHits++;
      hitsThisRound++;
      this.time = frame;
      this.pos = target.copy();
    }
    // Rocket hit the barrier
    for (PVector p : barriers) {
      if (dist(p.x, p.y, this.pos.x, this.pos.y) < 20) {
        this.crashed = true;
        this.hitbarrier = true;
      }
    }

    // Rocket has hit left or right of window
    if (this.pos.x > width || this.pos.x < 0) {
      this.crashed = true;
    }
    // Rocket has hit top or bottom of window
    if (this.pos.y > height || this.pos.y < 0) {
      this.crashed = true;
    }
    /*
    if (this.pos.y > height) {
     this.hitbarrier = true;
     }
     */

    if (obstacles) {


      if (this.pos.x > width / 2 - 100 && this.pos.x < width / 2 - 40) {
        if (this.pos.y < height * 0.2 || this.pos.y > height * 0.8) {
          this.crashed = true;
          this.hitbarrier = true;
        }
      }

      if (this.pos.x > width *0.65 && this.pos.x < width * 0.65 + 60) {
        if (this.pos.y > height * 0.3 && this.pos.y < height * 0.7) {
          this.crashed = true;
          this.hitbarrier = true;
        }
      }
    }

    //applies the random vectors defined in dna to consecutive frames of rocket
    this.applyForce(this.dna.genes[frame]);
    // if rocket has not got to goal and not crashed then update physics engine
    if (!this.completed && !this.crashed) {
      this.vel.add(this.acc);
      this.pos.add(this.vel);
      this.vel.limit(15);

      // only for debugging:
      //println("pos:" + this.pos.x + " " + this.pos.y + " vel:" + this.vel.x + " " + this.vel.y + " acc:" + this.acc.x + " " + this.acc.y + " force:" + this.dna.genes[count].x + " " + this.dna.genes[count].y); 
      this.acc.mult(0.1);
    }
  }

  void show() {
    // push and pop allow's rotating and translation not to affect other objects
    pushMatrix();
    //color customization of rockets
    noStroke();
    colorMode(HSB);

    translate(this.pos.x, this.pos.y);

    rotate(this.vel.heading() + (PI / 4.0));
    tint(paint, 150, 255);
    image(rocketImage, 0 - 25, 0 - 6);
    popMatrix();
  }
}
