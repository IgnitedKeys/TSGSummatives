/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.controllers;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author gavinlinnihan
 */

//
//@Controller
//public class CustomErrorController implements ErrorController {
//
//    //private static final String PATH = "${server.error.path}";
//
//    private static final String PATH = "/error";
//    @RequestMapping(value = PATH)
//    public ResponseEntity<Error> error(WebRequest webRequest, HttpServletResponse response) {
//        Error err = new Error();
//        //err.setMessage(ex.getMessage());
//        err.setMessage("oops! That is not a valid path");
//        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
//
//    }
//
//
//}
