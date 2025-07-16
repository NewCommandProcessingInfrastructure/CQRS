SELECT `m_tax_component`.`id`,
    `m_tax_component`.`name`,
    `m_tax_component`.`percentage`,
    `m_tax_component`.`debit_account_type_enum`,
    `m_tax_component`.`debit_account_id`,
    `m_tax_component`.`credit_account_type_enum`,
    `m_tax_component`.`credit_account_id`,
    `m_tax_component`.`start_date`,
    `m_tax_component`.`createdby_id`,
    `m_tax_component`.`created_date`,
    `m_tax_component`.`lastmodifiedby_id`,
    `m_tax_component`.`lastmodified_date`
FROM `fineract_default`.`m_tax_component`;

select tc.id as id, tc.name as name  from m_tax_component tc;

select  f.id as id, f.name as name, f.external_id as externalId from m_fund f  order by f.name;

select c.code as code, c.name as name, c.decimal_places as decimalPlaces,c.currency_multiplesof as inMultiplesOf, c.display_symbol as displaySymbol, c.internationalized_name_code as nameCode from m_organisation_currency c order by c.name;
select c.code as code, c.name as name, c.decimal_places as decimalPlaces,c.currency_multiplesof as inMultiplesOf, c.display_symbol as displaySymbol, c.internationalized_name_code as nameCode from m_currency c order by c.name;

SELECT * FROM m_organisation_currency;
SELECT * FROM m_currency;

SELECT 
    *
FROM
    m_currency
WHERE
    code = 'ABC';


