// 페이지 로드 시 모든 통계 로드
$(document).ready(function() {
    loadDailyStats();
    loadMonthlyStats();
    loadYearlyStats();
});

// 차트 객체를 저장할 변수들
let dailyChart, monthlyChart, yearlyChart;

// 차트 생성 함수들
function createDailyChart(data) {
    // 데이터 가공
    const labels = data.map(item => item.DAY);
    const values = data.map(item => item.COUNT);
    
    const ctx = document.getElementById('dailyChart').getContext('2d');
    if (dailyChart) dailyChart.destroy();
    dailyChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '일별 회원수',
                data: values,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function createMonthlyChart(data) {
    // 데이터 가공
    const labels = data.map(item => item.MONTH);
    const values = data.map(item => item.COUNT);
    
    const ctx = document.getElementById('monthlyChart').getContext('2d');
    if (monthlyChart) monthlyChart.destroy();
    monthlyChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '월별 회원수',
                data: values,
                borderColor: 'rgb(54, 162, 235)',
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function createYearlyChart(data) {
    // 데이터 가공
    const labels = data.map(item => item.YEAR);
    const values = data.map(item => item.COUNT);
    
    const ctx = document.getElementById('yearlyChart').getContext('2d');
    if (yearlyChart) yearlyChart.destroy();
    yearlyChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '연도별 회원수',
                data: values,
                borderColor: 'rgb(255, 99, 132)',
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// 데이터 로드 함수들
function loadYearlyStats() {
    $.get('/admin/member-stats/year', function(response) {
        createYearlyChart(response);
    });
}

function loadMonthlyStats() {
    $.get('/admin/member-stats/month', function(response) {
        createMonthlyChart(response);
    });
}

function loadDailyStats() {
    $.get('/admin/member-stats/day', function(response) {
        createDailyChart(response);
    });
}
