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
            sub(null);
            //显示人数
            peopleNumberRS();
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
            "url": "/job/recruitment_lecturer_delete",
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

function sub(obj) {
    var count = $(obj).attr("count");//人数
    switch (count) {
        case "0":
            $(obj).attr("count", "1");
            $(obj).attr("peopleNumber", "1");
            $(obj).html("1");
            break;
        case "1":
            $(obj).attr("count", "2");
            $(obj).attr("peopleNumber", "2");
            $(obj).html("2");
            break;
        case "2":
            $(obj).attr("count", "3");
            $(obj).html("0");
            break;
        case "3":
            $(obj).attr("count", "0");
            $(obj).attr("peopleNumber", "0");
            $(obj).html("0");
            break;
        default:
            $(obj).attr("count", "0");
            $(obj).attr("peopleNumber", "0");
            $(obj).html("0");

            break;
    }

    var sele = $(obj).attr("count");
    if (sele == 3) {
        $(obj).html($(obj).attr("time"));
        $(obj).attr("peopleNumber", "0");
        $(obj).removeClass("selecttime")

    } else {
        $(obj).addClass("selecttime");
    }


    var seleselecttimearr = $(".selecttime");
    var count = seleselecttimearr.length;
    $("#counthiden").show('slow');
    $("#seletcount").text(count);
}

//初始化人数
function peopleNumberRS() {
    var seleselecttimearr = $(".selecttime");
    seleselecttimearr.each(function () {
        $(this).html($(this).attr("peopleNumber"))
    });
}

//选择课程提交
function onsubminttime() {
    var seleselecttimearr = $(".selecttime");


    $("#timetablesubmit").attr("disabled", "true");
    $("#timetablesubmit").text("uploading...")
    var _list = new Array();
    seleselecttimearr.each(function () {
        var TimeTableCourse = new Object();
        debugger;
        TimeTableCourse.id = $(this).attr("tid");
        TimeTableCourse.peopleNumber = $(this).attr("peopleNumber");
        _list.push(TimeTableCourse);
    });


    $("#onsubminttime").attr("disabled");
    $.ajax({
        "url": "/timetable/updatetime",
        "type": "post",
        "data": {
            "tableCourseList": JSON.stringify(_list),

        },
        "success": function (result) {
            $("#onsubminttime").removeAttr("disabled");
            if (result == "success") {

                message({
                    message: '修改成功',
                    type: 'success'
                });
                guanbi();

            } else {
                message({
                    message: '服务器异常,请稍后再试!',
                    type: 'error'
                });
                console.log(result.toString());
            }
            $("#onsubminttime").removeAttr("disabled");
        },
        error: function (e) {
            $("#onsubminttime").removeAttr("disabled");
            message({
                message: '服务器异常,请重试!',
                type: 'error'
            });
            console.log(e.status);
            console.log(e.responseText);
        }
    })


}


function adddata() {
    alert("增加")
}

