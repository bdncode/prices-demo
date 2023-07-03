CREATE TABLE IF NOT EXISTS ITXADM.PRICES (
    PRICE_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    BRAND_ID BIGINT NOT NULL,
    START_DATE TIMESTAMP NOT NULL,
    END_DATE TIMESTAMP NOT NULL,
    PRICE_LIST BIGINT NOT NULL,
    PRODUCT_ID BIGINT NOT NULL,
    PRIORITY BIGINT NOT NULL,
    PRICE DECIMAL(18, 2) NOT NULL,
    CURR VARCHAR(3) NOT NULL
);