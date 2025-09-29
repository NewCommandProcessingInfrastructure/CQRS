SELECT * FROM m_portfolio_command_source;
SELECT * FROM m_portfolio_account_associations;

SELECT * FROM m_account_transfer_details;
SELECT * FROM m_account_transfer_transaction;
SELECT * FROM m_account_transfer_standing_instructions;
SELECT * FROM m_account_transfer_standing_instructions_history;

SELECT * FROM m_office;
SELECT * FROM m_savings_product;
SELECT * FROM m_savings_account;

SELECT * FROM m_permission p
WHERE LOWER(TRIM(p.code)) = LOWER(TRIM('CREATE_STANDINGINSTRUCTION')); -- UPDATE_STANDINGINSTRUCTION

SELECT * FROM m_permission p
WHERE LOWER(TRIM(p.code)) = LOWER(TRIM('UPDATE_STANDINGINSTRUCTION'));

SELECT * FROM m_permission p
WHERE LOWER(TRIM(p.code)) = LOWER(TRIM('DELETE_STANDINGINSTRUCTION'));

SELECT * FROM scheduler_detail;
SELECT * FROM BATCH_JOB_EXECUTION;
SELECT * FROM BATCH_JOB_EXECUTION_CONTEXT;
SELECT * FROM BATCH_JOB_EXECUTION_PARAMS;
SELECT * FROM BATCH_JOB_EXECUTION_SEQ;

SELECT * FROM BATCH_STEP_EXECUTION;
SELECT * FROM BATCH_STEP_EXECUTION_CONTEXT;
SELECT * FROM BATCH_STEP_EXECUTION_SEQ;

SELECT * FROM command;
SELECT * FROM job;
SELECT * FROM job_parameters;
SELECT * FROM job_run_history;

-- Tasklet Standing Instructions
SELECT atsi.id AS id, 
atsi.name AS name, 
atsi.priority AS priority,
atsi.status AS status, 
atsi.instruction_type AS instructionType,
atsi.amount AS amount,
atsi.valid_from AS validFrom, 
atsi.valid_till AS validTill,
atsi.recurrence_type AS recurrenceType, 
atsi.recurrence_frequency AS recurrenceFrequency,
atsi.recurrence_interval AS recurrenceInterval, 
atsi.recurrence_on_day AS recurrenceOnDay,
atsi.recurrence_on_month AS recurrenceOnMonth,
atd.id AS accountDetailId,
atd.transfer_type AS transferType,
fromoff.id AS fromOfficeId, 
fromoff.name AS fromOfficeName,
tooff.id AS toOfficeId, 
tooff.name AS toOfficeName,
fromclient.id AS fromClientId, 
fromclient.display_name AS fromClientName,
toclient.id AS toClientId, 
toclient.display_name AS toClientName,
fromsavacc.id AS fromSavingsAccountId, 
fromsavacc.account_no AS fromSavingsAccountNo,
fromsp.id AS fromProductId, 
fromsp.name AS fromProductName, 
fromloanacc.id AS fromLoanAccountId, 
fromloanacc.account_no AS fromLoanAccountNo,
fromlp.id AS fromLoanProductId, 
fromlp.name AS fromLoanProductName,
tosavacc.id AS toSavingsAccountId, 
tosavacc.account_no AS toSavingsAccountNo,
tosp.id AS toProductId, 
tosp.name AS toProductName, 
toloanacc.id AS toLoanAccountId, 
toloanacc.account_no AS toLoanAccountNo, 
tolp.id AS toLoanProductId, 
tolp.name AS toLoanProductName 
FROM m_account_transfer_standing_instructions atsi 
JOIN m_account_transfer_details atd ON atd.id = atsi.account_transfer_details_id 
JOIN m_office fromoff ON fromoff.id = atd.from_office_id 
JOIN m_office tooff ON tooff.id = atd.to_office_id 
JOIN m_client fromclient ON fromclient.id = atd.from_client_id 
JOIN m_client toclient ON toclient.id = atd.to_client_id 
LEFT JOIN m_savings_account fromsavacc ON fromsavacc.id = atd.from_savings_account_id 
LEFT JOIN m_savings_product fromsp ON fromsavacc.product_id = fromsp.id 
LEFT JOIN m_loan fromloanacc ON fromloanacc.id = atd.from_loan_account_id 
LEFT JOIN m_product_loan fromlp ON fromloanacc.product_id = fromlp.id 
LEFT JOIN m_savings_account tosavacc ON tosavacc.id = atd.to_savings_account_id 
LEFT JOIN m_savings_product tosp ON tosavacc.product_id = tosp.id 
LEFT JOIN m_loan toloanacc ON toloanacc.id = atd.to_loan_account_id 
LEFT JOIN m_product_loan tolp ON toloanacc.product_id = tolp.id 
WHERE atsi.status=1 AND DATE('2025-09-21') >= atsi.valid_from 
AND (atsi.valid_till IS NULL or DATE('2025-09-21') < atsi.valid_till) 
AND (atsi.last_run_date <> DATE('2025-09-21') 
OR atsi.last_run_date IS NULL) 
ORDER BY atsi.priority DESC;

SELECT * FROM m_account_transfer_standing_instructions;

