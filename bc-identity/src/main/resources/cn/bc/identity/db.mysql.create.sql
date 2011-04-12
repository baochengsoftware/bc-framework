-- 模块
create table BC_SECURITY_MODULE (
    ID integer NOT NULL auto_increment,
    BELONG integer COMMENT '所隶属的模块',
    TYPE_ integer(1) NOT NULL COMMENT '类型：1-文件夹,2-内部链接,3-外部链接',
    CODE varchar(255) NOT NULL COMMENT '编码',
    LABEL varchar(255) NOT NULL COMMENT '名称',
    URL varchar(255) COMMENT '地址',
    primary key (ID)
) COMMENT='系统模块';

-- 参与者
create table BC_IDENTITY_ACTOR (
    ID integer NOT NULL auto_increment,
    UID varchar(20) NOT NULL COMMENT '全局标识',
    NAME varchar(255) COMMENT '名称',
    TYPE_ integer(1) NOT NULL COMMENT '类型：1-用户,2-岗位,3-部门,4-单位',
    EMAIL varchar(255) NOT NULL COMMENT '联系邮箱',
    PHONE varchar(255) NOT NULL COMMENT '联系电话',
    primary key (ID)
) COMMENT='参与者';

-- 用户
create table BC_IDENTITY_USER (
    ID integer NOT NULL,
    FIRST_NAME varchar(255) NOT NULL COMMENT '姓氏',
    LAST_NAME varchar(255) NOT NULL COMMENT '名字',
    SEX integer(1) NOT NULL COMMENT '性别：1-男,2-女',
    primary key (ID)
) COMMENT='用户';
ALTER TABLE BC_IDENTITY_USER ADD CONSTRAINT UserRFActor FOREIGN KEY (ID) 
	REFERENCES BC_IDENTITY_ACTOR (ID) ON DELETE CASCADE ON UPDATE RESTRICT;

-- 组织：岗位、部门、单位
create table BC_IDENTITY_ORGANIZER (
    ID integer NOT NULL,
    BELONG integer COMMENT '所隶属的组织',
    primary key (ID)
) COMMENT='组织：岗位、部门、单位';
ALTER TABLE BC_IDENTITY_ORGANIZER ADD CONSTRAINT OrganizerRFActor FOREIGN KEY (ID) 
	REFERENCES BC_IDENTITY_ACTOR (ID) ON DELETE CASCADE ON UPDATE RESTRICT;
