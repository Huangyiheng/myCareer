function deletedata(e, dt, node, config) {
    /*var trList = dt.fnGetNodes();
    for (i = 0; i < trList.length; i++) {
        var trObj = trList[i];
        alert(trObj);
    }*/
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
        "order": [3, 'asc'], //默认的列排序,从0开始
        "aLengthMenu": [[15, 30, 50, 1000000000], [15, 30, 50, "全部"]], //每页数量[[10, 25, 60, -1], [10, 25, 50, "全部"]]
        //"colReorder": true, //扩展插件, 单击并拖动列重新排序
        //autoFill : true, //自动填充,需要后台代码支持
        //dom的使用参考:http://www.datatables.club/manual/daily/2016/05/11/option-dom.html
        //dom : 'lfBtip',//Bfrtip,B-按钮,l - Length changing 改变每页显示多少条数据的控件,f - Filtering input 即时搜索框控件,t - The Table 表格本身,	i - Information 表格相关信息控件,p - Pagination 分页控件,r - pRocessing 加载等待显示信息
        "dom": "<'row'<'col-sm-2'l><'col-sm-8'><'col-sm-2'B>>t<'row'<'col-sm-6'i><'col-sm-6'p>>", //自定义按钮,分页等的位置http://www.datatables.club/manual/daily/2016/05/11/option-dom.html
        //buttons: ['colvis', 'excel', 'print'],

        "bStateSave": true, //默认为false,是否开启状态保存，当选项开启的时候会使用一个cookie保存表格展示的信息的状态，例如分页信息，展示长度，过滤和排序等,这样当终端用户重新加载这个页面的时候可以使用以前的设置
        "bSortClasses": true, //默认为true,是否在当前被排序的列上额外添加sorting_1,sorting_2,sorting_3三个class，当该列被排序的时候，可以切换其背景颜色,该选项作为一个来回切换的属性会增加执行时间（当class被移除和添加的时候）,当对大数据集进行排序的时候你或许希望关闭该选项
        "sProcessing": "处理中...", //默认值为Processing...,当表格处理用户动作（例如排序或者类似行为）的时候显示的字符串
        scrollY : 710, //表格滚动,设置表格固定高度
        scrollCollapse: false,//当显示更少的记录时，是否允许表格减少高度
        bSort:true,// 默认为true 是否开启列排序，对单独列的设置在每一列的bSortable选项中指定

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
            "url": "/recrui_student_data",
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
                if (formMap.status != "" && formMap.status != "0")
                    d.relation = formMap.status;
                if (formMap.gmt_createstart != "")
                    d.createstart = new Date(formMap.gmt_createstart + " 00:00:00");
                if (formMap.gmt_createend != "")
                    d.createend = new Date(formMap.gmt_createend + " 23:59:59");

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
                    'columns': [1, 2, 3, 4,5,6] //要到处的列,默认为全部
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
                    'columns': [1, 2, 3, 4,5,6] //要到处的列,默认为全部
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
                    'columns': [1, 2, 3, 4,5,6] //要到处的列,默认为全部
                },
            },  {
                'extend': 'print',
                'text': '打印',
                'className': ' btn ', //按钮的clss名称可以通过类名增加样式
                'exportOptions': {
                    'modifier': {
                        'page': 'current'
                    },
                    'columns': [1, 2, 3, 4,5,6] //要到处的列,默认为全部
                },
            }/*,
            {
                text: '添加', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-primary ',
                action: function (e, dt, node, config) { //参考:https://datatables.net/reference/option/buttons.buttons.action
                    adddata();
                    this.disable(); // 禁用按钮
                }
            }
            ,
            {
                text: '删除', //自定义按钮,e触发事件的事件对象,dt主机DataTable的DataTables API实例,node单击的按钮节点的jQuery实例,config按钮的配置对象
                'className': ' btn btn-danger ',
                action: function (e, dt, node, config) { //参考:https://datatables.net/reference/option/buttons.buttons.action
                    deletedata(e, dt, node, config);
                    this.disable(); // 禁用按钮
                }
            }*/


        ],


        columns: [
            {
                sWidth:"2%",//设置宽度
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
            }  ,

             /* {
               data: 'id',
               title: 'id',
               "orderable": false, // 禁用排序
               "orderDataType": "any-number",
               "asSorting": ["desc", "asc"] //排序方式
           },*/
            {
               // "orderable": false,
                data: 'name',
                title: '学生名称',
                "asSorting": ["desc", "asc"]
            }, //排序按钮
          /*  { data: 'passwd' , title: '密码'},*/
            {
               // "orderable": false,
                data: 'createtime',
                title: '报名时间',
                render: function (data, type, row) {
                    return dateFormat(data);
                }
            },
            {
               // "orderable": false,
                data: 'age',
                title: '年龄'
            },


            {
               // "orderable": false,
                data: 'gender',
                title: '性别',
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                data: 'phone',
                title: '电话',
                render: function (data, type, row) {
                    if (data == null || data == "") {
                        return "空";
                    }
                    return data;
                }
            }/*,{
                data: 'id',
                title: '操作',
                "orderable": false, // 禁用排序
                render: function (data, type, row) {
                    return '<button class=" btn btn-success btn-sm" onclick="show(' + data + ')">查看</button>&nbsp;<button class=" btn btn-warning btn-sm" onclick="update(' + data + ')">修改</button>';
                }
            }*/
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