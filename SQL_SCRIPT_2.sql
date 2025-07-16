SELECT `expense_master`.`expense_master_id`,
    `expense_master`.`user_id`, 
    `expense_master`.`amount`,
    `expense_master`.`category`,    
    `expense_master`.`expense_date`,    
    `expense_master`.`expense_master_status`,
    `expense_master`.`daily_expense_status`,
	`expense_master`.`weekly_expense_status`,
	`expense_master`.`monthly_expense_status`,
    `expense_master`.`quarterly_expense_status`,
    `expense_master`.`yearly_expense_status`,  
    `expense_master`.`image_url`,
    `expense_master`.`address`,
    `expense_master`.`latitude`,
    `expense_master`.`longitude`,
    `expense_master`.`rating`,
    `expense_master`.`remarks`,
    `expense_master`.`tags`,
    `expense_master`.`title`,
    `expense_master`.`type`, 
    `expense_master`.`changed_at`,
    `expense_master`.`changed_by`,
    `expense_master`.`created_at`,
    `expense_master`.`created_by`
FROM `expense_master`.`expense_master`;

SELECT `kafka_messages_history`.`expense_history_id`,
	`kafka_messages_history`.`user_id`,
    `kafka_messages_history`.`expense_master_id`,
    `kafka_messages_history`.`expense_operation_name`,
    `kafka_messages_history`.`status`,
    `kafka_messages_history`.`timestamp`,
   `kafka_messages_history`.`created_by`
FROM `kafka_messages`.`kafka_messages_history`;

SELECT `daily_expense`.`daily_expense_id`,
	`daily_expense`.`expense_master_id`,
	`daily_expense`.`user_id`,	
    `daily_expense`.`amount`,
    `daily_expense`.`day`,    
    `daily_expense`.`month`,    
    `daily_expense`.`year`,
	`daily_expense`.`changed_at`,
    `daily_expense`.`changed_by`,
    `daily_expense`.`created_at`,
    `daily_expense`.`created_by`
FROM `aggregated_expense_master`.`daily_expense`;

SELECT `weekly_expense`.`weekly_expense_id`,    
    `weekly_expense`.`expense_master_id`,
    `weekly_expense`.`user_id`,
	`weekly_expense`.`amount`,
    `weekly_expense`.`week`,
    `weekly_expense`.`year`,
    `weekly_expense`.`changed_at`,
    `weekly_expense`.`changed_by`,
    `weekly_expense`.`created_at`,
    `weekly_expense`.`created_by`
FROM `aggregated_expense_master`.`weekly_expense`;

SELECT `monthly_expense`.`monthly_expense_id`,
    `monthly_expense`.`user_id`,
	`monthly_expense`.`amount`,
    `monthly_expense`.`month`,
    `monthly_expense`.`year`,
    `monthly_expense`.`changed_at`,
    `monthly_expense`.`changed_by`,
    `monthly_expense`.`created_at`,
    `monthly_expense`.`created_by`,
    `monthly_expense`.`expense_master_id`    
FROM `aggregated_expense_master`.`monthly_expense`;

SELECT `quarterly_expense`.`quarterly_expense_id`,
    `quarterly_expense`.`user_id`,
	`quarterly_expense`.`expense_master_id`,
    `quarterly_expense`.`amount`,
    `quarterly_expense`.`quarter`,
    `quarterly_expense`.`year`,
	`quarterly_expense`.`changed_at`,
    `quarterly_expense`.`changed_by`,
    `quarterly_expense`.`created_at`,
    `quarterly_expense`.`created_by`
FROM `aggregated_expense_master`.`quarterly_expense`;

SELECT `yearly_expense`.`yearly_expense_id`,
	`yearly_expense`.`expense_master_id`,
    `yearly_expense`.`user_id`,
    `yearly_expense`.`year`,
    `yearly_expense`.`amount`,
    `yearly_expense`.`changed_at`,
    `yearly_expense`.`changed_by`,
    `yearly_expense`.`created_at`,
    `yearly_expense`.`created_by`
FROM `aggregated_expense_master`.`yearly_expense`;
-- serverTimezone=UTC&useLegacyDatetimeCode=false&sessionVariables=time_zone=‘-00:00’