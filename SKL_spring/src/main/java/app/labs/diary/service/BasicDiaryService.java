package app.labs.diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.attach.dao.AttachRepository;
import app.labs.attach.model.Attach;
import app.labs.diary.dao.DiaryRepository;
import app.labs.diary.model.Diary;

@Service
public class BasicDiaryService implements DiaryService {
	
	@Autowired
	DiaryRepository diaryRepository;
	
	@Autowired
	AttachRepository attachRepository;
	
	@Override
	public int getDiaryCount() {
		return diaryRepository.getDiaryCount();
	}

	@Override
	public int getDiaryCount(String memberId) {
		return diaryRepository.getDiaryCount(memberId);
	}

	@Override
	public List<Diary> getDiaryList(String memberId) {
		return diaryRepository.getDiaryList(memberId);
	}

	@Override
	public int createDiaryId() {
		return diaryRepository.createDiaryId();
	}
	
	@Override
	public Diary getDiaryInfo(int diaryId) {
		return diaryRepository.getDiaryInfo(diaryId);
	}

	@Override
	public void insertDiary(Diary diary) {
		diaryRepository.insertDiary(diary);
	}

	@Override
	public void updateDiary(Diary diary) {
		diaryRepository.updateDiary(diary);
	}

	@Override
	public int deleteDiary(int diaryId) {
		attachRepository.deleteAttachByDiary(diaryId); // 다이어리 삭제시 첨부파일 동시 삭제
		return diaryRepository.deleteDiary(diaryId);
	}
	
	// Attach 관련 메서드 추가
//    @Override
//    public List <Attach> getAttachFiles(int diaryId) {
//    	List<Attach> attachList = attachRepository.getAttachFiles(diaryId);
//
//	    if (attachList.isEmpty()) {
//	        return null;  // 첨부파일 없음
//	    }
//
//	    return attachList;
//    }
//
//    @Override
//    public void insertAttach(Attach attach) {
//        attachRepository.insertAttach(attach);
//    }
//
//    @Override
//    public void updateAttach(Attach attach) {
//        attachRepository.updateAttach(attach);
//    }
//
//    @Override
//    public int deleteAttach(int attachId) {
//        return attachRepository.deleteAttach(attachId);
//    }
//
//    @Override
//    public int deleteAttachByDiary(int diaryId) {
//        return attachRepository.deleteAttachByDiary(diaryId);
//    }
}
