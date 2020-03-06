#删库
# DROP DATABASE ztsfs;
#
##创建数据库
# CREATE DATABASE `ztsfs` DEFAULT CHARACTER SET utf8;

# USE `ztsfs`;
##取消外键约束
# SET FOREIGN_KEY_CHECKS=0;



INSERT INTO `ztsfs`.`zt_users` (`id`, `enabled`, `password`, `phone`, `userface`, `username`, `empName`, `empId`) VALUES ('1', '1', '$2a$10$ySG2lkvjFHY5O0./CPIE1OI8VJsuKYEzOYzqIa7AJR6sEgSzUFOAm', '150008899', 'http://117.34.91.119:8085/headimg/1.jpg', '张三', '张三', '3');
INSERT INTO `ztsfs`.`zt_users` (`id`, `enabled`, `password`, `phone`, `userface`, `username`, `empName`, `empId`) VALUES ('2', '1', '$2a$10$ySG2lkvjFHY5O0./CPIE1OI8VJsuKYEzOYzqIa7AJR6sEgSzUFOAm', '13255588899', 'http://117.34.91.119:8085/headimg/3.jpg', '李四', '李四', '1');
INSERT INTO `ztsfs`.`zt_users` (`id`, `enabled`, `password`, `phone`, `userface`, `username`, `empName`, `empId`) VALUES ('3', '1', '$2a$10$ySG2lkvjFHY5O0./CPIE1OI8VJsuKYEzOYzqIa7AJR6sEgSzUFOAm', '11656565', 'http://117.34.91.119:8085/headimg/2.jpg', '王五', '王五', '2');

INSERT INTO `ztsfs`.`zt_position` (`id`, `createDate`, `enabled`, `name`, `specification`, `posNumber`) VALUES ('1', '2019-05-28', '1', '主管', '主管', '1');
INSERT INTO `ztsfs`.`zt_position` (`id`, `createDate`, `enabled`, `name`, `specification`, `posNumber`) VALUES ('2', '2019-05-28', '1', '副主管', '主管', '2');
INSERT INTO `ztsfs`.`zt_position` (`id`, `createDate`, `enabled`, `name`, `specification`, `posNumber`) VALUES ('3', '2019-05-28', '1', '员工', '员工', '3');


INSERT INTO `ztsfs`.`zt_employee` (`id`, `address`, `birthday`, `createDate`, `departmentIds`, `departmentName`, `email`, `enabled`, `entryDate`, `gender`, `name`, `phone`, `positionIds`, `positionName`, `username`) VALUES ('1', 'asd', NULL, '2019-05-23', '2', '生产', 'dfa@163.com', '1', NULL, '1', '李四', '14788888888', '2', '主管', NULL);
INSERT INTO `ztsfs`.`zt_employee` (`id`, `address`, `birthday`, `createDate`, `departmentIds`, `departmentName`, `email`, `enabled`, `entryDate`, `gender`, `name`, `phone`, `positionIds`, `positionName`, `username`) VALUES ('2', '电子正街', '2019-05-24 14:25:34', '2019-05-24', '1', '综合', '阿萨德@163.com', '1', NULL, '1', '王五', '15699998888', '3', '员工', NULL);
INSERT INTO `ztsfs`.`zt_employee` (`id`, `address`, `birthday`, `createDate`, `departmentIds`, `departmentName`, `email`, `enabled`, `entryDate`, `gender`, `name`, `phone`, `positionIds`, `positionName`, `username`) VALUES ('3', '昂啊', NULL, '2019-05-23', '1', '综合', 'tang@163.com', '1', NULL, '1', '张三', '15688889999', '2', '主管', NULL);
INSERT INTO `ztsfs`.`zt_employee` (`id`, `address`, `birthday`, `createDate`, `departmentIds`, `departmentName`, `email`, `enabled`, `entryDate`, `gender`, `name`, `phone`, `positionIds`, `positionName`, `username`) VALUES ('4', '阿萨德', '2019-05-30 16:00:00', NULL, '1', '综合', 'as@163.com', '1', '2019-05-21 16:00:00', '1', '小何', '15688994433', '58', '员工', NULL);


