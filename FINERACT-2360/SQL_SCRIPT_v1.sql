SELECT * FROM m_portfolio_command_source;

SELECT * FROM m_appuser;
SELECT * FROM m_permission;
SELECT * FROM m_permission WHERE code IN ('CREATE_LOAN');
SELECT * FROM m_role;
SELECT * FROM m_role_permission;
SELECT * FROM m_appuser_role;

SELECT * FROM m_client;

SELECT * FROM m_group WHERE id IN (261);

SELECT * FROM m_code_value;
SELECT * FROM m_code;

SELECT id, code_id, code_value, order_position 
FROM m_code_value 
WHERE code_id = (SELECT id FROM m_code WHERE code_name = 'GroupClosureReason') AND id IN (188);

SELECT id FROM m_code WHERE code_name = 'GroupClosureReason';

SELECT * FROM m_code;

SELECT * FROM m_appuser WHERE username='maker';

SELECT u.id AS user_id,
       u.username,
       r.id AS role_id,
       r.name AS role_name,
       p.id AS permission_id,
       p.code AS permission_code
FROM m_appuser u
INNER JOIN m_appuser_role ur ON u.id = ur.appuser_id
INNER JOIN m_role r ON ur.role_id = r.id
INNER JOIN m_role_permission rp ON r.id = rp.role_id
INNER JOIN m_permission p ON rp.permission_id = p.id
WHERE u.username = 'maker';

SELECT * FROM m_calendar;

-- MapSqlParameterSource {dueDate=2025-09-01, groupId=1, officeHierarchy=.%, entityTypeId=2}

-- Group Generate Collection Sheet
SELECT loandata.*, SUM(lc.amount_outstanding_derived) AS chargesDue 
FROM (SELECT gp.display_name AS groupName, 
gp.id AS groupId, 
cl.display_name AS clientName, 
sf.id AS staffId, 
sf.display_name AS staffName, 
gl.id AS levelId, 
gl.level_name AS levelName, 
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
(CASE WHEN ln.loan_status_id = 200 THEN ln.principal_amount ELSE NULL END) AS disbursementAmount, 
SUM(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.principal_amount ELSE 0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.principal_completed_derived ELSE 0.0 END), 0.0)) AS principalDue, 
ln.principal_repaid_derived AS principalPaid, 
SUM(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_amount ELSE  0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_completed_derived ELSE 0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_waived_derived ELSE 0.0 END), 0.0)) AS interestDue, 
ln.interest_repaid_derived AS interestPaid, 
SUM(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.fee_charges_amount ELSE  0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.fee_charges_completed_derived ELSE 0.0 END), 0.0)) AS feeDue, 
ln.fee_charges_repaid_derived AS feePaid, 
ca.attendance_type_enum AS attendanceTypeId 
FROM m_group gp 
LEFT JOIN m_office ofc ON ofc.id = gp.office_id 
AND ofc.hierarchy LIKE '.%' 
JOIN m_group_level gl ON gl.id = gp.level_Id 
LEFT JOIN m_staff sf ON sf.id = gp.staff_id 
JOIN m_group_client gc ON gc.group_id = gp.id 
JOIN m_client cl ON cl.id = gc.client_id 
LEFT JOIN m_loan ln ON cl.id = ln.client_id 
AND ln.group_id=gp.id 
AND ln.group_id IS NOT NULL AND ( ln.loan_status_id = 300 ) 
LEFT JOIN m_product_loan pl ON pl.id = ln.product_id 
LEFT JOIN m_currency rc on rc.`code` = ln.currency_code 
LEFT JOIN m_loan_repayment_schedule ls ON ls.loan_id = ln.id 
AND ls.completed_derived = 0 
AND ls.duedate <= '2025-09-01' 
LEFT JOIN m_calendar_instance ci ON gp.parent_id = ci.entity_id 
AND ci.entity_type_enum = 2 
LEFT JOIN m_meeting mt ON ci.id = mt.calendar_instance_id 
AND mt.meeting_date = '2025-09-01' 
LEFT JOIN m_client_attendance ca ON ca.meeting_id=mt.id 
AND ca.client_id=cl.id 
WHERE gp.id = 1
AND (ln.loan_status_id != 200 AND ln.loan_status_id != 100) 
AND (gp.status_enum = 300 OR (gp.status_enum = 600 AND gp.closedon_date >= '2025-09-01')) 
AND (cl.status_enum = 300 OR (cl.status_enum = 600 AND cl.closedon_date >= '2025-09-01')) 
GROUP BY gp.id, cl.id, ln.id, ca.attendance_type_enum 
ORDER BY gp.id , cl.id , ln.id ) loandata 
LEFT JOIN m_loan_charge lc ON lc.loan_id = loandata.loanId 
AND lc.is_paid_derived = FALSE AND lc.is_active = TRUE 
AND ( lc.due_for_collection_as_of_date  <= '2025-09-01' OR lc.charge_time_enum = 1) 
GROUP BY loandata.groupId, 
loandata.clientId, 
loandata.loanId, 
loandata.principalDue, 
loandata.interestDue, 
loandata.feeDue, 
loandata.attendanceTypeId 
ORDER BY loandata.groupId, loandata.clientId, loandata.loanId;

