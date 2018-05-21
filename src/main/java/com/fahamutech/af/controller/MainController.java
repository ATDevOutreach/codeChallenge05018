package com.fahamutech.af.controller;


import org.jscience.physics.amount.Amount;
import org.jscience.physics.model.RelativisticModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.measure.quantity.Mass;
import java.util.Map;

import static javax.measure.unit.SI.KILOGRAM;

@Controller
public class MainController {

    /**
     * this method call as ana index of the site
     * @return the view of the home page
     */
    @RequestMapping("/")
    String index() {
        return "index";
    }

    /**
     * used in demo mode only
     * @param model used to render view
     * @return the model to view
     */
    @RequestMapping("/hello")
    String hello(Map<String, Object> model) {
        RelativisticModel.select();
        Amount<Mass> m = Amount.valueOf("12 GeV").to(KILOGRAM);
        model.put("science", "E=mc^2: 12 GeV = " + m.toString());
        return "hello";
    }

}
