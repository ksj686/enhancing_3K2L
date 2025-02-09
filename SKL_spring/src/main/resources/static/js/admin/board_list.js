$(document).ready(function() {
    initializeDataTable();
    searchBoards();  // 초기 데이터 로드
});

var dataTable;

function initializeDataTable() {
    if (dataTable) {
        dataTable.destroy();
    }

    // dom: 'Bfrtip',
    //     buttons: [
    //         'selectAll',
    //         'selectNone',
    //         'colvis'
    //     ],
    // "columnDefs": [{
    //         "targets": 3, // 4번째 컬럼
    //         "orderable": false // 정렬 비활성화
    //     }],
    // "columnDefs": [{
    //         "targets": 3, // 차단 여부 컬럼
    //         "orderData": [3, 0], // 정렬 기준을 select의 값으로 설정
    //         "render": function(data, type, row) {
    //             return row[3]; // select의 값을 반환
    //         }
    //     }],

    dataTable = $('#dataTable').DataTable({
        language: {
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            zeroRecords: "검색 결과가 없습니다",
            info: "_START_ - _END_ / _TOTAL_",
            infoEmpty: "0 - 0 / 0",
            infoFiltered: "(전체 _MAX_ 개 중 검색결과)",
            search: "검색:",
            paginate: {
                first: "처음",
                last: "마지막",
                next: "다음",
                previous: "이전"
            }
        },
        destroy: true
    });

    // Reset 버튼 추가
    // $('#dataTable tbody').on('click', '.btn-primary', function() {
    //     var boardId = $(this).closest('tr').find('td:first').text(); // 게시글 ID 가져오기
    //     resetReportCount(boardId);
    // });

    // Reset 버튼 HTML 추가
    $('#dataTable tbody tr').each(function() {
        var boardId = $(this).find('td:first').text(); // 게시글 ID 가져오기
        $(this).find('td:eq(4)').append('<button class="btn btn-primary btn-sm reset-button" onclick="resetReportCount(' + boardId + ')">Reset</button>');
    });
}

function searchBoards() {
    $.ajax({
        type: 'POST',
        url: '/admin/getBoardList',
        data: {},
        success: function(fragment) {
            $('#dataTable').html(fragment);
            initializeDataTable();
        },
        error: function(xhr, status, error) {
            alert('게시글 조회 중 오류가 발생했습니다.');
            console.error('Error:', error);
        }
    });
}

function updateBoardStatus() {
    var boardIdList = [];
    var boardOffensiveList = [];
    var boardReportList = [];

    // 각 행의 데이터 수집
    $('select[id^="boardOffensive"]').each(function() {
        var boardId = $(this).attr('id').replace('boardOffensive', '');
        var currentOffensive = $(this).val();
        var currentReport = parseInt($(this).data('report'));

        // 변경된 데이터만 수집
        if (currentOffensive !== $(this).data('original-offensive') || currentReport !== $(this).data('original-report')) {
            boardIdList.push(boardId);
            boardOffensiveList.push(currentOffensive);
            boardReportList.push(currentReport);
        }
    });

    if (boardIdList.length > 0) {
        $.ajax({
            url: '/admin/updateBoardList',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                boardIdList: boardIdList,
                boardOffensiveList: boardOffensiveList,
                boardReportList: boardReportList
            }),
            success: function(response) {
                if(response.success) {
                    // alert('업데이트가 완료되었습니다.');
                    Swal.fire({
                        title: '업데이트가 완료되었습니다!',
                        icon: 'success',
                        draggable: false,
                        customClass: {
                            title: 'swal-title'
                        }
                    });
                    searchBoards();  // 목록 새로고침
                } else {
                    alert('업데이트 중 오류가 발생했습니다.');
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('업데이트 중 오류가 발생했습니다.');
            }
        });
    } else {
        // alert('변경된 데이터가 없습니다.');
        Swal.fire({text:'변경된 사항이 없습니다.'});
    }
}

function updateBoardDetail() {
    var boardIdList = [];
    var boardOffensiveList = [];
    var boardReportList = [];

    var boardId = $('#modalBoardId').text();
    var boardOffensive = $('#modalBoardOffensive').val();
    var boardReport = parseInt($('#modalBoardReport').text());

    // 변경사항 확인
    if (boardOffensive !== $("#modalBoardOffensive").data('original-offensive') || boardReport !== parseInt($("#modalBoardReport").data('original-report'))) {
        boardIdList.push(boardId);
        boardOffensiveList.push(boardOffensive);
        boardReportList.push(boardReport);
    }

    if (boardIdList.length > 0) {
        $.ajax({
            url: '/admin/updateBoardList',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                boardIdList: boardIdList,
                boardOffensiveList: boardOffensiveList,
                boardReportList: boardReportList
            }),
            success: function(response) {
                if(response.success) {
                    // alert('업데이트가 완료되었습니다.');
                    Swal.fire({
                        title: '업데이트가 완료되었습니다!',
                        icon: 'success',
                        draggable: false,
                        customClass: {
                            title: 'swal-title'
                        }
                    });
                    closeModal();
                    searchBoards();  // 목록 새로고침
                } else {
                    alert('업데이트 중 오류가 발생했습니다.');
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('업데이트 중 오류가 발생했습니다.');
            }
        });
    } else {
        // alert('변경된 사항이 없습니다.');
        Swal.fire({text:'변경된 사항이 없습니다.'});
    }
}

function showBoardDetail(element) {
    var boardId = parseInt($(element).data('board-id'));
    
    // AJAX로 게시글 상세 정보 가져오기
    $.ajax({
        type: 'GET',
        url: '/admin/getBoardDetail',
        data: { boardId: boardId },
        success: function(board) {
            // 모달에 데이터 채우기
            $('#modalBoardId').text(board.boardId);
            $('#modalBoardCategory').text(board.boardCategory);
            $('#modalBoardTitle').text(board.boardTitle);
            $('#modalBoardContent').html(board.boardContent.replace(/\n/g, '<br>'));
            $('#modalMemberId').text(board.memberId);
            $('#modalBoardDate').text(board.boardDate);
            $('#modalBoardOffensive').val(board.boardOffensive).data('original-offensive', board.boardOffensive);
            $('#modalBoardReport').text(board.boardReport).data('original-report', board.boardReport);
            
            // 모달 표시
            $('#boardDetailModal').modal('show');
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('게시글 상세 정보를 불러오는데 실패했습니다.');
        }
    });

    return false;
}

function resetReportCount(boardId) {
    // DataTable 인스턴스 가져오기
    var table = $('#dataTable').DataTable();
    // 해당 boardId의 행 찾기
    var row = table.row(function (idx, data, node) {
        return data[0] == boardId; // 게시글 ID로 행 찾기
    });
    // 실제 테이블에 표시되는 신고 횟수를 0으로 변경
    $('#dataTable tbody tr').each(function() {
        if ($(this).find('td:first').text() == boardId) {
            $(this).find('td:eq(4)').text('0');
            // data-report 값을 0으로 변경
            $(this).find('select').data('report', 0);
            // 버튼 유지
            $(this).find('td:eq(4)').append('<button class="btn btn-primary btn-sm reset-button" onclick="resetReportCount(' + boardId + ')">Reset</button>');
        }
    });
}

function resetModalReport() {
    $('#modalBoardReport').text(0);
}

function closeModal() {
    $('#boardDetailModal').modal('hide');
}