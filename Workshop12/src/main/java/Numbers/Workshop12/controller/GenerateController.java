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
        //Init an empty generate object that carries an int  -x number to be be gen 
        Generate genObj = new Generate();
        
        //Default to 1 everytime the page get loaded.
       // genObj.setNumberVal(1);
        model.addAttribute("generateObj", genObj);
        return "generatePage";
    }

    //Mapping the generate page to the post value -> post value is the value that is being send to the server
    @PostMapping("/generate")                   //if there is a post value/ submit -> it will redirect to generate page
    //when there is a POST method then it will be mapped towards this page with /generate -> hence it cant access the page using 
    //GET method 
    //generateNumbers is the class that will run when it is posted over, returning Strings
    //Creating the class based on Generate class 
    public String generateNumbers(@ModelAttribute Generate generate, Model model){
        
        //Initialising the genNo -> upperlimit of the randomNumber generator
        int genNo=31;
        String[] imgNumbers = new String [genNo];

        //This is the value that the user have keyed in 
        int numberRandomNum = generate.getNumberVal();

        //To show in the logger what was entered into the text field
        logger.info("from the text field > "+ numberRandomNum);

        //To check if the number that the user keyed in is invalid 
        if(numberRandomNum < 0 || numberRandomNum > 30){
            throw new RandomNumberException();
        }

        //If the number that is keyed is valid, name the image 0 to 31
        for(int i = 0; i<genNo; i++){
            //logger.info("number" + i + ".jpg");
            imgNumbers[i] = "number" + i + ".jpg";
        }

        //Generate a list of string list 
        List<String> selectedImg = new ArrayList<String>();

        //Instancing the new random to generate random numbers
        Random random = new Random();

        //Instacing a set and LinkedHashSet as it can only hold unique values 
        Set<Integer>uniqueGenResult = new LinkedHashSet<Integer>();

        //To check if the required number of random number is obtain, 
        //numberRandNum is the number that the user input, the number that he want 
        //uniqueGenResult is the set that contains all the random numbers
        while(uniqueGenResult.size()< numberRandomNum){

            //genNo is the total number of images that are inside that can be choosen 
            //which is 31
            Integer resultOfRandNumber = random.nextInt(genNo);

            //LinkedHashSet can only hold unique values, hence duplicate values will not be added 
            uniqueGenResult.add(resultOfRandNumber);
        }

        //Initialising a iterator to iterate throught the list of unique result 
        Iterator<Integer> it = uniqueGenResult.iterator();


        Integer currElem = null;
        while(it.hasNext()){

            //currElem will take the value of the curr number 
            currElem=it.next();

            //selectedImg is a list of String while the imgNumbers is a string of number1.jpg
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }

        //selectedImg -> list of img that are choosen and change it into a array
        //This will be used in result.html for looping thorugh to produce the respective img 
        model.addAttribute("randNoResult", selectedImg.toArray());

        //numberRandomNum -> the input that the user put in, required
        model.addAttribute("numberRandomNum", numberRandomNum);

        //Return the result.html 
        return "result";
    }
    
}
