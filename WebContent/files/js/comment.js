
// 菜单
$(document).on('click', 'nav a', function (e) {
	
    var $this = $(e.target), $active;
    $this.is('a') || ($this = $this.closest('a'));

    $active = $this.parent().siblings( ".active" );
    $active && $active.toggleClass('active').find('> ul:visible').slideUp(200);

    ($this.parent().hasClass('active') && $this.next().slideUp(200)) || $this.next().slideDown(200);
    $this.parent().toggleClass('active');
    $this.next().is('ul') && e.preventDefault();
    setTimeout(function(){ $(document).trigger('updateNav'); }, 300);
    
});



/**
 * 建议使用非s、a等开头的属性，这些为源码操作属性
 * •l - Length changing 每页显示多少条数据选项
 •f - Filtering input 搜索框
 •t - The Table 表格
 •i - Information 表格信息
 •p - Pagination 分页按钮
 •r - pRocessing 加载等待显示信息
 */
//dataTable基本设置
var options = {
    "jQueryUI": true,//Enable jQuery UI ThemeRoller support
    "pagingType": "full_numbers",//显示不同的页面风格
    "dom": '<""f>t<""ip><"clear">',
    "info": true,//展示页面信息
    "destroy": true,
    "paging": true,//启用分页
    "searching": false,//启用搜索框
    "ordering":  false,
    "stateSave": false,
    "language": {
        "processing": "加载中...",
        "lengthMenu": "显示 _MENU_ 项结果",
        "zeroRecords": "没有匹配结果",
        "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "infoEmpty": "显示第 0 至 0 项结果，共 0 项",
        "infoFiltered": "(由 _MAX_ 项结果过滤)",
        "infoPostFix": "",
        "search": "搜索：",
        "url": "",//可通过该选项从服务器获取
        "emptyTable": "表中数据为空",
        "loadingRecords": "加载中...",
        "infoThousands": ",",
        "paginate": {
            "first": "首页",
            "previous": "上页",
            "next": "下页",
            "last": "末页"
        },
        "aria": {
            "sortAscending": ": 以升序排列此列",
            "sortDescending": ": 以降序排列此列"
        }
    }
};

function isPhone(str){
    reg = /^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
    return reg.test(str)
}

//关闭layer()
function closeLayer(){
    layer.closeAll();
}
