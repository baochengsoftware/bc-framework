-- 系统安全相关模块
-- 模块
create table BC_SECURITY_MODULE (
    ID integer NOT NULL auto_increment,
    BELONG integer COMMENT '所隶属的模块',
    TYPE_ integer(1) NOT NULL COMMENT '类型：1-文件夹,2-内部链接,3-外部链接,4-html',
    CODE varchar(255) NOT NULL COMMENT '编码',
    LABEL varchar(255) NOT NULL COMMENT '名称',
    URL varchar(255) COMMENT '地址',
    OPTIONS text COMMENT '扩展参数',
    primary key (ID)
) COMMENT='系统模块';

-- 角色
create table BC_SECURITY_ROLE (
    ID integer NOT NULL auto_increment,
    ORG_ID integer COMMENT '所隶属的组织',
   	TYPE_ integer(1) NOT NULL COMMENT '类型',
    CODE varchar(255) NOT NULL COMMENT '编码',
    LABEL varchar(255) NOT NULL COMMENT '名称',
    primary key (ID)
) COMMENT='系统角色';
ALTER TABLE BC_SECURITY_ROLE ADD CONSTRAINT FK_ROLE_ORGANIZER FOREIGN KEY (ORG_ID) 
	REFERENCES BC_IDENTITY_ORGANIZER (ID);

-- 角色与权限的关联
create table BC_SECURITY_ROLE_MODULE (
    RID integer NOT NULL COMMENT '角色ID',
    MID integer NOT NULL COMMENT '模块ID',
    primary key (RID,MID)
) COMMENT='角色与权限的关联';
ALTER TABLE BC_SECURITY_ROLE_MODULE ADD CONSTRAINT FK_RM_ROLE FOREIGN KEY (RID) 
	REFERENCES BC_SECURITY_ROLE (ID);
ALTER TABLE BC_SECURITY_ROLE_MODULE ADD CONSTRAINT FK_RM_MODULE FOREIGN KEY (MID) 
	REFERENCES BC_SECURITY_MODULE (ID);