function update(data) {
    alert("修改" + data)
}

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
        "order": [15, 'asc'], //默认的列排序,从0开始
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
            "url": "/job/recruitment_lecturer_date",
            "type": "POST",
            "data": function (d) {
                var pageNum = dataTable ? dataTable.page() + 1 : 1; //加入了分页插件pagehellpr,需要传回当前页数
                d.pageNum = pageNum;
                if (d.order && d.order.length > 0) { //传入需要排序的列
                    d.orderStr = d.columns[d.order[0].column].data;
                    d.orderDir = d.order[0].dir;
                }
                var formMap = $("#testfrom").serializeObject();
                if (formMap.firstName != "")
                    d.firstName = formMap.firstName;
                if (formMap.eMail != "")
                    d.eMail = formMap.eMail;
                if (formMap.diploma != "")
                    d.diploma = formMap.diploma;
                if (formMap.teachingEnglish != "")
                    d.teachingEnglish = formMap.teachingEnglish;
                if (formMap.participateYour != "")
                    d.participateYour = formMap.participateYour;
                if (formMap.diplomaReceived != "")
                    d.diplomaReceived = formMap.diplomaReceived;
                if (formMap.teachingCertification != "")
                    d.teachingCertification = formMap.teachingCertification;
                /*  if (formMap.gmt_createstart != "")
                      d.createstart = new Date(formMap.gmt_createstart + " 00:00:00");
                  if (formMap.gmt_createend != "")
                      d.createend = new Date(formMap.gmt_createend + " 23:59:59");*/

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
            /*,
            {
                text: '添加', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-primary ',
                action: function (e, dt, node, config) { //参考:https://datatables.net/reference/option/buttons.buttons.action
                    adddata();
                    this.disable(); // 禁用按钮
                }
            }*/
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
                "data": "jobwantedId",
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

            /* {
              data: 'id',
              title: 'id',
              "orderable": false, // 禁用排序
              "orderDataType": "any-number",
              "asSorting": ["desc", "asc"] //排序方式
          },*/
            {
                // "orderable": false,
                data: 'surName',
                title: '姓',
                "orderable": false,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }, //排序按钮
            {
                // "orderable": false,
                data: 'firstName',
                title: '名',
                "orderable": false,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }, //排序按钮
            {
                // "orderable": false,
                data: 'eMail',
                title: '邮件 ',
                "orderable": false,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }, //排序按钮
            /*  { data: 'passwd' , title: '密码'},*/


            {
                data: 'phoneNumber',
                title: '手机号',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            },
            {
                data: 'dateOfBirth',
                title: '生日',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return dateFormatYMD(data);
                }
            }
            ,
            {
                data: 'nationality',
                title: '地区',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }

                    switch (data) {
                        case "0":
                            return "United State";
                            break;
                        case "1":
                            return "North America";
                            break;
                        case "2":
                            return "British";
                            break;
                        case "3":
                            return "Europe";
                            break;
                        case "4":
                            return "Australia";
                            break;
                        case "5":
                            return "New Zealand";
                            break;
                        case "6":
                            return "Italy";
                            break;
                        case "7":
                            return "Ukraine";
                            break;
                        case "8":
                            return "Russia";
                            break;
                        case "9":
                            return "Sorth Africa";
                            break;
                        case "10":
                            return "Denmark";
                            break;
                        case "11":
                            return "Switzerland";
                            break;
                        case "12":
                            return "Germany";
                            break;
                        default:
                            return "数据异常"

                    }


                }
            }
            ,
            {
                data: 'teachingEnglish',
                title: '教英语时长',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }

                    switch (data) {
                        case "01":
                            return "Lese than 1 year";
                            break;
                        case "12":
                            return "1-2 years";
                            break;
                        case "23":
                            return "2-3 years";
                            break;
                        case "3":
                            return "More than 3 years";
                            break;
                        default:
                            return "数据异常"

                    }


                    return data;
                }
            }
            ,
            {
                data: 'onlineTeaching',
                title: '在线老师时长',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    switch (data) {
                        case "01":
                            return "Lese than 1 year";
                            break;
                        case "12":
                            return "1-2 years";
                            break;
                        case "23":
                            return "2-3 years";
                            break;
                        case "3":
                            return "More than 3 years";
                            break;
                        default:
                            return "数据异常"

                    }

                }
            }


            ,
            {
                data: 'teachingJob',
                title: '期望工作类型',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    switch (data) {
                        case "0":
                            return "Full Time";
                            break;
                        case "1":
                            return "Part Time";
                            break;
                        default:
                            return "数据异常"

                    }
                }
            },

            {
                data: 'participateYour',
                title: '工作投入时长',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    switch (data) {
                        case "1":
                            return "10 hours/week";
                            break;
                        case "2":
                            return "20 hours/week";
                            break;
                        case "3":
                            return "30 hours/week";
                            break;
                        case "4":
                            return "40 hours/week";
                            break;
                        default:
                            return "数据异常"

                    }
                }
            }

            ,
            {
                data: 'diploma',
                title: '教学文凭',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    switch (data) {
                        case "0":
                            return "High School Diploma";
                            break;
                        case "1":
                            return "Bachelor";
                            break;
                        case "2":
                            return "Master";
                            break;
                        case "3":
                            return "PhD";
                            break;
                        case "4":
                            return "Other";
                            break;
                        default:
                            return "数据异常"

                    }
                }
            },
            {
                data: 'educationName',
                title: '学校名称',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }


            ,
            {
                data: 'diplomaReceived',
                title: '是否获得凭证',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    switch (data) {
                        case "1":
                            return "YES";
                            break;
                        case "0":
                            return "NO";
                            break;
                        default:
                            return "数据异常"

                    }
                }
            }
            ,
            {
                data: 'teachingCertification',
                title: '英语教学认证',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    switch (data) {
                        case "1":
                            return "TESL";
                            break;
                        case "2":
                            return "TEFL";
                            break;
                        case "3":
                            return "CELTA";
                            break;
                        case "4":
                            return "TESOL";
                            break;
                        case "5":
                            return "DELTA";
                            break;
                        case "6":
                            return "Masters";
                            break;
                        default:
                            return "数据异常"

                    }
                }
            }
            ,
            {
                data: 'createdtime',
                title: '提交时间',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return dateFormat(data);
                }
            }

            ,
            {
                data: 'p_yourself',
                title: '照片',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return "<a ><span onclick='picture(" + data.fileId + ")' style='font-size: 26px;' class='glyphicon glyphicon-search'></span></a>";
                }
            },
            {
                data: 'personal_video',
                title: '视频',
                "orderable": true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return "<a ><span onclick='videopalye(" + data.fileId + ")'style='font-size: 26px;' class='glyphicon glyphicon-search'></span></a >";
                }
            }
            ,
            {
                "orderable": true,
                data: 'step',
                title: '审核步骤',
                render: function (data, type, row) {
                    switch (data) {//0.表单填写，1表单审核,2选择课程审核,3面试,4签订合同,完成
                        case 0:
                            return "信息填写";
                            break;
                        case 1:
                            return "信息审核";
                            break;
                        case 2:
                            return "课表审核";
                            break;
                        case 3:
                            return "视频面试";
                            break;
                        case 4:
                            return "签订合同";
                            break;

                    }

                }
            }
            ,

            {
                "orderable": true,
                data: 'stepState',
                title: '审核状态',
                render: function (data, type, row) {
                    switch (data) {
                        case 0:
                            return '<button class=" btn btn-warning btn-sm" onclick="updatejob(' + row.jobwantedId + ', \'' + row.step + '\' ,\'' + row.stepState + '\')">待审核</button>';
                            break;
                        case 1:
                            return '<button class=" btn btn-success btn-sm"  onclick="updatejob(' + row.jobwantedId + ', \'' + row.step + '\' ,\'' + row.stepState + '\')">通过</button>';

                            break;
                        case 2:
                            return '<button class=" btn btn-danger btn-sm" onclick="updatejob(' + row.jobwantedId + ', \'' + row.step + '\' ,\'' + row.stepState + '\')">未通过</button>';

                            break;

                    }

                }
            }


            , {
                sWidth: "4%",//设置宽度
                data: 'jobwantedId',
                title: '操作',
                "orderable": false, // 禁用排序
                render: function (data, type, row) {
                    return '<button class=" btn btn-success btn-sm" onclick="onreload(' + data + ',' + row.nationality + ')">课表</button>'
                }
            }
        ]
    });
})

