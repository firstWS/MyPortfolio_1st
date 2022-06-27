package com.ws.ch4.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import com.ws.ch4.domain.UserDto;
import com.ws.ch4.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    RegisterService registerService;

//    @InitBinder
//    public void toDate(WebDataBinder binder) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
//        binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록
//
//        //	List<Validator> validatorList = binder.getValidators();
//        //	System.out.println("validatorList="+validatorList);
//    }

    @GetMapping("/add")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/add")
    public String save(@Valid UserDto userDto, BindingResult result, Model m) throws Exception {
        System.out.println("result=" + result);
        System.out.println("userDto=" + userDto);

        // User객체를 검증한 결과 에러가 있으면, registerForm을 이용해서 에러를 보여줘야 함.
        if (result.hasErrors()) {
            System.out.println("User객체 에러!!");
            return "registerForm";
        }

        // 2. DB에 신규회원 정보를 저장
        registerService.userInsert(userDto);
        System.out.println("정상적으로 회원가입되엇당!");
        return "registerInfo";
    }

//    @PostMapping("/userIdChk")
    @RequestMapping(value = "register/userIdChk", method = RequestMethod.POST)
    @ResponseBody
    public String userIdChkPOST(String userID) throws Exception{
//        System.out.println("userIdChk() 진입");

        int result = registerService.id_Check(userID);
//        System.out.println("userID = " + userID);

        if(result != 0)     return "fail";      //중복 아이디 존재 O
        else                return "success";   //중복 아이디 존재 X
    }

//    @PostMapping("/userEmailChk")
    @RequestMapping(value = "register/userEmailChk", method = RequestMethod.POST)
    @ResponseBody
    public String userEmailChkPOST(String userEmail) throws Exception{
//        System.out.println("userEmailChk() 진입");

        int result = registerService.email_Check(userEmail);
//        System.out.println("userEmail = " + userEmail);

        if(result != 0)     return "fail";      //중복 이메일 존재 O
        else                return "success";   //중복 이메일 존재 X
    }
}