package com.AI.core.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RequestMapping("/")
public interface AIController {

    @GetMapping("hello")
     String home(Model model);


    @GetMapping("index")
    String getIndex(Model model);

    @PostMapping("/import")
    String mapReadExcelData(@RequestParam("file") MultipartFile excelDataFile,@RequestParam("officerNumber")  Integer count,RedirectAttributes redirectAttributes) ;






}
