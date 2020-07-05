$(document).ready(function () {
    $("body").show('slow');
    initjob();
});

function initjob() {
    var count = 0;
    var StepState = parseInt($("#StepState").val());
    var Step = parseInt($("#Step").val());
    ////0.表单填写，1表单审核,2选择课程审核,3面试,4签订合同
    //0未审核,1通过,2未通过
    debugger;
    switch (Step) {
        case 0:
            switch (StepState) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
            break;
        case 1:
            $("#job_wanted_formvue").hide();
            $("#biaodanstate").show('slow');
            switch (StepState) {
                case 0:
                    $(".biaodanstate0").show('slow');
                    break;
                case 1:
                    $(".biaodanstate1").show('slow');
                    break;
                case 2:
                    $(".biaodanstate2").show('slow');
                    break;
            }
            break;
        case 2:
            layui.step.next('#stepForm');
            $("#kechengfromstate").hide();
            $("#kechengstate").show('slow');
            switch (StepState) {
                case 0:
                    $(".kechengstate0").show('slow');
                    break;
                case 1:
                    $(".kechengstate1").show('slow');
                    break;
                case 2:
                    $(".kechengstate2").show('slow');
                    break;
            }
            break;
        case 3:
            $("#mianshi").hide();
            $("#minashistate").show('slow');
            ref = setInterval(function () {
                layui.step.next('#stepForm');
                count++;
                if (count == 2) {
                    clearInterval(ref);
                }
            }, 400);

            switch (StepState) {
                case 0:
                    $(".minashistate0").show('slow');
                    break;
                case 1:
                    $(".minashistate1").show('slow');
                    break;
                case 2:
                    $(".minashistate2").show('slow');
                    break;
            }
            break;
        case 4:
            ref = setInterval(function () {
                layui.step.next('#stepForm');
                count++;
                if (count == 4) {
                    clearInterval(ref);
                }
            }, 400);
            switch (StepState) {
                case 0:
                    $(".miasnhitype0").show('slow');
                    break;
                case 1:
                    $(".miasnhitype1").show('slow');
                    break;
                case 2:
                    $(".miasnhitype2").show('slow');
                    break;

            }


            break;
    }
    /* ref = setInterval(function () {
         layui.step.next('#stepForm');
         count++;
         if (count == 2) {
             clearInterval(ref);
         }
     }, 500);*/
}

function chongxintijao() {
    $("#job_wanted_formvue").show('slow');
    $("#biaodanstate").hide('slow');
}

function chongxintijaokecheng() {
    $("#kechengfromstate").show('slow');
    $("#kechengstate").hide('slow');
}

function tongyihetong() {
    $.ajax({
        "url": "/job/contractyes",
        "type": "post",
        "success": function (result) {
            if (result == "success") {
                layui.step.next('#stepForm');
                $(".miasnhitype0").show();
            } else {
                message({
                    message: 'Server exception!',
                    type: 'error'
                });
            }


        },
        error: function (e) {
            console.log(e.status);
            console.log(e.responseText);
        }
    })
}

function chognxinmianshi() {
    $.ajax({
        "url": "/job/Agree_interview",
        "type": "post",
        "success": function (result) {
            if (result == "success") {
                $(".minashistate2").hide();
                $(".minashistate1").hide();
                $(".minashistate0").show('slow');
            } else {
                message({
                    message: 'Server exception!',
                    type: 'error'
                });
            }


        },
        error: function (e) {
            console.log(e.status);
            console.log(e.responseText);
        }
    })
}

function kaishishipmianshi() {
    $.ajax({
        "url": "/job/Agree_interview",
        "type": "post",
        "success": function (result) {
            if (result == "success") {
                layui.step.next('#stepForm');
            } else {
                message({
                    message: 'Server exception!',
                    type: 'error'
                });
            }


        },
        error: function (e) {
            console.log(e.status);
            console.log(e.responseText);
        }
    })
}

