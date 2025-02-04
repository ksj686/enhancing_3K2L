package app.labs.diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.diary.dao.DiaryRepository;
import app.labs.diary.model.Diary;

@Service
public class BasicDiaryService implements DiaryService {
	
	@Autowired
	DiaryRepository diaryRepository;
	
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
		return diaryRepository.deleteDiary(diaryId);
	}

}
