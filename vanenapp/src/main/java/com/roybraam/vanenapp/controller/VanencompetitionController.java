/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.controller;

import com.roybraam.vanenapp.entity.Vanencompetition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Roy Braam
 */
@Controller
public class VanencompetitionController {

    @RequestMapping("/admin/vanencompetition")
    public String edit(@ModelAttribute("vanencompetition")Vanencompetition competition) {
        return "admin/vanencompetition";
    }
}