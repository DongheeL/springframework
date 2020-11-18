package com.spring.biz.view.board;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.biz.board.BoardService;
import com.spring.biz.board.BoardVO;

@Controller
@SessionAttributes("board")	//board라는 이름의 Model이 있으면 session에 저장(ex: getBoard 메소드의 model.addAttribute("board", board))
public class BoardController {
	@Autowired
	private BoardService boardService;	//결합도를 낮춤으로써 구현체가 변경되어도
	
	public BoardController() {
		System.out.println(">>> BoardController() 객체 생성");
	}
	
	//@ModelAttribute : 리턴되는 데이터를 view쪽으로 전달	-> 모든 페이지에서 사용되는 데이터라면 @ModelAttribute를 사용하는 것이 편리
	//@ModelAttribute 선언된 메소드는 @RequestMapping 메소드보다 먼저 실행됨
	//	뷰에 전달될 때 설정된 명칭(예: conditionMap)으로 전달 
	@ModelAttribute("conditionMap")
	public Map<String, String> searchCondition(){
		System.out.println("--->>> searchCondition() 실행");
		Map<String, String> conditionMap = new TreeMap<String, String>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		return conditionMap;
	}
	
	@RequestMapping("/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {
		BoardVO board = boardService.getBoard(vo);
		//mav.addObject("board", board);
		//mav.setViewName("getBoard.jsp");
		model.addAttribute("board", board);
		System.out.println("boardVO : "+board);
		return "getBoard.jsp";
	}
	
	@RequestMapping("/getBoardList.do")
	public String getBoardList(
			BoardVO vo, Model model) {
		System.out.println(">>> 게시글 전체 목록 보여주기 - getBoardList()");
		System.out.println("condition : "+vo.getSearchCondition());
		System.out.println("keyword : "+vo.getSearchKeyword());
		
		//검색조건 값이 없을 때, 기본값 설정 (값 설정 없이 null이 넘어가면 'null'로 인식하기 때문에 제대로된 결과를 처리할 수 없다.)
		if(vo.getSearchCondition() == null) {
			vo.setSearchCondition("TITLE");
		}
		if(vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}
		
		List<BoardVO> list = boardService.getBoardList(vo);
//		List<BoardVO> list = boardService.getBoardList(condition, keyword);
		
		//mav.addObject("boardList", list);
		//mav.setViewName("getBoardList.jsp");
		model.addAttribute("boardList", list);
		
		return "getBoardList.jsp";
	}

	@RequestMapping("/insertBoard.do")
	public String insertBoard(BoardVO vo) throws IllegalStateException, IOException {
		System.out.println(">>> 게시글 입력 처리 - insertBoard()");
		
		/* 파일 업로드 관련 처리
		 * MultipartFile 인터페이스 주요 메소드
		 * String getOriginalFilename() : 업로드한 파일명 찾기
		 * void transferTo(File dest) : 업로드할 파일을 업로드 처리(파라미터 dest는 업로드할 위치)
		 * boolean isEmpty() : 업로드 파일 존재 여부
		 */
		
		MultipartFile uploadFile = vo.getUploadFile();
		System.out.println("> uploadFile : "+uploadFile);
		if(!(uploadFile.isEmpty())) {
			String filename = uploadFile.getOriginalFilename();
			System.out.println("filename(원본파일명) : "+filename);	//논리적 파일명
			System.out.println("저장용파일 : "+UUID.randomUUID());
										//문자열로 입력한 경로(temp)는 실제 존재해야 함
			uploadFile.transferTo(new File("C:/MyStudy/temp/"+filename));
		}
		
		boardService.insertBoard(vo);
		
		return "redirect:getBoardList.do";
	}	
	
	@RequestMapping("/updateBoard.do")
	// 인수(파라미터)에 설정한 @ModelAttribute("board") : 속성명 board가 있으면 사용
	// 없으면 새로 만들고, 있으면 해당 객체 사용
	// 파라미터값 설정해서 전달
	public String updateBoard(@ModelAttribute("board") BoardVO vo) {
		System.out.println(">>> 게시글 수정 처리");
		System.out.println("updateBoard BoardVO : "+vo);
		
		boardService.updateBoard(vo);
		
		return "redirect:getBoardList.do";
	}	
	
	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO vo) {
		System.out.println(">>> 게시글 삭제 처리");
		boardService.deleteBoard(vo);
		return "redirect:getBoardList.do";
	}	
	
	@RequestMapping("/getJson.do")
	@ResponseBody	//응답데이터를 응답 객체의 body영역에 전달
	public List<BoardVO> getListJson(BoardVO vo){
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		
		List<BoardVO> list = boardService.getBoardList(vo);
		System.out.println(">>getListJson() list : "+list);
		
		return list;
	}
}























