package app.labs.diary.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import app.labs.diary.model.Diary;

public interface DiaryService {
	int getDiaryCount();
	int getDiaryCount(String memberId);
	List <Diary> getDiaryList(String memberId);
	
	List <Diary> getDiaryListByDate(@Param("memberId") String memberId, @Param("diaryDate") String diaryDate);
	List<Diary> getDiariesByMonth(String memberId, int year, int month);
	
	int createDiaryId();
	Diary getDiaryInfo(int diaryId);
	void insertDiary(Diary diary);
	void updateDiary(Diary diary);
	int deleteDiary(int diaryId);
	
	
	

}
