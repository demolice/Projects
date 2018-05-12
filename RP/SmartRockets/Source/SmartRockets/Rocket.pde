/**
  *Třída nesoucí informaci jedné rakty a pomocné metody pro práci s ní
  */

class Rocket {
  //Poloha a rychlost
  PVector pos = new PVector(30, height  / 2);
  PVector vel = new PVector();
  PVector acc = new PVector();
  
  float fitness = 0.00;
  
  Dna dna;
  
  //Pomocné proměnné
  boolean completed;
  boolean crashed;
  boolean hitbarrier;
  float paint;
  int time;
  
  //Obrázek rakety, který se vykresluje


  Rocket() {
    this.dna = new Dna(null);
    this.completed = false;
    this.crashed = false;
    this.hitbarrier = false;
    this.paint = random(256);
  }
  
  //Konsturktor pro vytvoření jedince s počátečnímy geny
  Rocket(Dna dna) {  
    this.dna = dna;
    this.completed = false;
    this.crashed = false;
  }

  void applyForce(PVector force) {
    this.acc.add(force);
  }
  
  //Vypočítá hodnotu fitness pro danou raketu
  void calcFitness() {
    float d = dist(this.pos.x, this.pos.y, target.x, target.y); //Vypočítá vzdálenost pomocí Pythagorovy věty

    this.fitness = map(d, 0, width, width, 0);


    //Optimalizace fitness, aby lepší jedinci měli větší fitness
    this.fitness += (height - this.pos.x)/3;

    if (this.completed) {
      this.fitness *= 10;
      this.fitness *= 5*(lifespan/time);
    }

    if (this.crashed) {
      this.fitness /= 6;
    }

    if (this.hitbarrier == true) {
      this.fitness /= 2;
    }
  }
  
  //Vypočítá novou polohu pro raketu
  void update(int frame) {

    float d = dist(this.pos.x, this.pos.y, target.x, target.y);
    
    //Podmínka pro zásah cíle
    if (d <= 10) {
      this.completed = true;
      totalHits++;
      hitsThisRound++;
      this.time = frame;
      this.pos = target.copy();
    }
    
    // Zkouška pro zásah překážky
    for (PVector p : barriers) {
      if (dist(p.x, p.y, this.pos.x, this.pos.y) < 20) {
        this.crashed = true;
        this.hitbarrier = true;
      }
    }

    // Zkouška zásahu okrajů
    if (this.pos.x > width || this.pos.x < 0) {
      this.crashed = true;
    }

    if (this.pos.y > height || this.pos.y < 0) {
      this.crashed = true;
    }

    //Zkouška zásahu generovaných překážek
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

    //Vybere příslušný gen z genomu a podle síly přemístí raketu
    this.applyForce(this.dna.genes[frame]);
    if (!this.completed && !this.crashed) {
      this.vel.add(this.acc);
      this.pos.add(this.vel);
      this.vel.limit(15);
      this.acc.mult(0.1);
    }
  }
  
  //Vykreslí raketu
  void show(PImage rocketImage) {
    pushMatrix();

    noStroke();
    colorMode(HSB);

    translate(this.pos.x, this.pos.y);

    rotate(this.vel.heading() + (PI / 4.0));
    tint(paint, 150, 255); //filtr barvy
    image(rocketImage, 0 - 25, 0 - 6);
    popMatrix();
  }
}