layui.config({
    base: '../step/step-lay/'
}).use(['form', 'step'], function () {
    var $ = layui.$, form = layui.form, step = layui.step;

    step.render({
        elem: '#stepForm',
        filter: 'stepForm',
        width: '100%', //设置容器宽度
        stepWidth: '1440px',
        height: '2500px',
        stepItems: [{
            title: 'Basic lnfo'
        }, {
            title: 'Settle Schedule'
        }, {
            title: 'Demo Class & Interview'
        },
            {
                title: 'Signing the contract'
            }

        ]
    });

    step = step;
    form.on('submit(formStep)', function (data) {

        jobsumit();

        return false;
    });
    form.on('submit(formSteptimesub)', function (data) {

        onsubminttime();

        return false;
    });
    form.on('submit(formsucess)', function (data) {

        message('You have successfully registered! We will contact you within 3 working days');

        return false;
    });
    form.on('submit(formStep2)', function (data) {
        step.next('#stepForm');
        return false;
    });
    form.on('submit(formStep3)', function (data) {
        window.location.href = '/parallel/job';
        return false;
    });

    $('.pre').click(function () {
        step.pre('#stepForm');
    });

    $('.next').click(function () {
        step.next('#stepForm');
    });
})

function fanhuishouye(domeid, url) {
    var count = 6;  //先设定一个页面跳转的变化时间,5秒之后跳转
    var demo = document.getElementById(domeid);
    setTimeout(goIndxePage, 1000); //1秒之后，执行goIndexPage这个函数 ,使用setTimeout这个定时器，而不是setInterval，因为函数执行需要5秒，而定时器每隔1秒执行一次函数，虽然setTImeout定时器只能执行一次，但是If，else的判断让这个计时器可以模拟循环使用
    function goIndxePage() //函数内容
    {
        count--;
        demo.innerHTML = "The page will jump after " + count + " seconds";  //增强用户体验，给一个提示，并且加一个a标签，点击这个a标签可以直接前往百度首页
        if (count <= 0) //count小于0,说明5秒时间已经到了,这时候，我们需要跳转页面
        {
            window.location.href = url; //js中的页面跳转
        } else {
            //setTimeout(goIndxePage,1000);  //count在5秒之内，需要不断的执行goIndexPage这个函数，函数自己调用自己，叫做递归
            setTimeout(arguments.callee, 1000) // 当函数递归调用的时候，推荐用arguments.callee来代替函数本身
        }
    }





}


//选择课程提交
function onsubminttime() {
    var seleselecttimearr = $(".selecttime");
    if (seleselecttimearr.length < 4) {

        message({
            message: 'Choose at least four!',
            type: 'warning'
        });
    } else {
        $("#timetablesubmit").attr("disabled", "true");
        $("#timetablesubmit").text("uploading...")
        var idsb = "";
        seleselecttimearr.each(function () {
            var obj = $(this).attr("tid");
            idsb += obj + ",";
        });
        var timetype = $('input[name="typetime"]:checked').val();
        $("#formSteptimesubjindu").show('slow');
        $.ajax({
            "url": "/timetable/dataupdate",
            "type": "post",
            "data": {
                "idsb": idsb,
                "timetype": timetype
            },
            "success": function (result) {
                $("#timetablesubmit").removeAttr("disabled");
                $("#timetablesubmit").text(" Next Step");
                debugger;
                if (result == "success") {
                    $("#kechengfromstate").hide();
                    $("#kechengstate").show('slow');
                    $(".kechengstate0").show('slow');
                    $(".kechengstate1").hide('slow');
                    $(".kechengstate2").hide('slow');

                    //回到页面顶部
                    document.documentElement.scrollTop = 0;
                    $("html,body").animate({
                        scrollTop: 0,
                        screenLeft: 0,
                    }, 400);

                } else {
                    message({
                        message: '服务器异常,请稍后再试!',
                        type: 'error'
                    });
                    console.log(result.toString());
                }

            },
            xhr: function () { //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
                myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) { //检查upload属性是否存在
                    //绑定progress事件的回调函数
                    myXhr.upload.addEventListener('progress', formSteptimesubjindu, false);
                }
                return myXhr; //xhr对象返回给jQuery使用
            },
            error: function (e) {
                $("#timetablesubmit").removeAttr("disabled");
                $("#timetablesubmit").text(" Next Step");
                message({
                    message: '服务器异常,请重试!',
                    type: 'error'
                });
                console.log(e.status);
                console.log(e.responseText);
            }
        })


    }

}

function formSteptimesubjindu() {

    var curr = e.loaded;
    var total = e.total;
    process = curr / total * 100;
    $("#formSteptimesubjindu >div").css("width", process + "%");

}

// 声明一个表示状态的全局变量 status
var status = false;
var flag = false;

