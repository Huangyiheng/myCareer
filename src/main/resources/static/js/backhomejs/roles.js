/*存储当前父id*/
function roleaddmodal(id) {
    jobsub.id = id
    /*返回树形节点*/
    treereload(id);
    $('#roleaddmodal').modal('show');
    if (id != null) {
        $.ajax({
            "url": "/sys/getrolebyid",
            "type": "post",
            "data": {
                "id": id
            },
            "dataType": "JSON",
            "success": function (result) {
                if (result !== "") {
                    jobsub.id = result.id;
                    jobsub.name = result.name;
                    jobsub.description = result.description;


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


function treereload(id) {
    if (id == null) {
        id = 0;
    }
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        // contentType : 'application/x-www-form-urlencoded;charset=utf-8',
        dataType: "json",//预期服务器返回的数据类型
        url: "/sys/roletreedata?roleid=" + id,//url
        cache: false,//cache设置为false，上传文件不需要缓存。
        processData: false,//取消默认的序列化,因为formaata已经是序列化的数据
        contentType: false,//contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
        // data:   { "roleid": id },
        success: function (result) {
            tree.reload('permstreeid', {
                data: result//新的参数
            });


        },
        error: function () {
            message("服务器异常,获取当前角色权限失败");


        }
    })
}


var jobsub = new Vue({
    el: "#role_add_form",
    data: {
        id: '',
        name: '',
        description: '',
    }
})

/*返回选中id*/
function getChildNodes(treeNode, result) {
    for (var i in treeNode) {
        result.push(treeNode[i].id);
        result = getChildNodes(treeNode[i].children, result);
    }
    return result;
}

$("#role_add_sb").click(function () {
    $('#roleaddmodal').data('bootstrapValidator').validate();//启用验证
    flag = $('#roleaddmodal').data('bootstrapValidator').isValid();
    if (flag == true) {
        $('.alert-warning').html('').hide();
        $('.alert-success').html('').hide();
        $('.alert-info').html('提交中请稍后...').show();
        $("#role_add_sb").attr("disabled", "true");
        debugger;
        /*获取权限节点数据*/
        var checkData = tree.getChecked('permstreeid');
        var checkid = getChildNodes(checkData, []);
        var formData = new FormData();
        if (jobsub.id != null) {

            formData.append("id", jobsub.id);
        }
        formData.append("name", jobsub.name);
        formData.append("description", jobsub.description);
        formData.append("checkid", checkid);


        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            // contentType : 'application/x-www-form-urlencoded;charset=utf-8',
            //  dataType: "json",//预期服务器返回的数据类型
            url: "/sys/rolesadd",//url
            cache: false,//cache设置为false，上传文件不需要缓存。
            processData: false,//取消默认的序列化,因为formaata已经是序列化的数据
            contentType: false,//contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
            data: formData,

            success: function (result) {
                fanhui = result;
                if (fanhui != "error") {
                    $('.alert-warning').html('').hide();
                    $('.alert-info').html('').hide();
                    $('.alert-success').html('').hide();
                    $('#roleaddmodal').modal('hide');
                    message("提交成功");
                    $("#role_add_sb").removeAttr("disabled");
                    //刷新表格
                    dataTable.draw();

                } else if (fanhui == "error") {
                    $('.alert-warning').html('').hide();
                    $('.alert-info').html('').hide();
                    message("服务器异常");
                    $("#role_add_sb").removeAttr("disabled");

                } else {
                    $('.alert-warning').html('').hide();
                    $('.alert-info').html('').hide();
                    message("服务器异常");
                    $("#role_add_sb").removeAttr("disabled");

                }
            },
            error: function () {
                $('.alert-warning').html('').hide();
                $('.alert-info').html('').hide();
                message("服务器异常");
                $("#role_add_sb").removeAttr("disabled");

            }
        })

    } else {
        $('.alert-info').html('').hide();
        $('.alert-success').html('').hide();
        $('.alert-warning').html('请修改后再提交').show();
    }

})
function search() {
    dataTable.ajax.reload(); //从新加载表单
}

var dataTable;
$(function () { //DataTable必须写在匿名函数里面,否则会报错


    var counti = 0;
    dataTable = $('#RoleTable').DataTable({
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
            "url": "/sys/getroledata",
            "type": "POST",
            "data": function (d) {
                var pageNum = dataTable ? dataTable.page() + 1 : 1; //加入了分页插件pagehellpr,需要传回当前页数
                d.pageNum = pageNum;
                if (d.order && d.order.length > 0) { //传入需要排序的列
                    d.orderStr = d.columns[d.order[0].column].data;
                    d.orderDir = d.order[0].dir;
                }

                var formMap = $("#testfrom").serializeObject();
                if (formMap.name != "")
                    d.name = formMap.name;

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
                    roleaddmodal();

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
                "orderable": false,
                data: 'description',
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

                data: '公司id',
                title: 'companyId',
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
                sWidth: "5%",//设置宽度
                data: 'id',
                title: '操作',
                "orderable": false, // 禁用排序
                render: function (data, type, row) {


                    return '<button class=" btn btn-warning btn-sm" onclick="roleaddmodal(' + data + ')">修改</button>';
                }
            }
        ]
    });
})


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
            "url": "/sys/role_delete",
            "type": "post",
            "data": {
                "IDArray": params
            },
            "dataType": "",
            "success": function (result) {
                bt.enable();
                if (result == "success") {
                    message({
                        message: '删除成功!',
                        type: 'success'
                    });
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
/*页面加载完成后执行*/
$(document).ready(function () {


});

$('#roleaddmodal').bootstrapValidator(
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
            description: {
                validators: {
                    min: 3,
                    max: 30,
                    notEmpty: {
                        message: '描述不能为空'
                    }
                }
            }


        }
    }).on('success.form.bv', function (e) {
});

var tree;
layui.use(['tree', 'util'], function () {


    tree = layui.tree
        , layer = layui.layer
        , util = layui.util
        //模拟数据
        , data = [{
        title: '一级1'
        , id: 1
        , field: 'name1'//节点字段名对应实体类
        , checked: true//是否选中
        , spread: false//是否展开
        , href: ""//点击的连接
        , disabled: true//是否为禁用状态
    }];

    //基本演示
    tree.render({
        elem: '#permstree'
        , data: ""
        //, edit: ['add', 'update', 'del']//显示修改删除图标
        , accordion: false//是否开启手风琴模式，默认 false
        , onlyIconControl: false//是否仅允许节点左侧图标控制展开收缩。默认 false（即点击节点本身也可控制）。若为 true，则只能通过节点左侧图标来展开收缩
        , isJump: false//是否允许点击节点时弹出新窗口跳转。默认 false，若开启，需在节点数据中设定 link 参数（值为 url 格式）
        , showLine: false//	是否开启连接线。默认 true，若设为 false，则节点左侧出现三角图
        , text: {//自定义各类默认文本，目前支持以下设定：
            defaultNodeName: '未命名' //节点默认名称
            , none: '无数据' //数据为空时的提示文本
        }
        , showCheckbox: true  //是否显示复选框
        , id: 'permstreeid'//索引,其他位置调用时需要指定的唯一id
        , isJump: true //是否允许点击节点时弹出新窗口跳转
        , click: function (obj) {//点击节点执行

            var data = obj.data;  //获取当前点击的节点数据
          //  layer.msg('状态：' + obj.state + '<br>节点数据：' + JSON.stringify(data));
        }
    });

    //按钮事件
    util.event('lay-demo', {
        getChecked: function (othis) {
            var checkedData = tree.getChecked('demoId1'); //获取选中节点的数据

            layer.alert(JSON.stringify(checkedData), {shade: 0});
            console.log(checkedData);
        }
        , setChecked: function () {
            tree.setChecked('demoId1', [12, 16]); //勾选指定节点
        }
        , reload: function () {
            //重载实例
            tree.reload('demoId1', {});

        }
    });


});
