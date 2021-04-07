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
        var tr = $(this).closest('tr');
        //stock이 0이하일경우 알람을 띄우고 '품절'이라고 표시하기.
        if( parseInt(tr.children('[name="stock"]').val()) <= 0){
            tr.children('.state').text(function(i,oldText){
                return oldText === '판매중지'? '품절' : '판매중지';
            });
        }
        else{
            tr.children('.state').text(function(i,oldText){
                return oldText === '판매중지'? '판매 중' : '판매중지';
            });
        }


    })
});

//상품등록(가방)
var post = {
    init : function(type){
        var _this = this;
        $("#btn-save").on("click",function(){
            _this.save(type);
        });

        $("#btn-update").on("click",function(){
            _this.update(type);
        });
    },
    save : function(type){
        var formData = new FormData();
        makeProductVO(formData);

        $.ajax({
            type : 'POST',
            url : "/api/product/"+type,
            enctype : "multipart/form-data",
            data : formData,
            processData : false,
            contentType : false,
        }).done(function(){
            alert("등록완료");
            window.location.href = '/api/product/seller/user';
        }).fail(function(error){
            alert("등록실패");
        });

    },
    update : function(type){
        var formData = new FormData();
        // formData.append("productName",$('[name=productName]').val());
        //판매상태 처리, 상품 ID 받기 추가해야함
        let id = $("#product-id").val();

        makeProductVO(formData);
        $.ajax({
            type : 'PUT',
            url : "/api/product/"+type+"/"+id,
            enctype : "multipart/form-data",
            data : formData,
            processData : false,
            contentType : false,
        }).done(function(){
            alert("등록완료");
            window.location.href = '/api/product/seller/user';
            window.location.method = 'GET';
        }).fail(function(error){
            alert("등록실패");
        });
    }
};