function jobsumit() {

    $('#job_wanted_form').data('bootstrapValidator').validate();//启用验证
    flag = $('#job_wanted_form').data('bootstrapValidator').isValid();
    if (flag == true) {
        $("#jobsumitr").attr("disabled", "true");

        $("#jobsumitr").text("uploading...");

        var formData = new FormData();
        formData.append("firstName", jobsub.firstName);
        formData.append("surName", jobsub.surName);
        formData.append("phoneNumber", jobsub.phoneNumber);

        if (!jobsub.dateOfBirth == null || !jobsub.dateOfBirth == "") {
            formData.append("dateOfBirth", new Date(jobsub.dateOfBirth));
        }
        formData.append("nationality", jobsub.nationality);
        formData.append("teachingEnglish", jobsub.teachingEnglish);
        formData.append("onlineTeaching", jobsub.onlineTeaching);
        formData.append("currentlyEmployed", jobsub.currentlyEmployed);
        formData.append("teachingJob", jobsub.teachingJob);
        formData.append("participateYour", jobsub.participateYour);
        formData.append("diploma", jobsub.diploma);
        formData.append("educationName", jobsub.educationName);

        formData.append("diplomaReceived", jobsub.diplomaReceived);
        formData.append("teachingCertification", jobsub.teachingCertification);
        formData.append("photo_yourself_from", $("input[name='photo_yourself']")[0].files[0]);
        formData.append("personal_video_from", $("input[name='personal_video']")[0].files[0]);
        $("#jobsumitrjindu").show('slow');
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            //  dataType: "json",//预期服务器返回的数据类型
            url: "/job/recruiting",//url
            cache: false,//cache设置为false，上传文件不需要缓存。
            processData: false,//取消默认的序列化,因为formaata已经是序列化的数据
            contentType: false,//contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
            data: formData,
            success: function (result) {
                $("#jobsumitr").text("  Next Step");
                $("#jobsumitr").removeAttr("disabled");
                fanhui = result;
                debugger;
                if (fanhui == "sucees") {

                    $("#job_wanted_formvue").hide('slow');
                    $("#biaodanstate").show('slow');
                    $(".biaodanstate0").show('slow');
                    $(".biaodanstate1").hide('slow');
                    $(".biaodanstate2").hide('slow');
                    $("#stepForm").css("height", "280vh");
                    //回到页面顶部
                    document.documentElement.scrollTop = 0;
                    $("html,body").animate({
                        scrollTop: 0,
                        screenLeft: 0,
                    }, 400);
                } else {
                    $("#jobsumitr").text("Server exception");


                }
            },
            xhr: function () { //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
                myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) { //检查upload属性是否存在
                    //绑定progress事件的回调函数
                    myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
                }
                return myXhr; //xhr对象返回给jQuery使用
            },
            error: function () {
                $("#jobsumitr").removeAttr("disabled");
                $("#jobsumitr").text("Server exception");
            }
        })


    } else {
        $("#jobsumitr").removeAttr("disabled");
        return "false";
    }

}

function progressHandlingFunction(e) {

    var curr = e.loaded;
    var total = e.total;
    process = curr / total * 100;
    $("#jobsumitrjindu >div").css("width", process + "%");

}

var jobsub = new Vue({
    el: "#job_wanted_formvue",
    data: {
        firstName: '',
        surName: '',
        phoneNumber: '',
        e_mail: '',
        dateOfBirth: '',
        teachingEnglish: '',
        nationality: '',
        onlineTeaching: '',
        currentlyEmployed: '',
        teachingJob: '',
        participateYour: '',
        diploma: '',
        educationName: '',
        diplomaReceived: '',
        teachingCertification: '',
    },

    methods: {
        /*提交按钮*/
        jobsubvue: function (event) {


        }
    }
})


/*<!--    当个别控件判断失效时可以试着把他的位置换到上面来-->*/

