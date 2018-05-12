ArrayList<Textbox> boxes = new ArrayList(); //Pole textboxů
PrintWriter output; //Vypisuje informace
boolean writer = false;

private Population population; //Objekt obsahujicí informace o momentální populaci
int lifespan = 500; //Maximální doba života rakety
int ticketCount = 0; //Číslo momentálniho snímku
float maxForce = 2; // Maximální síla, s kterou může pomocná raketa působit na loď
int rocketsCount = 100; // Velikost populace 
int randomOnes = 10; // Počet novhých náhodných raket v každé nové populaci
float mutationRate = 0.05; //S jakou pravděpodobností proběhne mutace

//Pomocné proměné
Rocket bestRocket; //Proměná s nejlepší raketou z minulé generace
boolean drawingLine = false;

//Statisktiky
int totalHits; //Celkový počet zásahů v minulé generaci
int hitsThisRound; //Počet zásahu cíle v momentální generaci 
int allTimeBestHits; //Počet zásahů během nejlepší generace

boolean timeLogged = false;
boolean obstacles = true;
int bestTime = lifespan; //nejlepší čas rakety
int allTimeBestTime = lifespan;
int generationNumber = 0; //Číslo generace

//Proměné o cíli a překážkách
PVector target;
ArrayList<PVector> barriers = new ArrayList<PVector>();


//Metoda, která se spustí na začátku programu
void setup() {
  size(1280, 720);
  population = new Population(); //Vytvoření nové generace
  target = new PVector(width  - 50, height / 2); //nastavení cíle  
  colorMode(HSB);

  setUpBoxes(); //funkce pro nastavení Textboxu
}

//Metoda, která se neustále opakuje (obdoba while - do cyklu)
void draw() {
  background(26, 0, 62);

  population.run();

  ticketCount++;

  //Pokud všechny rakety nabourali, nemá smysl pokračovat v cyklu
  if (population.isGenerationStopped()) {
    ticketCount = lifespan;
  }

  //Podmínka pro dosažení maximální délky života
  if (ticketCount == lifespan) {
    population.evaluate();
    population.selection();

    updateHighscores();
    resetStatistics();

    if (writer) {      
      output.println(generationNumber + "\t"  + allTimeBestHits  +  "\t" + allTimeBestTime);
    }
  }


  // Vykreslení cíle
  noStroke();
  fill(255);
  ellipse(target.x, target.y, 16, 16);
  fill(0, 255, 255);

  //Vykreslení překážek
  for (PVector p : barriers) {
    ellipse(p.x, p.y, 20, 20);
  }

  if (obstacles) {
    drawObstacles();
  }

  //Vykreslení textboxů
  for (Textbox t : boxes) {
    t.tDraw();
  }

  drawBestPath(); //Vykreslí nejelpší traektorii
  showStats(); //Vykrelí statistiky
}

//Vykreslí bloky, které slouží jako překážky
void drawObstacles() {
  fill(26, 16, 32);
  stroke(0);
  strokeWeight(15);
  strokeJoin(ROUND);

  pushMatrix();
  translate((width / 2) - 100, 0);
  rect(0, 0, 60, height * 0.2);  
  popMatrix();

  pushMatrix();
  translate(width /2 -100, height * 0.8);
  rect(0, 0, 60, height * 0.2);  
  popMatrix();

  pushMatrix();
  translate(width * 0.65, height *0.3);
  rect(0, 0, 60, height * 0.4); 
  popMatrix();
}

