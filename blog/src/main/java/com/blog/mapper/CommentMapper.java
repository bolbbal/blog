package com.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.blog.domain.CommentVo;

@Mapper
public interface CommentMapper {

	// 댓글 등록
	@Insert("INSERT INTO tbl_comment (com_writer, com_content, bno) VALUES (#{com_writer}, #{com_content}, #{bno})")
	@Options(useGeneratedKeys = true, keyProperty = "com_bno")
	// useGeneratedKeys = true : 삽입 후 자동 생성된 키 값을 사용하도록 지정합니다.
	// keyProperty = "com_bno" : 생성된 키가 매핑될 객체의 속성명을 지정합니다.
	Long insertComment(CommentVo commentVo);

	@Select("SELECT * FROM tbl_comment WHERE com_bno = #{com_bno}")
	CommentVo getCommentByComBno(Long bno);

	// 댓글 목록 조회
	@Select("SELECT * FROM tbl_comment WHERE bno = #{bno} ORDER BY com_regdate DESC")
	List<CommentVo> getCommentsByBoardNo(Long bno);

	// 댓글 개수 조회
	@Select("SELECT COUNT(*) FROM tbl_comment WHERE bno = #{bno}")
	Long getCommentCountByBoardNo(Long bno);

	// 댓글 삭제
	@Delete("DELETE FROM tbl_comment WHERE com_bno = #{com_bno}")
	Long deleteComment(Long com_bno);

	// 댓글 수정
	@Update("UPDATE tbl_comment SET com_writer = #{com_writer}, com_content = #{com_content}, com_regdate = #{com_regdate} WHERE com_bno = #{com_bno}")
	Long updateComment(CommentVo commentVo);
}
