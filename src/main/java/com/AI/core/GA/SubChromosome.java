package com.AI.core.GA;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.AI.core.GA.Run.distance;

@Component
public class SubChromosome {
    public ArrayList<Integer> chromosome;

    public double fitness;

    public ArrayList<Integer> route;

    public SubChromosome(ArrayList<Integer> chromosome){
        this.fitness=0;
        this.chromosome=new ArrayList<>();
        this.chromosome.addAll(chromosome);
        this.route=new ArrayList<>();
    }

    public double calculate_fitness(){
        int index=0;//Danh dau dia diem ma xe dang dung o lo trinh hien tai
        route.add(0);
        double sum=0;
        while(!chromosome.isEmpty()){ //Khi nao con chua di het cac dia diem duoc gan cho SubChromosome
            int size = chromosome.size();
            double minDistance=10000; //Cho khoang cach gan nhat rat lon
            int min=chromosome.get(0);
            int position=0;
            for(int i=0;i<size;i++){ //Duyet tu dau den cuoi SubChromosome
                if(minDistance> distance[index][chromosome.get(i)] && chromosome.get(i)!=index){
                    minDistance=distance[index][chromosome.get(i)];
                    min=chromosome.get(i);//Tim dia diem gan nhat voi dia diem hien tai, tuc la index
                    position=i;
                }
            }
            sum+=minDistance;
            index=min; //Them dia diem gan nhat vao route, index nhay? den dia diem do
            route.add(index);
            chromosome.remove(position);

        }

        this.fitness=sum;
        return sum;
    }
}