-- Center Generate Collection Sheet
SELECT loandata.*, SUM(lc.amount_outstanding_derived) AS chargesDue 
FROM (SELECT gp.display_name AS groupName, 
gp.id AS groupId, 
cl.display_name AS clientName, 
sf.id AS staffId, 
sf.display_name AS staffName, 
gl.id AS levelId, 
gl.level_name AS levelName, 
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
(CASE WHEN ln.loan_status_id = 200 THEN ln.principal_amount ELSE NULL END) AS disbursementAmount, 
SUM(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.principal_amount ELSE 0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.principal_completed_derived ELSE 0.0 END), 0.0)) AS principalDue, 
ln.principal_repaid_derived AS principalPaid, 
SUM(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_amount ELSE  0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_completed_derived ELSE 0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.interest_waived_derived ELSE 0.0 END), 0.0)) AS interestDue, 
ln.interest_repaid_derived AS interestPaid, 
SUM(COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.fee_charges_amount ELSE  0.0 END), 0.0) - COALESCE((CASE WHEN ln.loan_status_id = 300 THEN ls.fee_charges_completed_derived ELSE 0.0 END), 0.0)) AS feeDue, 
ln.fee_charges_repaid_derived AS feePaid, 
ca.attendance_type_enum AS attendanceTypeId 
FROM m_group gp 
LEFT JOIN m_office ofc ON ofc.id = gp.office_id 
AND ofc.hierarchy LIKE '.%' 
JOIN m_group_level gl ON gl.id = gp.level_Id 
LEFT JOIN m_staff sf ON sf.id = gp.staff_id 
JOIN m_group_client gc ON gc.group_id = gp.id 
JOIN m_client cl ON cl.id = gc.client_id 
LEFT JOIN m_loan ln ON cl.id = ln.client_id 
AND ln.group_id=gp.id 
AND ln.group_id IS NOT NULL AND ( ln.loan_status_id = 300 ) 
LEFT JOIN m_product_loan pl ON pl.id = ln.product_id 
LEFT JOIN m_currency rc on rc.`code` = ln.currency_code 
LEFT JOIN m_loan_repayment_schedule ls ON ls.loan_id = ln.id 
AND ls.completed_derived = 0 
AND ls.duedate <= '2025-09-01' 
LEFT JOIN m_calendar_instance ci ON gp.parent_id = ci.entity_id 
AND ci.entity_type_enum = 2 
LEFT JOIN m_meeting mt ON ci.id = mt.calendar_instance_id 
AND mt.meeting_date = '2025-09-01' 
LEFT JOIN m_client_attendance ca ON ca.meeting_id=mt.id 
AND ca.client_id=cl.id 
WHERE gp.parent_id = 1
AND (ln.loan_status_id != 200 AND ln.loan_status_id != 100) 
AND (gp.status_enum = 300 OR (gp.status_enum = 600 AND gp.closedon_date >= '2025-09-01')) 
AND (cl.status_enum = 300 OR (cl.status_enum = 600 AND cl.closedon_date >= '2025-09-01')) 
GROUP BY gp.id, cl.id, ln.id, ca.attendance_type_enum 
ORDER BY gp.id , cl.id , ln.id ) loandata 
LEFT JOIN m_loan_charge lc ON lc.loan_id = loandata.loanId 
AND lc.is_paid_derived = FALSE AND lc.is_active = TRUE 
AND (lc.due_for_collection_as_of_date  <= '2025-09-01' OR lc.charge_time_enum = 1) 
GROUP BY loandata.groupId,
loandata.clientId,
loandata.loanId,
loandata.principalDue,
loandata.interestDue,
loandata.feeDue,
loandata.attendanceTypeId
ORDER BY loandata.groupId, loandata.clientId, loandata.loanId;

SELECT * FROM m_calendar;
SELECT * FROM m_group;
SELECT * FROM m_group_level; 
SELECT * FROM m_group_client; 
SELECT * FROM m_client; 
SELECT * FROM m_loan;
SELECT * FROM m_client;
SELECT * FROM m_savings_account;
SELECT * FROM m_savings_product;
SELECT * FROM m_calendar_instance;

SELECT * FROM m_loan WHERE loan_status_id = 300;

