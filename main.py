from fastapi import FastAPI
from emotion_filtering import predict_filter

app = FastAPI()


@app.post("/detect/filter")
async def emotion_filter(message: str = Form(...)):
    pre_filter = predict_filter(message)
    print(pre_filter)
    return pre_filter


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)