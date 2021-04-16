var publisher = {
    init: function () {
        var _this = this;
        $('#btn-create').on('click', function () {
            _this.save();
        });
        $('#btn-check').on('click',function (){
            _this.check();
        });
        $('#btn-checkNumber').on('click',function (){
            _this.check2();
        });
    },
    save: function () {
        var data = {
            email: $('#email').val(),
            name: $('#name').val(),
            businessNumber: $('#business_number').val(),
            businessName: $('#business_name').val(),
            password: $('#password').val()
        };
        console.log(data);

        $.ajax({
            type: 'POST',
            url: '/api/publisher/',
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            var Alert = function (msg, type) {
                swal({
                    title:'loading',
                    text: msg,
                    type: type,
                    timer: 1500,
                    showConfirmButton: false
                });
            }
            Alert("1초 후에 로그인 화면으로 이동합니다.", 'success');
            setTimeout(() => window.location.href = '/publisher/loginForm', 1000)
        }).fail(function (error) {
            alert(error.responseText);
        });
    },
    check :function () {
        var email=$('#email').val();
        console.log(email);
        $.ajax({
            type: 'POST',
            url: '/api/publisher/emailCheck?email='+email,
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function (response) {
            // console.log(response);
            var Alert = function (response, type) {
                swal({
                    title: 'success',
                    text: response,
                    timer: 1500,
                    showConfirmButton: false
                });
            }
            console.log(response);
            Alert(response, 'success');
            // setTimeout(() => window.location.href = '/', 3000)
        }).fail(function (error){
            console.log(error);
            var Alert = function (response, type) {
                swal({
                    title: 'warning!',
                    text: response,
                    icon : 'info',
                    type: type,
                    timer: 1500,
                    showConfirmButton: true
                });
            }
            Alert(error.responseText,false);
            // console.log(error);
        });
    },
    check2 :function () {
        var business_number=$('#business_number').val();
        // console.log(email);
        $.ajax({
            type: 'POST',
            url: '/api/publisher/businessNumberCheck?business_number='+business_number,
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function (response) {
            var Alert = function (response) {
                swal({
                    title: 'success',
                    text: response,
                    timer: 1500,
                    showConfirmButton: false
                });
            }
            console.log(response);
            Alert(response);
        }).fail(function (error){
            var Alert = function (response, type) {
                swal({
                    title: 'warning!',
                    text: response,
                    icon : 'info',
                    type: type,
                    timer: 1500,
                    showConfirmButton: true
                });
            }
            Alert(error.responseText,false);
        });
    },

}
publisher.init();