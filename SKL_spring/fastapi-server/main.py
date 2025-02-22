'''
    0. fastAPI, uvicorn 설치되지 않았을 경우에는 install 필요
       터메널에서 다음 설치명령어 실행
       "pip install fastapi uvicorn"

    1. 가상환경 설정 후 main.py 실행해야됨
       다음 명령어로 가상환경 진입
       "C:\labs_python\.venv\Scripts\activate"

    2. 파이썬 서버 시작 후 자바 서버 시작
       "python C:\labs_python\SamKimILee\SKL_spring\fastapi-server\main.py"

    (tip: 1번, 2번 터미널에 그냥 붙여넣으면 됨)
'''


from emotion_filtering import predict_filter
from emotion_classify import predict_emotion
from fastapi import FastAPI, Form, HTTPException

app = FastAPI()

@app.post("/detect/filter")
async def emotion_filter(message: str = Form(...)):
    try:
        print(f"Received data: {message}")
        pre_filter = predict_filter(message)
        return float(pre_filter)
    except Exception as e:
        print(f"Error: {e}")
        raise HTTPException(status_code=500, detail="Internal Server Error")
    

@app.post("/detect/feedback")
async def diary_feedback(message: str = Form(...)):
    try:
        print(f"Received data: {message}")
        pre_emotion = predict_emotion(message)
        print(f"감정분류 결과: ", pre_emotion)
        return {"result": pre_emotion}
        # return JSONResponse(content={'classify': '감정분류',
        #                              'feedback': '일기에 대한 피드백 내용'})
    except Exception as e:
        print(f"Error: {e}")
        raise HTTPException(status_code=500, detail="Internal Server Error")


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)

