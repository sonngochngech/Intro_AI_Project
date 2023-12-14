package com.AI.core.controller.impl;


import com.AI.core.GA.Run;
import com.AI.core.controller.AIController;

import com.AI.core.helper.ExcelHelper;
import com.AI.core.service.BingMapAPIService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequiredArgsConstructor
public class AIControllerImpl implements AIController {
    private final ExcelHelper excelHelper;
    private  final BingMapAPIService bingMapAPIService;
    private final Run run;


    @Override
    public String home(Model model) {

        String hello="ssss";
        return "home";
    }

    @Override
    public String mapReadExcelData(MultipartFile excelDataFile)   {
        try{
            InputStream inputStream=excelDataFile.getInputStream();
            ArrayList<String> addressList=excelHelper.readExcelHelper(inputStream);
//            log.info("{}",addressList);
            double[][] distances=bingMapAPIService.getDistance(addressList);
//            for(int i=0;i< 3;i++){
//                for(int j=0;j<3;j++){
//                    System.out.println(distances[i][j]);
//                }
//            }
//            log.info("{}",distances);
            Run.N=addressList.size();
            Run.distance=distances;
//            for(int i=0;i< 3;i++){
//                for(int j=0;j<3;j++){
//                    System.out.println(Run.distance[i][j]);
//                }
//            }

            run.runn();
//            System.out.println("SSSSSS");
//            for(ArrayList<Integer> i: Run.result){
//                for(int j:i){
//                    System.out.print(j);
//                }
//            }
//

            ArrayList<ArrayList<String>> routes=Run.result.stream()
                    .map(resu->resu.stream().map(re->addressList.get(re)).collect(Collectors.toCollection(ArrayList::new)))
                    .collect(Collectors.toCollection(ArrayList::new));
            log.info("hello");
            log.info("{}",routes);

        }catch(Exception e){
           e.printStackTrace();
        }







        return "redirect:/index";

    }
}
