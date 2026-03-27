package com.crizen.task02.mapper;

import com.crizen.task02.vo.CounselVO;
import com.crizen.task02.vo.CounselCommentVO;
import java.util.List;

public interface CounselMapper {

    // 1. 게시글 목록 조회 (getCounselList)
    List<CounselVO> getCounselList();

    // 2. 게시글 상세 조회 (getCounsel)
    CounselVO getCounsel(int seq_counsel);

    // 3. 게시글 등록 (insertCounsel)
    // 데이터베이스에 저장할 제목, 내용, 작성자 정보가 담긴 CounselVO 객체를 통째로 넘겨줍니다.
    void insertCounsel(CounselVO vo);

    // 4. 게시글 수정 (updateCounsel)
    // 수정할 번호와 변경된 제목, 내용이 담긴 CounselVO 객체를 넘겨줍니다.
    void updateCounsel(CounselVO vo);

    // 5. 게시글 삭제 (deleteCounsel)
    // 삭제할 게시글 번호(int)만 넘겨줍니다.
    void deleteCounsel(int seq_counsel);

    // 6. 특정 게시글의 댓글 목록 조회 (getCommentList)
    List<CounselCommentVO> getCommentList(int seq_counsel);

    // 7. 댓글 등록 (insertComment)
    // 부모 글 번호, 댓글 내용, 작성자 정보가 담긴 CounselCommentVO 객체를 넘겨줍니다.
    void insertComment(CounselCommentVO vo);

    // 8. 욕설 마스킹 일괄 처리
    void maskSwearWords();

    // 9. 5일 지난 게시글 삭제 일괄 처리
    void deleteOldCounsels();
}