//Nastaví textboxy
void setUpBoxes() {
  Textbox rocketsCountBox = new Textbox();
  boxes.add(rocketsCountBox);
  rocketsCountBox.addStartUpText(Integer.toString(rocketsCount));
  rocketsCountBox.name = "rocketsCountBox";

  Textbox maxForceBox = new Textbox();
  boxes.add(maxForceBox);
  maxForceBox.addStartUpText(Float.toString(maxForce));
  maxForceBox.name = "maxForceBox";

  Textbox mutationRateBox = new Textbox();
  boxes.add(mutationRateBox);
  mutationRateBox.addStartUpText(Float.toString(mutationRate));
  mutationRateBox.name = "mutationRateBox";

  Textbox randomOnesBox = new Textbox();
  boxes.add(randomOnesBox);
  randomOnesBox.addStartUpText(Integer.toString(randomOnes));
  randomOnesBox.name = "randomOnesBox";

  //nastaví pozici textboxů
  for (int i = 0; i < boxes.size(); i++) {
    pushMatrix();
    rectMode(CORNER);
    boxes.get(i).W = 100;
    boxes.get(i).X = 10;
    boxes.get(i).Y = height - 10 - ((i + 1) * 30) +2;    
    popMatrix();
  }
}

//upraví statistiky tak, aby byly aktuální
void updateHighscores() {
  if (hitsThisRound > allTimeBestHits) {
    allTimeBestHits = hitsThisRound;
  }

  if (bestTime < allTimeBestTime) {
    allTimeBestTime = bestTime;
  }
}

//vyresetuje statistiky
void resetStatistics() {
  generationNumber++;
  hitsThisRound = 0;
  ticketCount = 0;
  timeLogged = false;
  bestTime = lifespan;
}

void showStats() {
  fill(255);
  textSize(30);
  textAlign(BOTTOM);

  if (hitsThisRound < 0) hitsThisRound = 0;
  text(("Zásahu cíle tuhel generaci: " + hitsThisRound), 10, 30);
  text(("Maximální počet zásahů: " + allTimeBestHits), 10, 60);

  text(("Nejlepší čas tuto generaci: " + bestTime), 10, 90);
  if (allTimeBestTime == 500) {
    text("Cíl nebyl zasažen", 10, 120);
  } else {
    text(("Nejlepší čas vůbec: " + allTimeBestTime), 10, 120);
  }
  text(("Generace č. " + (generationNumber+1)), 10, 150);
  text(("Momentální krok: " + ticketCount), 10, 180);

  text("Velikost popoulace", 120, height - 10);
  text("Maximální zrychlení", 120, height - 40);
  text("Šance mutace", 120, height - 70);
  text("Počet náhodných raket v nové generaci", 120, height - 100);
}

//Vykreslí dráhu nejlepší rakety
void drawBestPath() {
  strokeWeight(5);
  stroke(255);
  beginShape(LINES);

  if (timeLogged || drawingLine) {
    drawingLine = true;

    bestRocket.pos = new PVector(30, height / 2);
    bestRocket.vel.mult(0);
    bestRocket.acc.mult(0);
    bestRocket.completed = false;
    bestRocket.crashed = false;

    for (int i = 0; i < lifespan; i++) {
      if (!bestRocket.crashed && !bestRocket.completed) {
        bestRocket.update(i);
      }
      vertex(bestRocket.pos.x, bestRocket.pos.y);
    }
    hitsThisRound--;
  }
}


//metoda, která se volá při tažení zmáčknuté myši
void mouseDragged() {
  boolean inBox = false;

  for (Textbox t : boxes) {
    if (t.overBox(mouseX, mouseY)) {
      inBox = true;
    }
  }
  if (!obstacles)
    if (!inBox) {
      barriers.add(new PVector(mouseX, mouseY));
      allTimeBestTime = lifespan;
      drawingLine = false;
    }
}

//metoda, která se volá při kliknutí na myš
void mousePressed() {
  for (Textbox t : boxes) {
    t.pressed(mouseX, mouseY);
  }
}

