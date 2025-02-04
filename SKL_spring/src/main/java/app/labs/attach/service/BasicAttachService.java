package app.labs.attach.service;

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
	
	@Override
	public Attach getAttachFile(int diaryId) {
		Attach attach = attachRepository.getAttachFile(diaryId);
		log.info("Attach 정보: " + attach); // 첨부파일이 불러와지는지 확인용
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
	public void deleteAttach(int diaryId) {
		attachRepository.deleteAttach(diaryId);
	}

	@Override
	public void deleteAttachById(int attachId) {
		attachRepository.deleteAttachById(attachId);
	}

}
