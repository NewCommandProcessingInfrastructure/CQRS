SELECT * FROM m_portfolio_command_source;

SELECT * FROM m_group;
SELECT * FROM m_group_level; 
SELECT * FROM m_group_client; 
SELECT * FROM m_client; 
SELECT * FROM m_loan;
SELECT * FROM m_client;
SELECT * FROM m_savings_account;
SELECT * FROM m_savings_product;
SELECT * FROM m_calendar_instance;

SELECT * FROM m_loan WHERE id IN (4236, 4237);

SELECT * FROM m_loan_transaction;
SELECT * FROM m_loan_repayment_schedule;
SELECT * FROM m_loan_repayment_schedule_history;

SELECT * FROM m_loan_transaction WHERE loan_id=4236;
SELECT * FROM m_loan_status_change_history;


SELECT `m_loan`.`id`,
    `m_loan`.`account_no`,
    `m_loan`.`client_id`,
    `m_loan`.`group_id`,
    `m_loan`.`loan_status_id`,
    `m_loan`.`loan_type_enum`,
    `m_loan`.`principal_amount_proposed`,
    `m_loan`.`principal_amount`,
    `m_loan`.`approved_principal`,
    `m_loan`.`net_disbursal_amount`,
    `m_loan`.`total_charges_due_at_disbursement_derived`,
    `m_loan`.`principal_disbursed_derived`,
    `m_loan`.`principal_repaid_derived`,
    `m_loan`.`principal_writtenoff_derived`,
    `m_loan`.`principal_outstanding_derived`,
    `m_loan`.`interest_charged_derived`,
    `m_loan`.`interest_repaid_derived`,
    `m_loan`.`interest_waived_derived`,
    `m_loan`.`interest_writtenoff_derived`,
    `m_loan`.`interest_outstanding_derived`,
    `m_loan`.`total_expected_repayment_derived`,
    `m_loan`.`total_repayment_derived`,
    `m_loan`.`total_expected_costofloan_derived`,
    `m_loan`.`total_costofloan_derived`,
    `m_loan`.`total_waived_derived`,
    `m_loan`.`total_writtenoff_derived`,
    `m_loan`.`total_outstanding_derived`,
    `m_loan`.`total_overpaid_derived`,
    `m_loan`.`fixed_emi_amount`,
    `m_loan`.`principal_adjustments_derived`,
    `m_loan`.`enable_down_payment`,
    `m_loan`.`total_principal_derived`
FROM `fineract_default`.`m_loan` WHERE id IN (4236, 4237);

SELECT * FROM m_loan_capitalized_income_balance;
SELECT * FROM m_loan_capitalized_income_balance WHERE loan_id IN (4236, 4237);

SELECT * FROM m_loan_approved_amount_history;
SELECT * FROM m_loan_charge;

SELECT * FROM m_loan_repayment_schedule;
SELECT * FROM m_loan;

SELECT * FROM m_loan_product_credit_allocation_rule;

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'fineract_tenants'
ORDER BY table_name;

SELECT `DATABASECHANGELOG`.`ID`,
    `DATABASECHANGELOG`.`AUTHOR`,
    `DATABASECHANGELOG`.`FILENAME`,
    `DATABASECHANGELOG`.`DATEEXECUTED`,
    `DATABASECHANGELOG`.`ORDEREXECUTED`,
    `DATABASECHANGELOG`.`EXECTYPE`,
    `DATABASECHANGELOG`.`MD5SUM`,
    `DATABASECHANGELOG`.`DESCRIPTION`,
    `DATABASECHANGELOG`.`COMMENTS`,
    `DATABASECHANGELOG`.`TAG`,
    `DATABASECHANGELOG`.`LIQUIBASE`,
    `DATABASECHANGELOG`.`CONTEXTS`,
    `DATABASECHANGELOG`.`LABELS`,
    `DATABASECHANGELOG`.`DEPLOYMENT_ID`
FROM `fineract_default`.`DATABASECHANGELOG`;

SELECT `DATABASECHANGELOGLOCK`.`ID`,
    `DATABASECHANGELOGLOCK`.`LOCKED`,
    `DATABASECHANGELOGLOCK`.`LOCKGRANTED`,
    `DATABASECHANGELOGLOCK`.`LOCKEDBY`
FROM `fineract_default`.`DATABASECHANGELOGLOCK`;

SHOW TABLES LIKE 'm_client_registered_user';

SELECT installed_rank, version, description, success
FROM flyway_schema_history
ORDER BY installed_rank DESC;

CREATE DATABASE fineract_default CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'fineract'@'%' IDENTIFIED BY 'fineract';
GRANT ALL PRIVILEGES ON fineract_default.* TO 'fineract'@'%';
FLUSH PRIVILEGES;

SELECT * FROM `eazy_bank`.`users_table`;
SELECT * FROM `finance`.`users_table_additional`;
SELECT * FROM m_client;


SET GLOBAL general_log = 'ON';
SET GLOBAL log_output = 'TABLE';
SELECT * FROM mysql.general_log ORDER BY event_time;

SELECT
    event_time,
    user_host,
    thread_id,
    server_id,
    command_type,
    CAST(argument AS CHAR(10000) CHARACTER SET utf8) AS query_text
FROM mysql.general_log
ORDER BY event_time;



SELECT * FROM m_loan;
SELECT * FROM m_product_loan;
SELECT * FROM m_client;

SELECT * FROM m_loan;
SELECT * FROM m_staff;

SELECT * FROM m_loan_repayment_schedule;