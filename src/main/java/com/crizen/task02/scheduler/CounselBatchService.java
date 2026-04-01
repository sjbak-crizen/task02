package com.crizen.task02.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crizen.task02.mapper.CounselMapper;

// @Service: 비즈니스 로직을 처리하는 서비스 계층임을 명시.
// "counselBatchService": root-context.xml에서 Quartz가 찾을 수 있도록 닉네임을 명시적으로 지정했습니다.
@Service("counselBatchService")
public class CounselBatchService {

    @Autowired
    private CounselMapper counselMapper; // 일괄처리를 위해 DB 연결용 Mapper 주입

    // 마스킹 배치 타겟 메서드
    public void processMasking() {
        System.out.println("=== [Quartz] 욕설 마스킹 배치 시작 ==="); // 콘솔창 확인용 로그
        counselMapper.maskSwearWords(); // 실제 DB 업데이트 명령
    }

    // 오래된 글 삭제 배치 타겟 메서드
    public void processDeleteOld() {
        System.out.println("=== [Quartz] 오래된 게시글 삭제 배치 시작 ==="); // 콘솔창 확인용 로그
        counselMapper.deleteOldCounsels(); // 실제 DB 삭제 명령
    }
}