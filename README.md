# New Command Processing Infrastructure

1) Replicated the same logic from fineract-command (NewCommandProcessingInfrastructure).
2) Added (simulated database error) to see the retry mechanism for failure.
3) Added CommandPostProcessor to the pipeline for postprocessing the command updation request. Similar to existing logic:

### package org.apache.fineract.commands.service.SynchronousCommandProcessingService

```java
private CommandProcessingResult executeCommand(final CommandWrapper wrapper, final JsonCommand command,
            final boolean isApprovedByChecker, CommandSource commandSource, AppUser user, boolean isEnclosingTransaction) {

        final CommandProcessingResult result;
        try {
            result = commandSourceService.processCommand(findCommandHandler(wrapper), command, commandSource, user, isApprovedByChecker);
        } catch (Throwable t) { // NOSONAR
            RuntimeException mappable = ErrorHandler.getMappable(t);
            ErrorInfo errorInfo = commandSourceService.generateErrorInfo(mappable);
            Integer statusCode = errorInfo.getStatusCode();
            commandSource.setResultStatusCode(statusCode);
            commandSource.setResult(errorInfo.getMessage());
            if (statusCode != SC_OK) {
                commandSource.setStatus(ERROR);
            }
            if (!isEnclosingTransaction) { // TODO: temporary solution
                commandSourceService.saveResultNewTransaction(commandSource);
            }
            // must not throw any exception; must persist in new transaction as the current transaction was already
            // marked as rollback
            publishHookErrorEvent(wrapper, command, errorInfo);
            throw mappable;
        }

        Retry persistenceRetry = retryConfigurationAssembler.getRetryConfigurationForCommandResultPersistence();

        try {
            CommandSource finalCommandSource = commandSource;
            CommandSource savedCommandSource = persistenceRetry.executeSupplier(() -> {
                // Get metrics for logging
                long attemptNumber = persistenceRetry.getMetrics().getNumberOfFailedCallsWithRetryAttempt() + 1;

                // Critical: Refetch on retry attempts (not on first attempt)
                CommandSource currentSource = finalCommandSource;
                if (attemptNumber > 1) {
                    log.info("Retrying command result save - attempt {} for command ID {}", attemptNumber, finalCommandSource.getId());
                    currentSource = commandSourceService.getCommandSource(finalCommandSource.getId());
                }

                // Update command source with results
                currentSource.setResultStatusCode(SC_OK);
                currentSource.updateForAudit(result);
                currentSource.setResult(toApiResultJsonSerializer.serializeResult(result));
                currentSource.setStatus(PROCESSED);

                // Return saved command source
                return commandSourceService.saveResultSameTransaction(currentSource);
            });

            // Command successfully saved
            storeCommandIdInContext(savedCommandSource);

        } catch (Exception e) {
            // After all retries have been exhausted
            log.error("Failed to persist command result after multiple retries for command ID {}", commandSource.getId(), e);
            throw new CommandResultPersistenceException("Failed to persist command result after multiple retries", e);
        }

        result.setRollbackTransaction(null);
        publishHookEvent(wrapper.entityName(), wrapper.actionName(), command, result); // TODO must be performed in a
        // new transaction
        return result;
    }
```
