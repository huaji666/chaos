#百度富文本编辑
百度富文本工作流程简要说明
1、页面加载后，会通过请求 ueditor/upLoad.json 获取服务器配置文件，完成服务器校验。
2、然后所有文件上传 请求会 通过 ueditor/upLoad.json 走。
3、需要配置虚拟目录，否则图片无法显示 <Context path="file" docBase="文件存储目录"></Context>

##ueditor.config.js 前端配置文件。
详细说明请看官网说明
##config.json 后端配置文件
详细说明请看官网说明
