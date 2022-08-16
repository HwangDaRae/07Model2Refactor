
DROP TABLE Cart;
DROP TABLE transaction;
DROP TABLE product;
DROP TABLE users;
DROP TABLE upload_file;


ALTER TABLE product ADD amount NUMBER(20);
UPDATE product SET amount = 4;
ALTER TABLE transaction ADD amount NUMBER(20);
UPDATE product SET amount = 7;


DROP SEQUENCE seq_product_prod_no;
DROP SEQUENCE seq_transaction_tran_no;


CREATE SEQUENCE seq_product_prod_no		INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE seq_transaction_tran_no	INCREMENT BY 1 START WITH 10000;


CREATE TABLE users ( 
	user_id 				VARCHAR2(20)	NOT NULL,
	user_name 			VARCHAR2(50)	NOT NULL,
	password 			VARCHAR2(10)	NOT NULL,
	role 					VARCHAR2(5) 		DEFAULT 'user',
	ssn 					VARCHAR2(13),
	cell_phone 			VARCHAR2(14),
	addr 				VARCHAR2(100),
	email 				VARCHAR2(50),
	reg_date 				DATE,
	PRIMARY KEY(user_id)
);


CREATE TABLE product ( 
	prod_no 			NUMBER 			NOT NULL,
	prod_name 			VARCHAR2(100) 	NOT NULL,
	prod_detail 		VARCHAR2(200),
	manufacture_day		VARCHAR2(8),
	price 				NUMBER(10),
	image_file 			VARCHAR2(100),
	reg_date 			DATE,
	amount				NUMBER(10),
	PRIMARY KEY(prod_no)
);

CREATE TABLE transaction ( 
	tran_no 				NUMBER 			NOT NULL,
	tran_id					VARCHAR2(50)	NOT NULL,
	prod_no 				NUMBER(16)		NOT NULL REFERENCES product(prod_no),
	buyer_id 				VARCHAR2(20)	NOT NULL REFERENCES users(user_id),
	payment_option		CHAR(3),
	receiver_name 		VARCHAR2(20),
	receiver_phone		VARCHAR2(14),
	demailaddr 			VARCHAR2(100),
	dlvy_request 			VARCHAR2(100),
	tran_status_code		CHAR(3),
	order_data 			DATE,
	dlvy_date 			DATE,
	amount				NUMBER(10),
	total_price			NUMBER(20),
	PRIMARY KEY(tran_no)
);

CREATE TABLE Cart (
	prod_no				NUMBER(20)			NOT NULL REFERENCES product(prod_no),
	user_id				VARCHAR2(20)		NOT NULL REFERENCES users(user_id),
	image				VARCHAR(50),
	product_name		VARCHAR2(100),
	product_detail		VARCHAR2(200),
	amount				NUMBER(10),
	price				NUMBER(10)
);

CREATE TABLE Upload_File (
	fileNo				VARCHAR2(100)		NOT NULL,
	fileCount			NUMBER(20)			NOT NULL,
	fileName1			VARCHAR2(100)		NOT NULL,
	fileName2			VARCHAR2(100),
	fileName3			VARCHAR2(100),
	fileName4			VARCHAR2(100),
	fileName5			VARCHAR2(100)
);



INSERT 
INTO users ( user_id, user_name, password, role, ssn, cell_phone, addr, email, reg_date ) 
VALUES ( 'admin', 'admin', '1234', 'admin', NULL, NULL, '서울시 서초구', 'admin@mvc.com',to_date('2012/01/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS')); 

INSERT 
INTO users ( user_id, user_name, password, role, ssn, cell_phone, addr, email, reg_date ) 
VALUES ( 'manager', 'manager', '1234', 'admin', NULL, NULL, NULL, 'manager@mvc.com', to_date('2012/01/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'));     

INSERT 
INTO users ( user_id, user_name, password, role, ssn, cell_phone, addr, email, reg_date ) 
VALUES ( 'non-member', 'non-member', 'non-member', 'non', NULL, NULL, NULL, 'manager@mvc.com', to_date('2012/01/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'));        