SELECT gp.display_name As groupName, 
gp.id As groupId, 
cl.display_name As clientName, 
cl.id As clientId, 
sf.id As staffId, 
sf.display_name As staffName, 
gl.id As levelId, 
gl.level_name As levelName, 
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
(CASE WHEN sa.deposit_type_enum=100 THEN 'Saving Deposit' ELSE (CASE WHEN sa.deposit_type_enum=300 THEN 'Recurring Deposit' ELSE 'Current Deposit' END) END) as depositAccountType, 
rc.internationalized_name_code as currencyNameCode, 
SUM(COALESCE(mss.deposit_amount,0) - coalesce(mss.deposit_amount_completed_derived,0)) as dueAmount FROM m_group gp 
LEFT JOIN m_office ofc ON ofc.id = gp.office_id 
AND ofc.hierarchy like '.%'
JOIN m_group_level gl ON gl.id = gp.level_Id 
LEFT JOIN m_staff sf ON sf.id = gp.staff_id 
JOIN m_group_client gc ON gc.group_id = gp.id 
JOIN m_client cl ON cl.id = gc.client_id 
JOIN m_savings_account sa ON sa.client_id=cl.id 
and sa.status_enum=300 
JOIN m_savings_product sp ON sa.product_id=sp.id 
LEFT JOIN m_deposit_account_recurring_detail dard ON sa.id = dard.savings_account_id 
AND dard.is_mandatory = true 
AND dard.is_calendar_inherited = true 
LEFT JOIN m_mandatory_savings_schedule mss ON mss.savings_account_id=sa.id 
AND mss.duedate <= '2025-09-01' 
LEFT JOIN m_currency rc on rc.`code` = sa.currency_code 
WHERE gp.id = 1 
and (gp.status_enum = 300 
or (gp.status_enum = 600 
and gp.closedon_date >= '2025-09-01')) 
and (cl.status_enum = 300 or (cl.status_enum = 600 
and cl.closedon_date >= '2025-09-01')) 
GROUP BY gp.id ,cl.id , sa.id 
ORDER BY gp.id , cl.id , sa.id;

SELECT * FROM m_payment_type;

-- Create Test Data Set For Above.
SELECT * FROM m_loan WHERE id=4277;
SELECT * FROM m_loan WHERE loan_status_id=300;
SELECT * FROM m_loan;
SELECT * FROM m_group;
SELECT * FROM m_group_client;
SELECT * FROM m_group_level;
SELECT * FROM m_group_roles;
SELECT * FROM m_client WHERE id=197;

-- Center
SELECT * FROM m_group WHERE level_id=1;

-- Group 
SELECT * FROM m_group WHERE level_id=2;

SELECT * FROM m_client;

SELECT * FROM m_client WHERE id IN (196,197,198,199,200,201);
SELECT * FROM m_client WHERE status_enum = 300 AND id IN (198,199,200,201); -- 198 199 200 201

SELECT 
    g.id AS groupId,
    g.display_name AS groupName,
    c.id AS clientId,
    c.display_name AS clientName,
    gl.id AS groupLevelId,
    gl.level_name AS groupLevelName
FROM m_group g
INNER JOIN m_group_client gc ON g.id = gc.group_id
INNER JOIN m_client c ON gc.client_id = c.id
INNER JOIN m_group_level gl ON g.level_id = gl.id
WHERE g.id = 1;


-- Group: 1 clients: 1
--  Client: 196 savings: 1

-- Group: 1 clients: 1
--  Client: 196 savings: 1


-- '1', '5382'

-- '1', '5384'

-- '1', '5386'

-- '1', '5387'

-- '1', '5389'

-- '1', '5391'

-- '1', '5392'

-- '1', '5393'

-- '1', '5394'

-- '1', '5395'

-- '1', '5396'

-- '1', '5397'

-- '1', '5400'

-- '1', '5401'
-- '1', '5402'
-- '1', '5403'
-- '1', '5404'
-- '1', '5405'
-- '1', '5406'
-- '1', '5407'
-- '1', '5408'
-- '1', '5409'
-- '1', '5410'
-- '1', '5411'
-- '1', '5412'
-- '1', '5413'
-- '1', '5414'
-- '1', '5415'
-- '1', '5416'
-- '1', '5417'
-- '1', '5418'
-- '1', '5426'
-- '1', '5432'
-- '1', '5433'
-- '1', '5434'
-- '1', '5435'

SELECT * FROM m_client WHERE id IN (5382,5384,5386,5387,5389,5391,5392,5393,5394,5395,5396,5397,5400,5401,5402,5403,5404,5405,5406,5407,5408,5409,5410,5411,5412,5413,5414,5415,5416,5417,5418,5426,5432,5433,5434,5435);

SELECT * FROM m_savings_account WHERE status_enum=300;
SELECT * FROM m_savings_account;

SELECT * FROM m_savings_account WHERE id = 2572;
SELECT * FROM m_savings_account_transaction;

SELECT * FROM m_calendar WHERE id=1;
SELECT * FROM m_calendar;

SELECT * FROM m_group WHERE id=1;
SELECT * FROM m_client;

SELECT * FROM m_client WHERE id IN (5474,5475);

SELECT * FROM m_calendar_instance;

SELECT * FROM m_savings_account_transaction;

SELECT * FROM m_command;
