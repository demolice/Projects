import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SmartRockets extends PApplet {

ArrayList<Textbox> boxes = new ArrayList(); //Pole textboxů
PrintWriter output; //Vypisuje informace
boolean writer = false;

private Population population; //Objekt obsahujicí informace o momentální populaci
int lifespan = 500; //Maximální doba života rakety
int ticketCount = 0; //Číslo momentálniho snímku
float maxForce = 2; // Maximální síla, s kterou může pomocná raketa působit na loď
int rocketsCount = 100; // Velikost populace 
int randomOnes = 10; // Počet novhých náhodných raket v každé nové populaci
float mutationRate = 0.05f; //S jakou pravděpodobností proběhne mutace

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
public void setup() {
  
  population = new Population(); //Vytvoření nové generace
  target = new PVector(width  - 50, height / 2); //nastavení cíle  
  colorMode(HSB);

  setUpBoxes(); //funkce pro nastavení Textboxu
}

//Metoda, která se neustále opakuje (obdoba while - do cyklu)
public void draw() {
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
public void drawObstacles() {
  fill(26, 16, 32);
  stroke(0);
  strokeWeight(15);
  strokeJoin(ROUND);

  pushMatrix();
  translate((width / 2) - 100, 0);
  rect(0, 0, 60, height * 0.2f);  
  popMatrix();

  pushMatrix();
  translate(width /2 -100, height * 0.8f);
  rect(0, 0, 60, height * 0.2f);  
  popMatrix();

  pushMatrix();
  translate(width * 0.65f, height *0.3f);
  rect(0, 0, 60, height * 0.4f); 
  popMatrix();
}

//Nastaví textboxy
public void setUpBoxes() {
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
public void updateHighscores() {
  if (hitsThisRound > allTimeBestHits) {
    allTimeBestHits = hitsThisRound;
  }

  if (bestTime < allTimeBestTime) {
    allTimeBestTime = bestTime;
  }
}

//vyresetuje statistiky
public void resetStatistics() {
  generationNumber++;
  hitsThisRound = 0;
  ticketCount = 0;
  timeLogged = false;
  bestTime = lifespan;
}

public void showStats() {
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
public void drawBestPath() {
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
public void mouseDragged() {
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
public void mousePressed() {
  for (Textbox t : boxes) {
    t.pressed(mouseX, mouseY);
  }
}

//metoda, která se volá při zamčknutí klávesy
public void keyPressed() {
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

public void startWriting() {
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

public void endWriting() {
  output.flush();
  output.close();
  println("Writing ended");
}

//Upraví data při novém vstupu od uživatele
public void updateData(Textbox t) {
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
public void reset() {
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
class Dna {
  PVector[] genes = new PVector[lifespan]; //Nese genetickou informaci jedince

  //Konstruktor, pokud nedostane geny, vygeneruje nové
  Dna(PVector[] genes) {
    if (genes != null) {
      this.genes = genes;
    } else {
      for (int i = 0; i < lifespan; i++) {
        this.genes[i] = PVector.random2D();
        this.genes[i].setMag(maxForce);
      }
    }
  }
  
  //Metoda pro jednobodové křížení křížení
  public Dna crossover(Dna partner) {
    PVector[] newgenes = new PVector[lifespan];

    int mid = floor(random(this.genes.length));

    for (int i = 0; i < this.genes.length; i++) {
      if (mid < lifespan) {
        newgenes[i] = this.genes[i];
      } else {
        newgenes[i] = partner.genes[i];
      }
    }      

    return new Dna(newgenes);
  }
  
  //Metoda pro mutaci
  public void mutation() {
    for (int i = 0; i < genes.length; i++) {
      if (random(1) <= mutationRate) {
        this.genes[i] = PVector.random2D();
        this.genes[i].setMag(maxForce);
      }
    }
  }
}
class Population {
  
  PImage img; //Obrázek rakety který se vykresluje
  
  Rocket[] rockets; //Pole všech raket

  Rocket[] matingPool; //Ruletové pole

  Population() {
    
    this.img = loadImage("rocket.png");

    //Generace nové populace
    this.rockets = new Rocket[rocketsCount];
    for (int i = 0; i < rocketsCount; i++) {
      this.rockets[i] = new Rocket();
    }
  }


  //Metoda pro ohodnocení
  public void evaluate() {
    float maxFit = 0; //Maximální dosažená hodnota fitness

    for (int i = 0; i< rocketsCount; i++) {
      this.rockets[i].calcFitness();
      if (this.rockets[i].fitness > maxFit) {
        maxFit = this.rockets[i].fitness;
      }
    }

    //Normalizuje fitenss do intervalu 0 až 1
    for (int i = 0; i < rocketsCount; i++) {
      this.rockets[i].fitness /= maxFit;
    }


    //Vypočítání velikosti pole pro ruletu
    int lengthOfMatingPool = 0;
    for (int i = 0; i < rocketsCount; i++) {
      int n = (int) (this.rockets[i].fitness * 100);
      lengthOfMatingPool += n;
    }

    int currentIndex = 0;

    //Naplenění rulety
    this.matingPool = new Rocket[lengthOfMatingPool];
    for (int i = 0; i < rocketsCount; i++) {
      int n = (int) (this.rockets[i].fitness * 100);
      for (int j = 0; j < n; j++) {
        this.matingPool[currentIndex]= this.rockets[i];
        currentIndex++;
      }
    }
  }

  //Selekce
  public void selection() {
    Rocket[] newRockets = new Rocket[this.rockets.length];

    for (int i = 0; i< (rocketsCount-randomOnes); i++) {

      //Vybere dva náhodné jedince pro křížení
      int parentAindex = floor(random(this.matingPool.length));
      int parentBindex = floor(random(this.matingPool.length));

      Rocket parentA = this.matingPool[parentAindex];
      Rocket parentB = this.matingPool[parentBindex];


      // Vytvoří DNA potomka na základě rodičovských genů
      Dna child = parentA.dna.crossover(parentB.dna);

      child.mutation(); //Provede mutaci potomka

      newRockets[i] = new Rocket(child);

      newRockets[i].paint = (parentA.paint + parentB.paint) % 255;
    }
    
    //Doplnění pole náhodnými jedinci
    for (int i = (rocketsCount-randomOnes); i < rocketsCount; i++) {
      newRockets[i] = new Rocket();
    }
    
    this.rockets = newRockets;
  }

  public boolean isGenerationStopped() {
    boolean crashed = true;
    // if there's an alive rocket, not all have crashed
    for (int i = 0; i < this.rockets.length; i++) {
      if (!this.rockets[i].crashed && !this.rockets[i].completed) {
        crashed = false;
      }
    }
    return crashed;
  }
  
  //Updatuje každou raketu v populaci
  public void run() {
    for (int i = 0; i < this.rockets.length; i++) {
      if (!this.rockets[i].completed) {
        this.rockets[i].update(ticketCount);
      }
      
      //Zapsání nerychlejšího jedince
      if (this.rockets[i].completed && !timeLogged) {
        bestTime = this.rockets[i].time;
        timeLogged = true;
        bestRocket = new Rocket(this.rockets[i].dna);
      }
        this.rockets[i].show(img);
    }
  }
}
/**
  *Třída nesoucí informaci jedné rakty a pomocné metody pro práci s ní
  */

class Rocket {
  //Poloha a rychlost
  PVector pos = new PVector(30, height  / 2);
  PVector vel = new PVector();
  PVector acc = new PVector();
  
  float fitness = 0.00f;
  
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

  public void applyForce(PVector force) {
    this.acc.add(force);
  }
  
  //Vypočítá hodnotu fitness pro danou raketu
  public void calcFitness() {
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
  public void update(int frame) {

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
        if (this.pos.y < height * 0.2f || this.pos.y > height * 0.8f) {
          this.crashed = true;
          this.hitbarrier = true;
        }
      }

      if (this.pos.x > width *0.65f && this.pos.x < width * 0.65f + 60) {
        if (this.pos.y > height * 0.3f && this.pos.y < height * 0.7f) {
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
      this.acc.mult(0.1f);
    }
  }
  
  //Vykreslí raketu
  public void show(PImage rocketImage) {
    pushMatrix();

    noStroke();
    colorMode(HSB);

    translate(this.pos.x, this.pos.y);

    rotate(this.vel.heading() + (PI / 4.0f));
    tint(paint, 150, 255); //filtr barvy
    image(rocketImage, 0 - 25, 0 - 6);
    popMatrix();
  }
}
/**
  * Processing nebosahuje třídu pro textboxy, proto bylo potřeba navrhnout vlastní
  */

public class Textbox {
   public int X = 0, Y = 0, H = 28, W = 200;
   public int textsize = 24;
   
   // COLORS
   public int Background = color(26, 0, 69);
   public int Foreground = color(255);
   public int BackgroundSelected = color(26, 0, 13);
   public int Border = color(30, 30, 30);
   
   public boolean BorderEnable = false;
   public int BorderWeight = 1;
   
   public String Text = "";
   public int TextLength = 0;
   
   public String name = "";

   private boolean selected = false;
   
   Textbox() {
   }
   
   Textbox(int x, int y, int w, int h) {
      X = x; Y = y; W = w; H = h;
   }
   
   public void tDraw() {
      if (selected) {
         fill(BackgroundSelected);
      } else {
         fill(Background);
      }
      
      if (BorderEnable) {
         strokeWeight(BorderWeight);
         stroke(Border);
      } else {
         noStroke();
      }
      
      rectMode(CORNER);
      rect(X, Y, W, H);
      
      // DRAWING THE TEXT ITSELF
      fill(Foreground);
      textSize(textsize);
      text(Text, X + (textWidth("a") / 2), Y + textsize);
   }
   
   public boolean tKeyPressed(char KEY, int KEYCODE) {
      if (selected) {
         if (KEYCODE == (int)BACKSPACE) {
            backspace();
            return true;
         } else if (KEYCODE == 32) {
            // SPACE
            //addText(' ');
         } else if (KEYCODE == (int)ENTER) {
            return true;
         } else {
            boolean isKeyNumber = (KEY >= '0' && KEY <= '9');
            boolean isDot = (KEY == '.');
      
            if (isDot || isKeyNumber) {
               addText(KEY);
               return true;
            }
         }
      }
    
      return false;
   }
   
   private void addText(char text) {
      if (textWidth(Text + text) < W) {
         Text += text;
         TextLength++;
      }
   }
   
   public void addStartUpText(String text) {
     TextLength = text.length();
     this.Text += text;
   }
   
   public void repairSize() {
     TextLength = Text.length();
   }
   
   private void backspace() {
      if (TextLength - 1 >= 0) {
         Text = Text.substring(0, TextLength - 1);
         TextLength--;
      }
   }
   
   public boolean overBox(int x, int y) {
      if (x >= X && x <= X + W) {
         if (y >= Y && y <= Y + H) {
            return true;
         }
      }
      
      return false;
   }
   
   public void pressed(int x, int y) {
      if (overBox(x, y)) {
         selected = true;
      } else {
         selected = false;
      }
   }
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SmartRockets" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
