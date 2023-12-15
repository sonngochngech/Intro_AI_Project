package com.AI.core.controller.impl;


import com.AI.core.GA.Configs;
import com.AI.core.GA.Run;
import com.AI.core.controller.AIController;

import com.AI.core.helper.ExcelHelper;
import com.AI.core.service.BingMapAPIService;
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
            Configs.MAX_VEHICLE=count;
            InputStream inputStream=excelDataFile.getInputStream();
            ArrayList<String> addressList=excelHelper.readExcelHelper(inputStream);
            double[][] distances=bingMapAPIService.getDistanceByCoordinates(addressList);
            Run.N=addressList.size();
            Run.distance=distances;

            run.runn();
            ArrayList<ArrayList<String>> routes=Run.result.stream()
                    .map(resu->resu.stream().map(re->addressList.get(re)).collect(Collectors.toCollection(ArrayList::new)))
                    .collect(Collectors.toCollection(ArrayList::new));
            redirectAttributes.addFlashAttribute("routes", routes);
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
