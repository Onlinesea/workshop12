package Numbers.Workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Numbers.Workshop12.exeception.RandomNumberException;
import Numbers.Workshop12.model.Generate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class GenerateController {
    private Logger logger = LoggerFactory.getLogger(GenerateController.class);

    //root path
    //define out main page that forward to the generatePage (form)
    @GetMapping("/")
    public String showGenerateNumForm(Model model){
        logger.info("-- showGenerateNumForm --");
        //Init an empty generate object that carries an int  -x number to be 
        Generate genObj = new Generate();
        
        //Default to 1 everytime the page get loaded.
       // genObj.setNumberVal(1);
        model.addAttribute("generateObj",genObj);

        return "generatePage";

    }

    @PostMapping("/generate")
    public String generateNumbers(@ModelAttribute Generate generate, Model model){
        int genNo=31;
        String[] imgNumbers = new String [genNo];
        int numberRandomNum = generate.getNumberVal();
        logger.info("from the text field > "+ numberRandomNum);
        if(numberRandomNum < 0 || numberRandomNum > 30){
            throw new RandomNumberException();
        }
        for(int i = 0; i<genNo; i++){
            //logger.info("number" + i + ".jpg");
            imgNumbers[i] = "number" + i + ".jpg";
        }

        //logger.info("arr > " + imgNumbers);

        List<String> selectedImg = new ArrayList<String>();
        Random random = new Random();
        Set<Integer>uniqueGenResult = new LinkedHashSet<Integer>();

        while(uniqueGenResult.size()< numberRandomNum){
            Integer resultOfRandNumber = random.nextInt(genNo);
            uniqueGenResult.add(resultOfRandNumber);
        }

        Iterator<Integer> it = uniqueGenResult.iterator();
        Integer currElem = null;
        while(it.hasNext()){
            currElem=it.next();
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }

        model.addAttribute("randNoResult", selectedImg.toArray());
        model.addAttribute("numberRandomNum", numberRandomNum);


        return "result";
    }
    
}