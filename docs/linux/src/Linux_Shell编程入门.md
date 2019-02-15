## 1 脚本解释器

### 1.1 sh

即Bourne shell，POSIX（Portable Operating System Interface）标准的shell解释器，它的二进制文件路径通常是/bin/sh，由Bell Labs开发。

### 1.2 bash

Bash是Bourne shell的替代品，属GNU Project，二进制文件路径通常是/bin/bash。业界通常混用bash、sh、和shell。

在CentOS里，/bin/sh是一个指向/bin/bash的符号链接:

```
[root@ruanshubin ~]# ls -l /bin/sh
lrwxrwxrwx. 1 root root 4 Jul  1  2017 /bin/sh -> bash
```

### 1.3 高级编程语言

理论上讲，只要一门语言提供了解释器（而不仅是编译器），这门语言就可以胜任脚本编程，常见的解释型语言都是可以用作脚本编程的，如：Perl、Tcl、Python、PHP、Ruby。Perl是最老牌的脚本编程语言了，Python这些年也成了一些linux发行版的预置解释器。

编译型语言，只要有解释器，也可以用作脚本编程，如C shell是内置的（/bin/csh），Java有第三方解释器Jshell，Ada有收费的解释器AdaScript。

## 2 如何选择shell编程语言

### 2.1 熟悉vs陌生

如果你已经掌握了一门编程语言（如PHP、Python、Java、JavaScript），建议你就直接使用这门语言编写脚本程序，虽然某些地方会有点啰嗦，但你能利用在这门语言领域里的经验（单元测试、单步调试、IDE、第三方类库）。

### 2.2 简单vs高级

如果你觉得自己熟悉的语言（如Java、C）写shell脚本实在太啰嗦，你只是想做一些备份文件、安装软件、下载数据之类的事情，学着使用sh，bash会是一个好主意。

shell只定义了一个非常简单的编程语言，所以，如果你的脚本程序复杂度较高，或者要操作的数据结构比较复杂，那么还是应该使用Python、Perl这样的脚本语言，或者是你本来就已经很擅长的高级语言。因为sh和bash在这方面很弱，比如说：

- 它的函数只能返回字串，无法返回数组；
- 它不支持面向对象，你无法实现一些优雅的设计模式；
- 它是解释型的，一边解释一边执行，连PHP那种预编译都不是，如果你的脚本包含错误(例如调用了不存在的函数)，只要没执行到这一行，就不会报错。

### 2.3 环境兼容性

如果你的脚本是提供给别的用户使用，使用sh或者bash，你的脚本将具有最好的环境兼容性，perl很早就是linux标配了，python这些年也成了一些linux发行版的标配，至于mac os，它默认安装了perl、python、ruby、php、java等主流编程语言。

## 3 linux下的bash编程

### 3.1 运行方式

- 第一种方式

```
[root@ruanshubin ~]# vim hello.sh

#!/bin/sh
# 定义变量
yourName="ruanshubin"
echo ${yourName}

[root@ruanshubin ~]# chomod +x ./hello.sh
[root@ruanshubin ~]# ./hello.sh
```

- 第二种方式

```
[root@ruanshubin ~]# /bin/sh ./hello.sh
或者：
[root@ruanshubin ~]# /bin/bash ./hello.sh
```

### 3.2 变量

#### 3.2.1 定义变量

定义变量时，变量名不加美元符号（$），如：

```
yourName="ruanshubin"
```

注意，变量名和等号之间不能有空格。

#### 3.2.2 使用变量

使用一个定义过的变量，只要在变量名前面加美元符号即可，如：

```
yourName="ruanshubin"
echo $yourName
echo ${yourName}
```

变量名外面的花括号是可选的，加不加都行，加花括号是为了帮助解释器识别变量的边界，比如下面这种情况：

```
for skill in Ada Coffe Action Java 
do
  echo "I am good at ${skill}Script"
done
```

如果不给skill变量加花括号，写成echo “I am good at $skillScript”，解释器就会把$skillScript当成一个变量（其值为空），代码执行结果就不是我们期望的样子了。

**推荐给所有变量加上花括号，这是个好的编程习惯。**

### 3.3 注释

以“#”开头的行就是注释，会被解释器忽略。

sh里没有多行注释，只能每一行加一个#号。

### 3.4 字符串

字符串是shell编程中最常用最有用的数据类型（除了数字和字符串，也没啥其它类型好用了），字符串可以用单引号，也可以用双引号，也可以不用引号。

#### 3.4.1 单引号

单引号字符串的限制：

- 单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的；
- 单引号字串中不能出现单引号（对单引号使用转义符后也不行）。

#### 3.4.2 双引号

- 双引号里可以有变量；
- 双引号里可以出现转义字符；

#### 3.4.3 字符串操作

```
#!/bin/sh

# 定义变量
yourName="ruanshubin"
echo ${yourName}

# 拼接字符串
greeting="hello, "${yourName}" !"
greeting1="hello, ${yourName} !"
echo ${greeting} ${greeting1}

# 获取字符串长度
echo "变量yourName的字符串长度为： "${#yourName}

# 提取子字符串
echo "变量yourName的前四位字符为： "${yourName:0:4} 
```

### 3.5 流程控制

#### 3.5.1 if 

```
## if
if condition
then
	command
fi

## if else
if condition
then
	command1
else
	commond2
fi

## if elseif else
if condition1
then
	command1
elif condition2
	commond2
else
	commond3
fi
```

#### 3.5.2 for

```
for var in item
do
	command
done

## C风格的for
for (( EXP1; EXP2; EXP3))
do
	command
done
```

#### 3.5.3 while

```
while condition
do
	command
done
```

#### 3.5.4 until

```
until condition
do
	command
done
```

#### 3.5.5 case

```
1. case "${opt}" in
2. 	"Install-Puppet-Server" )
3. 		install_master $1
4. 		exit
5. 	;;
6.
7. 	"Install-Puppet-Client" )
8. 		install_client $1
9. 		exit
10. ;;
11.
12. "Config-Puppet-Server" )
13. 	config_puppet_master
14. 	exit
15. ;;
16.
17. "Config-Puppet-Client" )
18. 	config_puppet_client
19. 	exit
20. ;;
21.
22. "Exit" )
23. 	exit
24. ;;
25.
26. * ) echo "Bad option, please choose again"
27. esac
```

case的语法和C family语言差别很大，它需要一个esac（就是case反过来）作为结束标记，每个case分支用右圆括号，用两个分号表示break。
