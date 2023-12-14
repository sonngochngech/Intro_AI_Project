package com.AI.core.GA;

import org.springframework.stereotype.Component;

@Component
public class Configs {
    public static final int POPULATION_SIZE=200;

    public static final double CROSSOVER_RATE=0.9;

    public static final double MUTATION_RATE=0.1;

    public static final int MAX_GENERATION=200;
    public static final int MAX_VEHICLE=10;

}
