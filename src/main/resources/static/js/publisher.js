var publisher = {
    init: function () {
        var _this = this;
        $('#btn-create').on('click', function () {
            _this.save();
        });
        $('#btn-check').on('click',function (){
            _this.check();
        });
    },
    save: function () {


        var data = {
            name: $('#name').val(),
            email: $('#email').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/user/',
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
            setTimeout(() => window.location.href = '/', 1000)
        }).fail(function (error) {
            // console.log("false");
            // console.log(error);
            alert(JSON.stringify(error.responseText));
        });
    },
    check :function () {
        var email=$('#email').val();
        // console.log(email);
        $.ajax({
            type: 'POST',
            url: 'api/user/mailCheck?email='+email,
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
            // console.log(error);
            var Alert = function (response, type) {
                swal({
                    title: 'warning!',
                    text: response,
                    icon : 'info',
                    type: type,
                    timer: 1500,
                    showConfirmButton: false
                });
            }
            Alert(error.responseText,false);
            // console.log(error);
        });
    },

}
user.init();