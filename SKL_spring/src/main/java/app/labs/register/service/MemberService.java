package app.labs.register.service;

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
}