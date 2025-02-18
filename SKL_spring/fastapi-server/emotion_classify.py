import torch
from transformers import AutoModelForSequenceClassification, AutoTokenizer, AutoModelForSeq2SeqLM


'''
요약
'''
def summary_sentence(sentence):
    # 모델과 토크나이저 불러오기
    summary_model_name = "EbanLee/kobart-summary-v3"
    summary_model = AutoModelForSeq2SeqLM.from_pretrained(summary_model_name)
    tokenizer = AutoTokenizer.from_pretrained(summary_model_name)

    summaries = []
    chunk_size = 1024  # 모델의 최대 입력 길이

    input_text = "senetence"
    # 입력 텍스트를 적절히 나눔
    input_chunks = [input_text[i:i + chunk_size] for i in range(0, len(input_text), chunk_size)]

    chunk_summaries = []
    for chunk in input_chunks:
        # 토큰화 및 요약 생성
        inputs = tokenizer("summarize: " + chunk, return_tensors="pt", max_length=chunk_size, truncation=True)
        outputs = summary_model.generate(
            input_ids=inputs['input_ids'], 
            num_beams=5, 
            do_sample=True, 
            min_length=10, 
            max_length=512  # 생성 텍스트의 최대 길이
        )
        chunk_summaries.append(tokenizer.decode(outputs[0], skip_special_tokens=True))

    # 각 텍스트의 요약 결합
    final_summary = " ".join(chunk_summaries)
    limited_summary = ". ".join(final_summary.split(". ")[:5]) + ("." if not final_summary.endswith(".") else "")
    
    return limited_summary


'''
분류
'''
# Load model and tokenizer
model_name = "hun3359/klue-bert-base-sentiment"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForSequenceClassification.from_pretrained(model_name)

# 라벨 매핑
id2label = model.config.id2label  # 모델의 소분류 라벨
subcategory_to_maincategory = {  # 대분류 매핑
    "구역질 나는": "분노", "노여워하는": "분노", "방어적인": "분노", "분노": "분노",
    "성가신": "분노", "악의적인": "분노", "안달하는": "분노", "좌절한": "분노",
    "짜증내는": "분노", "툴툴대는": "분노",
    "감사하는": "기쁨", "기쁨": "기쁨", "느긋": "기쁨", "만족스러운": "기쁨",
    "신뢰하는": "기쁨", "신이 난": "기쁨", "안도": "기쁨", "자신하는": "기쁨",
    "편안한": "기쁨", "흥분": "기쁨",
    "걱정스러운": "불안", "당혹스러운": "불안", "두려운": "불안", "불안": "불안",
    "스트레스 받는": "불안", "조심스러운": "불안", "초조한": "불안", "취약한": "불안",
    "혼란스러운": "불안", "회의적인인": "불안",
    "고립된(당황한)": "당황", "남의 시선을 의식하는": "당황", "당황": "당황",
    "부끄러운": "당황", "열등감": "당황", "외로운": "당황", "죄책감의": "당황",
    "한심한": "당황", "혐오스러운": "당황", "혼란스러운(당황한)": "당황",
    "낙담한": "슬픔", "눈물이 나는": "슬픔", "마비된": "슬픔", "비통한": "슬픔",
    "슬픔": "슬픔", "실망한": "슬픔", "염세적인": "슬픔", "우울한": "슬픔",
    "환멸을 느끼는": "슬픔", "후회되는": "슬픔",
    "가난한 불우한": "상처", "괴로워하는": "상처", "배신당한": "상처",
    "버려진": "상처", "상처": "상처", "억울한": "상처", "질투하는": "상처",
    "충격받은": "상처", "희생된": "상처"
}

# Define the function
# 수정된 classify_emotion 함수
def classify_emotion(sentence):
    # Tokenize and classify
    inputs = tokenizer(sentence, return_tensors="pt", truncation=True, padding=True, max_length=512)
    outputs = model(**inputs)
    probabilities = torch.nn.functional.softmax(outputs.logits, dim=-1)

    # Calculate scores for each main category
    main_category_scores = {}
    for idx, prob in enumerate(probabilities[0]):
        subcategory = id2label.get(idx, f"Unknown({idx})")
        main_category = subcategory_to_maincategory.get(subcategory, "Unknown")
        if main_category not in main_category_scores:
            main_category_scores[main_category] = 0.0
        main_category_scores[main_category] += prob.item()

    # Find the highest-scoring main category
    predicted_main_category = max(main_category_scores, key=main_category_scores.get)
    predicted_score = main_category_scores[predicted_main_category]

    # Return the result as a tuple
    # return predicted_main_category
    return predicted_main_category, predicted_score

# 사용 예시
if __name__ == "__main__":
    sentence = "살면서 착하게 살려고 노력했었는데 그게 무슨소용인지 모르겠다고 눈물을 흘렸다. 내가 할 수 있는건 최대한 빨리 중환자실에 보내서 악화되는 것을 예방하는 것이었고 심리적안정을 주기위해 항상 환자가 해줬던 것처럼 두손을 잡아드렸다. 병원에서 일한다는건 사람이 가장 힘들고 악해질때 만나는 것이라 생각한다. 바쁜 병원은 식사해야하는 5분조차 만들기 어려워 2교대를 하는데도 새벽부터 밤까지 굶는다. 생리대 교환할 시간이 없어 피부염에 걸리고 방광염에 걸린 친구들이 많은게 현실이다 \
올해는 눈이 오지 않았다. 눈이 온다하여 창문을 열고 바라보면 그저 하염없이 내리는 빗물들의 도닥도닥 거리는 음악의 향연들만이 펼쳐져 있었다. 눈이 오는게 당연한거지 당연하지 않을 수 없다. 그러나 젖어버린 옷을 부여잡고 함박눈처럼 미소지어보는 어느 겨울날 그 한자락 끝에서였다."
    sentence = summary_sentence(sentence)
    print(classify_emotion(sentence))
