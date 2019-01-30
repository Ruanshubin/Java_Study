## 1 Markdown插件

### 1.1 showdown.js

```
function compile(){

    //获取要转换的文字
    var text = document.getElementById("content").value;
    //创建实例
    var converter = new showdown.Converter();
    //进行转换
    var html = converter.makeHtml(text);
    //展示到对应的地方  result便是id名称
	document.getElementById("result").innerHTML = html;
}

```

### 1.2 editor.js

- 引入css及js文件

```
<!--引入样式文件-->
<link rel="stylesheet" href="/data_tower/editor/examples/css/style.css" />
<link rel="stylesheet" href="/data_tower/editor/css/editormd.preview.css" />
<link rel="stylesheet" href="/data_tower/editor/css/editormd.css" />

<!--引入js文件-->
<script src="/data_tower/editor/examples/js/jquery.min.js"></script>
<script src="/data_tower/editor/lib/marked.min.js"></script>
<script src="/data_tower/editor/lib/prettify.min.js"></script>
<script src="/data_tower/editor/lib/raphael.min.js"></script>
<script src="/data_tower/editor/lib/underscore.min.js"></script>
<script src="/data_tower/editor/lib/sequence-diagram.min.js"></script>
<script src="/data_tower/editor/lib/flowchart.min.js"></script>
<script src="/data_tower/editor/lib/jquery.flowchart.min.js"></script>
<script src="/data_tower/editor/editormd.js"></script>
```

- 声明md存放域

```
<div id="testEditorMdview"></div>
```

#### 1.2.1 直接引用md文件

```
<script type="text/javascript"> 
	
	//markDown转HTMl的方法
	function mdToHtml(content){
		
		editormd.markdownToHTML("testEditorMdview", {
			markdown: content,
			htmlDecode: "style,script,iframe", //可以过滤标签解码
			emoji: true,
			taskList:true,
			tex: true,               // 默认不解析
			flowChart:true,         // 默认不解析
			sequenceDiagram:true,  // 默认不解析
			
			/**设置主题颜色*/
			editorTheme: "pastel-on-dark",
			theme: "gray",
			previewTheme: "dark"
		});
	}
	
	$.get("数据塔建设--规范设计.md", function(content){	
		mdToHtml(content);
	}); 
	
</script>
```

#### 1.2.2 控制器转发

```
// 
String mdPath = req.getSession().getServletContext().getRealPath("/views/home/数据塔建设--规范设计.md");
BufferedReader bufferedReader = new BufferedReader(new FileReader(mdPath));
// 创建StringBuffer容器
StringBuffer stringBuffer = new StringBuffer();
String string = null;
while((string = bufferedReader.readLine()) != null) {
	stringBuffer.append(string);
	stringBuffer.append("\r\n");
}
String md = stringBuffer.toString();
```

- 将文件读取为字符串，但是直接返回给前端会出现乱码，可进行转化：

```
// 转为GBK编码
String md_iso = new String(md.getBytes("utf-8"), "ISO8859-1");
resp.getWriter().write(md_iso);
```

- 更改获取方式

```
$.get("/data_tower/page?oper=data_specification", function(content){	
	mdToHml(content);
	}); 
```

#### 1.2.3 session传值

可以将本地读取的md文件转为字符串放入session中,然后传递给view层,需要注意的是:

- 编码均是utf-8,不需要进行编码转换.

```
HttpSession session = req.getSession();
session.setAttribute("data_specification", md);
logger.info("用户：" + PageServlet.getIPAddress(req) + "查看了主页->数据规范");
req.getRequestDispatcher("/views/home/data_specification.jsp").forward(req, resp);
```

- view层

```
<div id="testEditorMdview">
	 <textarea id="appendTest" style="display:none;">${data_specification}</textarea> 
</div>

<script type="text/javascript"> 

	//markDown转HTMl的方法
 function mdToHtml(){
		
		editormd.markdownToHTML("testEditorMdview", {
			//markdown: content,
			htmlDecode: "style,script,iframe", //可以过滤标签解码
			emoji: true,
			taskList:true,
			tex: true,               // 默认不解析
			flowChart:true,         // 默认不解析
			sequenceDiagram:true,  // 默认不解析
			
			/**设置主题颜色*/
			editorTheme: "pastel-on-dark",
			theme: "gray",
			previewTheme: "dark"
		});
	}
	
	mdToHtml();

</script>
```








