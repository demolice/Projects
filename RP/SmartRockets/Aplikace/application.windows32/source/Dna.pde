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
  Dna crossover(Dna partner) {
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
  void mutation() {
    for (int i = 0; i < genes.length; i++) {
      if (random(1) <= mutationRate) {
        this.genes[i] = PVector.random2D();
        this.genes[i].setMag(maxForce);
      }
    }
  }
}
