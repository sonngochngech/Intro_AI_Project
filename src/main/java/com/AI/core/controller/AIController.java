package com.AI.core.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/")
public interface AIController {

    @GetMapping("hello")
     String home(Model model);

    @PostMapping("/import")
    String mapReadExcelData(@RequestParam("file") MultipartFile excelDataFile) ;





}
