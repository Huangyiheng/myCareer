/*存储当前父id*/
var pid = 0;

function pointapifc(pointid) {
    $("#pointapimodal").modal('show');
    pid = pointid;
    jobsub.type = 3;
    $("#addtype").attr('disabled', true);
setTimeout(function () {
    pointapitable.ajax.reload();
},500)


}

function deltemenu() {
    if (pid == 0) {
        message({
            message: '请先选择要删除的菜单',
            type: 'warning'
        });
    } else {


        $.ajax({
            "url": "/sys/delte_menu",
            "type": "post",
            "data": {
                "delteid": pid
            },
            "dataType": "",
            "success": function (result) {

                if (result == "success") {
                    message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    loadmenu();
                } else if (result == "false") {
                    message({
                        message: '请先删除该菜单下的子功能和api',
                        type: 'error'
                    });
                    console.log(result.toString());

                } else {
                    message({
                        message: '服务器异常,请稍后再试!',
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
    pid = 0;
}

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
            "url": "/sys/delte_point_api",
            "type": "post",
            "data": {
                "IDArray": params
            },
            "dataType": "",
            "success": function (result) {
                bt.enable();
                if (result == "success") {
                    buttonTablesearch();
                    message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    loadmenu();
                } else if (result == "false") {
                    message({
                        message: '请先删除该菜单下的子功能和api',
                        type: 'error'
                    });
                    console.log(result.toString());

                } else {
                    message({
                        message: '服务器异常,请稍后再试!',
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
    pid = 0;

}


function updatemenu(data_id) {
    var dete_id;
    if (data_id == "" || data_id == null) {
        if (pid == 0) {
            message({
                message: '请先选择要操作的菜单',
                type: 'warning'
            });
        } else {

            dete_id = pid;
        }
    } else {
        dete_id = data_id;
    }

    if (dete_id == 0 || dete_id == "") {
        message({
            message: '请先选择要操作的菜单',
            type: 'warning'
        });
    } else {


        $.ajax({
            "url": "/sys/get_permission",
            "type": "post",
            "data": {
                "delteid": dete_id
            },
            "dataType": "JSON",
            "success": function (result) {
                debugger;
                if (result !== "") {
                    pid = result.pid;
                    jobsub.id = result.id;
                    jobsub.name = result.name;
                    jobsub.type = result.type;
                    jobsub.described = result.described;
                    jobsub.pointStatus = result.pointStatus;
                    jobsub.pointIcon = result.pointIcon;
                    jobsub.apiUrl = result.apiUrl;
                    jobsub.apiMethod = result.apiMethod;
                    jobsub.menuIcon = result.menuIcon;
                    jobsub.menuOrder = result.menuOrder;
                    jobsub.apiUrl = result.apiUrl;

                    $('#addmokuai').modal('show');

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
    pid = 0;

}


function buttonTablesearch() {
    buttonTable.draw(false);//从新加载表格
    apiTable.draw(false);

}

var buttonTable;
$(function () { //DataTable必须写在匿名函数里面,否则会报错


    var counti = 0;
    buttonTable = $('#buttonTable').DataTable({
        "serverSide": true, //服务器处理分页
        "pageLength": 15,
        "select": false,
        "searching": true, //是否显示本地搜索框,默认为true
        "ordering": true, //是否显示排序,默认为TRUE
        "order": [0, 'asc'], //默认的列排序,从0开始
        "aLengthMenu": [[15, 30, 50, 1000000000], [15, 30, 50, "全部"]], //每页数量[[10, 25, 60, -1], [10, 25, 50, "全部"]]
        "dom": "<'row'<'col-sm-2'l><'col-sm-8'><'col-sm-2'B>>t<'row'<'col-sm-6'i><'col-sm-6'p>>", //自定义按钮,分页等的位置http://www.datatables.club/manual/daily/2016/05/11/option-dom.html
        "bStateSave": true, //默认为false,是否开启状态保存，当选项开启的时候会使用一个cookie保存表格展示的信息的状态，例如分页信息，展示长度，过滤和排序等,这样当终端用户重新加载这个页面的时候可以使用以前的设置
        "bSortClasses": true, //默认为true,是否在当前被排序的列上额外添加sorting_1,sorting_2,sorting_3三个class，当该列被排序的时候，可以切换其背景颜色,该选项作为一个来回切换的属性会增加执行时间（当class被移除和添加的时候）,当对大数据集进行排序的时候你或许希望关闭该选项
        "sProcessing": "处理中...", //默认值为Processing...,当表格处理用户动作（例如排序或者类似行为）的时候显示的字符串
        scrollY: 710, //表格滚动,设置表格固定高度
        scrollCollapse: true,//当显示更少的记录时，是否允许表格减少高度
        bSort: true,// 默认为true 是否开启列排序，对单独列的设置在每一列的bSortable选项中指定

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
            "url": "/sys/getpointorapi",
            "type": "POST",
            "data": function (d) {
                var pageNum = buttonTable ? buttonTable.page() + 1 : 1; //加入了分页插件pagehellpr,需要传回当前页数
                d.pageNum = pageNum;
                if (d.order && d.order.length > 0) { //传入需要排序的列
                    d.orderStr = d.columns[d.order[0].column].data;
                    d.orderDir = d.order[0].dir;
                }

                var formMap = $("#testfrom").serializeObject();
                if (formMap.buttonname != "")
                    d.buttonname = formMap.buttonname;
                d.pid = pid;
                d.type = 2;
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
            }

            ,
            {
                text: '删除', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-danger ',
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
                data: 'name',
                title: '名称',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;

                }
            },
            {
                // "orderable": false,
                data: 'code',
                title: '编码',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                data: 'described',
                title: '描述',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }/*,
            {
                // "orderable": false,
                data: 'enVisible',
                title: '企业是否可见',
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }*/
            /* ,{
                 // "orderable": false,
                 data: 'pointIcon',
                 title: '图标',
                 render: function (data, type, row) {
                     if (data == null || data == "") {
                         return "空";
                     }
                 }
             }*/
            /* ,
             {
                 // "orderable": false,
                 data: 'pointStatus',
                 title: '状态',
                 render: function (data, type, row) {
                     if (data == null || data == "") {
                         return "空";
                     }
                 }
             }*/
            , {

                data: 'pid',
                title: 'pid',
                "asSorting": ["desc", "asc"],
                visible: true,
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }

            , {
                sWidth: "8%",//设置宽度
                data: 'id',
                title: '操作',
                "orderable": false, // 禁用排序
                render: function (data, type, row) {

                    return '<button class=" btn btn-success btn-sm" onclick="pointapifc(' + data + ')">Api</button>&nbsp;<button class=" btn btn-warning btn-sm" onclick="updatemenu(' + data + ')">修改</button>';
                }
            }
        ]
    });
})


/*apidatatbles*/

var apiTable;
$(function () { //DataTable必须写在匿名函数里面,否则会报错
    var counti = 0;
    apiTable = $('#apiTable').DataTable({
        "serverSide": true, //服务器处理分页
        "pageLength": 15,
        "select": false,
        "searching": true, //是否显示本地搜索框,默认为true
        "ordering": true, //是否显示排序,默认为TRUE
        "order": [3, 'asc'], //默认的列排序,从0开始
        "aLengthMenu": [[15, 30, 50, 1000000000], [15, 30, 50, "全部"]], //每页数量[[10, 25, 60, -1], [10, 25, 50, "全部"]]
        "dom": "<'row'<'col-sm-2'l><'col-sm-8'><'col-sm-2'B>>t<'row'<'col-sm-6'i><'col-sm-6'p>>", //自定义按钮,分页等的位置http://www.datatables.club/manual/daily/2016/05/11/option-dom.html
        "bStateSave": true, //默认为false,是否开启状态保存，当选项开启的时候会使用一个cookie保存表格展示的信息的状态，例如分页信息，展示长度，过滤和排序等,这样当终端用户重新加载这个页面的时候可以使用以前的设置
        "bSortClasses": true, //默认为true,是否在当前被排序的列上额外添加sorting_1,sorting_2,sorting_3三个class，当该列被排序的时候，可以切换其背景颜色,该选项作为一个来回切换的属性会增加执行时间（当class被移除和添加的时候）,当对大数据集进行排序的时候你或许希望关闭该选项
        "sProcessing": "处理中...", //默认值为Processing...,当表格处理用户动作（例如排序或者类似行为）的时候显示的字符串
        scrollY: 710, //表格滚动,设置表格固定高度
        scrollCollapse: false,//当显示更少的记录时，是否允许表格减少高度
        bSort: true,// 默认为true 是否开启列排序，对单独列的设置在每一列的bSortable选项中指定

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
            "sEmptyTable": "表中数据为空",
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
            "url": "/sys/getpointorapi",
            "type": "POST",
            "data": function (d) {
                var pageNum = apiTable ? apiTable.page() + 1 : 1; //加入了分页插件pagehellpr,需要传回当前页数
                d.pageNum = pageNum;
                if (d.order && d.order.length > 0) { //传入需要排序的列
                    d.orderStr = d.columns[d.order[0].column].data;
                    d.orderDir = d.order[0].dir;
                }

                var formMap = $("#testfrom").serializeObject();
                if (formMap.buttonname != "")
                    d.buttonname = formMap.buttonname;
                d.pid = pid;
                d.type = 3;
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
            }
            ,
            {
                text: '添加', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-primary ',
                action: function (e, dt, node, config) { //参考:https://datatables.net/reference/option/buttons.buttons.action
                    $("#addtype").attr('disabled', false);
                    addtypepid();
                }
            }
            ,
            {
                text: '删除', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-danger ',
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
                data: 'name',
                title: '名称',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;

                }
            },
            {
                // "orderable": false,
                data: 'code',
                title: '编码',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                data: 'described',
                title: '描述',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }

            , {
                // "orderable": false,
                data: 'pointIcon',
                title: '图标',
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                }
            }
            ,
            {
                "orderable": false,
                data: 'apiMethod',
                title: '提交方式',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    } else {
                        if (data == 1) {
                            return "POST";
                        } else {
                            return "GET"
                        }
                    }

                }
            }
            ,
            {
                "orderable": false,
                data: 'apiUrl',
                title: '路径',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }
            ,
            {
                "orderable": false,
                data: 'apiLevel',
                title: '是否验证',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    } else {
                        if (data == 1) {
                            return "无需验证";
                        } else {
                            return "需要验证"
                        }
                    }
                }
            }
            , {
                sWidth: "5%",//设置宽度
                data: 'id',
                title: '操作',
                "orderable": false, // 禁用排序
                render: function (data, type, row) {

                    return '<button class=" btn btn-warning btn-sm" onclick="updatemenu(' + data + ')">修改</button>';
                }
            }
        ]
    });
})


var pointapitable;

$(function () { //DataTable必须写在匿名函数里面,否则会报错
    var counti = 0;
    pointapitable = $('#pointapitable').DataTable({
        "serverSide": true, //服务器处理分页
        "pageLength": 15,
        "select": false,
        "searching": true, //是否显示本地搜索框,默认为true
        "ordering": true, //是否显示排序,默认为TRUE
        "order": [3, 'asc'], //默认的列排序,从0开始
        "aLengthMenu": [[15, 30, 50, 1000000000], [15, 30, 50, "全部"]], //每页数量[[10, 25, 60, -1], [10, 25, 50, "全部"]]
        "dom": "<'row'<'col-sm-2'l><'col-sm-8'><'col-sm-2'B>>t<'row'<'col-sm-6'i><'col-sm-6'p>>", //自定义按钮,分页等的位置http://www.datatables.club/manual/daily/2016/05/11/option-dom.html
        "bStateSave": true, //默认为false,是否开启状态保存，当选项开启的时候会使用一个cookie保存表格展示的信息的状态，例如分页信息，展示长度，过滤和排序等,这样当终端用户重新加载这个页面的时候可以使用以前的设置
        "bSortClasses": true, //默认为true,是否在当前被排序的列上额外添加sorting_1,sorting_2,sorting_3三个class，当该列被排序的时候，可以切换其背景颜色,该选项作为一个来回切换的属性会增加执行时间（当class被移除和添加的时候）,当对大数据集进行排序的时候你或许希望关闭该选项
        "sProcessing": "处理中...", //默认值为Processing...,当表格处理用户动作（例如排序或者类似行为）的时候显示的字符串
        scrollY: 500, //表格滚动,设置表格固定高度
        scrollCollapse: false,//当显示更少的记录时，是否允许表格减少高度
        bSort: true,// 默认为true 是否开启列排序，对单独列的设置在每一列的bSortable选项中指定

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
            "sEmptyTable": "表中数据为空",
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
            "url": "/sys/getpointorapi",
            "type": "POST",
            "data": function (d) {
                var pageNum = apiTable ? apiTable.page() + 1 : 1; //加入了分页插件pagehellpr,需要传回当前页数
                d.pageNum = pageNum;
                if (d.order && d.order.length > 0) { //传入需要排序的列
                    d.orderStr = d.columns[d.order[0].column].data;
                    d.orderDir = d.order[0].dir;
                }

                var formMap = $("#testfrom").serializeObject();
                if (formMap.buttonname != "")
                    d.buttonname = formMap.buttonname;
                d.pid = pid;
                d.type = 3;
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
            }
            ,
            {
                text: '添加', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-primary ',
                action: function (e, dt, node, config) { //参考:https://datatables.net/reference/option/buttons.buttons.action
                    addtypepid();
                }
            }
            ,
            {
                text: '删除', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-danger ',
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
                data: 'name',
                title: '名称',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;

                }
            },
            {
                // "orderable": false,
                data: 'code',
                title: '编码',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                data: 'described',
                title: '描述',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            },


            {
                "orderable": false,
                data: 'apiMethod',
                title: '提交方式',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    } else {
                        if (data == 1) {
                            return "POST";
                        } else {
                            return "GET"
                        }
                    }

                }
            }
            ,
            {
                "orderable": false,
                data: 'apiUrl',
                title: '路径',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }
            ,
            {
                "orderable": false,
                data: 'apiLevel',
                title: '是否验证',
                "asSorting": ["desc", "asc"],
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    } else {
                        if (data == 1) {
                            return "无需验证";
                        } else {
                            return "需要验证"
                        }
                    }
                }
            }
            , {
                sWidth: "5%",//设置宽度
                data: 'id',
                title: '操作',
                "orderable": false, // 禁用排序
                render: function (data, type, row) {

                    return '<button class=" btn btn-warning btn-sm" onclick="updatemenu(' + data + ')">修改</button>';
                }
            }
        ]
    });
})


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


