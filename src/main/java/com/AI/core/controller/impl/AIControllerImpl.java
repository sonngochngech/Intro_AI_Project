package com.AI.core.controller.impl;


import com.AI.core.GA.Configs;
import com.AI.core.GA.Run;
import com.AI.core.controller.AIController;

import com.AI.core.helper.ExcelHelper;
import com.AI.core.service.BingMapAPIService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "home";
    }

    @Override
    public String mapReadExcelData(MultipartFile excelDataFile,Integer count, RedirectAttributes redirectAttributes)   {
        try{
            //get input information
            Configs.MAX_VEHICLE=count;
            InputStream inputStream=excelDataFile.getInputStream();
            ArrayList<String> addressList=excelHelper.readExcelHelper(inputStream);
            double[][] distances=bingMapAPIService.getDistanceByCoordinates(addressList);
            Run.N=addressList.size();
            Run.distance=distances;

            //run algorithm
            run.runn();

//            get route in xml format
            int counttt =1;
            String startString="<path>";
            for(ArrayList<Integer> h: Run.result){
                String startsub="<way id=\""+counttt+ "\">";
                for(int i:h){
                    startsub= startsub + "<node lat=\""+  BingMapAPIService.points.get(i).x+"\" lon=\""+BingMapAPIService.points.get(i).y+"\"/>";
                }
                startsub+="</way>";
                startString+=startsub;
                counttt++;
            }
            startString+="</path>";


//            get route in String List format
            ArrayList<ArrayList<String>> routes=Run.result.stream()
                    .map(resu->resu.stream().map(re->addressList.get(re)).collect(Collectors.toCollection(ArrayList::new)))
                    .collect(Collectors.toCollection(ArrayList::new));

            redirectAttributes.addFlashAttribute("routes", routes);
            redirectAttributes.addFlashAttribute("stringRoutes",startString);
            Run.result.clear();
        }catch(Exception e){
           e.printStackTrace();
        }

        return "redirect:/index";
    }

    @Override
    public String getIndex(Model model) {
        return "index";
    }
}
