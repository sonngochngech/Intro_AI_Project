package com.AI.core.GA;

import com.AI.core.helper.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Configs {
    public static final int POPULATION_SIZE=200;

    public static final double CROSSOVER_RATE=0.9;

    public static final double MUTATION_RATE=0.1;

    public static final int MAX_GENERATION=200;
    public static  int MAX_VEHICLE=10;
    public static ArrayList<Point> points=new ArrayList<>();

}
