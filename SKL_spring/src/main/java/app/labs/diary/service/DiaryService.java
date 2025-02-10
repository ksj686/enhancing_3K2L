package app.labs.diary.service;

import java.util.List;

import app.labs.attach.model.Attach;
import app.labs.diary.model.Diary;

public interface DiaryService {
	int getDiaryCount();
	int getDiaryCount(String memberId);
	List <Diary> getDiaryList(String memberId);
	Diary getDiaryInfo(int diaryId);
	void insertDiary(Diary diary);
	void updateDiary(Diary diary);
	int deleteDiary(int diaryId);
	
	// Attach 관련 메소드
//	List <Attach> getAttachFiles(int diaryId);
//	void insertAttach(Attach attach);
//	void updateAttach(Attach attach);
//	int deleteAttach(int attachId);
//	int deleteAttachByDiary(int diaryId);
}
