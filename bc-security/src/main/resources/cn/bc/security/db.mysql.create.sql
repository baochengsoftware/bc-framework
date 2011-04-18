-- 系统安全相关模块
-- 模块
create table BC_SECURITY_MODULE (
    ID int NOT NULL auto_increment,
    STATUS int(1) NOT NULL COMMENT '状态：0-已禁用,1-启用中,2-已删除',
    INNER_ int(1) COMMENT '是否为内置对象:0-否,1-是',
    BELONG int COMMENT '所隶属的模块',
    TYPE_ int(1) NOT NULL COMMENT '类型：1-文件夹,2-内部链接,3-外部链接,4-html',
    CODE varchar(100) NOT NULL COMMENT '编码，兼排序作用',
    NAME varchar(255) NOT NULL COMMENT '名称',
    URL varchar(255) COMMENT '地址',
    OPTIONS text COMMENT '扩展参数',
    primary key (ID)
) COMMENT='模块';

-- 角色
create table BC_SECURITY_ROLE (
    ID int NOT NULL auto_increment,
    STATUS int(1) NOT NULL COMMENT '状态：0-已禁用,1-启用中,2-已删除',
    INNER_ int(1) COMMENT '是否为内置对象:0-否,1-是',
   	TYPE_ int(1) NOT NULL COMMENT '类型',
    CODE varchar(100) NOT NULL COMMENT '编码，兼排序作用',
    NAME varchar(255) NOT NULL COMMENT '名称',
    primary key (ID)
) COMMENT='角色';

-- 角色与模块的关联
create table BC_SECURITY_ROLE_MODULE (
    RID int NOT NULL COMMENT '角色ID',
    MID int NOT NULL COMMENT '模块ID',
    primary key (RID,MID)
) COMMENT='角色与模块的关联：角色可以使用哪些模块资源';
ALTER TABLE BC_SECURITY_ROLE_MODULE ADD CONSTRAINT FK_RM_ROLE FOREIGN KEY (RID) 
	REFERENCES BC_SECURITY_ROLE (ID);
ALTER TABLE BC_SECURITY_ROLE_MODULE ADD CONSTRAINT FK_RM_MODULE FOREIGN KEY (MID) 
	REFERENCES BC_SECURITY_MODULE (ID);