function updatejob(jobid, step, stepState) {
    /*1填写表单,2选择课程,3面试,4签订合同*/
    debugger;
    switch (step) {
        case "1":
            $("#step").html("信息审核");
            break;
        case "2":
            $("#step").html("课表审核");
            break;
        case "3":
            $("#step").html("视频面试审核");
            break;
        case "4":
            $("#step").html("签订合同");
            break;
    }


    jobsub.jobwantedId = jobid;
    jobsub.step = step;
    jobsub.stepState = stepState;
    $("#updatejob").modal('show');
}

var jobsub = new Vue({
    el: "#updatejob_form",
    data: {
        stepState: '',
        step: '',
        jobwantedId: ''

    }
})

function updatejobsub() {
    debugger;
    $.ajax({
        "url": "/job/updatejob",
        "type": "post",
        "data": {
            "jobwantedId": jobsub.jobwantedId,
            "step": jobsub.step,
            "stepState": jobsub.stepState
        },
        // "dataType": "JSON",
        "success": function (result) {
            if (result == "success") {
                $('#updatejob').modal('hide');
                message({
                    message: '修改成功!',
                    type: 'success'
                });
                dataTable.draw(false);
            } else {
                message({
                    message: '服务器异常,请稍后再试!',
                    type: 'error'
                });
                console.log(result.toString());
            }

        },
        error: function () {
            message({
                message: '服务器异常,请重试!',
                type: 'error'
            });
            console.log(e.status);
            console.log(e.responseText);
        }
    })


}


