package app.labs.register.model;


import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Member {
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String memberNickname;
	private String memberEmail;
	private String memberStatus;
	private String memberClass;
//	private byte[] memberProfile;
	private String memberPhone;
	private String memberBirthdate;
	private Date memberSignupDate;
	private Date lastLogin;
}

