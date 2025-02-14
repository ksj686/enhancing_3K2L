from keras.preprocessing.sequence import pad_sequences
import keras
import pickle

'''
비속어 필터링 함수
'''
def predict_filter(text):
    # 비속어/혐오 필터링 모델 로드
    model = keras.models.load_model('model/model_GRU_0115_cleaned_mecab_stop.keras')

    # Tokenizer 불러오기
    with open('model/tokenizer_cleaned_mecab_stop.pkl', 'rb') as f:
        tokenizer = pickle.load(f)

    max_length = model.get_layer('embedding_1').input_shape[1]

    # 텍스트를 토큰화하고 패딩
    encoded_text = tokenizer.texts_to_sequences([text])
    padded_text = pad_sequences(encoded_text, maxlen=max_length, padding='post')

    # 모델을 사용해 예측
    prediction = model.predict(padded_text)
    percentage = f"{prediction[0][0] * 100:.2f}"

    # # 예측 결과 출력 (0과 1로 반환되므로 0이 '비속어 없음', 1이 '비속어 있음')
    # if prediction >= 0.55:
    #     fileter_result = "비속어 있음"
    # else:
    #     fileter_result = "비속어 없음"

    return percentage