function showTeacher(jobwantedId, statetype) {
    $.ajax({
        "url": "/job/updateShowTeacher",
        "type": "post",
        "data": {
            "jobwantedId": jobwantedId,
            "showTeacher": statetype,
        },
        "success": function (result) {
            debugger;
            if (result == "success") {
                message({
                    message: '修改成功!',
                    type: 'success'
                });
                console.log(result.toString());
                dataTable.draw(false);
            } else {
                message({
                    message: '服务器异常,请稍后再试!',
                    type: 'error'
                });
                console.log(result.toString());
            }

        },
        error: function () {
            message({
                message: '服务器异常,请重试!',
                type: 'error'
            });

        }
    })


}

function videopalye(id) {//播放视频
    $("#videomodal").modal('show');
    $("#videopayle").html("<video width=\"100%\"  controls>\n" +
        "  <source src=\"/file/video?fileid=" + id + "\"  type=\"video/mp4\">\n" +
        "  您的浏览器不支持 HTML5 video 标签。\n" +
        "</video>")

}

function picture(id) {//打开图片
    $("#videomodal").modal('show');
    $("#videopayle").html("<img src=\"/file/picture?fileid=" + id + "\" width=\"100%;\">")
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

var courseTypeGeorgia = [
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
var courseTypeCanada = [
    ['<span class="canada">13:35-14:05</span>&nbsp;<span class="canadawintertime">00:35-01:05</span>', 1],
    ['<span class="canada">14:10-14:40</span>&nbsp;<span class="canadawintertime">01:10-01:40</span>', 1],
    ['<span class="canada">14:45-15:15</span>&nbsp;<span class="canadawintertime">01:45-02:15</span>', 1],
    ['<span class="canada">15:20-15:50</span>&nbsp;<span class="canadawintertime">02:20-02:50</span>', 1],
    ['<span class="canada">15:55-16:25</span>&nbsp;<span class="canadawintertime">02:55-03:25</span>', 1],
    ['<span class="canada">16:30-17:00</span>&nbsp;<span class="canadawintertime">03:30-04:00</span>', 1],
    ['<span class="canada">17:05-17:35</span>&nbsp;<span class="canadawintertime">04:05-04:35</span>', 1],
    ['<span class="canada">17:40-18:10</span>&nbsp;<span class="canadawintertime">04:40-05:10</span>', 1],
    ['<span class="canada">19:20-19:50</span>&nbsp;<span class="canadawintertime">06:20-06:50</span>', 1],
    ['<span class="canada">19:55-20:25</span>&nbsp;<span class="canadawintertime">06:55-07:25</span>', 1],
    ['<span class="canada">20:30-21:00</span>&nbsp;<span class="canadawintertime">07:30-08:00</span>', 1],
    ['<span class="canada">21:05-21:35</span>&nbsp;<span class="canadawintertime">08:05-08:35</span>', 1],
    ['<span class="canada">08:30-09:00</span>&nbsp;<span class="canadawintertime">19:30-20:00</span>', 1],
    ['<span class="canada">09:05-09:35</span>&nbsp;<span class="canadawintertime">20:05-20:35</span>', 1],
    ['<span class="canada">09:40-10:10</span>&nbsp;<span class="canadawintertime">20:40-21:10</span>', 1],
    ['<span class="canada">10:15-10:45</span>&nbsp;<span class="canadawintertime">21:15-21:45</span>', 1],
    ['<span class="canada">10:50-11:20</span>&nbsp;<span class="canadawintertime">21:50-22:20</span>', 1]
];