/*模块管理添加*/
var flag = false;


function data_id(d) {
    pid = d;
    buttonTablesearch();
}
function addtypepiddis(){
    $("#addtype").attr('disabled', false);
    addtypepid();
}
function addtypepid() {
    $('#addmokuai').modal('show')
}

var jobsub = new Vue({
    el: "#permission_add_form",
    data: {
        id: '',
        name: '',
        type: '',
        described: '',
        pointStatus: '',
        pointIcon: '',
        apiUrl: '',
        apiMethod: '',
        apiLevel: '',
        menuIcon: '',
        menuOrder: ''
    },
    watch: {
        type(newVal, oldVal) {
            //可以发送ajax请求
            //1为菜单 2为功能按钮 3为API接口路径-->
            if (newVal == 1) {
                $("#menu_isshow").show();
                $("#api_idshow").hide();
                $("#point_isshow").hide();
            }
            if (newVal == 2) {
                $("#api_idshow").hide();
                $("#menu_isshow").hide();
                $("#point_isshow").show();

            }
            if (newVal == 3) {
                $("#point_isshow").hide();
                $("#menu_isshow").hide();
                $("#api_idshow").show();
            }
        }
    }
})

$("#permission_add_sb").click(function () {
    $('#permission_add_form').data('bootstrapValidator').validate();//启用验证
    flag = $('#permission_add_form').data('bootstrapValidator').isValid();
    if (flag == true) {
        $('.alert-warning').html('').hide();
        $('.alert-success').html('').hide();
        $('.alert-info').html('提交中请稍后...').show();
        $("#permission_add_sb").attr("disabled", "true");


        var formData = new FormData();
        formData.append("id", jobsub.id);
        formData.append("name", jobsub.name);
        formData.append("type", jobsub.type);
        formData.append("described", jobsub.described);
        formData.append("pid", pid);
        formData.append("pointStatus", jobsub.pointStatus);
        formData.append("pointIcon", jobsub.pointIcon);
        formData.append("apiUrl", jobsub.apiUrl);
        formData.append("apiMethod", jobsub.apiMethod);
        formData.append("apiLevel", jobsub.apiLevel);
        formData.append("menuIcon", jobsub.menuIcon);
        formData.append("menuOrder", jobsub.menuOrder);


        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            // contentType : 'application/x-www-form-urlencoded;charset=utf-8',
            //  dataType: "json",//预期服务器返回的数据类型
            url: "/sys/permissionadd",//url
            cache: false,//cache设置为false，上传文件不需要缓存。
            processData: false,//取消默认的序列化,因为formaata已经是序列化的数据
            contentType: false,//contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
            data: formData,

            success: function (result) {
                fanhui = result;
                if (fanhui == "sucees") {
                    $('.alert-warning').html('').hide();
                    $('.alert-info').html('').hide();
                    $('.alert-success').html('').hide();
                    $('#addmokuai').modal('hide');
                    $("#pointapimodal").modal('hide');
                    message("提交成功");

                    $("#permission_add_sb").removeAttr("disabled");
                    pid = 0;
                    loadmenu();
                } else if (fanhui == "error") {
                    $('.alert-warning').html('').hide();
                    $('.alert-info').html('').hide();
                    message("服务器异常");
                    $("#permission_add_sb").removeAttr("disabled");
                    pid = 0;
                } else {
                    $('.alert-warning').html('').hide();
                    $('.alert-info').html('').hide();
                    message("服务器异常");
                    $("#permission_add_sb").removeAttr("disabled");
                    pid = 0;
                }
            },
            error: function () {
                $('.alert-warning').html('').hide();
                $('.alert-info').html('').hide();
                message("服务器异常");
                $("#permission_add_sb").removeAttr("disabled");
                console.log(reason);
                pid = 0;
            }
        })

    } else {
        $('.alert-info').html('').hide();
        $('.alert-success').html('').hide();
        $('.alert-warning').html('请修改后再提交').show();
    }
    pid = 0;
})
/*< !--当个别控件判断失效时可以试着把他的位置换到上面来-- >*/

