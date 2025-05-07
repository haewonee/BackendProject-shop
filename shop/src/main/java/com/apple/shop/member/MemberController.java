package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/register")
    public String register(Authentication auth){
        if(auth!=null && auth.isAuthenticated()){
            return "redirect:/list";
        }
        return "register.html";
    }

    @PostMapping("/member")
    public String addMember(@RequestParam String displayName, @RequestParam String username, @RequestParam String password) throws Exception {
        this.memberService.saveMem(displayName, username, password);
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth){
        CustomUser result = (CustomUser)auth.getPrincipal();
        System.out.println(result.displayName);
        return "mypage.html";
    }

}
