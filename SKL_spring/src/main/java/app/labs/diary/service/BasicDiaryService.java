package app.labs.diary.service;

import java.util.List;

import app.labs.board.event.BoardCreateEvent;
import app.labs.diary.event.DiaryCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	public List<Diary> getDiaryList(String memberId) {
		return diaryRepository.getDiaryList(memberId);
	}

	@Override
	public Diary getDiaryByDate(String memberId, String diaryDate){
		return diaryRepository.getDiaryByDate(memberId, diaryDate);
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
		eventPublisher.publishEvent(new DiaryCreateEvent(diary));
		diaryRepository.insertDiary(diary);
	}

	@Override
	public void updateDiary(Diary diary) {
		eventPublisher.publishEvent(new DiaryCreateEvent(diary));
		diaryRepository.updateDiary(diary);
	}

	@Override
	public int deleteDiary(int diaryId) {
		attachRepository.deleteAttachByDiary(diaryId); // 다이어리 삭제시 첨부파일 동시 삭제
		return diaryRepository.deleteDiary(diaryId);
	}
	
	@Override
	public void updateDiaryEmotion(int diaryId, String diaryEmotion) {
		diaryRepository.updateDiaryEmotion(diaryId, diaryEmotion);
	}
}
