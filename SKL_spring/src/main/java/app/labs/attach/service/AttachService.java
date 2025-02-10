package app.labs.attach.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import app.labs.attach.model.Attach;

public interface AttachService {
	Attach getAttachFile(int diaryId);
//	List <Attach> getAttachFiles(@Param("diaryId") int diaryId);

	void insertAttach(Attach attach);
	void updateAttach(Attach attach);
	void deleteAttach(int attachId);
	void deleteAttachByDiary(int diaryId);
}
