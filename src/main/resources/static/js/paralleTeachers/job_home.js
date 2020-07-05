var jobsub = new Vue({
    el: "#jobfrom",
    data: {
        eMail: '',
        password: '',
    }
})
$("#parraformsub").click(function () {
    debugger;
    $('#jobfrom').data('bootstrapValidator').validate();//启用验证
    flag = $('#jobfrom').data('bootstrapValidator').isValid();

    if (flag) {
        $("#parraformsub").attr("disabled", "true");
        var formData = new FormData();
        formData.append("eMail", jobsub.eMail);
        formData.append("password", jobsub.password);
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            // contentType : 'application/x-www-form-urlencoded;charset=utf-8',
            //  dataType: "json",//预期服务器返回的数据类型
            url: "/job/paraller_home_sub",//url
            cache: false,//cache设置为false，上传文件不需要缓存。
            processData: false,//取消默认的序列化,因为formaata已经是序列化的数据
            contentType: false,//contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
            data: formData,

            success: function (result) {
                debugger;

                switch (result) {
                   case "registersucess":
                       window.location.href = "/job/job_wanted_to";
                        break;
                    case "logsucess":
                        window.location.href = "/job/job_wanted_to";
                        break;
                    case "passwroderror":
                        $("#parraformsub").removeAttr("disabled");
                        message({
                            message:'wrong password',
                            type:'error'
                        });
                        break;
                    default:{
                        $("#parraformsub").removeAttr("disabled");
                        message({
                            message:'Server exception',
                            type:'error'
                        });
                    }

                }


            },
            error: function () {
                message({
                    message:'Server exception',
                    type:'error'
                });
                $("#parraformsub").removeAttr("disabled");

            }
        })

    } else {
        message({
            message: 'Please complete the form',
            type: 'warning'
        });
    }

})


$('#jobfrom').bootstrapValidator(
    {
        message: '无效的值',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            eMail: {
                validators: {
                    notEmpty: {
                        message: 'The email is required and cannot be empty'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }/*,
                    threshold: 2,//有2字符以上才发送ajax请求
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: "/job/emailrepeat",
                        message: 'This mailbox has been registered',
                        delay: 1000,//ajax刷新的时间是1秒一次
                        type: 'POST',
                        //自定义提交数据，默认值提交当前input value
                        data: function(validator) {
                            return {
                                eMail : $("input[name=eMail]").val(),
                                method : "checkUserName"//UserServlet判断调用方法关键字。
                            };
                        }
                    }*/
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    },

                    different: {
                        field: 'eMail',
                        message: 'The password cannot be the same as username'
                    }
                }
            }/*,
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'eMail',
                        message: 'The password cannot be the same as username'
                    }
                }
            }*/

        }
    }).on('success.form.bv', function (e) {
});


/*< !--下拉框点击事件-- >*/
$(function () {
    var Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;
        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function (e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
        ;
    }
    var accordion = new Accordion($('#accordion'), false);
});
$(function () {
    var Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;
        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function (e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
        ;
    }
    var accordion = new Accordion($('#accordion1'), false);
});
$(function () {
    var Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;
        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function (e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
        ;
    }
    var accordion = new Accordion($('#accordion2'), false);
});
$(function () {
    var Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;
        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function (e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
        ;
    }
    var accordion = new Accordion($('#accordion3'), false);
});

function dingbu() {
    scrollTo(0, 0);
}



