package com.spring.biz.view.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.biz.user.UserService;
import com.spring.biz.user.UserVO;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	public UserController() {
		System.out.println(">>> UserController() 객체 생성");
	}
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login(UserVO vo) {
		System.out.println(">> 로그인 처리 - POST");
		System.out.println("vo : " + vo);
		System.out.println("userDAO : " + userService);
		
		UserVO user = userService.getUser(vo);
		if (user != null) {
			System.out.println("> 로그인 성공!!");
			return "redirect:getBoardList.do";
		} else {
			System.out.println("> 로그인 실패~~~");
			return "login.jsp";
		}
	}
	
	/*	@ModelAttribute : 모델의 속성값으로 저장(속성명 별도 지정)
	 	별도 명칭 부여 안하면 <데이터 타입>의 첫글자를 소문자로 가진 명칭으로 사용됨.
	 *	@ModelAttribute UserVO : 속성명 userVO 
	 	@ModelAttribute("user") UserVO	---> 속성명 user 사용
	 */
	
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public String loginView(@ModelAttribute("user") UserVO vo) {
		System.out.println(">>> 로그인 처리 - login 뷰로 이동");
		vo.setId("test1");
		vo.setPassword("test1");
		return "login.jsp";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		//1. 세션 초기화
		session.invalidate();
		
		return "login.jsp";
	}	
}
