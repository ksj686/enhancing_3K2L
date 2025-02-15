from fastapi import FastAPI, Form
from fastapi.responses import JSONResponse
from emotion_filtering import predict_filter

app = FastAPI()


@app.post("/detect/filter")
async def emotion_filter(message: str = Form(...)):
    pre_filter = predict_filter(message)
    return float(pre_filter)
    # print('filter_result:', pre_filter['result'], 'filter_percentage:', pre_filter['percentage'])
    # return JSONResponse(content={
    #     'filter_result': pre_filter['result'],
    #     'filter_percentage': pre_filter['percentage']
    # })


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)