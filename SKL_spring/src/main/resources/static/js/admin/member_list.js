// Initialize DataTable when document is ready
$(document).ready(function() {
    searchMembers();
});

var dataTable;

function initializeDataTable() {
    if (dataTable) {
        dataTable.destroy();
    }
    dataTable = $('#dataTable').DataTable({
        "language": {
            "lengthMenu": "페이지당 _MENU_ 개씩 보기",
            "zeroRecords": "검색 결과가 없습니다",
            "info": "_START_ - _END_ / _TOTAL_",
            "infoEmpty": "0 - 0 / 0",
            "infoFiltered": "(전체 _MAX_ 개 중 검색결과)",
            "search": "검색:",
            "paginate": {
                "first": "처음",
                "last": "마지막",
                "next": "다음",
                "previous": "이전"
            }
        }
    });
}

function updateMemberStatus() {
    var memberIdList = [];
    var memberStatusList = [];
    $('select[id^="memberStatus"]').each(function() {
        var memberId = $(this).attr('id').replace('memberStatus', '');
        var memberStatus = $(this).val();
        memberIdList.push(memberId);
        memberStatusList.push(memberStatus);
    });
    
    $.ajax({
        type: 'POST',
        url: '/admin/updateMemberStatusList',
        data: {
            memberIdList: memberIdList,
            memberStatusList: memberStatusList
        },
        success: function(data) {
            if (data.success) {
                Swal.fire({
                    title: '수정이 완료되었습니다!',
                    icon: 'success',
                    draggable: false,
                    customClass: {
                        title: 'swal-title'
                    }
                });
                searchMembers();
            } else {
                alert('수정 실패');
            }
        }
    });
}

function searchMembers() {
    $.ajax({
        type: 'POST',
        url: '/admin/getMemberList',
        data: {
        },
        success: function(fragment) {
            $('#dataTable').html(fragment);
            initializeDataTable();
        },
        error: function(xhr, status, error) {
            alert('회원 조회 중 오류가 발생했습니다.');
            console.error('Error:', error);
        }
    });
}
        