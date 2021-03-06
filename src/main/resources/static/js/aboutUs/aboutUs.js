var Timetable = null;

function guanbi() {
    $("#coursesTablediv").hide();
}
function resetrec() {
    setTimeout(function () {
        dataTable.draw(false);
    }, 300)

}
function onreload(id, nationality) {
    $.ajax({
        "url": "/timetable/databyjob",
        "type": "post",
        "data": {
            "jobid": id
        },
        "success": function (result) {
            if (result !== "") {
                //  $("#timetablemodal").modal('show');
                $("#coursesTablediv").show();

                var json = eval(result);
                var week = window.innerWidth > 360 ? ['MON', 'TUE', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] :
                    ['一', '二', '三', '四', '五', '六', '天'];
                var day = new Date().getDay();
                var courseType;
                switch (nationality) {
                    case 0:
                        return "United State";
                        break;
                    case   1  :
                        courseType = courseTypeCanada;//加拿大时间
                        break;
                    case 2:
                        return "British";
                        break;
                    case 3:
                        courseType = courseTypeGeorgia;//格鲁吉亚时间
                        break;
                    case 4:
                        return "Australia";
                        break;
                    case 5:
                        return "New Zealand";
                        break;
                    case 6:
                        return "Italy";
                        break;
                    case 7:
                        return "Ukraine";
                        break;
                    case 8:
                        return "Russia";
                        break;
                    case 9:
                        return "Sorth Africa";
                        break;
                    case 10:
                        return "Denmark";
                        break;
                    case 11:
                        return "Switzerland";
                        break;
                    case 12:
                        return "Germany";
                        break;
                    default:
                        return "数据异常"
                }

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

                switch (nationality) {
                    case 0:
                        return "United State";
                        break;
                    case   1  ://加拿大时间
                        $(".left-hand-TextDom").text("China time / Canada");
                        break;
                    case 2:
                        return "British";
                        break;
                    case 3://格鲁吉亚时间
                        $(".left-hand-TextDom").text("China time / Georgla");
                        break;
                    case 4:
                        return "Australia";
                        break;
                    case 5:
                        return "New Zealand";
                        break;
                    case 6:
                        return "Italy";
                        break;
                    case 7:
                        return "Ukraine";
                        break;
                    case 8:
                        return "Russia";
                        break;
                    case 9:
                        return "Sorth Africa";
                        break;
                    case 10:
                        return "Denmark";
                        break;
                    case 11:
                        return "Switzerland";
                        break;
                    case 12:
                        return "Germany";
                        break;
                    default:
                        return "数据异常"
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

function deletedata(e, dt, node, config, bt) {
    var params = [];
    var trList = dt.rows().nodes();

    for (i = 0; i < trList.length; i++) {
        var trObj = trList[i];
        if (trObj.firstElementChild.firstElementChild.checked) {
            params.push(trObj.firstElementChild.firstElementChild.value);//所选行id
        }
    }
    if (params.length < 1) {
        message({
            message: '请选择一行数据!',
            type: 'warning'
        });
    } else {
        bt.disable(); // 禁用按钮

        var delete_id = params.toString();
        delete_id = delete_id.substring(0, delete_id.length - 1);
        $.ajax({
            "url": "/aboutUs/aboutUs_delete",
            "type": "post",
            "data": {
                "IDArray": params
            },
            "dataType": "",
            "success": function (result) {
                bt.enable();
                if (result == "success") {
                    //draw()重绘表格.false不是指不重绘，而是重绘当前页的表格；没有false会做一个完全的重绘，并且回到第一页.
                    dataTable.draw(false);
                } else {
                    dataTable.draw(false);
                    message({
                        message: '删除失败,请重试!',
                        type: 'error'
                    });
                    console.log(result.toString());

                }

            },
            error: function () {
                bt.enable();
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

function adddata() {
    $('#addAboutUs').modal('show');
}

var jobsub = new Vue({
    el: "#aboutUs_add_form",
    data: {
        id: '',
        content: '',
        photo_yourself: ''
    }
})
$("#aboutUs_add_sb").click(function () {
    $('#addAboutUs').data('bootstrapValidator').validate();//启用验证
    flag = $('#addAboutUs').data('bootstrapValidator').isValid();
    if (flag == true) {
        $('.alert-warning').html('').hide();
        $('.alert-success').html('').hide();
        $('.alert-info').html('提交中请稍后...').show();
        $("#aboutUs_add_sb").attr("disabled", "true");
        debugger;
        var formData = new FormData();
        if (jobsub.id != null) {
            formData.append("id", jobsub.id);
        }
        formData.append("content", jobsub.content);
        formData.append("photo_yourself", $("input[name='photo_yourself']")[0].files[0]);
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            // contentType : 'application/x-www-form-urlencoded;charset=utf-8',
            //  dataType: "json",//预期服务器返回的数据类型
            url: "/aboutUs/aboutUsAdd",//url
            cache: false,//cache设置为false，上传文件不需要缓存。
            processData: false,//取消默认的序列化,因为formaata已经是序列化的数据
            contentType: false,//contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
            data: formData,
            success: function (result) {
                fanhui = result;
                if (fanhui != "error") {

                    $('#addAboutUs').modal('hide');
                    message("提交成功");
                    dataTable.draw(false);
                    $("#aboutUs_add_sb").removeAttr("disabled");

                } else if (fanhui == "error") {
                    message({
                        message:'服务器异常',
                        type:'error'
                    });

                    $("#aboutUs_add_sb").removeAttr("disabled");

                } else {

                    message({
                        message:'服务器异常',
                        type:'error'
                    });
                    $("#aboutUs_add_sb").removeAttr("disabled");
                }
            },
            error: function () {
                message({
                    message:'服务器异常',
                    type:'error'
                });
                $("#aboutUs_add_sb").removeAttr("disabled");
            }
        })

    } else {
        $('.alert-info').html('').hide();
        $('.alert-success').html('').hide();
        $('.alert-warning').html('请修改后再提交').show();
    }

})


$('#addAboutUs').bootstrapValidator({
    message: '无效的值',
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
                file: {
                    extension: 'jpg,png,jpeg',
                    // type: 'application/jpg',/*[JPG,jpg,png]*/
                    minSize: 0,
                    maxSize: 10 * 1024 * 1024,
                    message: 'Please choose a jpg or png or jpeg file with a size less than 10M.'
                }
            }
        },
        content: {
            validators: {

                notEmpty: {
                    message: '内容不能为空'
                },
                stringLength: {
                    max: 250,
                    min:80
                }

            }
        }
    }
}).on('success.form.bv', function (e) {
});


function show(data) {
    alert("查看" + data)
}

function search() {
    dataTable.ajax.reload(); //从新加载表单
}

var dataTable;
$(function () { //DataTable必须写在匿名函数里面,否则会报错
    var counti = 0;
    dataTable = $('#myTable').DataTable({
        "serverSide": true, //服务器处理分页
        "pageLength": 15,
        "select": false,
        "searching": true, //是否显示本地搜索框,默认为true
        "ordering": true, //是否显示排序,默认为TRUE
        "order": [0, 'asc'], //默认的列排序,从0开始
        "aLengthMenu": [[15, 30, 50, 1000000000], [15, 30, 50, "全部"]], //每页数量[[10, 25, 60, -1], [10, 25, 50, "全部"]]
        //"colReorder": true, //扩展插件, 单击并拖动列重新排序
        //autoFill : true, //自动填充,需要后台代码支持
        //dom的使用参考:http://www.datatables.club/manual/daily/2016/05/11/option-dom.html
        //dom : 'lfBtip',//Bfrtip,B-按钮,l - Length changing 改变每页显示多少条数据的控件,f - Filtering input 即时搜索框控件,t - The Table 表格本身,	i - Information 表格相关信息控件,p - Pagination 分页控件,r - pRocessing 加载等待显示信息
        "dom": "<'row'<'col-sm-2'l><'col-sm-7'><'col-sm-3'B>>t<'row'<'col-sm-6'i><'col-sm-6'p>>", //自定义按钮,分页等的位置http://www.datatables.club/manual/daily/2016/05/11/option-dom.html
        //buttons: ['colvis', 'excel', 'print'],

        "bStateSave": true, //默认为false,是否开启状态保存，当选项开启的时候会使用一个cookie保存表格展示的信息的状态，例如分页信息，展示长度，过滤和排序等,这样当终端用户重新加载这个页面的时候可以使用以前的设置
        "bSortClasses": true, //默认为true,是否在当前被排序的列上额外添加sorting_1,sorting_2,sorting_3三个class，当该列被排序的时候，可以切换其背景颜色,该选项作为一个来回切换的属性会增加执行时间（当class被移除和添加的时候）,当对大数据集进行排序的时候你或许希望关闭该选项
        "sProcessing": "处理中...", //默认值为Processing...,当表格处理用户动作（例如排序或者类似行为）的时候显示的字符串
        scrollY: 710, //表格滚动,设置表格固定高度
        scrollCollapse: true,//当显示更少的记录时，是否允许表格减少高度
        bSort: true,// 默认为true 是否开启列排序，对单独列的设置在每一列的bSortable选项中指定

        //"bScrollInfinite":true,// 默认为false,是否开启不限制长度的滚动条（和sScrollY属性结合使用），不限制长度的滚动条意味着当用户拖动滚动条的时候DataTable会不断加载数据,当数据集十分大的时候会有些用处，该选项无法和分页选项同时使用，分页选项会被自动禁止，注意，额外推荐的滚动条会优先与该选项

        //"sScrollX": "50%",//限制宽度
        //"sScrollXInner": "50%",//限制宽度
        /*  data: dataearrays 数组方式获取数据*/
        /* data: dataobjiect, 对象方式获取数据*/

        /* "bAutoWidth":true,//默认为true,是否自动计算列宽，计算列宽会花费一些时间，如果列宽通过aoColumns传递，可以关闭该属性作为优化
         "bDeferRender":false,//默认为false, 是否延迟渲染，当用Ajax或者js方式加载数据时开启延迟渲染会带来很大的速度提升 ,当该属性设置为true时，表格每一行新增的元素只有在需要被画出来时才会被DataTable创建出来
         "bFilter":true,//默认为true,是否对数据进行过滤，数据过滤是十分灵活的，允许终端用户输入多个用空格分隔开的关键字,匹配包含这些关键字的行，即使关键字的顺序不是用户输入的顺序，过滤操作会跨列进行匹配，关键字可以分布在一行中不同的列,要注意的是如果你想在DataTable中使用过滤，该选项必须设置为true，如果想移除默认过滤输入框但是保留过滤功能，请设置为false(API没写，推测是false)
         "bInfo":true,//默认为true,是否显示表格信息，是指当前页面上显示的数据的信息，如果有过滤操作执行，也会显示过滤操作的信息
         "bLengthChange":true,//默认为true,是否允许终端用户选择分页的页数，页数为10，25，50和100，需要分页组件bPaginate的支持
         "bPaginage":true,//默认为true,是否开启分页功能,即使设置为false,仍然会有一个默认的<前进,后退>分页组件
         "bProcessing":false,//默认为false,当表格在处理的时候（比如排序操作）是否显示“处理中...”,当表格的数据中的数据过多以至于对其中的记录进行排序时会消耗足以被察觉的时间的时候，该选项会有些用处
         "bServerSide":true,// 默认为false,配置DataTable使用服务器端处理，注意，sAjaxSource参数必须指定，以便给DataTable一个为每一行获取数据的数据源

          //"sScrollY":"100%",//默认为空字符串，即：无效,是否开启垂直滚动，垂直滚动会驱使DataTable设置为给定的长度，任何溢出到当前视图之外的数据可以通过垂直滚动进行察看,当在小范围区域中显示大量数据的时候，可以在分页和垂直滚动中选择一种方式，该属性可以是css设置，或者一个数字（作为像素量度来使用）
         "sInfo": "Got a total of _TOTAL_ entries to show (_START_ to _END_)",// 默认值为Showing _START_ to _END_ of _TOTAL_ entries ,该属性给终端用户提供当前页面的展示信息，字符串中的变量会随着表格的更新被动态替换，而且可以被任意移动和删除
         "sInfoFiltered": " - filtering from _MAX_ records",//默认值为(filtered from _MAX_ total entries) ,当用户过滤表格中的信息的时候，该字符串会被附加到信息字符串的后面，从而给出过滤器强度的直观概念
         "sInfoPostFix": "All records shown are derived from real information.",//默认值为空字符串,使用该属性可以很方便的向表格信息字符串中添加额外的信息，被添加的信息在任何时候都会被附加到表格信息组件的后面,sInfoEmpty和sInfoFiltered可以以任何被使用的方式进行结合
         "sLengthMenu": "Display _MENU_ records", //默认值为Show _MENU_ entries ,描述当分页组件的下拉菜单的选项被改变的时候发生的动作，‘_MENU_‘变量会被替换为默认的10，25，50，100,如果需要的话可以被自定义的下拉组件替换
         "sLoadingRecords": "Please wait - loading...",//默认值为Loading...,当使用Ajax数据源和表格在第一次被加载搜集数据的时候显示的字符串，该信息在一个空行显示,向终端用户指明数据正在被加载，注意该参数在从服务器加载的时候无效，只有Ajax和客户端处理的时候有效
         "sProcessing": "DataTables is currently busy",//默认值为Processing...,当表格处理用户动作（例如排序或者类似行为）的时候显示的字符串
         "sSearch": "Apply filter _INPUT_ to table",//默认为Search:,描述用户在输入框输入过滤条件时的动作，变量‘_INPUT_‘,如果用在字符串中,DataTable会使用用户输入的过滤条件替换_INPUT_为HTML文本组件，从而可以支配它（即用户输入的过滤条件）出现在信息字符串中的位置 ,如果变量没有指定，用户输入会自动添加到字符串后面
         */
        "createdRow": function (row, data, dataIndex) { //创建行时的事件
            for (var int = 1; int < row.cells.length - 1; int++) {
                $(row.cells[int]).click(function () { //绑定多选框的选中状态
                    if ($(row.cells[0]).find(":checkbox").is(':checked')) {
                        $(row.cells[0]).find(":checkbox").prop("checked", false);
                    } else {
                        $(row.cells[0]).find(":checkbox").prop("checked", true);
                    }
                    var icheck = document.getElementsByName("check"); //所有多选按钮
                    var flag = true;
                    //点击的时候在遍历icheck，看看是否有没选中的
                    for (var j = 0; j < icheck.length; j++) {
                        //定义一个标志来记录
                        if (icheck[j].checked == false) {
                            flag = false;
                            break;
                        }
                    }
                    $("input[name='checkall']").prop("checked", flag);
                });
            }
        },
        language: { //国际化
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },

        "ajax": {
            "url": "/aboutUs/aboutUsDate",
            "type": "POST",
            "data": function (d) {
                var pageNum = dataTable ? dataTable.page() + 1 : 1; //加入了分页插件pagehellpr,需要传回当前页数
                d.pageNum = pageNum;
                if (d.order && d.order.length > 0) { //传入需要排序的列
                    d.orderStr = d.columns[d.order[0].column].data;
                    d.orderDir = d.order[0].dir;
                }
                var formMap = $("#testfrom").serializeObject();
                if (formMap.content != "")
                    d.content = formMap.content;
                return d;
            }
        },
        buttons: [
            {
                'extend': 'colvis',
                'text': '显示的列',
                'className': ' btn ', //按钮的clss名称可以通过类名增加样式
                'exportOptions': {
                    'modifier': {
                        'page': 'current'
                    },
                    'columns': [1, 2, 3, 4, 5, 6] //要到处的列,默认为全部
                },
            },

            {
                'extend': 'excel',
                'text': 'excel',
                'title': '', //文件标题
                'className': ' btn ', //按钮的clss名称可以通过类名增加样式
                'bom': 'true', ///////乱码的话设置为true
                'exportOptions': {
                    'modifier': {
                        'page': 'current'
                    },
                    //   'columns': [1, 2, 3, 4, 5, 6] //要到处的列,默认为全部
                },
            }, {
                'extend': 'pdf',
                'text': 'pdf',
                'title': '', //文件标题
                'className': ' btn ', //按钮的clss名称可以通过类名增加样式
                'exportOptions': {
                    'modifier': {
                        'page': 'current'
                    },
                    // 'columns': [1, 2, 3, 4, 5, 6] //要到处的列,默认为全部
                },
            }, {
                'extend': 'print',
                'text': '打印',
                'className': ' btn ', //按钮的clss名称可以通过类名增加样式
                'exportOptions': {
                    'modifier': {
                        'page': 'current'
                    },
                    // 'columns': [1, 2, 3, 4, 5, 6] //要到处的列,默认为全部
                },
            }
            ,
            {
                text: '添加', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-primary ',
                action: function (e, dt, node, config) { //参考:https://datatables.net/reference/option/buttons.buttons.action
                    adddata();
                    //this.disable(); // 禁用按钮
                }
            }
            ,
            {
                text: '删除', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-danger ',
                id: 'delbt',
                action: function (e, dt, node, config) { //参考:https://datatables.net/reference/option/buttons.buttons.action
                    deletedata(e, dt, node, config, this);
                }
            }


        ],


        columns: [
            {
                sWidth: "2%",//设置宽度
                "orderable": false, // 禁用排序
                "targets": [0], // 指定的列
                "data": "id",
                'title': '<input type="checkbox" value="" onclick="checkall(this)" name="checkall"/>',
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox" value="' + data + '" name="check"/>';
                }
            },

            {
                title: '序号',
                data: null,
                className: 'text-center whiteSpace',
                "orderable": false, // 禁用排序
                render: function (data, type, row, meta) {
                    //当前行数+1+没有起始数
                    return meta.row + 1 + meta.settings._iDisplayStart;

                }
            },


            {
                // "orderable": false,
                data: 'content',
                title: '内容',
                "orderable": false,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }, //排序按钮

            {
                data: 'createTime',
                title: '创建时间',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return dateFormat(data);
                }
            },
            {
                data: 'p_yourself',
                title: '图片',
                "orderable": false,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return "<a ><span onclick='picture(" + data.fileId + ")' style='font-size: 26px;' class='glyphicon glyphicon-search'></span></a>";
                }
            }
            , {
                sWidth: "5%",//设置宽度
                data: 'id',
                title: '操作',
                "orderable": false, // 禁用排序
                render: function (data, type, row) {
                    return '<button class=" btn btn-warning btn-sm" onclick="addAboutUs(' + data + ')">修改</button>';
                }
            }

        ]
    });
})
/*存储当前父id*/
function addAboutUs(id) {
    jobsub.id = id
    /*返回树形节点*/
    $('#addAboutUs').modal('show');
    if (id != null) {
        $.ajax({
            "url": "/aboutUs/getaboutUsbyid",
            "type": "post",
            "data": {
                "id": id
            },
            "dataType": "JSON",
            "success": function (result) {
                if (result !== "") {
                    jobsub.id = result.id;
                    jobsub.content = result.content;
                    // jobsub.photo_yourself = result.p_yourself;
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

}

function checkall(a) {
    $("input[name='check']").each(function () {
        this.checked = a.checked;
    });
}

$('#gmt_modify,#gmt_create').datetimepicker({
    format: 'yyyy-mm-dd', //日期格式
    language: 'zh-CN', //设置语言中文
    //startDate:new Date(),//开始日期为当前日期
    endDate: new Date(), //结束日期
    weekStart: 1, //一周从哪一天开始。0（星期日）到6（星期六）
    todayBtn: 1,
    autoclose: true, //选择一个日期后是否关闭窗口
    todayHighlight: 1,
    startView: 2, //日期时间选择器打开之后首先显示的视图。0:时间,1:时间小时,2:日期,3:月份,4:年分
    minView: 2, //最小视图到日期结束
    forceParse: true, //强制解析输入的值转为时间
    minuteStep: 1, //分钟步进间隔
    showMeridian: 1
})

function picture(id) {//打开图片
    $("#videomodal").modal('show');
    $("#videopayle").html("<img src=\"/file/picture?fileid=" + id + "\" width=\"100%;\">")
}
$(function () {
    initFileInput("tearcherPicture");
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
        msgFilesTooMany: "Only one can be uploaded", /*"选择上传的文件数量({n}) 超过允许的最大数值{m}！",*/
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
