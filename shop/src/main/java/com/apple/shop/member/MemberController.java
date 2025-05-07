package com.apple.shop.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@Tag(name="MemberController", description = "회원 정보 관련된 API들입니다.")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "가입 화면 반환",description = "존재하는 사용자고, 로그인한 사용자인데 요청 보내면 리다이렉션")
    @GetMapping("/register")
    public String register(Authentication auth){
        if(auth!=null && auth.isAuthenticated()){
            return "redirect:/list";
        }
        return "register.html";
    }

    @Operation(summary = "회원정보 추가", description = "사용자로부터 정보를 받아 디비에 저장합니다.")
    @PostMapping("/member")
    public String addMember(@RequestParam String displayName, @RequestParam String username, @RequestParam String password) throws Exception {
        this.memberService.saveMem(displayName, username, password);
        return "redirect:/list";
    }

    @Operation(summary = "로그인 화면 반환")
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @Operation(summary = "마이페이지 화면 반환")
    @GetMapping("/my-page")
    public String myPage(Authentication auth){
        CustomUser result = (CustomUser)auth.getPrincipal();
        System.out.println(result.displayName);
        return "mypage.html";
    }

}
