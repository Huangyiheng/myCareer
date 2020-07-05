$(function () {
    initFileInput("kv-explorer");

})
$(document).ready(function () {
    initjob();
});
function initjob() {
    var count = 0;
    var StepState = parseInt($("#StepState").val());
    var Step = parseInt($("#Step").val());
    ////0.表单填写，1表单审核,2选择课程审核,3面试,4签订合同
    //0未审核,1通过,2未通过
    switch (Step) {
        case 0:
            $("#job_wanted_formvue").show('slow');
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
        case 3:
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
        case 4:
            $("#job_wanted_formvue").hide();
            $("#biaodanstate").show('slow');
            switch (StepState) {
                case 0:
                    $(".biaodanstate0").show('slow');
                    break;
                case 1:
                    $(".mianstonguo").show('slow');
                    break;
                case 2:
                    $(".miansweitongguo").show('slow');
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
/*上一步*/
function shangyibu(sub) {
    $(sub).attr("disabled", "true");
    debugger;
    var StepState = parseInt($("#StepState").val());
    var Step = parseInt($("#Step").val());
    ////0.表单填写，1表单审核,2选择课程审核,3面试,4签订合同
    //0未审核,1通过,2未通过
    switch (Step) {
        case 0:

            break;
        case 1:
            $(".biaodanstate").hide('slow');
            $("#job_wanted_formvue").show('slow');
            break;
        case 2:
            $(".biaodanstate").hide('slow');
            $("#xuanze").show('slow');
            break;
    }
}
/*下一步*/
function xiayibu(sub) {
    debugger;
    $(sub).attr("disabled", "true");
    var count = 0;
    var StepState = parseInt($("#StepState").val());
    var Step = parseInt($("#Step").val());
    ////0.表单填写，1表单审核,2选择课程审核,3面试,4签订合同
    //0未审核,1通过,2未通过
    switch (Step) {
        case 0:

            break;
        case 1:
            $(".biaodanstate").hide('slow');
        $("#xuanze").show('slow');
            break;
        case 2:
            $(".biaodanstate").hide('slow');
            $("#mianshi").show('slow');
            break;
        case 3:
            $(".biaodanstate").hide('slow');
            $("#hetong").show('slow');
            break;
        case 4:
            $(".biaodanstate").hide('slow');
            $("#kefu").show('slow');


            break;
    }
}





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
//开始视频面试
function kaishishipmianshi() {


    $.ajax({
        "url": "/job/Agree_interview",
        "type": "post",
        "success": function (result) {
            if (result == "success") {
                $("#mianshi").hide('slow');
                $(".biaodanstate0").show('slow');
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
//t同意合同
function tongyihetong() {

    $.ajax({
        "url": "/job/contractyes",
        "type": "post",
        "success": function (result) {
            if (result == "success") {
                $("#hetong").hide('slow');
                $("#kefu").show('slow');
                $(".biaodanstate").hide('slow');
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

// 声明一个表示状态的全局变量 status
var status = false;
var flag = false;
/*表单提交*/
function jobsumit() {
    debugger;
    $('#job_wanted_form').data('bootstrapValidator').validate();//启用验证
    flag = $('#job_wanted_form').data('bootstrapValidator').isValid();
    if (flag == true) {
        $("#jobsumitr").attr("disabled", "true");
        debugger;
        $("#jobsumitr").text("uploading..");
        $('.alert-warning').html('').hide();
        $('.alert-success').html('').hide();
        $('.alert-info').html('Uploading now, please wait a moment...').show('slow');

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
                $("#jobsumitr").text("Succeed");

                fanhui = result;
                if (fanhui == "sucees") {
                    $("#job_wanted_formvue").hide('slow');
                    $(".biaodanstate0").show('slow');
                   // $("#jobsumitrjindu").hide('slow')
                   // $("#xuanze").show('slow');
                    $('html, body').animate({
                        scrollTop: ($(".sbiaodanstate0").offset().top)
                    }, 1000);
                } else {
                    $("#jobsumitr").text("Next Step");
                    $("#jobsumitr").removeAttr("disabled");
                    $("#jobsumitr").text("Server error. Please try again");

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
                $("#jobsumitr").text("Server error. Please try again");
            }
        })


    } else {
        $("#jobsumitr").text("Incomplete!");
        setTimeout(function () {
            $("#jobsumitr").text("Next Step");
        },2000)
        $("#jobsumitr").removeAttr("disabled");
        return "false";
    }

}

function fanhuishouyetime(domeid,url){
    var count=11;  //先设定一个页面跳转的变化时间,5秒之后跳转
    var demo=document.getElementById(domeid);
    setTimeout(goIndxePage,1000); //1秒之后，执行goIndexPage这个函数 ,使用setTimeout这个定时器，而不是setInterval，因为函数执行需要5秒，而定时器每隔1秒执行一次函数，虽然setTImeout定时器只能执行一次，但是If，else的判断让这个计时器可以模拟循环使用
    function  goIndxePage() //函数内容
    {
        count--;
        demo.innerHTML="back to the homepage in "+count+" seconds";  //增强用户体验，给一个提示，并且加一个a标签，点击这个a标签可以直接前往百度首页
        if(count<=0) //count小于0,说明5秒时间已经到了,这时候，我们需要跳转页面
        {
            window.location.href=url; //js中的页面跳转
        }
        else
        {
            //setTimeout(goIndxePage,1000);  //count在5秒之内，需要不断的执行goIndexPage这个函数，函数自己调用自己，叫做递归
            setTimeout(arguments.callee,1000) // 当函数递归调用的时候，推荐用arguments.callee来代替函数本身
        }
    }
}
function progressHandlingFunction(e) {

    var curr = e.loaded;
    var total = e.total;
    process = curr / total * 100;
    $("#jobsumitrjindu >div").css("width", process + "%");

}
function formSteptimesubjindu(e){

    var curr = e.loaded;
    var total = e.total;
    process = curr / total * 100;
    $("#formSteptimesubjindu >div").css("width", process + "%");
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
        nationality: '3',
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
            validators: {
                notEmpty: {
                    message: 'required and can\'t be empty'
                },
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
        },
        surName: {
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
                },
                stringLength: {
                    min: 5,
                    max: 11,
                    message: 'Please enter your 11-digit mobile phone number'
                }

            }
        },

        birth_time: {
            validators: {
                notEmpty: {
                    message: 'The date is   can not be empty'
                },
                date: {
                    format: 'YYYY-MM-DD'
                }
            }
        }
    },

    Nationality: {
        message: 'The Nationality is not valid',
        validators: {
            notEmpty: {
                message: 'required and can\'t be empty'
            },
            stringLength: {
                min: 3,
                max: 30,
                message: ' must be more than 3 and less than 30 characters long'
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
                    message: '   can not be empty'
                }
            }
        }
    },
    onlineTeaching: {
        validators: {
            enabled: true,
            validators: {
                notEmpty: {
                    message: '   can not be empty'
                }
            }
        }
    },

    currentlyEmployed: {
        validators: {
            enabled: true,
            validators: {
                notEmpty: {
                    message: '   can not be empty'
                }
            }
        }
    },

    time_teachingJob: {
        validators: {
            enabled: true,
            validators: {
                notEmpty: {
                    message: '   can not be empty'
                }
            }
        }
    },
    participate_teaching: {
        validators: {
            enabled: true,
            validators: {
                notEmpty: {
                    message: '   can not be empty'
                }
            }
        }
    },
    highest_degree: {
        validators: {
            enabled: true,
            validators: {
                notEmpty: {
                    message: '   can not be empty'
                }
            }
        }
    },


    diplomaReceived: {
        validators: {
            enabled: true,
            validators: {
                notEmpty: {
                    message: '  can not be empty'
                }
            }
        }
    },
    English_Language: {
        validators: {
            enabled: true,
            validators: {
                notEmpty: {
                    message: '   can not be empty'
                }
            }
        }
    }


}).on('success.form.bv', function (e) {
});

function fanhuishouye() {
    window.location.href = '/job';
}


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
var timett;

function readionck(typeid) {
    //1.周中上课(2345),2周中周末(4567),3周中周末(2367)
    timett = typeid;
    $("#divnonoe").show('slow');
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

//选择课程提交
function onsubminttime() {
    debugger;
    var seleselecttimearr = $(".selecttime");
    if (seleselecttimearr.length < 4) {
        $("#timetablesubmit").text("Please select the time!");
        setTimeout(function () {
            $("#timetablesubmit").text("Next Step");

        }, 2000);


    } else {
        $("#timetablesubmit").attr("disabled", "true");
        $("#timetablesubmit").text("Upload...")
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

                if (result == "success") {
                    $("#xuanze").hide('slow');
                    $(".biaodanstate0").show('slow');


                } else {
                    $("#timetablesubmit").text("Server error. Please try again");
                    $("#timetablesubmit").removeAttr("disabled");
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
                $("#timetablesubmit").text("Server error. Please try again");
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


/*    $.ajax({
        "url": "/timetable/data",
        "type": "post",
        "success": function (result) {
            debugger
            if (result !== "") {
                var json = eval(result);
                var week = ['1', '2', '3', '4', '5', '6', '7'];
                var day = new Date().getDay();
                var courseType = [
                    ['<span class="colspan">4:30-6:00</span>', 1],
                    ['<span class="colspan">6:00-6:30</span>', 1],
                    ['<span class="colspan">6:35-7:05</span>', 1],
                    ['<span class="colspan">7:10-7:40</span>', 1],
                    ['<span class="colspan">9:30-10:00</span>', 1],
                    ['<span class="colspan">10:05-10:35</span>', 1],
                    ['<span class="colspan">10:40-11:10</span>', 1],
                    ['<span class="colspan">11:15-11:45</span>', 1],
                    ['<span class="colspan">11:50-12:20</span>', 1],
                    ['<span class="colspan">13:00-13:30</span>', 1],
                    ['<span class="colspan">13:35-14:05</span>', 1],
                    ['<span class="colspan">14:10-14:40</span>', 1],
                    ['<span class="colspan">14:45-15:15</span>', 1],
                    ['<span class="colspan">15:20-15:50</span>', 1],
                    ['<span class="colspan">15:55-16:25</span>', 1],
                    ['<span class="colspan">16:30-17:00</span>', 1],
                    ['<span class="colspan">17:05-17:35</span>', 1]
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
                            leftHandWidth: 80,
                            Gheight: 40,
                            palette: false
                        }
                    });

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
                            leftHandWidth: 80,
                            Gheight: 40,
                            palette: false
                        }
                    });

                }
                $(".left-hand-TextDom").text("EURO");
                $('html, body').animate({
                    scrollTop: ($("#coursesTable").offset().top - 75)
                }, 1000);
                switch (typeid) {
                    case 1:
                        z2345shi();
                        break;
                    case 2:
                        z4567shi();
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
    })*/
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
                var week =  ['1', '2', '3', '4', '5', '6', '7'];
                var day = new Date().getDay();
                var courseType = [
                    ['<span class="canada">13:35-14:05</span>&nbsp;/&nbsp;<span class="canadawintertime">00:35-01:05</span>', 1],
                    ['<span class="canada">14:10-14:40</span>&nbsp;/<span class="canadawintertime">01:10-01:40</span>', 1],
                    ['<span class="canada">14:45-15:15</span>&nbsp;/<span class="canadawintertime">01:45-02:15</span>', 1],
                    ['<span class="canada">15:20-15:50</span>&nbsp;/<span class="canadawintertime">02:20-02:50</span>', 1],
                    ['<span class="canada">15:55-16:25</span>&nbsp;/<span class="canadawintertime">02:55-03:25</span>', 1],
                    ['<span class="canada">16:30-17:00</span>&nbsp;/<span class="canadawintertime">03:30-04:00</span>', 1],
                    ['<span class="canada">17:05-17:35</span>&nbsp;/<span class="canadawintertime">04:05-04:35</span>', 1],
                    ['<span class="canada">17:40-18:10</span>&nbsp;/<span class="canadawintertime">04:40-05:10</span>', 1],
                    ['<span class="canada">19:20-19:50</span>&nbsp;/<span class="canadawintertime">06:20-06:50</span>', 1],
                    ['<span class="canada">19:55-20:25</span>&nbsp;/<span class="canadawintertime">06:55-07:25</span>', 1],
                    ['<span class="canada">20:30-21:00</span>&nbsp;/<span class="canadawintertime">07:30-08:00</span>', 1],
                    ['<span class="canada">21:05-21:35</span>&nbsp;/<span class="canadawintertime">08:05-08:35</span>', 1],
                    ['<span class="canada">08:30-09:00</span>&nbsp;/<span class="canadawintertime">19:30-20:00</span>', 1],
                    ['<span class="canada">09:05-09:35</span>&nbsp;/<span class="canadawintertime">20:05-20:35</span>', 1],
                    ['<span class="canada">09:40-10:10</span>&nbsp;/<span class="canadawintertime">20:40-21:10</span>', 1],
                    ['<span class="canada">10:15-10:45</span>&nbsp;/<span class="canadawintertime">21:15-21:45</span>', 1],
                    ['<span class="canada">10:50-11:20</span>&nbsp;/<span class="canadawintertime">21:50-22:20</span>', 1]
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
                            leftHandWidth: 150,
                            Gheight: 40,
                            palette: false
                        }
                    });

                }
                $(".left-hand-TextDom").text("North America  /  UTC ");
                $(".tbdiv").removeClass("selecttime");
                switch (typeid) {
                    case 1:
                        North_Americaz2345shi();
                        break;
                    case 2:
                        North_Americaz4567shi();
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
function North_Americaz4567shi() {
    //划0
    for (var w = 2; w <= 7; w++) {
        for (var y = 9; y <= 13; y++) {
            var classid = w + "" + y;
            $("." + classid + "").text("O");
        }
    }
    for (var w = 5; w <= 6; w++) {
        for (var y = 14; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").text("O");
        }
    }
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
function North_Americaz2345shi() {
    for (var w = 2; w <= 5; w++) {
        for (var y = 9; y <= 13; y++) {
            var classid = w + "" + y;
            $("." + classid + "").text("O");
        }
    }
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
                var week =  ['1', '2', '3', '4', '5', '6', '7'];
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
    for (var w = 6; w <= 7; w++) {
        for (var y = 4; y <= 10; y++) {
            var classid = w + "" + y;
            $("." + classid + "").text("O");
        }
    }
    for (var w = 2; w <= 7; w++) {
        for (var y = 11; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").text("O");
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

    for (var w = 2; w <= 5; w++) {
        for (var y = 11; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").text("O");
        }
    }
    for (var w = 2; w <= 5; w++) {
        for (var y = 11; y <= 17; y++) {
            var classid = w + "" + y;
            $("." + classid + "").parent().attr("name", "color2");
        }
    }


}

