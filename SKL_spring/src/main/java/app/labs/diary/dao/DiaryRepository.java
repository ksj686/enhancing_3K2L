package app.labs.diary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.diary.model.Diary;

@Mapper
public interface DiaryRepository {
	int getDiaryCount();
	int getDiaryCount(@Param("memberId") String memberId);
	List <Diary> getDiaryList(@Param("memberId") String memberId);
	int createDiaryId();
	Diary getDiaryInfo(int diaryId);
	void insertDiary(Diary diary);
	void updateDiary(Diary diary);
	int deleteDiary(@Param("diaryId") int diaryId);
	
}
