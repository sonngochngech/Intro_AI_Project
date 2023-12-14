package com.AI.core.GA;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

import static com.AI.core.GA.Configs.MAX_VEHICLE;
import static com.AI.core.GA.Run.N;
@Component
public class Individual {
    public ArrayList<Integer> chromosome;

    public double fitness;

    public Individual(){
        this.chromosome = new ArrayList<>();
        this.fitness = -1;

    }

    public Individual(ArrayList<Integer> chromosome){
        this.chromosome = new ArrayList<>(chromosome);
        this.fitness = -1;

    }

    public void random_init(){
        ArrayList<Integer> randomChromosome = new ArrayList<>();
        Random rand = new Random();


        // Tạo các phần tử trong chromosome
        for (int i = 0; i < N-1; i++) {
            randomChromosome.add(rand.nextInt(MAX_VEHICLE)+1); // chọn ngẫu nhiên từ 1 đến 20
        }
        this.chromosome = randomChromosome; // gán chromosome của this Individual bằng chromosome ngẫu nhiên vừa tạo
    }

    public void calculateFitness(){
        //
        double max = 0;
        int current = 1;
        while (current < MAX_VEHICLE+1) {
            ArrayList<Integer> destilist = new ArrayList<>();
            int size = chromosome.size();
            for (int i = 0; i < size; i++) {
                if (chromosome.get(i) == current)
                    destilist.add(i+1);
            }
            SubChromosome desti = new SubChromosome(destilist);
            double temp=desti.calculate_fitness();
            if (max< temp){
                max = temp;
            }
            current++;
        }

        this.fitness = (double)1.0/max;

    }

    public void swapMutate(){
        Random rand = new Random();
        int index1 = rand.nextInt(N-1);
        int index2 = rand.nextInt(N-1);

        // swap 2 index trong chromosome của this Individual
        int tmp = chromosome.get(index1);
        chromosome.set(index1, chromosome.get(index2));
        chromosome.set(index2, tmp);

    }

    double getFitness(){
        return -this.fitness;
    }



}
