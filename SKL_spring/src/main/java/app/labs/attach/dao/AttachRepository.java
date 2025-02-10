package app.labs.attach.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.attach.model.Attach;

@Mapper

public interface AttachRepository {
	Attach getAttachFile(@Param("diaryId") int diaryId);
//	List <Attach> getAttachFiles(@Param("diaryId") int diaryId);
	void insertAttach(Attach attach);
	void updateAttach(Attach attach);
	int deleteAttach(@Param("attachId") int attachId);
	int deleteAttachByDiary(@Param("diaryId") int diaryId);
}
