package app.labs;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class FastAPILauncher {
//
//    private Process fastApiProcess;
//
//   @PostConstruct
//   public void startFastAPIServer() {
//       try {
//           // FastAPI 서버 실행 (백그라운드에서 실행)
//           ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c",
//                   "C:\\labs_python\\.venv\\Scripts\\activate && python fastapi-server/main.py");
//           processBuilder.redirectErrorStream(true);
//           fastApiProcess = processBuilder.start(); // 프로세스를 fastApiProcess에 저장
//           System.out.println("FastAPI 서버 실행됨!");
//       } catch (IOException e) {
//           e.printStackTrace();
//           System.err.println("FastAPI 서버 실행 실패!");
//       }
//   }
//
//   @PreDestroy
//   public void stopFastAPIServer() {
//       if (fastApiProcess != null) {
//           fastApiProcess.destroy(); // FastAPI 프로세스 종료
//           System.out.println("FastAPI 서버 종료됨!");
//       }
//   }
}