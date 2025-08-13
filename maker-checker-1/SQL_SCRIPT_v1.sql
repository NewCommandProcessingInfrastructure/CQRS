SELECT aud.id AS id,
aud.action_name AS actionName,
aud.entity_name AS entityName,
aud.resource_id AS resourceId,
aud.subresource_id AS subresourceId,
aud.client_id AS clientId,
aud.loan_id AS loanId,
mk.username AS maker,
aud.made_on_date AS madeOnDate,
aud.made_on_date_utc AS madeOnDateUTC,
aud.api_get_url AS resourceGetUrl,
ck.username AS checker,
aud.checked_on_date AS checkedOnDate,
aud.checked_on_date_utc AS checkedOnDateUTC,
ev.enum_message_property AS processingResult,
o.name AS officeName,
gl.level_name AS groupLevelName,
g.display_name AS groupName,
c.display_name AS clientName,
l.account_no AS loanAccountNo,
s.account_no AS savingsAccountNo,
aud.client_ip AS ip
FROM m_portfolio_command_source aud
LEFT JOIN m_appuser mk ON mk.id = aud.maker_id
LEFT JOIN m_appuser ck ON ck.id = aud.checker_id
LEFT JOIN m_office o ON o.id = aud.office_id
LEFT JOIN m_group g ON g.id = aud.group_id
LEFT JOIN m_group_level gl ON gl.id = g.level_id
LEFT JOIN m_client c ON c.id = aud.client_id
LEFT JOIN m_loan l ON l.id = aud.loan_id
LEFT JOIN m_savings_account s ON s.id = aud.savings_account_id
LEFT JOIN r_enum_value ev ON ev.enum_name = 'status'
AND ev.enum_id = aud.status;

SELECT * FROM m_portfolio_command_source;

SELECT `app_user`.`id`,
    `app_user`.`enabled`,
    `app_user`.`password`,
    `app_user`.`username`
FROM `maker_checker`.`app_user`;

SELECT `app_user_roles`.`user_id`,
    `app_user_roles`.`role`
FROM `maker_checker`.`app_user_roles`;

SELECT `transaction`.`id`,
    `transaction`.`amount`,
    `transaction`.`checker_username`,
    `transaction`.`created_at`,
    `transaction`.`description`,
    `transaction`.`maker_username`,
    `transaction`.`status`
FROM `maker_checker`.`transaction`;
