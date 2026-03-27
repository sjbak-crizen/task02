package com.crizen.task02.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crizen.task02.mapper.CounselMapper;

@Service("counselBatchService")
public class CounselBatchService {

    @Autowired
    private CounselMapper counselMapper;

    // 1. 욕설 마스킹 처리 (3분마다 호출될 메서드)
    public void processMasking() {
        System.out.println("=== [Quartz] 욕설 마스킹 배치 시작 ===");
        counselMapper.maskSwearWords();
        System.out.println("=== [Quartz] 욕설 마스킹 배치 완료 ===");
    }

    // 2. 5일 지난 글 삭제 (매일 8시 호출될 메서드)
    public void processDeleteOld() {
        System.out.println("=== [Quartz] 오래된 게시글 삭제 배치 시작 ===");
        counselMapper.deleteOldCounsels();
        System.out.println("=== [Quartz] 오래된 게시글 삭제 배치 완료 ===");
    }
}