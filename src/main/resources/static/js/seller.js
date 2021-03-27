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

//상품등록(가방)
var post = {
    init : function(){
        var _this = this;
        $("#btn-save").on("click",function(){
            _this.save();
        });

        $("#btn-update").on("click",function(){
            _this.update();
        });
    },
    save : function(){
        var formData = new FormData();
        makeProductVO(formData);

        $.ajax({
            type : 'POST',
            url : "/api/product/bag",
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
    update : function(){
        var formData = new FormData();
        // formData.append("productName",$('[name=productName]').val());
        //판매상태 처리, 상품 ID 받기 추가해야함
        let id = $("#product-id").val();

        makeProductVO(formData);
        $.ajax({
            type : 'PUT',
            url : "/api/product/bag/"+id,
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
post.init();

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
        "<tr align=\"center\">" +
        "<td>품질보증기준</td><td><textarea name=\"qualityAssuranceStandard\"></textarea></td></tr>" +
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