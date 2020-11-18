package com.spring.biz.board.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.biz.board.BoardVO;

@Repository
public class BoardDAOMybatis {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public BoardDAOMybatis() {
		System.out.println(">> BoardDAOMybatis() 객체 생성 ");
	}
	
	public void insertBoard(BoardVO vo) {
		System.out.println("===> MyBatis(JdbcTemplate)로 insertBoard() 실행");
		
		mybatis.insert("boardDAO.insertBoard", vo);
//		Object[] args = {vo.getTitle(),vo.getWriter(),vo.getContent()};
//		jdbcTemplate.update(BOARD_INSERT, args);
	}
	//글수정
	public void updateBoard(BoardVO vo) {
		System.out.println("===> MyBatis(JdbcTemplate)로 updateBoard() 실행");
		
		mybatis.update("boardDAO.updateBoard", vo);
//		jdbcTemplate.update(BOARD_UPDATE, vo.getTitle(),vo.getContent(),vo.getSeq());
	}
	//글삭제
	public void deleteBoard(BoardVO vo) {
		System.out.println("===> MyBatis(JdbcTemplate)로 deleteBoard() 실행");
		mybatis.update("boardDAO.deleteBoard", vo);
	}
	//글
	public BoardVO getBoard(BoardVO vo) {
		System.out.println("===> MyBatis(JdbcTemplate)로 getBoard() 실행");

		return mybatis.selectOne("boardDAO.getBoard", vo);
//		Object[] args = {vo.getSeq()};
//		return jdbcTemplate.queryForObject(BOARD_GET, args, new BoardRowMapper());
	}
	public List<BoardVO> getBoardList(BoardVO vo){
		System.out.println("===> MyBatis(JdbcTemplate)로 getBoardList() 실행");
		
		return mybatis.selectList("boardDAO.getBoardList", vo);
	}
}