$('#job_wanted_form').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        inlineRadioOptions: {
            validators: {
                notEmpty: {
                    message: ''/*The gender is required*/
                }
            }
        },
        photo_yourself: {
            validators: {
                notEmpty: {
                    message: 'required and can\'t be empty'
                },
                file: {
                    extension: 'jpg,png,jpeg',
                    // type: 'application/jpg',/*[JPG,jpg,png]*/
                    minSize: 0,
                    maxSize: 10 * 1024 * 1024,
                    message: 'Please choose a jpg or png or jpeg file with a size less than 10M.'
                }
            }
        },

        personal_video: {
            notEmpty: {
                message: 'required and can\'t be empty'
            },
            validators: {

                file: {
                    extension: 'mp4',
                    // type: 'application/jpg',/*[JPG,jpg,png]*/
                    minSize: 1024 * 1024,
                    maxSize: 50 * 1024 * 1024,
                    message: 'Please choose a mp4 file with a size between 1M and 50M.'
                }
            }
        },

        EducationName: {
            message: 'The First Name is not valid',
            validators: {
                notEmpty: {
                    message: 'required and can\'t be empty'
                },
                stringLength: {
                    min: 3,
                    max: 30,
                    message: 'must be more than 3 and less than 30 characters long'
                },

                regexp: {
                    regexp: /^[a-zA-Z0-9_\.]+$/,
                    message: 'can only consist of alphabetical, number, dot and underscore'
                }
            }
        },

        firstName: {
            message: 'The First Name is not valid',
            validators: {
                notEmpty: {
                    message: 'required and can\'t be empty'
                },
                stringLength: {
                    min: 3,
                    max: 30,
                    message: 'must be more than 3 and less than 30 characters long'
                },
                /*remote: {
                    url: 'remote.php',
                    message: 'The username is not available'
                },*/

            }
        }, surName: {
            message: 'The First Name is not valid',
            validators: {
                notEmpty: {
                    message: 'required and can\'t be empty'
                },
                stringLength: {
                    min: 3,
                    max: 30,
                    message: 'must be more than 3 and less than 30 characters long'
                },
                /*remote: {
                    url: 'remote.php',
                    message: 'The username is not available'
                },*/

            }
        },

        phoneNumber: {
            message: 'The phone number is not valid',
            validators: {
                notEmpty: {
                    message: 'The phone number is required'
                },
                digits: {
                    message: 'The value can contain only digits'
                }
            }
        },

        birth_time: {
            validators: {
                notEmpty: {
                    message: 'The date is  can not be empty'
                },
                date: {
                    format: 'YYYY-MM-DD'
                }
            }
        },
        nationality: {
            message: 'The nationality is not valid',
            validators: {
                notEmpty: {
                    message: 'required and can\'t be empty'
                },

                /*remote: {
                    url: 'remote.php',
                    message: 'The username is not available'
                },*/
                regexp: {
                    regexp: /^[a-zA-Z0-9_\.]+$/,
                    message: 'can only consist of alphabetical, number, dot and underscore'
                }
            }
        },
        teachingEnglish: {
            validators: {
                enabled: true,
                validators: {
                    notEmpty: {
                        message: '  can not be empty'
                    }
                }
            }
        },
        onlineTeaching: {
            validators: {
                enabled: true,
                validators: {
                    notEmpty: {
                        message: '  can not be empty'
                    }
                }
            }
        },

        currentlyEmployed: {
            validators: {
                enabled: true,
                validators: {
                    notEmpty: {
                        message: '  can not be empty'
                    }
                }
            }
        },

        time_teachingJob: {
            validators: {
                enabled: true,
                validators: {
                    notEmpty: {
                        message: '  can not be empty'
                    }
                }
            }
        },
        participate_teaching: {
            validators: {
                enabled: true,
                validators: {
                    notEmpty: {
                        message: '  can not be empty'
                    }
                }
            }
        },
        highest_degree: {
            validators: {
                enabled: true,
                validators: {
                    notEmpty: {
                        message: '  can not be empty'
                    }
                }
            }
        },


        diplomaReceived: {
            validators: {
                enabled: true,
                validators: {
                    notEmpty: {
                        message: ' can not be empty'
                    }
                }
            }
        },
        English_Language: {
            validators: {
                enabled: true,
                validators: {
                    notEmpty: {
                        message: '  can not be empty'
                    }
                }
            }
        }

    }
}).on('success.form.bv', function (e) {
});

$("#personal_video").fileinput({
    language: 'en', //设置语言
    uploadUrl: '',//'/document/uploadfile.do?type='+type, //上传的地址
    allowedFileExtensions: ['MP4'], //'JPG', 'jpg', 'gif', 'png', 'exe', 'txt', 'rar', 'jar', 'bmp'接收的文件后缀
    uploadAsync: true, //默认异步上传
    showUpload: false, //是否显示上传按钮
    showRemove: true, //显示移除按钮
    showPreview: true, //是否显示预览
    showCaption: true, //是否显示标题
    browseClass: "btn btn-primary", //按钮样式
    dropZoneEnabled: false, //是否显示拖拽区域
    maxImageWidth: 10000,//图片的最大宽度
    maxImageHeight: 10000,//图片的最大高度
    maxFileSize: 50 * 1024, //单位为kb，如果为0表示不限制文件大小
    maxFileCount: 1, //表示允许同时上传的最大文件个数
    enctype: 'multipart/form-data',
    validateInitialCount: true,
    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
    msgFilesTooMany: "Only one can be uploaded",/*"选择上传的文件数量({n}) 超过允许的最大数值{m}！",*/
    layoutTemplates: {
        actionUpload: '',//去除上传预览缩略图中的上传图片；
        actionZoom: ''   //去除上传预览缩略图中的查看详情预览的缩略图标。
    },
})
$(function () {
    initFileInput("kv-explorer");
})

