SELECT loandata.*, sum(lc.amount_outstanding_derived) AS chargesDue 
FROM (SELECT cl.display_name AS clientName, 
cl.id AS clientId, 
ln.id AS loanId, 
ln.account_no AS accountId, 
ln.loan_status_id AS accountStatusId, 
pl.short_name AS productShortName, 
ln.product_id AS productId, 
ln.currency_code AS currencyCode, 
ln.currency_digits AS currencyDigits, 
ln.currency_multiplesof AS inMultiplesOf, 
rc.`name` AS currencyName, 
rc.display_symbol AS currencyDisplaySymbol, 
rc.internationalized_name_code AS currencyNameCode, 
(CASE WHEN ln.loan_status_id = 200 THEN ln.principal_amount ELSE null END) AS disbursementAmount, 
sum(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.principal_amount ELSE 0.0 END), 0.0) - 
	COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.principal_completed_derived ELSE 0.0 END), 0.0)) AS principalDue, 
    ln.principal_repaid_derived AS principalPaid, 
    sum(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_amount ELSE 0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_completed_derived ELSE 0.0 END), 0.0)) AS interestDue, 
    ln.interest_repaid_derived AS interestPaid, 
    sum(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.fee_charges_amount ELSE 0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.fee_charges_completed_derived ELSE 0.0 END), 0.0)) AS feeDue, 
    ln.fee_charges_repaid_derived AS feePaid 
    FROM m_loan ln 
    JOIN m_client cl ON cl.id = ln.client_id 
    LEFT JOIN m_office offi ON offi.id = cl.office_id AND offi.hierarchy like '.%' 
    LEFT JOIN m_product_loan pl ON pl.id = ln.product_id 
    LEFT JOIN m_currency rc ON rc.`code` = ln.currency_code 
    JOIN m_loan_repayment_schedule ls ON ls.loan_id = ln.id 
    AND ls.completed_derived = 0 AND ls.duedate <= 2025-07-10
    WHERE offi.id = 1 AND (ln.loan_status_id = 300) AND ln.group_id IS null 
    GROUP BY cl.id , ln.id ORDER BY cl.id , ln.id ) loandata 
    LEFT JOIN m_loan_charge lc ON lc.loan_id = loandata.loanId AND lc.is_paid_derived = false AND lc.is_active = true AND ( lc.due_for_collection_as_of_date  <= 2024-07-10 OR lc.charge_time_enum = 1) 
    GROUP BY loandata.clientId, loandata.loanId 
    ORDER BY loandata.clientId, loandata.loanId;
    
    SELECT * FROM m_office;
    SELECT * FROM m_product_loan;
    
SELECT loandata.*, 
sum(lc.amount_outstanding_derived) as chargesDue 
from (
SELECT cl.display_name As clientName, 
cl.id As clientId, 
ln.id As loanId, 
ln.account_no As accountId, 
ln.loan_status_id As accountStatusId, 
pl.short_name As productShortName, 
ln.product_id As productId, 
ln.currency_code as currencyCode, 
ln.currency_digits as currencyDigits, 
ln.currency_multiplesof as inMultiplesOf, 
rc.`name` as currencyName, 
rc.display_symbol as currencyDisplaySymbol, 
rc.internationalized_name_code as currencyNameCode, (
CASE WHEN ln.loan_status_id = 200 
THEN ln.principal_amount ELSE null END) 
As disbursementAmount, 
sum(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.principal_amount ELSE 0.0 END), 0.0) - 
COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.principal_completed_derived ELSE 0.0 END), 0.0)) 
As principalDue, 
ln.principal_repaid_derived As principalPaid, 
sum(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_amount ELSE 0.0 END), 0.0) - 
COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_completed_derived ELSE 0.0 END), 0.0)) 
As interestDue, 
ln.interest_repaid_derived As interestPaid, 
sum(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.fee_charges_amount ELSE 0.0 END), 0.0) - 
COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.fee_charges_completed_derived ELSE 0.0 END), 0.0)) As feeDue, 
ln.fee_charges_repaid_derived As feePaid 
FROM m_loan ln 
JOIN m_client cl ON cl.id = ln.client_id 
LEFT JOIN m_office ofc ON ofc.id = cl.office_id 
AND ofc.hierarchy like '.%'
LEFT JOIN m_product_loan pl ON pl.id = ln.product_id 
LEFT JOIN m_currency rc on rc.`code` = ln.currency_code 
JOIN m_loan_repayment_schedule ls ON ls.loan_id = ln.id 
AND ls.completed_derived = 0 
AND ls.duedate <= 2025-07-10
where ofc.id = 1
and (ln.loan_status_id = 300) 
and ln.group_id is null GROUP BY cl.id , 
ln.id ORDER BY cl.id , ln.id ) loandata 
LEFT JOIN m_loan_charge lc ON lc.loan_id = loandata.loanId 
AND lc.is_paid_derived = false 
AND lc.is_active = true 
AND ( lc.due_for_collection_as_of_date <= 2025-07-10 OR lc.charge_time_enum = 1) 
GROUP BY loandata.clientId, loandata.loanId 
ORDER BY loandata.clientId, loandata.loanId;



SELECT (
CASE WHEN sa.deposit_type_enum=100 THEN 'Saving Deposit' 
ELSE (CASE WHEN sa.deposit_type_enum=300 THEN 'Recurring Deposit' ELSE 'Current Deposit' END) END) 
as depositAccountType, 
cl.display_name As clientName, 
cl.id As clientId, 
sa.id As savingsId, 
sa.account_no As accountId, 
sa.status_enum As accountStatusId, 
sp.short_name As productShortName, 
sp.id As productId, 
sa.currency_code as currencyCode, 
sa.currency_digits as currencyDigits, 
sa.currency_multiplesof as inMultiplesOf, 
rc.`name` as currencyName, 
rc.display_symbol as currencyDisplaySymbol, 
rc.internationalized_name_code as currencyNameCode, 
SUM(COALESCE(mss.deposit_amount,0) - coalesce(mss.deposit_amount_completed_derived,0)) as dueAmount 
FROM m_savings_account sa 
JOIN m_client cl ON cl.id = sa.client_id 
JOIN m_savings_product sp ON sa.product_id=sp.id 
LEFT JOIN m_deposit_account_recurring_detail dard ON sa.id = dard.savings_account_id 
AND dard.is_mandatory = true 
AND dard.is_calendar_inherited = false 
LEFT JOIN m_mandatory_savings_schedule mss ON mss.savings_account_id=sa.id 
AND mss.completed_derived = 0 
AND mss.duedate <= ? 
LEFT JOIN m_office ofc ON ofc.id = cl.office_id AND ofc.hierarchy like ? LEFT JOIN m_currency rc on rc.`code` = sa.currency_code WHERE sa.status_enum=300 and sa.group_id is null and sa.deposit_type_enum in (100,300,400) and (cl.status_enum = 300 or (cl.status_enum = 600 and cl.closedon_date >= ?)) and ofc.id = ? GROUP BY cl.id , sa.id ORDER BY cl.id , sa.id;

SELECT * FROM m_portfolio_command_source;