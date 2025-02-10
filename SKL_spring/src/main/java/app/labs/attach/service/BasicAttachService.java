package app.labs.attach.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.attach.dao.AttachRepository;
import app.labs.attach.model.Attach;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BasicAttachService implements AttachService {

	@Autowired
	AttachRepository attachRepository;
	
	/*
	 * @Override public List<Attach> getAttachFiles(int diaryId) { List<Attach>
	 * attachList = attachRepository.getAttachFiles(diaryId);
	 * 
	 * if (attachList.isEmpty()) { return null; // 첨부파일 없음 }
	 * 
	 * return attachList; // 첫 번째 파일만 반환 }
	 */
	
	@Override
	public Attach getAttachFile(int diaryId) {
	    Attach attach = attachRepository.getAttachFile(diaryId);
	    log.info("첨부파일 조회 결과: " + attach); // ✅ 로그 추가하여 null 여부 확인
	    return attach;
	}


	@Override
	public void insertAttach(Attach attach) {
		log.info("첨부파일 저장 시작: " + attach); // 첨부파일이 저장되는지 확인용
		attachRepository.insertAttach(attach);
		log.info("첨부파일 저장 완료");
	}

	@Override
	public void updateAttach(Attach attach) {
		attachRepository.updateAttach(attach);
	}

	@Override
	public void deleteAttach(int attachId) {
		attachRepository.deleteAttach(attachId);
	}

	@Override
	public void deleteAttachByDiary(int diaryId) {
		attachRepository.deleteAttachByDiary(diaryId);
	}

}
