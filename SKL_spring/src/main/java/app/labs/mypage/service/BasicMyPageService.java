package app.labs.mypage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import app.labs.mypage.dao.MyPageRepository;


@Service
@Slf4j
public class BasicMyPageService implements MyPageService {

    @Autowired
    private MyPageRepository mypageRepository;

    @Override
    public List<Map<String, Object>> getBoardStats(String memberId, String date) {
        return mypageRepository.getBoardStats(memberId, date);
    }

    @Override
    public List<Map<String, Object>> getJournalStats(String memberId, String date) {
        return mypageRepository.getJournalStats(memberId, date);
    }

}