INSERT INTO `ztsfs`.`zt_department` (`Id`, `createDate`, `deNumber`, `describes`, `enable`, `name`) VALUES ('1', '2019-05-28', 'ADMINISTRATION', '综合', '1', '综合');
INSERT INTO `ztsfs`.`zt_department` (`Id`, `createDate`, `deNumber`, `describes`, `enable`, `name`) VALUES ('2', '2019-05-28', 'PRODUCTION', '生产部门', '1', '生产');
INSERT INTO `ztsfs`.`zt_department` (`Id`, `createDate`, `deNumber`, `describes`, `enable`, `name`) VALUES ('3', '2019-05-28', 'MARKETING', '市场', '1', '市场部');
INSERT INTO `ztsfs`.`zt_department` (`Id`, `createDate`, `deNumber`, `describes`, `enable`, `name`) VALUES ('4', '2019-05-28', 'DEVELOPMENT', '研发部', '1', '研发');
INSERT INTO `ztsfs`.`zt_department` (`Id`, `createDate`, `deNumber`, `describes`, `enable`, `name`) VALUES ('5', '2019-05-28', 'FINANCE', '财务', '1', '财务部');


INSERT INTO `ztsfs`.`zt_sysrole` (`id`, `name`, `roleName`, `enabled`) VALUES ('1', 'ROLE_admin', '系统管理员', '1');
INSERT INTO `ztsfs`.`zt_sysrole` (`id`, `name`, `roleName`, `enabled`) VALUES ('2', 'ROLE_aman', '综合', '1');
INSERT INTO `ztsfs`.`zt_users_roles` (`Users_id`, `roles_id`) VALUES ('1', '1');
INSERT INTO `ztsfs`.`zt_users_roles` (`Users_id`, `roles_id`) VALUES ('2', '1');
INSERT INTO `ztsfs`.`zt_users_roles` (`Users_id`, `roles_id`) VALUES ('3', '1');


INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('1', NULL, '北三环', NULL, NULL, '1@3.com', '1', NULL, NULL, '金泽物流', '0', NULL, '13333333333', 'up', NULL,'1');
INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('2', NULL, '12', NULL, NULL, '1@1.com', '1', NULL, NULL, '腾达物流', '0', NULL, '13344444444', 'up', NULL,'2');
INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('3', NULL, '', NULL, NULL, '1@1.com', '1', NULL, NULL, '金泽总部', '3', '金泽物流', '13322222222', 'down', '3','3');
INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('4', NULL, '', NULL, NULL, '1@1.com', '1', NULL, NULL, '好看', '3', '金泽物流', '13222222222', 'down', '3','4');
INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('5', NULL, '', NULL, NULL, '1@1.com', '1', NULL, NULL, '一一', '3', '金泽总部', '13233333333', 'down', '3','5');
INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('6', NULL, '', NULL, NULL, '1@1.com', '1', NULL, NULL, '金泽分部', '3', '金泽物流', '13222222221', 'down', '3','2');
INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('7', '延长', '陕西西安', NULL, NULL, '1@1.com', '1', NULL, '高新区', '延长石油股份有限公司', '0', NULL, '13333331111', 'down', NULL,'3');
INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('8', '吴起', '陕西省吴起县', NULL, NULL, '1@1.com', '1', NULL, '吴定大街2号', '吴起采油厂', '7', '延长石油股份有限公司', '13444445555', 'down', '7','2');
INSERT INTO `ztsfs`.`zt_client` (`id`, `abbreviation`, `address`, `addressDetails`, `createDate`, `email`, `enabled`, `fax`, `infor`, `name`, `parentClientId`, `parentName`, `phone`, `status`, `parent_id`,`grade`) VALUES ('9', '吴采', '1212', NULL, NULL, '1@1.com', '1', NULL, '2号楼', '吴起采油厂生产组', '7', '吴起采油厂', '13455556677', 'down', '7','4');

INSERT INTO `ztsfs`.`zt_client_child` (`Client_id`, `child_id`) VALUES ('3', '4');
INSERT INTO `ztsfs`.`zt_client_child` (`Client_id`, `child_id`) VALUES ('3', '5');
INSERT INTO `ztsfs`.`zt_client_child` (`Client_id`, `child_id`) VALUES ('3', '6');
INSERT INTO `ztsfs`.`zt_client_child` (`Client_id`, `child_id`) VALUES ('7', '8');
INSERT INTO `ztsfs`.`zt_client_child` (`Client_id`, `child_id`) VALUES ('7', '9');



