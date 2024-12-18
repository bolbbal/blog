use jsl18;

create table tbl_board (
    bno bigint not null auto_increment,
    title varchar(100) not null,
    content varchar(4000) not null,
    writer varchar(50) not null,
    regdate datetime default current_timestamp,
    updatedate datetime default current_timestamp,
    constraint tbl_board_pk primary key (bno)
);

create table tbl_attach (
    uuid varchar(100) not null,
    uploadpath varchar(200) not null,
    filename varchar(100) not null,
    uploadfile varchar(100) not null,
    filetype char(1) default 'I',
    bno bigint,
    constraint tbl_attach_fk foreign key (bno) references tbl_board (bno) on delete cascade
);

create table siteuser (
    id bigint not null auto_increment,
    username varchar(100) not null,
    password varchar(300) not null,
    email varchar(100) not null,
    role varchar(10) default 'ROLE_USER' not null,
    constraint siteuser_pk primary key (id)
);

create table tbl_reply (
    reply_bno bigint not null auto_increment,
    board_bno bigint not null,
    username varchar(100) not null,
    content varchar(200) not null,
    regdate datetime default current_timestamp,
    constraint tbl_reply_pk primary key (reply_bno),
    constraint tbl_reply_fk foreign key (board_bno) references tbl_board (bno) on delete cascade
);