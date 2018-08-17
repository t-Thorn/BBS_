var E = window.wangEditor
var editor = new E('#editor')

$(function () {
    // 或者 var editor = new E( document.getElementById('editor') )
    editor.customConfig.uploadImgShowBase64 = true  // 上传图片到服务器
    editor.create()

})