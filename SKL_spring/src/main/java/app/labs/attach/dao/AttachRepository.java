package app.labs.attach.dao;

import org.apache.ibatis.annotations.Mapper;

import app.labs.attach.model.Attach;

@Mapper

public interface AttachRepository {
	Attach getAttachFile(int diaryId);
	void insertAttach(Attach attach);
	void updateAttach(Attach attach);
	void deleteAttach(int diaryId);
	void deleteAttachById(int attachId);
}
