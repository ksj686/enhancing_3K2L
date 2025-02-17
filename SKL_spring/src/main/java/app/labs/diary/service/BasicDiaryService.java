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
	public List<Diary> getDiaryListByDate(String memberId, String diaryDate){
		return diaryRepository.getDiaryListByDate(memberId, diaryDate);
	}
	
	// 📌 특정 연도/월의 일기 목록 조회
    @Override
    public List<Diary> getDiariesByMonth(String memberId, int year, int month) {
        String yearMonth = String.format("%04d-%02d", year, month); // YYYY-MM 포맷
        return diaryRepository.getDiaryListByMonth(memberId, yearMonth);
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
	
	
}
