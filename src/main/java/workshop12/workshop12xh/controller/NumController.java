package workshop12.workshop12xh.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import workshop12.workshop12xh.model.NumberObject;

@Controller
public class NumController {
    
    private static Logger logger = LoggerFactory.getLogger(NumController.class);

    @GetMapping
    public String generateForm(Model model){
        NumberObject numObj = new NumberObject();
        model.addAttribute("formObj", numObj);

        return "form"; 
    }

    @PostMapping("/generate")
    public String submittedResult(@ModelAttribute NumberObject numObj, Model model){
        String numString = Integer.toString(numObj.getNumber());

        this.generateResult(numString, model);

        return "form";
    }

    @GetMapping("/generate")
    public String requestParamResult(@RequestParam String numString, Model model){

        this.generateResult(numString, model);

        return "form";
        
    }

    @GetMapping("/generate/{numString}")
    public String pathVariable(@PathVariable String numString, Model model){

        this.generateResult(numString, model);

        return "form";
    }

    private void generateResult(String numRequiredString, Model model){
        int maxNum = 31;
        String[] imgString = new String[maxNum];

        for(int i=0; i<imgString.length; i++){
            imgString[i]= "number" + i + ".jpg";
        }

        Random random = new Random();
        Set<Integer> uniqueGenNum = new LinkedHashSet<Integer>();
        int uniqueNumRequired = Integer.parseInt(numRequiredString);

        logger.info("text from field > " + uniqueNumRequired);
        while(uniqueGenNum.size()< uniqueNumRequired){
            uniqueGenNum.add(random.nextInt(maxNum));    
        }

        List<String> generatedImgs = new ArrayList<>();
        Iterator<Integer> it = uniqueGenNum.iterator();

        while(it.hasNext()){
            int curr = it.next();
            generatedImgs.add(imgString[curr]);
        }

        NumberObject numObj = new NumberObject();
        numObj.setNumber(uniqueNumRequired);
        model.addAttribute("formObj", numObj);
        model.addAttribute("generatedImgs", generatedImgs.toArray());
        model.addAttribute("numberInput", uniqueNumRequired);

    }
}
