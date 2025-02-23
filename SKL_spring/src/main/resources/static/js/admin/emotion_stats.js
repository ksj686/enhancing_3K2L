// 페이지 로드 시 모든 통계 로드
$(document).ready(function() {
    loadTodayDiaryStats();
    loadTodayBoardStats();
    loadTotalDiaryStats();
    loadTotalBoardStats();
});

// Chart.js에 플러그인 등록
Chart.register(ChartDataLabels);

// 차트 객체를 저장할 변수들
let todayDiaryChart, todayBoardChart, totalDiaryChart, totalBoardChart;

// 색상 설정
// const emotionColors = {
//     backgroundColor: [
//         'rgba(255, 99, 132, 0.6)',
//         'rgba(54, 162, 235, 0.6)',
//         'rgba(255, 206, 86, 0.6)',
//         'rgba(75, 192, 192, 0.6)',
//         'rgba(51, 188, 92, 0.6)',
//         'rgba(58, 59, 94, 0.6)',
//         'rgba(153, 102, 255, 0.6)'
//     ],
//     borderColor: [
//         'rgb(255, 99, 132)',
//         'rgb(54, 162, 235)',
//         'rgb(255, 206, 86)',
//         'rgb(75, 192, 192)',
//         'rgb(51, 188, 92)',
//         'rgb(58, 59, 94)',
//         'rgb(153, 102, 255)'
//     ]
// };

const emotionColors = {
    backgroundColor: [
        'rgba(245, 167, 117, 0.9)',
        'rgba(248, 219, 121, 0.9)',
        'rgba(100, 142, 248, 0.9)',
        'rgba(181, 168, 251, 0.9)',
        'rgba(51, 188, 92, 0.9)',
        'rgba(58, 59, 94, 0.9)',
        'rgba(153, 102, 255, 0.9)'
    ],
    borderColor: [
        'rgb(245, 167, 117)',
        'rgb(248, 219, 121)',
        'rgb(100, 142, 248)',
        'rgb(181, 168, 251)',
        'rgb(51, 188, 92)',
        'rgb(58, 59, 94)',
        'rgb(153, 102, 255)'
    ]
};
// 도넛 차트 생성 함수
function createDonutChart(ctx, data, title) {
    // 데이터가 없을 경우 처리
    if (!data || data.length === 0) {
        ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height); // 차트 초기화
        ctx.font = '20px Arial';
        ctx.fillStyle = '#000'; // 텍스트 색상
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText('오늘의 데이터가 없습니다', ctx.canvas.width / 2, ctx.canvas.height / 2); // 중앙에 메시지 표시
        return; // 함수 종료
    }

    const total = data.reduce((sum, item) => sum + (item.COUNT_EMOTION || item.COUNT_CATEGORY), 0);
    
    return new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: data.map(item => item.DIARY_EMOTION || item.BOARD_CATEGORY),
            datasets: [{
                data: data.map(item => item.COUNT_EMOTION || item.COUNT_CATEGORY),
                backgroundColor: emotionColors.backgroundColor.map(color => color.replace('0.6', '0.9')),  // 더 어두운 배경색
                borderColor: emotionColors.borderColor,
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            aspectRatio: 1.5,
            plugins: {
                datalabels: {
                    formatter: (value) => {
                        const percentage = ((value / total) * 100).toFixed(1);
                        return `${percentage}%`;
                    },
                    color: '#fff',  // 글씨색만 흰색으로
                    font: {
                        weight: 'bold',
                        size: 12
                    }
                },
                legend: {
                    position: 'right',
                    labels: {
                        font: {
                            size: 14
                        }
                    }
                },
                title: {
                    display: true,
                    text: title,
                    font: {
                        size: 18,
                        weight: 'bold'
                    },
                    padding: {
                        top: 10,
                        bottom: 20
                    }
                }
            },
            cutout: '60%'
        }
    });
}

