package app.labs.diary.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import app.labs.diary.model.Diary;

public interface DiaryService {
	List <Diary> getDiaryList(String memberId);
	Diary getDiaryByDate(@Param("memberId") String memberId, @Param("diaryDate") String diaryDate);
	int createDiaryId();
	Diary getDiaryInfo(int diaryId);
	int getDiaryIdLately(@Param("memberId") String memberId);
	void insertDiary(Diary diary);
	void updateDiary(Diary diary);
	int deleteDiary(int diaryId);
	void updateDiaryEmotion(int diaryId, String diaryEmotion);
	
	

}
