package com.AI.core.GA;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;

@Component
public class Population {
    public ArrayList<Individual> individuals;

    public Individual best;

    public Population(){
        individuals = new ArrayList<Individual>();
        best = null;
    }

    public void randomInit(){
        for(int i = 0; i < Configs.POPULATION_SIZE; i++){
            Individual individual = new Individual();
            individual.random_init();
            individual.calculateFitness();
            individuals.add(individual);
        }
    }

    public ArrayList<Individual> crossover(Individual p1,Individual p2){
        ArrayList<Individual> offsprings = new ArrayList<Individual>();

        for(int i = 0; i < 2; i++){
            Individual offspring = new Individual();
            ArrayList<Integer> chromosome = new ArrayList<Integer>();

            for(int j = 0; j < p1.chromosome.size(); j++){
                if(Math.random() < 0.5){
                    chromosome.add(p1.chromosome.get(j));
                }
                else{
                    chromosome.add(p2.chromosome.get(j));
                }
            }

            offspring.chromosome = chromosome;
            offsprings.add(offspring);
        }

        return offsprings;
    }

    public void executeSelection(){
//        Set<Individual> selectedParentsSet = new HashSet<>();
//        int tournamentSize = 5; //Thay doi dc
//
//        while(selectedParentsSet.size()!=Configs.POPULATION_SIZE) {
//            ArrayList<Individual> tournament = new ArrayList<>();
//            Set<Individual> selectedForTournament = new HashSet<>();
//
//            for (int j = 0; j < tournamentSize; j++) {
//                Individual randomIndividual;
//                do {
//                    randomIndividual = individuals.get((int) (Math.random() * individuals.size()));
//                } while (selectedForTournament.contains(randomIndividual));
//
//                tournament.add(randomIndividual);
//                selectedForTournament.add(randomIndividual);
//            }
//
//            tournament.sort(Comparator.comparingDouble(individual -> individual.fitness));
//
//            selectedParentsSet.add(tournament.get(0));
//
//        }
//
//        individuals = new ArrayList<>(selectedParentsSet);

        this.individuals.sort(Comparator.comparingDouble(Individual::getFitness));
//        best=individuals.get(0);
        int len=individuals.size();
        while(individuals.size()>Configs.POPULATION_SIZE){
            individuals.remove(len-1);
            len--;

        }
        best=individuals.get(0);
    }
}