function initFileInput(ctrlName) {
    var control = $('#' + ctrlName);
    var type = $("input[name='typeid']").val();
    control.fileinput({
        language: 'en', //设置语言
        uploadUrl: '',//'/document/uploadfile.do?type='+type, //上传的地址
        allowedFileExtensions: ['JPG', 'jpg', 'png'], //'JPG', 'jpg', 'gif', 'png', 'exe', 'txt', 'rar', 'jar', 'bmp'接收的文件后缀
        //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
        uploadAsync: true, //默认异步上传
        showUpload: false, //是否显示上传按钮
        showRemove: true, //显示移除按钮
        showPreview: true, //是否显示预览
        showCaption: true, //是否显示标题
        browseClass: "btn btn-primary", //按钮样式
        dropZoneEnabled: false, //是否显示拖拽区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        maxImageWidth: 10000,//图片的最大宽度
        maxImageHeight: 10000,//图片的最大高度
        maxFileSize: 1024 * 10, //单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,

        maxFileCount: 1, //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount: true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "Only one can be uploaded",/*"选择上传的文件数量({n}) 超过允许的最大数值{m}！",*/
        layoutTemplates: {
            // actionDelete:'', //去除上传预览的缩略图中的删除图标
            actionUpload: '',//去除上传预览缩略图中的上传图片；
            actionZoom: ''   //去除上传预览缩略图中的查看详情预览的缩略图标。
        },
    }).on('filepreupload', function (event, data, previewId, index) { //上传中
        var form = data.form,
            files = data.files,
            extra = data.extra,
            response = data.response,
            reader = data.reader;
        //alert("event"+event+"data"+data+"previewid"+previewid+"index"+index);
        //console.log('文件正在上传');
    }).on("fileuploaded", function (event, data, previewId, index) { //一个文件上传成功
        var data = data.response.lstOrderImport;
        //alert(data);
        $('#uploadfile').modal('hide');
        $('#documentadd').modal('hide');
        //添加成功刷新页面
        $('#body').load('/document/documenthome.do');
        //$('#documentshow').load("/document/documentshow.do?page=1&typeid="+type);
        //alert("sdfsd "+event+"previewId"+previewId+"data"+data.response.list[0].fname+"index"+index);
        //alert("event"+event+"data"+data+"previewid"+previewid+"index"+index);
        //console.log('文件上传成功！' + data.id);
    }).on('fileerror', function (event, data, msg) { //一个文件上传失败
        //alert("event"+event+"data"+data+"msg"+msg);
        //console.log('文件上传失败！' + data.id);

    }).on('filebatchuploaderror', function (event, data, msg) {
        //alert("event"+event+"data"+data+"msg"+msg);
        //$(".file-error-message").text("请按照正确的进行操作！谢谢！");
    })

}

//根据国家显示不同图片
function ifguojia() {
    debugger;
    var Nationality = parseInt( $("#Nationality").val());
    switch (Nationality) {
        case 1://North America
            $("#imgleft").attr("src", "\\static\\images\\paralleTeachers\\TimeNorthAmerica1.png");
            $("#imgright").attr("src", "\\static\\images\\paralleTeachers\\TimeNorthAmerica2.png");
            break;
        case 3://Europe
            $("#imgright").attr("src", "\\static\\images\\paralleTeachers\\timeEurope1.png");
            $("#imgleft").attr("src", "\\static\\images\\paralleTeachers\\timeEurope2.png");

            break;
    }

}

var timett;

function readionck(typeid) {
    //1.周中上课(2345),2周中周末(4567),3周中周末(2367)
    timett = typeid;
    $("#seletcount").text("0");

    switch (typeid) {
        case 1:
            $("#countkeshu").text("28");
            onreload(1);
            break;
        case 2:
            $("#countkeshu").text("56");
            onreload(2);
            break;
    }
}


//1.周中上课(2345),2周中周末(4567),3周中周末(2367)
function sub(obj) {
    var typeid = timett;
    switch (typeid) {
        case 1:
            z2345(obj);
            break;
        case 2:
            z4567(obj);
            break;
    }
    var seleselecttimearr = $(".selecttime");
    var count = seleselecttimearr.length;
    $("#counthiden").show('slow');
    $("#seletcount").text(count);
}