// 스택 바 차트 생성 함수
function createStackedBarChart(ctx, data, title) {
    // 날짜 포맷 변환 함수
    function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toISOString().split('T')[0]; // YYYY-MM-DD 형식으로 변환
    }

    // 날짜별로 데이터 구성
    const dates = [...new Set(data.map(item => 
        formatDate(item.DIARY_DATE || item.BOARD_DATE)
    ))];
    const emotions = [...new Set(data.map(item => 
        item.DIARY_EMOTION || item.BOARD_CATEGORY
    ))];
    
    const datasets = emotions.map((emotion, index) => ({
        label: emotion,
        data: dates.map(date => {
            const item = data.find(d => 
                formatDate(d.DIARY_DATE || d.BOARD_DATE) === date && 
                (d.DIARY_EMOTION === emotion || d.BOARD_CATEGORY === emotion)
            );
            return item ? (item.COUNT_EMOTION || item.COUNT_CATEGORY) : 0;
        }),
        backgroundColor: emotionColors.backgroundColor[index].replace('0.6', '0.9'),  // 더 어두운 배경색
        borderColor: emotionColors.borderColor[index],
        borderWidth: 1
    }));

    // 날짜별 전체 합계 계산
    const dailyTotals = dates.reduce((acc, date) => {
        acc[date] = emotions.reduce((sum, emotion) => {
            const item = data.find(d => 
                formatDate(d.DIARY_DATE || d.BOARD_DATE) === date && 
                (d.DIARY_EMOTION === emotion || d.BOARD_CATEGORY === emotion)
            );
            return sum + (item ? (item.COUNT_EMOTION || item.COUNT_CATEGORY) : 0);
        }, 0);
        return acc;
    }, {});

    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels: dates,
            datasets: datasets
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            aspectRatio: 1.5,
            plugins: {
                datalabels: {
                    formatter: (value, context) => {
                        const dateIndex = context.dataIndex;
                        const currentDate = dates[dateIndex];
                        const total = dailyTotals[currentDate];
                        if (value === 0) return '';
                        const percentage = ((value / total) * 100).toFixed(1);
                        return `${percentage}%`;
                    },
                    color: '#fff',  // 글씨색만 흰색으로
                    font: {
                        weight: 'bold',
                        size: 11
                    },
                    anchor: 'center',
                    align: 'center'
                },
                legend: {
                    position: 'right',
                    labels: {
                        font: {
                            size: 14
                        }
                    }
                },
                title: {
                    display: true,
                    text: title,
                    font: {
                        size: 18,
                        weight: 'bold'
                    },
                    padding: {
                        top: 10,
                        bottom: 20
                    }
                }
            },
            scales: {
                x: {
                    stacked: true
                },
                y: {
                    stacked: true,
                    beginAtZero: true
                }
            }
        }
    });
}

// 데이터 로드 함수들
function loadTodayDiaryStats() {
    $.get('/admin/emotion-stats/diary/today', function(response) {
        const ctx = document.getElementById('todayDiaryChart').getContext('2d');
        if (todayDiaryChart) todayDiaryChart.destroy();
        todayDiaryChart = createDonutChart(ctx, response, '오늘의 일기 감정 분포');
    });
}

function loadTodayBoardStats() {
    $.get('/admin/emotion-stats/board/today', function(response) {
        const ctx = document.getElementById('todayBoardChart').getContext('2d');
        if (todayBoardChart) todayBoardChart.destroy();
        todayBoardChart = createDonutChart(ctx, response, '오늘의 공감의 방 감정 분포');
    });
}

function loadTotalDiaryStats() {
    $.get('/admin/emotion-stats/diary/total', function(response) {
        const ctx = document.getElementById('totalDiaryChart').getContext('2d');
        if (totalDiaryChart) totalDiaryChart.destroy();
        totalDiaryChart = createStackedBarChart(ctx, response, '전체 일기 감정 추이');
    });
}

function loadTotalBoardStats() {
    $.get('/admin/emotion-stats/board/total', function(response) {
        const ctx = document.getElementById('totalBoardChart').getContext('2d');
        if (totalBoardChart) totalBoardChart.destroy();
        totalBoardChart = createStackedBarChart(ctx, response, '전체 공감의 방 감정 추이');
    });
}