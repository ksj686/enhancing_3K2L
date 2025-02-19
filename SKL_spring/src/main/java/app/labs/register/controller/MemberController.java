package app.labs.register.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.register.model.Member;
import app.labs.register.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 아이디 중복 확인을 위한 API
    @GetMapping("/members/check-memberid")
    public ResponseEntity<Boolean> checkMemberId(@RequestParam(name = "memberId") String memberId) {
        boolean isDuplicated = memberService.isUserIdDuplicated(memberId);
        log.info("memberID check for: {}, isDuplicated: {}", memberId, isDuplicated);

        return ResponseEntity.ok(isDuplicated);
    }

    // 닉네임 중복 확인을 위한 API
    @GetMapping("/members/check-memberNick")
    public ResponseEntity<Boolean> checkMemberNick(@RequestParam(name = "memberNickname") String memberNickname) {
        boolean isDuplicated = memberService.isMemberNickDuplicated(memberNickname);
        log.info("memberNickname check for: {}, isDuplicated: {}", memberNickname, isDuplicated);

        return ResponseEntity.ok(isDuplicated);
    }

    // 회원가입 폼을 반환하는 메서드
    // @GetMapping("/register/insertform")
    // public String showInsertForm(Model model) {
    // return "thymeleaf/register/insertform";
    // }

    // 기본 메서드들: 서버의 시간을 반환하는 홈 페이지
    @GetMapping(value = { "/members", "/members/" })
    public String home(Model model) {
        model.addAttribute("serverTime", "서버시간");
        return "thymeleaf/register/home";
    }

    // 회원가입 페이지를 반환하는 메서드
    @GetMapping("/members/insert")
    public String insertMember(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("memberid");

        if (memberId != null) {
            return "redirect:/home";
        }
        return "thymeleaf/register/register";
    }

    // 회원가입 처리 메서드
    @PostMapping("/members/insert")
    public String insertMember(Member member, RedirectAttributes redirectAttributes) {
        try {
            // log.info("회원가입 진행중");
            memberService.insertMember(member);
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료 되었습니다!");
            return "redirect:/login";
        } catch (RuntimeException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "회원가입에 실패했습니다. 다시 시도해주세요."); // 실패 메시지
            return "redirect:/members/insert";
        }
    }

    // 회원 정보 수정 폼을 반환하는 메서드
//    @GetMapping("/members/edit")
//    public String showEditProfileForm(HttpSession session, Model model) {
//        String memberId = (String) session.getAttribute("memberid");
//        log.info("GET /members/edit - Session memberId: {}", memberId);
//
//        if (memberId != null) {
//            Member member = memberService.findByUserId(memberId);
//            log.info("GET /members/edit - Member: {}", member);
//            model.addAttribute("member", member);
//            return "thymeleaf/register/edit_profile";
//        } else {
//            return "redirect:/login";
//        }
//    }

    // 회원 정보 수정 처리 메서드
//    @PostMapping("/members/edit")
//    public String updateProfile(@ModelAttribute Member member, HttpSession session,
//            RedirectAttributes redirectAttributes) {
//        String memberId = (String) session.getAttribute("memberid");
//        log.info("POST /members/edit - Session memberId: {}", memberId);
//        log.info("Updating member: {}", member);
//
//        if (memberId != null && memberId.equals(member.getMemberId())) {
//            memberService.updateMember(member);
//            redirectAttributes.addFlashAttribute("message", "수정 되었습니다!");
//            return "redirect:/members/mypage";
//        } else {
//            return "redirect:/login";
//        }
//    }

    // 로그인 폼을 반환하는 메서드
    @GetMapping("/login")
    public String loginMember(Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String memberId = (String) session.getAttribute("memberid");
        if (memberId != null) {
            return "redirect:/home";
        } else {
            return "thymeleaf/register/loginform";
        }
    }

    // 로그인 처리 메서드
    @PostMapping("/login")
    public String loginMember(@RequestParam("memberId") String memberId, @RequestParam("memberPwd") String memberPwd,
            HttpSession session, RedirectAttributes redirectAttrs) {
        Member member = memberService.findByUserId(memberId);
        if (member != null) {
            if (member.getMemberPwd().equals(memberPwd)) {
                if (member.getMemberStatus().equals("ACTIVE")) {
                    memberService.updateLastLogin(memberId);
                    List<Map<String, Object>> checkAttendJoin = memberService.checkAttendJoin(memberId);
                    // log.info("size: " + checkAttendJoin.size());
                    // log.info(checkAttendJoin.toString());
                    if (!(checkAttendJoin.size() > 0)) {
                        memberService.addAttendJoin(memberId);
                    }
                    session.setMaxInactiveInterval(600); // 10분
                    session.setAttribute("memberid", memberId);
                    return "redirect:/home";
                } else {
                    session.invalidate();
                    redirectAttrs.addFlashAttribute("message", "현재 사용할 수 없는 계정입니다.");
                    return "redirect:/login";
                }
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
                session.invalidate();
                redirectAttrs.addFlashAttribute("message", "아이디 또는 패스워드가 잘못되었습니다.");
                return "redirect:/login";
            }
        } else {
            System.out.println("사용자를 찾을 수 없습니다.");
            session.invalidate();
            redirectAttrs.addFlashAttribute("message", "아이디 또는 패스워드가 잘못되었습니다.");
            return "redirect:/login";
        }
    }

    // 로그아웃 메서드
    @GetMapping("/logout")
    public String logoutMember(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    // 아이디 찾기 폼을 반환하는 메서드
    @GetMapping("/members/find-username")
    public String showFindUsernameForm() {
        return "thymeleaf/register/find_username";
    }

    // 아이디 찾기 처리 메서드
    @PostMapping("/members/find-username")
    public String findUsername(@RequestParam(value = "emailOrPhone") String emailOrPhone, Model model) {
        String memberId = memberService.findUserIdByEmailOrPhone(emailOrPhone);
        if (memberId != null) {
            model.addAttribute("message", "귀하의 아이디는 " + memberId + "입니다.");
            model.addAttribute("messageType", "success"); // 성공 메시지
        } else {
            model.addAttribute("message", "등록된 이메일 또는 전화번호를 찾을 수 없습니다.");
            model.addAttribute("messageType", "error"); // 오류 메시지
        }
        return "thymeleaf/register/find_username";
    }

    // 비밀번호 찾기 폼을 반환하는 메서드
    @GetMapping("/members/find-password")
    public String showFindPasswordForm() {
        return "thymeleaf/register/find_password";
    }

    // 비밀번호 찾기 처리 메서드
    @PostMapping("/members/find-password")
    public String findPassword(@RequestParam(value = "emailOrPhone") String emailOrPhone, Model model) {
        String password = memberService.findPasswordByEmailOrPhone(emailOrPhone);
        if (password != null) {
            model.addAttribute("message", "귀하의 비밀번호는 " + password + "입니다.");
            model.addAttribute("messageType", "success"); // 성공 메시지

        } else {
            model.addAttribute("message", "등록된 이메일 또는 전화번호를 찾을 수 없습니다.");
            model.addAttribute("messageType", "error"); // 오류 메시지

        }
        return "thymeleaf/register/find_password";
    }
}
