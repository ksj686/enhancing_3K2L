$(document).ready(function() {
    initializeDataTable();
    searchEvents();  // 초기 데이터 로드
});

var dataTable;

function initializeDataTable() {
    if (dataTable) {
        dataTable.destroy();
    }

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
}

function searchEvents() {
    $.ajax({
        type: 'POST',
        url: '/admin/getEvents',
        data: {},
        success: function(fragment) {
            $('#dataTable').html(fragment);  // 컨테이너에 fragment 삽입
            initializeDataTable();  // 데이터 로드 후 DataTable 초기화
        },

        error: function(xhr, status, error) {
            alert('이벤트 조회 중 오류가 발생했습니다.');
            console.error('Error:', error);
        }
    });
}

// function updateEventStatus() {
//     var eventId = [];
//     var eventName = [];
//     var eventDescription = [];

//     // 각 행의 데이터 수집
//     $('select[id^="boardOffensive"]').each(function() {
//         var boardId = $(this).attr('id').replace('boardOffensive', '');
//         var currentOffensive = $(this).val();
//         var currentReport = parseInt($(this).data('report'));

//         // 변경된 데이터만 수집
//         if (currentOffensive !== $(this).data('original-offensive') || currentReport !== $(this).data('original-report')) {
//             boardIdList.push(boardId);
//             boardOffensiveList.push(currentOffensive);
//             boardReportList.push(currentReport);
//         }
//     });

//     if (boardIdList.length > 0) {
//         $.ajax({
//             url: '/admin/updateBoardList',
//             type: 'POST',
//             contentType: 'application/json',
//             data: JSON.stringify({
//                 boardIdList: boardIdList,
//                 boardOffensiveList: boardOffensiveList,
//                 boardReportList: boardReportList
//             }),
//             success: function(response) {
//                 if(response.success) {
//                     // alert('업데이트가 완료되었습니다.');
//                     Swal.fire({
//                         title: '업데이트가 완료되었습니다!',
//                         icon: 'success',
//                         draggable: false,
//                         customClass: {
//                             title: 'swal-title'
//                         }
//                     });
//                     searchBoards();  // 목록 새로고침
//                 } else {
//                     alert('업데이트 중 오류가 발생했습니다.');
//                 }
//             },
//             error: function(xhr, status, error) {
//                 console.error('Error:', error);
//                 alert('업데이트 중 오류가 발생했습니다.');
//             }
//         });
//     } else {
//         // alert('변경된 데이터가 없습니다.');
//         Swal.fire({text:'변경된 사항이 없습니다.'});
//     }
// }

function updateEventDetail() {
    var eventId = $('#modalEventId').text();
    var eventName = $('#modalEventName').val();
    var eventDescription = $('#modalEventDescription').val();
    
    // 변경사항 확인
    if (eventName !== $("#modalEventName").data('original-name') || eventDescription !== $("#modalEventDescription").data('original-description')) {
        $.ajax({
            url: '/admin/updateEvent',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                eventId: eventId,
                eventName: eventName,
                eventDescription: eventDescription
            }),
            success: function(response) {
                if(response.success) {
                    // alert('업데이트가 완료되었습니다.');
                    Swal.fire({
                        title: '변경사항이 적용되었습니다!',
                        icon: 'success',
                        draggable: false,
                        customClass: {
                            title: 'swal-title'
                        }
                    });
                    closeModal();
                    searchEvents();  // 목록 새로고침
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

function showEventDetail(element) {
    // var row = $(element).closest('tr');  // span -> td -> tr
    var row = $(element).parent().parent();  // span -> td -> tr
    
    // 행에서 데이터 추출
    var eventId = row.find('td:first').text();
    var eventName = $(element).text();
    var eventDescription = row.find('td:last').text();
    
    // 모달에 데이터 채우기
    $('#modalEventId').text(eventId);
    $('#modalEventName').val(eventName);
    $('#modalEventDescription').val(eventDescription);
    
    // 원본 데이터 저장 (수정 여부 확인용)
    $("#modalEventName").data('original-name', eventName);
    $("#modalEventDescription").data('original-description', eventDescription);
    
    // 모달 표시
    $('#eventDetailModal').modal('show');
    
    return false;
}

function showNewEvent() {
    $('#addEventModal').modal('show');
}

async function deleteEvent() {
    var eventId = parseInt($('#modalEventId').text());

    var result = await Swal.fire({
        title: '삭제 확인',
        text: '정말 삭제하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#6c757d',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
    });

    if (result.isConfirmed) {
        $.ajax({
            url: '/admin/deleteEvent',
            type: 'POST',
            data: {
                eventId: eventId
            },
            success: function(response) {
                if (response.success) {

                    Swal.fire({
                        title: '삭제되었습니다!',
                        icon: 'success',
                        draggable: false,
                        customClass: {
                            title: 'swal-title'
                        }
                    });

                    closeModal();
                    searchEvents();  // 페이지 새로고침
                } else {
                    alert('삭제 실패: ' + response.message);
                }
            },
            error: function(xhr, status, error) {
                alert('오류가 발생했습니다: ' + error);
            }
        });
    }
}

function addEvent() {
    var eventName = $('#modalAddEventName').val();
    var eventDescription = $('#modalAddEventDescription').val();

    $.ajax({
        url: '/admin/insertEvent',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            eventName: eventName,
            eventDescription: eventDescription
        }),
        success: function(response) {
            if(response.success) {
                Swal.fire({
                    title: '이벤트가 등록되었습니다!',
                    icon: 'success',
                    draggable: false,
                    customClass: {
                        title: 'swal-title'
                    }
                });
                searchEvents();
                closeModal();
            } else {
                alert('이벤트 등록 실패: ' + response.message);
            }

        },
        error: function(xhr, status, error) {
            alert('오류가 발생했습니다: ' + error);
        }
    });
}

function closeModal() {
    $('#eventDetailModal').modal('hide');
    $('#addEventModal').modal('hide');
}