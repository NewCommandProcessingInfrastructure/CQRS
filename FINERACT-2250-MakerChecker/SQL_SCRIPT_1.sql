SELECT * FROM m_portfolio_command_source;


SELECT * FROM m_portfolio_command_source WHERE action_name = "ACTIVATE";
SELECT * FROM m_portfolio_command_source WHERE id = 83474;

SELECT * FROM m_permission;
SELECT * FROM m_selfservice_user_client_mapping;
SELECT * FROM m_permission WHERE entity_name = 'CLIENT';
SELECT * FROM c_configuration;

UPDATE m_portfolio_command_source SET status = 2 WHERE status <> 2;

SELECT application_table_name FROM x_registered_table WHERE registered_table_name = 'm_client_registered_user';

SELECT * FROM x_registered_table WHERE application_table_name = 'm_client';

SELECT * FROM x_registered_table;

SELECT application_table_name FROM x_registered_table;

SELECT * FROM m_client WHERE id = 5403;
SELECT * FROM m_client;

select o.id as officeId, null as groupId, c.id as clientId, null as savingsId, null as loanId, null as transactionId, null as entityId from m_client c join m_office o on o.id = c.office_id and o.hierarchy like '.%'  where c.id = 5392;

SELECT * FROM m_client c 
JOIN m_office o ON o.id = c.office_id 
AND o.hierarchy like '.%';
-- WHERE c.id = 5392;

SELECT * FROM m_role;
SELECT * FROM m_loan;

SELECT * FROM m_role_permission;

SELECT * FROM m_appuser;
SELECT * FROM m_permission;
SELECT * FROM m_selfservice_user_client_mapping;
SELECT * FROM m_permission WHERE entity_name = 'CLIENT';

SELECT * FROM m_appuser_role;

SELECT * FROM m_charge;
SELECT * FROM m_product_loan;

SELECT can_define_fixed_emi_amount FROM m_product_loan;

SELECT * FROM m_loan_2afzn;
SELECT * FROM m_loan_32EG7;
SELECT * FROM m_loan_AKYIX;
SELECT * FROM m_client_SF4BZ; -- 1122 1124 1125


SELECT * FROM job;
SELECT job.display_name FROM job job WHERE job.currently_running=true AND job.updates_allowed=false;

SELECT * FROM m_command;