function z4567(obj) {


    //$(".tbdiv").toggleClass("selecttime");
    var namid = $(obj).attr("name");

    $("div[name='" + namid + "']").each(function () {
        $(this).toggleClass("selecttime");
    })
}

function z2345(obj) {

    /*var bian=0;*/

    $(obj).parent().parent().children().each(function () {
        /*周12345反选*/
        /*  if(bian==0){
              bian==1;
              $(this).children("div").addClass("selecttime");
          }
          if(bian==1){
              $(this).children("div").removeClass("selecttime");
              bian==0;
          }*/
        $(this).children("div").toggleClass("selecttime");
    });
    /*   if(bian==0){
           bian==1;
       }
       if(bian==1){
           bian==0;
       }*/

    //$(obj).removeClass("selecttime");

}

function z3467(obj) {

    // $(".tbdiv").removeClass("selecttime");
    $(obj).parent().parent().children().each(function () {
        $(this).children("div").toggleClass("selecttime");

    });

}


var Timetable = null;

function onreload(typeid) {

    $('html, body').animate({
        scrollTop: ($("#coursesTable").offset().top - 170)
    }, 1000);
debugger;
    var Nationality = parseInt( $("#Nationality").val());
    switch (Nationality) {//根据不同地区返回不同数据
        case 1://North America
            Nationalitydata(typeid);
            break;
        case 3://Europe
            Europedata(typeid);

            break;
    }
    return true;

};

