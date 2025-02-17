package app.labs.register.service;

import java.util.List;
import java.util.Map;

import app.labs.register.model.Member;

public interface MemberService {
	void updateMember(Member member);

	void insertMember(Member member);

	Member loginMember(Member member);

	Member findByUserId(String userId);

	String findUserIdByEmailOrPhone(String emailOrPhone);

	String findPasswordByEmailOrPhone(String emailOrPhone);

	boolean existsByUserId(String memberId);

	boolean isUserIdDuplicated(String userId);

	void updateLastLogin(String memberId);

	void addAttendJoin(String memberId);

	List<Map<String, Object>> checkAttendJoin(String memberId);

	boolean isMemberNickDuplicated(String memberNickname);
}