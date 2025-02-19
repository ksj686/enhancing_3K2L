package app.labs.register.model;

import org.springframework.web.multipart.MultipartFile;
//import java.sql.Date;
//import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String memberNickname;
	private String memberEmail;
	private String memberStatus;
	private String memberClass;
	private MultipartFile memberProfile_mpf;
	private byte[] memberProfile;
	private String memberPhone;
	private String memberBirthdate;
	private String memberSignupDate;
	private String memberLastLogin;
	private String memberAddress;
}
