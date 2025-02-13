package app.labs.diary.service;

import java.util.List;

import app.labs.diary.model.Diary;

public interface DiaryService {
	int getDiaryCount();
	int getDiaryCount(String memberId);
	List <Diary> getDiaryList(String memberId);
	int createDiaryId();
	Diary getDiaryInfo(int diaryId);
	void insertDiary(Diary diary);
	void updateDiary(Diary diary);
	int deleteDiary(int diaryId);
	
	
	

}