//metoda, která se volá při zamčknutí klávesy
void keyPressed() {
  if (keyCode == 32) {
    obstacles = !obstacles;
    reset();
    println(obstacles);
  } else if (key == 's') {
    if (!writer) {
      reset();
      writer = true;
      startWriting();
    } else {
      endWriting();
      writer = false;
    }
  } else {
    for (Textbox t : boxes) {
      println(t.name + " is " + t.selected);
      if (t.tKeyPressed(key, (int)keyCode)) {
        updateData(t); //Získá data z textboxů a zapíše je do proměných
        reset();
      }
    }
    println("END---------------------------------------");
  }
}

void startWriting() {
  println("Writing started!");

  output = createWriter(month() + " " + day() + " " + hour() + " " + minute() + ".txt");
  output.println("Population size: " + rocketsCount);
  output.println("Mutation rate: " + mutationRate);
  output.println("Random ones: " + randomOnes);
  output.println("Max force: " + maxForce);
  output.println("-----------------------");
  output.println("schema:");
  output.println("generation number\tall time best\tall time best time");
}

void endWriting() {
  output.flush();
  output.close();
  println("Writing ended");
}

//Upraví data při novém vstupu od uživatele
void updateData(Textbox t) {
  switch (t.name) {
    case ("rocketsCountBox"):
    t.repairSize();
    println(t.Text);

    try {
      int newParam = Integer.parseInt(t.Text);

      if (newParam > 0) {
        rocketsCount = newParam;
      } else {
        rocketsCount = 1;
      }

      if (randomOnes > rocketsCount) {
        randomOnes = rocketsCount;
      }
    } 
    catch (Exception e) {
      println(e);
    }
    break;

    case("randomOnesBox"):
    t.repairSize();

    if (!t.Text.equals("")) {
      try {
        int newParam = Integer.parseInt(t.Text);

        if (newParam >= 0) {
          if (newParam > rocketsCount) {
            randomOnes = 0;
            t.Text = Integer.toString(randomOnes);
          } else {
            randomOnes = newParam;
          }
        } else {
          randomOnes = 0;
          t.Text = "0";
        }
      } 
      catch (Exception e) {
        t.Text = Integer.toString(randomOnes);
        println(e);
        println("Random ones: " + randomOnes);
      }
    }
    break;

    case("mutationRateBox"):
    t.repairSize();

    if (!t.Text.equals("")) {
      if (!(t.Text.substring(t.Text.length() - 1).equals("0") || t.Text.substring(t.Text.length() - 1).equals("."))) {
        try {
          float newParam = Float.parseFloat(t.Text);

          if (newParam > 0) {
            mutationRate = newParam;
            t.Text = Float.toString(mutationRate);
          } else {
            t.Text = Float.toString(mutationRate);
          }
        } 
        catch (Exception e) {
          t.Text = Float.toString(mutationRate);
          println(e);
        }
      }
    }
    println("Mutation rate: " + mutationRate);
    break;

    case("maxForceBox"):
    t.repairSize();

    if (!t.Text.equals("")) {
      if (!(t.Text.substring(t.Text.length() - 1).equals("0") || t.Text.substring(t.Text.length() - 1).equals("."))) {
        try {
          float newParam = Float.parseFloat(t.Text);

          if (newParam > 0) {
            maxForce = newParam;

            if (maxForce % 1 == 0) {
              t.Text = Integer.toString((int) maxForce);
            } else {
              t.Text = Float.toString(maxForce);
            }
          } else {
            t.Text = Float.toString(maxForce);
          }
        } 
        catch (Exception e) {
          t.Text = Float.toString(maxForce);
          println(e);
        }
      }
    }
    println("Max Force: " + maxForce);
    break;
  }
}

//vyresetuje simulaci
void reset() {
  population = new Population();
  resetStatistics();
  ticketCount = 0;
  totalHits = 0;
  hitsThisRound = 0;
  allTimeBestHits = 0;
  bestTime = lifespan;
  allTimeBestTime = 500;
  generationNumber = 0;
  barriers = new ArrayList();
  drawingLine = false;
  bestRocket = null;
}
