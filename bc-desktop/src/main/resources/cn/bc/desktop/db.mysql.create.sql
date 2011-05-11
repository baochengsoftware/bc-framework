-- 系统桌面相关模块
-- 桌面快捷方式
create table BC_DESKTOP_SHORTCUT (
    ID int NOT NULL auto_increment,
    UID varchar(36) COMMENT '全局标识',
    STATUS_ int(1) NOT NULL COMMENT '状态：0-已禁用,1-启用中,2-已删除',
    INNER_ int(1) NOT NULL COMMENT '是否为内置对象:0-否,1-是',
    ORDER_ varchar(100) NOT NULL COMMENT '排序号',
    STANDALONE int(1) NOT NULL COMMENT '是否在独立的浏览器窗口中打开',
    NAME varchar(255) COMMENT '名称,为空则使用模块的名称',
    URL varchar(255) COMMENT '地址,为空则使用模块的地址',
    MID int COMMENT '对应的模块ID',
    AID int COMMENT '所属的参与者(如果为上级参与者,如单位部门,则其下的所有参与者都拥有该快捷方式)',
    primary key (ID)
) COMMENT='桌面快捷方式';
ALTER TABLE BC_DESKTOP_SHORTCUT ADD CONSTRAINT FK_SHORTCUT_MODULE FOREIGN KEY (MID) 
	REFERENCES BC_SECURITY_MODULE (ID);
ALTER TABLE BC_DESKTOP_SHORTCUT ADD CONSTRAINT FK_SHORTCUT_ACTOR FOREIGN KEY (AID) 
	REFERENCES BC_IDENTITY_ACTOR (ID);
