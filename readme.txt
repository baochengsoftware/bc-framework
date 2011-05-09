宝城软件Java开发平台 https://github.com/baochengsoftware/bc-framework

编译发布需要的工具：ant1.8+、maven3+、java1.5+

生成数据库的构建脚本：
    >ant build
    运行后会在本目录下生成如下名称的sql文件
    1）db.[dbtype].create.sql 数据库的建表脚本
    2）db.[dbtype].data.sql 数据库的初始化数据
    3）db.[dbtype].drop.sql 删除数据库表的脚本
    * [dbtype]可能的值为mysql、oracle、mssql等。

编译发布步骤(mysql)：
    1）创建名称为bc-demo的数据库
    2）用上面生成的sql脚本创建数据库表和导入初始化数据
    3）执行>mvn clean install
    * 如果不初始化数据库又想打包成功，执行>mvn clean install -Dmaven.test.skip=true,这将忽略单元测试而直接打包。如果要连接其他数据库进行单元测试，修改./bc-parent/pom.xml中profiles节点的相关数据库连接参数，然后命令行直接指定即可，如使用oracle数据库：>mvn clean install -Poracle。
    单元测试中的数据库连接全部是基于用户名和密码的连接方式，如果要使用连接池或JPA，请自行修改各个模块下的如下文件：src\test\resources\spring-test-db.xml