INSERT INTO users 
VALUES ( 'user01', 'SCOTT', '1111', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user02', 'SCOTT', '2222', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user03', 'SCOTT', '3333', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user04', 'SCOTT', '4444', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user05', 'SCOTT', '5555', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user06', 'SCOTT', '6666', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user07', 'SCOTT', '7777', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user08', 'SCOTT', '8888', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user09', 'SCOTT', '9999', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user10', 'SCOTT', '1010', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user11', 'SCOTT', '1111', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user12', 'SCOTT', '1212', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user13', 'SCOTT', '1313', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user14', 'SCOTT', '1414', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user15', 'SCOTT', '1515', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user16', 'SCOTT', '1616', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user17', 'SCOTT', '1717', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user18', 'SCOTT', '1818', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user19', 'SCOTT', '1919', 'user', NULL, NULL, NULL, NULL, sysdate);
           
           
insert into product values (seq_product_prod_no.nextval,'vaio vgn FS70B','소니 바이오 노트북 신동품','20120514',2000000, 'AHlbAAAAtBqyWAAA.jpg',to_date('2012/12/14 11:27:27', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'자전거','자전거 좋아요~','20120514',10000, 'AHlbAAAAvetFNwAA.jpg',to_date('2012/11/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'),12);
insert into product values (seq_product_prod_no.nextval,'보르도','최고 디자인 신품','20120201',1170000, 'AHlbAAAAvewfegAB.jpg',to_date('2012/10/14 10:49:39', 'YYYY/MM/DD HH24:MI:SS'),11);
insert into product values (seq_product_prod_no.nextval,'보드세트','한시즌 밖에 안썼습니다. 눈물을 머금고 내놓음 ㅠ.ㅠ','20120217', 200000, 'AHlbAAAAve1WwgAC.jpg',to_date('2012/11/14 10:50:58', 'YYYY/MM/DD HH24:MI:SS'),2);
insert into product values (seq_product_prod_no.nextval,'인라인','좋아욥','20120819', 20000, 'AHlbAAAAve37LwAD.jpg',to_date('2012/11/14 10:51:40', 'YYYY/MM/DD HH24:MI:SS'),8);
insert into product values (seq_product_prod_no.nextval,'삼성센스 2G','sens 메모리 2Giga','20121121',800000, 'AHlbAAAAtBqyWAAA.jpg',to_date('2012/11/14 18:46:58', 'YYYY/MM/DD HH24:MI:SS'),5);
insert into product values (seq_product_prod_no.nextval,'연꽃','정원을 가꿔보세요','20121022',232300, 'AHlbAAAAtDPSiQAA.jpg',to_date('2012/11/15 17:39:01', 'YYYY/MM/DD HH24:MI:SS'),9);
insert into product values (seq_product_prod_no.nextval,'삼성센스','노트북','20120212',600000, 'flower.jpg',to_date('2012/11/12 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),3);
insert into product values (seq_product_prod_no.nextval,'test1','test1','20120212',60000, 'flower.jpg',to_date('2012/11/11 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test2','test2','20120212',50000, 'flower.jpg',to_date('2012/11/10 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test3','test3','20120212',20000, 'flower.jpg',to_date('2012/11/09 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test4','test4','20120212',15000, 'flower.jpg',to_date('2012/11/08 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test5','test5','20120212',7200, 'flower.jpg',to_date('2012/11/07 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),13);
insert into product values (seq_product_prod_no.nextval,'test6','test6','20120212',1000, 'flower.jpg',to_date('2012/11/06 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),18);
insert into product values (seq_product_prod_no.nextval,'test7','test7','20120212',94000, 'flower.jpg',to_date('2012/11/05 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),20);
insert into product values (seq_product_prod_no.nextval,'test8','test8','20120212',7000, 'flower.jpg',to_date('2012/11/14 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test9','test9','20120212',8000, 'flower.jpg',to_date('2012/11/15 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test10','test10','20120212',14000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/11/16 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test11','test11','20120213',15000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/12 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test12','test12','20120214',11000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/14 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test13','test13','20120215',7000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/16 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test14','test14','20120220',4000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/18 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test15','test15','20120215',2000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/19 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test16','test16','20120226',84000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/20 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test17','test17','20120223',52000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/06 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test18','test18','20120227',8000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/07 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test19','test19','20120219',6200, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/02 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test20','test20','20120204',160000, 'pngtree-purple-flower-squid-illustration-image_1448683.jpg',to_date('2012/10/01 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test21','test21','20130213',15000, '62129057.jpg',to_date('2012/10/12 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test22','test22','20130214',11000, '62129057.jpg',to_date('2012/10/14 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test23','test23','20130215',7000, '62129057.jpg',to_date('2012/10/16 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test24','test24','20130220',4000, '62129057.jpg',to_date('2012/10/18 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test25','test25','20130215',2000, '62129057.jpg',to_date('2012/10/19 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test26','test26','20130226',84000, '62129057.jpg',to_date('2012/10/20 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test27','test27','20130223',52000, '62129057.jpg',to_date('2012/10/06 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test28','test28','20130227',8000, '62129057.jpg',to_date('2012/10/07 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test29','test29','20130219',6200, '62129057.jpg',to_date('2012/10/02 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);
insert into product values (seq_product_prod_no.nextval,'test30','test30','20130204',160000, '62129057.jpg',to_date('2012/10/01 13:04:31', 'YYYY/MM/DD HH24:MI:SS'),10);



INSERT INTO cart VALUES(10001, 'user02', 'AHlbAAAAvetFNwAA.jpg', '자전거', '자전거 좋아요', 3, 10000);
INSERT INTO cart VALUES(10002, 'user02', 'AHlbAAAAvewfegAB.jpg', '보르도', '최고 디자인 신품', 2, 1170000);
INSERT INTO cart VALUES(10003, 'user02', 'AHlbAAAAve1WwgAC.jpg', '보드세트', '한시즌 밖에 안썼습니다. 눈물을 머금고 내놓음 ㅠ.ㅠ', 5, 200000);
INSERT INTO cart VALUES(10004, 'user02', 'AHlbAAAAve37LwAD.jpg', '인라인', '좋아욥', 2, 20000);



commit;


