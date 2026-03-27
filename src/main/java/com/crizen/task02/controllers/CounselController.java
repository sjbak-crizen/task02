package com.crizen.task02.controllers;

import com.crizen.task02.vo.CounselCommentVO;
import com.crizen.task02.vo.CounselVO;

import com.crizen.task02.mapper.CounselMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/counsel")
public class CounselController {

    @Autowired
    private CounselMapper counselMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("list", counselMapper.getCounselList());
        return "counsel/list";
    }

    // 작성 화면
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeForm() {
        return "counsel/write";
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writeAction(CounselVO vo) {
        counselMapper.insertCounsel(vo);
        return "redirect:/counsel/list";
    }

    // 상세 & 수정 화면
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(@RequestParam("seq_counsel") int seq_counsel, Model model) {
        model.addAttribute("counsel", counselMapper.getCounsel(seq_counsel));
        model.addAttribute("comments", counselMapper.getCommentList(seq_counsel));
        return "counsel/view";
    }

    // 수정 처리
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(CounselVO vo) {
        counselMapper.updateCounsel(vo);
        return "redirect:/counsel/view?seq_counsel=" + vo.getSeq_counsel();
    }

    // 삭제 처리
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteAction(@RequestParam("seq_counsel") int seq_counsel) {
        counselMapper.deleteCounsel(seq_counsel);
        return "redirect:/counsel/list";
    }

    // 댓글 작성
    @RequestMapping(value = "/comment/write", method = RequestMethod.POST)
    public String commentWrite(CounselCommentVO vo) {
        counselMapper.insertComment(vo);
        return "redirect:/counsel/view?seq_counsel=" + vo.getSeq_counsel();
    }
}