function makeProductVO(formData){
    formData.append("productName",$('[name=productName]').val());
    $('[name="optionName"]').each(function(){
        formData.append("optionName",$(this).val());
    });
    $('[name="price"]').each(function(){
        formData.append("price",$(this).val());
    });
    $('[name="stock"]').each(function(){
        formData.append("stock",$(this).val());
    });
    $('[name="mainImage"]').each(function(){
        if($(this).prop('files').length > 0){
            formData.append("mainImage",$(this).prop('files')[0]);
            formData.append("mainImageKey",$(this).closest('tr').attr('class'));
        }
    });
    $('[name="detailImage"]').each(function(){
        if($(this).prop('files').length > 0){
            formData.append("detailImage",$(this).prop('files')[0]);
            formData.append("detailImageKey",$(this).closest('tr').attr('class'));
        }
    });
    $('.state').each(function(){
        formData.append('state',$(this).text());
    });
    $('[name="kind"]').each(function(){
        formData.append("kind",$(this).val());
    });
    $('[name="material"]').each(function(){
        formData.append("material",$(this).val());
    });
    $('[name="size"]').each(function(){
        formData.append("size",$(this).val());
    });
    $('[name="color"]').each(function(){
        formData.append("color",$(this).val());
    });
    $('[name="producer"]').each(function(){
        formData.append("producer",$(this).val());
    });
    $('[name="madeIn"]').each(function(){
        formData.append("madeIn",$(this).val());
    });
    $('[name="precautions"]').each(function(){
        formData.append("precautions",$(this).val());
    });
    $('[name="qualityAssuranceStandard"]').each(function(){
        formData.append("qualityAssuranceStandard",$(this).val());
    });
    $('[name="afterServiceAddress"]').each(function(){
        formData.append("afterServiceAddress",$(this).val());
    });
    $('[name="manufacturedDate"]').each(function(){
        formData.append("manufacturedDate",$(this).val());
    });
    $('[name="foodType"]').each(function(){
        formData.append("foodType",$(this).val());
    });
    $('[name="qualityMaintenanceDate"]').each(function(){
        formData.append("qualityMaintenanceDate",$(this).val());
    });
    $('[name="capacityByPackingUnit"]').each(function(){
        formData.append("capacityByPackingUnit",$(this).val());
    });
    $('[name="materialContent"]').each(function(){
        formData.append("materialContent",$(this).val());
    });
    $('[name="nutritionalIngredients"]').each(function(){
        formData.append("nutritionalIngredients",$(this).val());
    });
    $('[name="geneticallyModified"]').each(function(){
        formData.append("geneticallyModified",$(this).val());
    });
    $('[name="importedFood"]').each(function(){
        formData.append("importedFood",$(this).val());
    });
    $('[name="consumerCounselingPhoneNum"]').each(function(){
        formData.append("consumerCounselingPhoneNum",$(this).val());
    });
    $('[name="meatPart"]').each(function(){
        formData.append("meatPart",$(this).val());
    });
    $('[name="origin"]').each(function(){
        formData.append("origin",$(this).val());
    });
    $('[name="indication"]').each(function(){
        formData.append("indication",$(this).val());
    });
    $('[name="composition"]').each(function(){
        formData.append("composition",$(this).val());
    });
    $('[name="storageMethod"]').each(function(){
        formData.append("storageMethod",$(this).val());
    });
    $('[name="foodName"]').each(function(){
        formData.append("foodName",$(this).val());
    });
}
function makeBagDto(formData){
    formData.append("productName",$('[name=productName]').val());
    $('.default').each(function(){
        var name = $(this).attr("name");
        $("[name="+name+"]").each(function(){
            if($(this).attr('type') !== "file"){
                formData.append(name,$(this).val());
            }else if($(this).prop('files').length > 0){
                formData.append(name, $(this).prop('files')[0]);
                formData.append(name+"Key", $(this).closest('tr').attr('class'));
            }
        });
    });
    $('.state').each(function(){
        formData.append('state',$(this).text());
    });
}
function addRow(rows){
    let newRow ="<tr class="+rows+" align='center'>"+
        "<td><input type=\"checkbox\" name=\"check\"></td>" +
        "<td><input type=\"text\" name=\"optionName\"></td>" +
        "<td class=\"state-new\">새 상품</td>" +
        "<td><input type=\"text\" name=\"price\" oninput=\"this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\\..*)\\./g, '$1');\" /></td>" +
        "<td><input type=\"text\" name=\"stock\" oninput=\"this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\\..*)\\./g, '$1');\" /></td>" +
        "<td><input type=\"file\" name=\"mainImage\" accept=\"image/jpeg, image/png\" value=\"등록\"></td>" +
        "<td><input type=\"file\" name=\"detailImage\" accept=\"image/jpeg, image/png\" value=\"등록\"></td>" +
        "<td><input type=\"button\" class=\"btn-information\" value=\"등록\" onclick=\"openInformation(this)\"></td></tr>";
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
        "</table><input type=\"button\" value=\"등록하기\" onclick=\"closeInformation()\"></div></div>";
    $("body").append(newInformation);
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
        "</table><input type=\"button\" value=\"등록하기\" onclick=\"closeInformation()\"></div></div>";
    $("body").append(newInformation);
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
        "</table><input type=\"button\" value=\"등록하기\" onclick=\"closeInformation()\"></div></div>";
    $("body").append(newInformation);
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
        "</table><input type=\"button\" value=\"등록하기\" onclick=\"closeInformation()\"></div></div>";
    $("body").append(newInformation);
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
        "</table><input type=\"button\" value=\"등록하기\" onclick=\"closeInformation()\"></div></div>";
    $("body").append(newInformation);
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
        "</table><input type=\"button\" value=\"등록하기\" onclick=\"closeInformation()\"></div></div>";
    $("body").append(newInformation);
}
function openInformation(e){
    let className = $(e).closest('tr').attr('class');
    $('.hover-information').each(function (){
        if($(this).hasClass(className)) $(this).show();
        else $(this).hide();
    })
}
function closeInformation(){
    $(".hover-information").hide();
}

function stopProduct(){
    //체크된 tr의 상품 ID를 받고 컨트롤러에 요청하여 해당 상품의 옵션들이 전부 판매중지상태가 되도록 한다.
    console.log("판매 멈춰!");
}