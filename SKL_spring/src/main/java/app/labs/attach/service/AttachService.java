package app.labs.attach.service;

import app.labs.attach.model.Attach;

public interface AttachService {
	Attach getAttachFile(int diaryId);
	void insertAttach(Attach attach);
	void updateAttach(Attach attach);
	void deleteAttach(int diaryId);
	void deleteAttachById(int attachId);
}
