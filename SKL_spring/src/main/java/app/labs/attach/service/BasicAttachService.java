package app.labs.attach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.attach.dao.AttachRepository;
import app.labs.attach.model.Attach;

@Service
public class BasicAttachService implements AttachService {

	@Autowired
	AttachRepository attachRepository;
	
	@Override
	public Attach getAttachFile(int diaryId) {
		return attachRepository.getAttachFile(diaryId);
	}

	@Override
	public void insertAttach(Attach attach) {
		attachRepository.insertAttach(attach);
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
