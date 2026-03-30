package com.crizen.task02.mapper;

import com.crizen.task02.vo.CounselVO;
import com.crizen.task02.vo.CounselCommentVO;
import java.util.List;

// MyBatis 매퍼 인터페이스: 여기 적힌 메서드 이름(id)과 CounselMapper.xml 안의 id가 일치해야 쿼리가 작동합니다.
public interface CounselMapper {
    // 기본 CRUD 및 댓글
    List<CounselVO> getCounselList(CounselVO searchVO); // 결과물이 여러 줄이므로 List<VO> 형태로 받습니다.
    CounselVO getCounsel(int seq_counsel); // 특정 글 번호 1건만 조회하므로 VO 단일 객체로 받습니다.
    void insertCounsel(CounselVO vo); // 등록 시에는 VO 통째로 넘겨줍니다. 리턴값(void)은 성공 여부(int)로 바꿀 수도 있습니다.
    void updateCounsel(CounselVO vo);
    void deleteCounsel(int seq_counsel); // 삭제는 번호(PK)만 알면 되므로 int 값만 넘깁니다.

    List<CounselCommentVO> getCommentList(int seq_counsel); // 특정 부모 글의 모든 댓글을 가져오므로 List 형태입니다.
    void insertComment(CounselCommentVO vo);

    // Quartz 일괄처리용 메서드
    void maskSwearWords();    // 욕설 마스킹 실행 (파라미터 없이 쿼리만 실행)
    void deleteOldCounsels(); // 5일 경과 데이터 삭제 실행 (파라미터 없이 쿼리만 실행)
}