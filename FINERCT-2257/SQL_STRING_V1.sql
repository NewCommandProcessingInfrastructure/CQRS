select g.id as id, 
g.loan_id as loanId, 
g.client_reln_cv_id as clientRelationshipTypeId, 
g.entity_id as entityId, 
g.type_enum guarantorType, 
g.firstname as firstname, 
g.lastname as lastname, 
g.dob as dateOfBirth, 
g.address_line_1 as addressLine1, 
g.address_line_2 as addressLine2, 
g.city as city, 
g.state as state, 
g.country as country, 
g.zip as zip, 
g.house_phone_number as housePhoneNumber, 
g.mobile_number as mobilePhoneNumber, 
g.comment as comment,  
g.is_active as guarantorStatus, 
cv.code_value as typeName, 
gfd.amount, 
gfd.id as gfdId, 
gfd.amount as amount, 
gfd.amount_released_derived as amountReleased,
gfd.amount_remaining_derived as amountRemaining, 
gfd.amount_transfered_derived as amountTransfered, 
gfd.status_enum as statusEnum, 
sa.id as savingsId, 
sa.account_no as accountNumber, 
gt.id as gtId, 
gt.is_reversed as reversed, 
oht.id as ohtId, 
oht.amount as transactionAmount, 
oht.transaction_type_enum as transactionType, 
oht.transaction_date as transactionDate, 
oht.is_reversed as transactionReversed 
FROM m_guarantor g 
left JOIN m_code_value cv on g.client_reln_cv_id = cv.id 
left JOIN m_guarantor_funding_details gfd on g.id = gfd.guarantor_id 
left JOIN m_portfolio_account_associations aa on gfd.account_associations_id = aa.id 
and aa.is_active = true 
and aa.association_type_enum = '2' 
left JOIN m_savings_account sa on sa.id = aa.linked_savings_account_id 
left join m_guarantor_transaction gt on gt.guarantor_fund_detail_id = gfd.id 
left join m_deposit_account_on_hold_transaction oht on oht.id = gt.deposit_on_hold_transaction_id 
where g.loan_id = '1013' 
and g.id IN (16,17,18,19,20,21,22,23,24,25) 
group by g.id, gfd.id, gt.id, sa.id, oht.id, cv.id 
order by g.id;

SELECT * FROM m_portfolio_account_associations;

SELECT * FROM m_code_value;

SELECT * FROM m_client;

SELECT * FROM m_guarantor;
SELECT * FROM m_guarantor_funding_details;
SELECT * FROM m_guarantor_transaction;

SELECT * FROM m_loan;
SELECT * FROM m_loan WHERE id=4236;
SELECT * FROM m_loan_transaction;
SELECT * FROM m_loan WHERE external_id="e001aa31-5d8f-4b2e-892a-87add0abf348";
SELECT * FROM m_portfolio_account_associations;

SELECT * FROM m_guarantor WHERE id = 366;
SELECT * FROM m_guarantor_funding_details WHERE guarantor_id = 319;
SELECT * FROM m_guarantor_transaction WHERE guarantor_fund_detail_id IN (
    SELECT id FROM m_guarantor_funding_details WHERE guarantor_id = 200
);

SELECT `id`, 
`is_active`, 
`address_line_1`, 
`address_line_2`, 
`city`, 
`comment`, 
`country`, 
`dob`, 
`entity_id`, 
`firstname`, 
`type_enum`, 
`house_phone_number`, 
`lastname`, 
`mobile_number`, 
`state`, 
`zip`, 
`client_reln_cv_id`, 
`loan_id` 
FROM `m_guarantor` 
WHERE ((`loan_id` = 1013) 
AND (`id` = 25));

SELECT `id`, 
`amount`, 
`amount_released_derived`, 
`amount_remaining_derived`, 
`amount_transfered_derived`, 
`status_enum`, 
`account_associations_id`, 
`guarantor_id` 
FROM `m_guarantor_funding_details` 
WHERE (`guarantor_id` = 25);

SELECT * FROM m_client;

SELECT 
    g.id AS guarantor_id,
    g.firstname,
    g.lastname,
    g.mobile_number,
    g.zip,

    gfd.id AS funding_detail_id,
    gfd.amount,
    gfd.amount_released_derived,
    gfd.amount_remaining_derived,
    gfd.status_enum,

    gft.id AS transaction_id,
    gft.is_reversed,
    gft.loan_transaction_id,
    gft.deposit_on_hold_transaction_id

FROM m_guarantor g
INNER JOIN m_guarantor_funding_details gfd ON g.id = gfd.guarantor_id
INNER JOIN m_guarantor_transaction gft ON gfd.id = gft.guarantor_fund_detail_id
WHERE g.id = 25;

SELECT * FROM m_client;
SELECT * FROM m_client_transfer_details;
SELECT * FROM m_savings_account;

SELECT * FROM m_product_loan;

SELECT * FROM m_product_loan_guarantee_details;

SELECT * FROM m_product_loan_charge;

SELECT * FROM m_loan;
SELECT * FROM m_working_days;
SELECT * FROM m_holiday;
SELECT * FROM m_payment_detail;

SELECT * FROM m_savings_account;

SELECT * FROM m_loan_transaction;
SELECT * FROM m_loan_transaction_relation;
SELECT * FROM m_loan_transaction_repayment_schedule_mapping;

SELECT * FROM m_loan_tranche_charges;

SELECT * FROM c_configuration; -- reschedule-repayments-on-holidays -- enable-auto-generated-external-id

SELECT * FROM c_configuration WHERE name IN('enable-auto-generated-external-id');

SELECT * FROM m_appuser;