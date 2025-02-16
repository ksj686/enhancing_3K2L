package app.labs;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class FastAPILauncher {
//
//    @PostConstruct
//    public void startFastAPIServer() {
//        try {
//            // FastAPI 서버 실행 (백그라운드에서 실행)
//            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c",
//                    "C:\\labs_python\\.venv\\Scripts\\activate && python fastapi-server/main.py");
//            processBuilder.redirectErrorStream(true);
//            processBuilder.start();
//            System.out.println("FastAPI 서버 실행됨!");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("FastAPI 서버 실행 실패!");
//        }
//    }
}