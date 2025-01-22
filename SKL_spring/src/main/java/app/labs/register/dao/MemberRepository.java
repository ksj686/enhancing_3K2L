package app.labs.register.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.register.model.Member;

@Mapper
public interface MemberRepository {
	void updateMember(@Param("userName") String userName, @Param("email") String email, @Param("phoneNumber") String phoneNumber, @Param("userId") String userId);
	void insertMember(Member member);
	Member loginMember(Member member);
    Member findByEmailOrPhone(@Param("emailOrPhone") String emailOrPhone);
	Member findByUserId(String userId);
	int existsByUserId(String memberId);
   }
   
   
       
      
   
