package com.spring.biz.board.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.biz.board.BoardVO;

@Repository	//DAO관련은 Repository 어노테이션 사용
public class BoardDAOSpring {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//SQL문
	private final String BOARD_INSERT 
		= "INSERT INTO BOARD (SEQ, TITLE, WRITER, CONTENT) "
		+ "VALUES ((SELECT NVL(MAX(SEQ),0)+1 FROM BOARD),?,?,?)"; 
	private final String BOARD_UPDATE
		= "UPDATE BOARD SET TITLE = ?, CONTENT = ? WHERE SEQ = ?";
	private final String BOARD_DELETE
		= "DELETE FROM BOARD WHERE SEQ = ?";
	private final String BOARD_GET
		= "SELECT * FROM BOARD WHERE SEQ = ?";
	private final String BOARD_LIST
		= "SELECT * FROM BOARD ORDER BY SEQ DESC";
	private final String BOARD_LIST_T
	= "SELECT * FROM BOARD WHERE TITLE LIKE '%'||?||'%' ORDER BY SEQ DESC";
	private final String BOARD_LIST_C
	= "SELECT * FROM BOARD WHERE CONTENT LIKE '%'||?||'%' ORDER BY SEQ DESC";

	
	public BoardDAOSpring() {
		System.out.println(">> BoardDAOSpring() 객체 생성");
	}
	
	//글등록
	public void insertBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC(JdbcTemplate)로 insertBoard() 실행");
		Object[] args = {vo.getTitle(),vo.getWriter(),vo.getContent()};
		jdbcTemplate.update(BOARD_INSERT, args);
	}
	//글수정
	public void updateBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC(JdbcTemplate)로 updateBoard() 실행");
		
		jdbcTemplate.update(BOARD_UPDATE, vo.getTitle(),vo.getContent(),vo.getSeq());
	}
	//글삭제
	public void deleteBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC(JdbcTemplate)로 deleteBoard() 실행");
		jdbcTemplate.update(BOARD_DELETE, vo.getSeq());
	}
	//글
	public BoardVO getBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC(JdbcTemplate)로 getBoard() 실행");

		Object[] args = {vo.getSeq()};
		return jdbcTemplate.queryForObject(BOARD_GET, args, new BoardRowMapper());
	}
	public List<BoardVO> getBoardList(BoardVO vo){
		System.out.println("===> Spring JDBC(JdbcTemplate)로 getBoardList() 실행");
		Object[] args = {vo.getSearchKeyword()};
		String sql = "";
		if("TITLE".equals(vo.getSearchCondition())) {
			sql = BOARD_LIST_T;
		}else if("CONTENT".equals(vo.getSearchCondition())){
			sql = BOARD_LIST_C;
		}
		
//		return jdbcTemplate.query(sql, new Object[] {vo.getSearchKeyword()}, new BoardRowMapper());
		return jdbcTemplate.query(sql, args, new BoardRowMapper());
	}

		
}



























