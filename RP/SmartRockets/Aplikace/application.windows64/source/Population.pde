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
  void evaluate() {
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
  void selection() {
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
  void run() {
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