//******************************************************************北美
function Nationalitydata(typeid) {
    $('html, body').animate({
        scrollTop: ($("#coursesTable").offset().top - 170)
    }, 1000);

    $.ajax({
        "url": "/timetable/data_Nationality",
        "type": "post",
        "success": function (result) {
            if (result !== "") {
                var json = eval(result);
                var week = window.innerWidth > 360 ? ['MON', 'TUE', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] :
                    ['一', '二', '三', '四', '五', '六', '天'];
                var day = new Date().getDay();
                var courseType = [
                    ['<span class="canada">1:35-2:05</span>&nbsp;/&nbsp;<span class="canadawintertime">0:35-1:05</span>', 1],
                    ['<span class="canada">2:10-2:40</span>&nbsp;/&nbsp;<span class="canadawintertime">1:10-1:40</span>', 1],
                    ['<span class="canada">2:45-3:15</span>&nbsp;/&nbsp;<span class="canadawintertime">1:45-2:15</span>', 1],
                    ['<span class="canada">3:20-3:50</span>&nbsp;/&nbsp;<span class="canadawintertime">2:20-2:50</span>', 1],
                    ['<span class="canada">3:55-4:25</span>&nbsp;/&nbsp;<span class="canadawintertime">2:55-3:25</span>', 1],
                    ['<span class="canada">4:30-5:00</span>&nbsp;/&nbsp;<span class="canadawintertime">3:30-4:00</span>', 1],
                    ['<span class="canada">5:05-5:35</span>&nbsp;/&nbsp;<span class="canadawintertime">4:05-4:35</span>', 1],
                    ['<span class="canada">5:40-6:10</span>&nbsp;/&nbsp;<span class="canadawintertime">4:40-5:10</span>', 1],
                    ['<span class="canada">7:20-7:50</span>&nbsp;/&nbsp;<span class="canadawintertime">6:20-6:50</span>', 1],
                    ['<span class="canada">7:55-8:25</span>&nbsp;/&nbsp;<span class="canadawintertime">6:55-7:25</span>', 1],
                    ['<span class="canada">8:30-9:00</span>&nbsp;/&nbsp;<span class="canadawintertime">7:30-8:00</span>', 1],
                    ['<span class="canada">9:05-9:35</span>&nbsp;/&nbsp;<span class="canadawintertime">8:05-8:35</span>', 1],
                    ['<span class="canada">20:30-21:00</span>&nbsp;/&nbsp;<span class="canadawintertime">19:30-20:00</span>', 1],
                    ['<span class="canada">21:05-21:35</span>&nbsp;/&nbsp;<span class="canadawintertime">20:05-20:35</span>', 1],
                    ['<span class="canada">21:40-22:10</span>&nbsp;/&nbsp;<span class="canadawintertime">20:40-21:10</span>', 1],
                    ['<span class="canada">22:15-22:45</span>&nbsp;/&nbsp;<span class="canadawintertime">21:15-21:45</span>', 1],
                    ['<span class="canada">22:50-23:20</span>&nbsp;/&nbsp;<span class="canadawintertime">21:50-22:20</span>', 1]
                ];
                // 实例化(初始化课表)
                if (Timetable == null) {
                    Timetable = new Timetables({
                        el: '#coursesTable',
                        timetables: json,
                        week: week,
                        merge: false,
                        timetableType: courseType,
                        highlightWeek: day,
                        gridOnClick: function (e) {
                            console.log(e)
                        },
                        styles: {
                            leftHandWidth: 180,
                            Gheight: 40,
                            palette: false
                        }
                    });
                    $(".left-hand-TextDom").text("North America  /   UTC ");
                } else {
                    Timetable.setOption({
                        timetables: json,
                        week: week,
                        styles: {
                            palette: ['#dedcda', '#ff4081']
                        },
                        timetableType: courseType,
                        gridOnClick: function (e) {
                            console.log(e);
                        },
                        styles: {
                            leftHandWidth: 180,
                            Gheight: 40,
                            palette: false
                        }
                    });

                }
                $(".left-hand-TextDom").text("North America  /  UTC ");
                $(".tbdiv").removeClass("selecttime");
                switch (typeid) {
                    case 1:
                        Nationalityz2345shi();
                        break;
                    case 2:
                        Nationalityz4567shi();
                        break;

                }


            } else {
                message({
                    message: '服务器异常,请稍后再试!',
                    type: 'error'
                });
                console.log(result.toString());
            }

        },
        error: function (e) {
            message({
                message: '服务器异常,请重试!',
                type: 'error'
            });
            console.log(e.status);
            console.log(e.responseText);
        }
    })
    return true;

};
/*初始化周中周末上课表格*/
function Nationalityz4567shi() {
    for (var w = 1; w <= 1; w++) {
        for (var y = 1; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }

    /*空余天数画斜杠*/
    for (var w = 2; w <= 7; w++) {
        for (var y = 1; y <= 8; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();
        }
    }
    for (var w = 1; w <= 4; w++) {
        for (var y = 13; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }

    for (var w = 7; w <= 7; w++) {
        for (var y = 13; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }
    for (var w = 5; w <= 6; w++) {
        for (var y = 17; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }

    /*不同颜色区分,周4567*/
    for (var w = 4; w <= 7; w++) {
        for (var y = 9; y <= 12; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().attr("name", "color2");
        }
    }



    /*周4567联动*/
    for (var w = 4; w <= 7; w++) {
        for (var y = 9; y <= 12; y++) {
            var classid = w + "" + y;

            $("." + classid + "").attr("name", "zhou4567" + (y - 10));
        }
    }

    for (var w = 2; w <= 3; w++) {
        for (var y = 9; y <= 12; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().attr("name", "color3");
            $("." + classid + "").attr("name", "zhou" + (y - 9));
        }
    }


    for (var w = 5; w <= 6; w++) {
        for (var y = 13; y <= 16; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().attr("name", "color3");
            $("." + classid + "").attr("name", "zhou" + (y - 13));
        }
    }


}
/*初始化12345*/
function Nationalityz2345shi() {
    for (var w = 1; w <= 1; w++) {
        for (var y = 1; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();
        }
    }
    for (var w = 2; w <= 7; w++) {
        for (var y = 1; y <= 8; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();
        }
    }
    for (var w = 2; w <= 7; w++) {
        for (var y = 13; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();
        }
    }
    for (var w = 6; w <= 7; w++) {
        for (var y = 9; y <= 12; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();
        }
    }
}



//**********************************************************************欧洲
function Europedata(typeid) {
    $.ajax({
        "url": "/timetable/data_Europe",
        "type": "post",
        "success": function (result) {
            if (result !== "") {
                var json = eval(result);
                var week = window.innerWidth > 360 ? ['MON', 'TUE', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] :
                    ['一', '二', '三', '四', '五', '六', '天'];
                var day = new Date().getDay();
                var courseType = [
                    ['8:30-10:00 / <span class="colspan">4:30-6:00</span>', 1],
                    ['10:00-10:30 / <span class="colspan">6:00-6:30</span>', 1],
                    ['10:35-11:05 / <span class="colspan">6:35-7:05</span>', 1],
                    ['11:10-11:40 / <span class="colspan">7:10-7:40</span>', 1],
                    ['13:30-14:00 / <span class="colspan">9:30-10:00</span>', 1],
                    ['14:05-14:35 / <span class="colspan">10:05-10:35</span>', 1],
                    ['14:40-15:10 / <span class="colspan">10:40-11:10</span>', 1],
                    ['15:15-15:45 / <span class="colspan">11:15-11:45</span>', 1],
                    ['15:50-16:20 / <span class="colspan">11:50-12:20</span>', 1],
                    ['17:00-17:30 / <span class="colspan">13:00-13:30</span>', 1],
                    ['17:35-18:05 / <span class="colspan">13:35-14:05</span>', 1],
                    ['18:10-18:40 / <span class="colspan">14:10-14:40</span>', 1],
                    ['18:45-19:15 / <span class="colspan">14:45-15:15</span>', 1],
                    ['19:20-19:50 / <span class="colspan">15:20-15:50</span>', 1],
                    ['19:55-20:25 / <span class="colspan">15:55-16:25</span>', 1],
                    ['20:30-21:00 / <span class="colspan">16:30-17:00</span>', 1],
                    ['21:05-21:35 / <span class="colspan">17:05-17:35</span>', 1]
                ];
                // 实例化(初始化课表)
                if (Timetable == null) {
                    Timetable = new Timetables({
                        el: '#coursesTable',
                        timetables: json,
                        week: week,
                        merge: false,
                        timetableType: courseType,
                        highlightWeek: day,
                        gridOnClick: function (e) {
                            console.log(e)
                        },
                        styles: {
                            leftHandWidth: 150,
                            Gheight: 40,
                            palette: false
                        }
                    });
                    $(".left-hand-TextDom").text("China time / EURO");
                } else {
                    Timetable.setOption({
                        timetables: json,
                        week: week,
                        styles: {
                            palette: ['#dedcda', '#ff4081']
                        },
                        timetableType: courseType,
                        gridOnClick: function (e) {
                            console.log(e);
                        },
                        styles: {
                            leftHandWidth: 150,
                            Gheight: 40,
                            palette: false
                        }
                    });

                }
                $(".left-hand-TextDom").text("China time / EURO");
                $(".tbdiv").removeClass("selecttime");
                switch (typeid) {
                    case 1:
                        Europez2345shi();
                        break;
                    case 2:
                        Europezz4567shi();
                        break;

                }


            } else {
                message({
                    message: '服务器异常,请稍后再试!',
                    type: 'error'
                });
                console.log(result.toString());
            }

        },
        error: function (e) {
            message({
                message: '服务器异常,请重试!',
                type: 'error'
            });
            console.log(e.status);
            console.log(e.responseText);
        }
    })
}
/*初始化周中周末上课表格*/
function Europezz4567shi() {
    /*空余天数画斜杠*/
    for (var w = 1; w <= 5; w++) {
        for (var y = 1; y <= 10; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }
    for (var w = 1; w <= 1; w++) {
        for (var y = 11; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }
    /*不同颜色区分,周4567*/
    for (var w = 4; w <= 7; w++) {
        for (var y = 11; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().attr("name", "color2");
        }
    }

    /*周4567联动*/
    for (var w = 4; w <= 7; w++) {
        for (var y = 11; y <= 17; y++) {
            var classid = w + "" + y;

            $("." + classid + "").attr("name", "zhou4567" + (y - 10));
        }
    }


    /*空余天数画斜杠结束*/
    for (var w = 2; w <= 3; w++) {
        for (var y = 11; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().attr("name", "color3");
            $("." + classid + "").attr("name", "zhou" + (y - 10));
        }
    }


    for (var w = 6; w <= 7; w++) {
        for (var y = 1; y <= 3; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }
    for (var w = 6; w <= 7; w++) {
        for (var y = 4; y <= 10; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().attr("name", "color3");
            $("." + classid + "").attr("name", "zhou" + (y - 3));
        }
    }


}
/*初始化12345*/
function Europez2345shi() {
    for (var w = 1; w <= 1; w++) {
        for (var y = 1; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();
        }
    }
    for (var w = 1; w <= 7; w++) {
        for (var y = 1; y <= 10; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();
        }
    }
    for (var w = 6; w <= 7; w++) {
        for (var y = 11; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();
        }
    }
}



/*周中周末上课*/
/*
function z3467shi() {
    for (var w = 1; w <= 3; w++) {
        for (var y = 1; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }
    for (var w = 4; w <= 7; w++) {
        for (var y = 1; y <= 11; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().append("/");
            $("." + classid + "").remove();

        }
    }
}*/

