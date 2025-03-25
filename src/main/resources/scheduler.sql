CREATE TABLE scheduler
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '식별자',
    todo      VARCHAR(500) Comment '할일',
    name      VARCHAR(100) COMMENT '이름',
    password  INT COMMENT '비밀번호',
    createdAt TIMESTAMP NOT NULL COMMENT '작성일시',
    COLUMN    updatedAt TIMESTAMP NOT NULL COMMENT '수정일시';
)