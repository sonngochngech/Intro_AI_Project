package com.AI.core.GA;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.AI.core.GA.Configs.MAX_VEHICLE;

@Slf4j
@Service
@AllArgsConstructor
@Getter
@Setter
public class Run {
    public static double[][] distance;
    public static int N;
    public static ArrayList<ArrayList<Integer>> result=new ArrayList<>();


    public double runn() throws IOException {
        GA solver=new GA();
        Individual best=solver.run();
        System.out.println(best.fitness);
        int current = 1;
        while (current < MAX_VEHICLE+1) {
            ArrayList<Integer> destilist = new ArrayList<>();
            int size = best.chromosome.size();
            for (int i = 0; i < size; i++) {
                if (best.chromosome.get(i) == current)
                    destilist.add(i+1);
            }
            SubChromosome sub=new SubChromosome(destilist);
            sub.calculate_fitness();
            result.add(sub.route);
            current++;
        }
        return best.fitness;
    }




}