INSERT INTO `ztsfs`.`zt_sysunit` (`id`, `code`, `createDate`, `enabled`, `name`) VALUES ('1', 'kg', '2019-06-28 00:00:00', b'1', '千克');
INSERT INTO `ztsfs`.`zt_sysunit` (`id`, `code`, `createDate`, `enabled`, `name`) VALUES ('2', 'kg', '2019-06-28 00:00:00', b'1', '公斤');
INSERT INTO `ztsfs`.`zt_sysunit` (`id`, `code`, `createDate`, `enabled`, `name`) VALUES ('3', 'a', '2019-06-28 00:00:00', b'1', '枚');
INSERT INTO `ztsfs`.`zt_sysunit` (`id`, `code`, `createDate`, `enabled`, `name`) VALUES ('4', 'P', '2019-06-28 00:00:00', b'1', '双');
INSERT INTO `ztsfs`.`zt_sysunit` (`id`, `code`, `createDate`, `enabled`, `name`) VALUES ('5', 'mm', '2019-06-28 00:00:00', b'1', '毫米');


INSERT INTO `ztsfs`.`zt_productuse`(`id`, `code`, `createDate`, `enabled`, `name`) VALUES (1, '', '2019-11-28 11:28:55', 1, '罐车锁');
INSERT INTO `ztsfs`.`zt_productuse`(`id`, `code`, `createDate`, `enabled`, `name`) VALUES (2, '', '2019-11-28 11:28:59', 1, '棚车锁');




INSERT INTO `ztsfs`.`zt_color` (`id`, `code`, `createDate`, `enabled`, `name`) VALUES ('1', '', '2019-07-26 14:07:14', '1', '黄');
INSERT INTO `ztsfs`.`zt_color` (`id`, `code`, `createDate`, `enabled`, `name`) VALUES ('2', '', '2019-07-26 14:07:27', '1', '蓝');
INSERT INTO `ztsfs`.`zt_color` (`id`, `code`, `createDate`, `enabled`, `name`) VALUES ('3', '', '2019-07-26 14:07:33', '1', '绿');

INSERT INTO `ztsfs`.`zt_specification` (`id`, `code`, `createDate`, `enable`, `name`) VALUES ('1', '450MM', '2019-07-26 14:19:16', '1', '450mm');
INSERT INTO `ztsfs`.`zt_specification` (`id`, `code`, `createDate`, `enable`, `name`) VALUES ('2', '650MM', '2019-07-26 14:19:31', '1', '650mm');
INSERT INTO `ztsfs`.`zt_specification` (`id`, `code`, `createDate`, `enable`, `name`) VALUES ('3', '700MM', '2019-07-26 14:20:16', '1', '700mm');


INSERT INTO `ztsfs`.`zt_workstep` (`Id`, `code`, `createDate`, `enabled`, `intro`, `workstepName`) VALUES ('1', NULL, '2019-07-25', '1', '测试', '注塑');
INSERT INTO `ztsfs`.`zt_workstep` (`Id`, `code`, `createDate`, `enabled`, `intro`, `workstepName`) VALUES ('2', NULL, '2019-07-25', '1', '打字', '打字');
INSERT INTO `ztsfs`.`zt_workstep` (`Id`, `code`, `createDate`, `enabled`, `intro`, `workstepName`) VALUES ('3', NULL, '2019-07-25', '1', '检测', '检测');
INSERT INTO `ztsfs`.`zt_workstep` (`Id`, `code`, `createDate`, `enabled`, `intro`, `workstepName`) VALUES ('4', NULL, '2019-07-25', '1', '装箱', '装箱');
INSERT INTO `ztsfs`.`zt_workstep` (`Id`, `code`, `createDate`, `enabled`, `intro`, `workstepName`) VALUES ('5', NULL, '2019-07-25', '1', '刮锁', '刮锁');
INSERT INTO `ztsfs`.`zt_workstep` (`Id`, `code`, `createDate`, `enabled`, `intro`, `workstepName`) VALUES ('6', NULL, '2019-07-26', '1', '熔断', '熔断');

##ceshi