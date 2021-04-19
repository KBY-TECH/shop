var checkAll = $('#check-all');
checkAll.change(function(){
    var checked = $(this).prop('checked');
    $('input[name="check"]').prop('checked',checked);
});
var boxes = $('input[name="check"]');
boxes.change(function(){
    var boxLength = boxes.length;
    var checkedLength = $('input[name="check"]:checked').length;
    var checkedAll = (boxLength === checkedLength);
    checkAll.prop('checked',checkedAll);
});
let btnStop = $('.btn-stop');
btnStop.click(function(){
    $('[name="check"]:checked').each(function(){
        let tr = $(this).closest('tr');
        let stateInput = tr.find('[name="state"]');
        let prevState = stateInput.val();
        //stock이 0이하일경우 알람을 띄우고 '품절'이라고 표시하기.
        if( parseInt(tr.find('[name="stock"]').val()) <= 0){
            stateInput.val(prevState === '판매중지'? '품절' : '판매중지');
        }
        else{
            stateInput.val(prevState === '판매중지'? '판매 중' : '판매중지');
        }
    });
});
function addRow(rows){
    let newRow ="<tr class="+rows+" align='center'>"+
        "<td><input type=\"checkbox\" name=\"check\"></td>" +
        "<td><input type=\"text\" name=\"optionName\"></td>" +
        "<td ><input type=\"text\" name=\"state\" value=\"새 상품\" readonly></td>" +
        "<td><input type=\"text\" name=\"price\" oninput=\"this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\\..*)\\./g, '$1');\" /></td>" +
        "<td><input type=\"text\" name=\"stock\" oninput=\"this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\\..*)\\./g, '$1');\" /></td>" +
        "<td><button class='input-required btn btn-default' type=\"button\"><label for=\"mainImage"+rows+"\">등록</label></button><input id=\"mainImage"+rows+"\" class=\"mainImage\" type=\"file\" name=\"mainImage\" accept=\"image/jpeg, image/png\" style=\"display: none\" onchange=\"imageUpdated(this)\"><input type=\"hidden\" id=\"mainImageKey"+rows+"\" name=\"mainImageKey\" disabled></td>" +
        "<td><button class='input-required btn btn-default' type=\"button\"><label for=\"detailImage"+rows+"\">등록</label></button><input id=\"detailImage"+rows+"\" class=\"detailImage\" type=\"file\" name=\"detailImage\" accept=\"image/jpeg, image/png\" style=\"display: none\" onchange=\"imageUpdated(this)\"><input type=\"hidden\"  id=\"detailImageKey"+rows+"\" name=\"detailImageKey\" disabled></td>" +
        "<td><button class=\"btn-information input-required btn btn-default\" type=\"button\" onclick=\"openInformation(this)\"><label>등록</label></button></td>";
    $('#options').append(newRow);

}
function addFrozenfood(rows){
    let newInformation = "<div class='hover-information "+rows+"'>" +
        "<span class=\"helper\"></span>" +
        "<div class=\"information\"><p>아이템의 고시정보를 입력하세요.</p>" +
        "<table border=\"1\">" +
        "<tr align=\"center\"><td>고시정보명</td><td>내용</td></tr>" +
        "<tr align=\"center\"><td>식품의 유형</td><td><textarea name=\"foodType\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>생산자 및 소재지</td><td><textarea name=\"producer\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조연월일, 유통기한, 또는 품질유지기한</td><td><textarea name=\"qualityMaintenanceDate\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>포장단위별 내용물의 용량(중량), 수량</td><td><textarea name=\"capacityByPackingUnit\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>원재료명 및 함량</td><td><textarea name=\"materialContent\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>영양성분</td><td><textarea name=\"nutritionalIngredients\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>유전자변형식품에 해당하는 경우의 표시</td><td><textarea name=\"geneticallyModified\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>소비자안전을 위한 주의사항</td><td><textarea name=\"precautions\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>수입식품 문구</td><td><textarea name=\"importedFood\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>소비자상담관련 전화번호</td><td><textarea name=\"consumerCounselingPhoneNum\"></textarea></td></tr>" +
        "</table><button type=\"button\" class=\"btn btn-default\" onclick=\"closeInformation(this)\">등록하기</button></div></div>";
    $("form").append(newInformation);
}
function addBag(rows){
    let newInformation = "<div class='hover-information "+rows+"'>" +
        "<span class=\"helper\"></span>" +
        "<div class=\"information\"><p>아이템의 고시정보를 입력하세요.</p>" +
        "<table border=\"1\">" +
        "<tr align=\"center\"><td>고시정보명</td><td>내용</td></tr>" +
        "<tr align=\"center\"><td>종류</td><td><textarea name=\"kind\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>소재</td><td><textarea name=\"material\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>크기</td><td><textarea name=\"size\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>색상</td><td><textarea name=\"color\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조자(수입자)</td><td><textarea name=\"producer\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조국</td><td><textarea name=\"madeIn\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>취급시 주의사항</td><td><textarea name=\"precautions\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>품질보증기준</td><td><textarea name=\"qualityAssuranceStandard\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>A/S 책임자와 전화번호</td><td><textarea name=\"afterServiceAddress\"></textarea></td></tr>" +
        "</table><button type=\"button\" class=\"btn btn-default\" onclick=\"closeInformation(this)\">등록하기</button></div></div>";
    $("form").append(newInformation);
}
function addMeat(rows){
    let newInformation = "<div class='hover-information "+rows+"'>" +
        "<span class=\"helper\"></span>" +
        "<div class=\"information\"><p>아이템의 고시정보를 입력하세요.</p>" +
        "<table border=\"1\">" +
        "<tr align=\"center\"><td>고시정보명</td><td>내용</td></tr>" +
        "<tr align=\"center\"><td>품목 또는 명칭</td><td><textarea name=\"meatPart\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>포장단위별 내용물의 용량(중량),수량,크기</td><td><textarea name=\"capacityByPackingUnit\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>생산자(수입자)</td><td><textarea name=\"producer\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>원산지</td><td><textarea name=\"origin\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조연월일,유통기한</td><td><textarea name=\"qualityMaintenanceDate\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>관련법상 표시사항</td><td><textarea name=\"indication\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>수입식품 문구 여부</td><td><textarea name=\"importedFood\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>상품구성</td><td><textarea name=\"composition\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>보관방법,취급방법</td><td><textarea name=\"storageMethod\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>소비자안전을 위한 주의사항</td><td><textarea name=\"precautions\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>소비자상담관련 전화번호</td><td><textarea name=\"consumerCounselingPhoneNum\"></textarea></td></tr>" +
        "</table><button type=\"button\" class=\"btn btn-default\" onclick=\"closeInformation(this)\">등록하기</button></div></div>";
    $("form").append(newInformation);
}
function addSeafood(rows){
    let newInformation = "<div class='hover-information "+rows+"'>" +
        "<span class=\"helper\"></span>" +
        "<div class=\"information\"><p>아이템의 고시정보를 입력하세요.</p>" +
        "<table border=\"1\">" +
        "<tr align=\"center\"><td>고시정보명</td><td>내용</td></tr>" +
        "<tr align=\"center\"><td>제품명</td><td><textarea name=\"foodName\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>식품의 유형</td><td><textarea name=\"foodType\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>생산자 및 소재지</td><td><textarea name=\"producer\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조연월일, 유통기한, 또는 품질유지기한</td><td><textarea name=\"qualityMaintenanceDate\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>포장단위별 내용물의 용량(중량), 수량</td><td><textarea name=\"capacityByPackingUnit\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>원재료명 및 함량</td><td><textarea name=\"materialContent\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>영양성분</td><td><textarea name=\"nutritionalIngredients\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>유전자변형식품에 해당하는 경우의 표시</td><td><textarea name=\"geneticallyModified\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>소비자안전을 위한 주의사항</td><td><textarea name=\"precautions\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>수입식품 문구</td><td><textarea name=\"importedFood\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>소비자상담관련 전화번호</td><td><textarea name=\"consumerCounselingPhoneNum\"></textarea></td></tr>" +
        "</table><button type=\"button\" class=\"btn btn-default\" onclick=\"closeInformation(this)\">등록하기</button></div></div>";
    $("form").append(newInformation);
}
function addClothes(rows){
    let newInformation = "<div class='hover-information "+rows+"'>" +
        "<span class=\"helper\"></span>" +
        "<div class=\"information\"><p>아이템의 고시정보를 입력하세요.</p>" +
        "<table border=\"1\">" +
        "<tr align=\"center\"><td>고시정보명</td><td>내용</td></tr>" +
        "<tr align=\"center\"><td>제품 소재</td><td><textarea name=\"material\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>색상</td><td><textarea name=\"color\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>치수</td><td><textarea name=\"size\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조자(수입자)</td><td><textarea name=\"producer\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조국</td><td><textarea name=\"madeIn\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>취급시 주의사항</td><td><textarea name=\"precautions\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조연월</td><td><textarea name=\"manufacturedDate\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>품질보증기준</td><td><textarea name=\"qualityAssuranceStandard\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>A/S 책임자와 전화번호</td><td><textarea name=\"afterServiceAddress\"></textarea></td></tr>" +
        "</table><button type=\"button\" class=\"btn btn-default\" onclick=\"closeInformation(this)\">등록하기</button></div></div>";
    $("form").append(newInformation);
}
function addWallet(rows){
    let newInformation = "<div class='hover-information "+rows+"'>" +
        "<span class=\"helper\"></span>" +
        "<div class=\"information\"><p>아이템의 고시정보를 입력하세요.</p>" +
        "<table border=\"1\">" +
        "<tr align=\"center\"><td>고시정보명</td><td>내용</td></tr>" +
        "<tr align=\"center\"><td>종류</td><td><textarea name=\"kind\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>소재</td><td><textarea name=\"material\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>크기</td><td><textarea name=\"size\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조자(수입자)</td><td><textarea name=\"producer\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>제조국</td><td><textarea name=\"madeIn\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>취급시 주의사항</td><td><textarea name=\"precautions\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>품질보증기준</td><td><textarea name=\"qualityAssuranceStandard\"></textarea></td></tr>" +
        "<tr align=\"center\"><td>A/S 책임자와 전화번호</td><td><textarea name=\"afterServiceAddress\"></textarea></td></tr>" +
        "</table><button type=\"button\" class=\"btn btn-default\" onclick=\"closeInformation(this)\">등록하기</button></div></div>";
    $("form").append(newInformation);
}
let informButton;
function openInformation(e){
    let className = $(e).closest('tr').attr('class');
    $('.hover-information').each(function (){
        if($(this).hasClass(className)){
            $(this).show();
            informButton = $(e);
        }
        else $(this).hide();
    })
}
function closeInformation(e){
    $(".hover-information").hide();
    let isInformationNull = false;
    $(e).closest('.information').find('textarea').each(function(){;
        if(!$(this).val()){
            isInformationNull = true;
        }
    });
    if(!isInformationNull){
        informButton.removeClass('btn-default');
        informButton.addClass('btn-success');
        informButton.children('label').text('변경');
    }
    else{
        informButton.removeClass('btn-success');
        informButton.addClass('btn-default');
        informButton.children('label').text('등록');
    }
}

function imageUpdated(e){
    let optionNum = $(e).closest('tr').attr('class');
    let className = $(e).attr('class')+'Key'+optionNum;
    $('#'+className).prop('disabled',false).val(optionNum);
    $(e).off('change');
    let labelButton = $(e).closest('td').children('button');
    labelButton.removeClass('btn-default');
    labelButton.addClass('btn-success');
    labelButton.children('label').text('변경');
}

function beforeSubmit(){
    let result = true;
    $('.input-required').each(function(){
        if(!$(this).hasClass('btn-success')){
            let btn = $(this);
            btn.addClass('warning');
            setTimeout(function() {
                btn.removeClass('warning');
                },6000);
            result = false;
        }
    })
    return result;
}