$('#permission_add_form').bootstrapValidator(
    {
        message: '无效的值',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '名称不能为空'
                    }
                }
            },
            described: {
                validators: {
                    min: 3,
                    max: 30,
                    notEmpty: {
                        message: '描述不能为空'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: '类型不能为空'
                    }
                }
            }

        }
    }).on('success.form.bv', function (e) {
});

function loadmenu() {


    layui.use('element', function () {
        var $ = layui.jquery, element = layui.element;


        //获取所有的菜单
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            // contentType : 'application/x-www-form-urlencoded;charset=utf-8',
            dataType: "json",//预期服务器返回的数据类型
            url: "/sys/getTreeJson",//url
            cache: false,//cache设置为false，上传文件不需要缓存。
            processData: false,//取消默认的序列化,因为formaata已经是序列化的数据
            contentType: false,//contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
            data: "",

            success: function (data) {
                fanhui = data;
                if (fanhui != "error") {
                    var html;
                    var li = "";
                    for (var i = 0; i < data.length; i++) {

                        data[i].menuIcon = " glyphicon glyphicon-th";
                        /*如果没有子节点*/
                        if (data[i].nodes == null) {
                            li += "  <li class=\"layui-nav-item\"><a onclick=\"data_id('" + data[i].id + "')\"> <span class='" + data[i].menuIcon + "'></span>&nbsp;&nbsp;" + data[i].text + "</a></li>"

                        } else {
                            var lis = "  <li class=\"layui-nav-item\"><a  onclick=\"data_id('" + data[i].id + "')\" ><span class='" + data[i].menuIcon + "'></span>&nbsp;&nbsp;" + data[i].text + "</a>"
                            var dl = "   <dl class=\"layui-nav-child\">";
                            var nodes = data[i].nodes;
                            for (var x = 0; x < nodes.length; x++) {
                                dl += "  <dd><a onclick=\"data_id('" + nodes[x].id + "')\"> <span class='" + data[i].menuIcon + "'></span>&nbsp;&nbsp;" + nodes[x].text + "</a></dd>";
                            }
                            dl += "</dl>";

                            lis += dl;
                            lis += "</li>";
                            li += lis;

                        }

                    }
                    $("#module_manage").html(li);
                    element.init();

                } else {
                    $.message({
                        message: '服务器异常,权限列表获取失败',
                        type: 'error'
                    });

                }
            },
            error: function () {


            }
        })


    });
}

/*页面加载完成后执行*/
$(document).ready(function () {
    loadmenu();

});