package com.AI.core.GA;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

import static com.AI.core.GA.Configs.*;

@Component
public class GA {
    public Population pop;

    public GA(){
        pop=new Population();
        pop.randomInit();
    }
    public GA(Population pop){
        this.pop=pop;
    }


    public Individual run(){

        int currentGeneration=0;
        while(currentGeneration< MAX_GENERATION){
            currentGeneration++;
            //mutate and crossover
            ArrayList<Individual> offSpring=reproduction(pop);
            for(Individual indi: offSpring){
                indi.calculateFitness();
            }

            //add off_spring
            pop.individuals.addAll(offSpring);
            //selection
            pop.executeSelection();
//            System.out.println(1/pop.best.fitness +"xxxx");

        }
        return pop.best;

    }

    public ArrayList<Individual> reproduction(Population pop){

        ArrayList<Individual> offSpring = new ArrayList<>();
        Random rand=new Random();
        while (offSpring.size() < POPULATION_SIZE) {
            int p1 = rand.nextInt(POPULATION_SIZE);
            int p2 = rand.nextInt(POPULATION_SIZE);
            while (p1 == p2) {
                p2 = rand.nextInt(POPULATION_SIZE);
            }
            if (rand.nextDouble() < CROSSOVER_RATE) {
                ArrayList<Individual> child = pop.crossover(pop.individuals.get(p1), pop.individuals.get(p2));

                offSpring.addAll(child);
            }
        }


        for (int i = 0; i < offSpring.size(); i++) {
            if (rand.nextDouble() < MUTATION_RATE) {
                offSpring.get(i).swapMutate();
            }

//            System.out.println(offSpring.get(i).fitness);
        }

        return offSpring;
    }
}
