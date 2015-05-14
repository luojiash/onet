$(document).ready(function() {
/*
 * 初始化按钮的loading功能，点击后将显示Loading字样，并且按钮被disabled掉，无法连续点击，防止二次提交
 * 超过3秒后按钮将恢复原状
 * http://my.oschina.net/u/699015/blog/169407
 */
$('button[data-loading-text]').click(function () {
    var btn = $(this).button('loading');
    setTimeout(function () {
        btn.button('reset');
    }, 2000);
})

});