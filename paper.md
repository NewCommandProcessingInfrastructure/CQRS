title: 'Extensible Orchestrated Interceptor Workflows for High-Performance Processing'
tags:
  - Java
  - cqrs
  - pipelines
  - database logging
  - persistence
authors:
  - name: Kapil Panchal
    affiliation: "1"
affiliations:
  - name: Independent Researcher
   index: 1
date: December 2025

# Introduction

In modern enterprise systems, the complexity of command processing often
leads to challenges in scalability, maintainability, and performance.
Traditional implementations of Command Query Responsibility Segregation
(CQRS) and event-driven architectures frequently intertwine business
logic with cross-cutting concerns such as logging, retry mechanisms, and
audit propagation. This entanglement results in tightly coupled
components, making systems brittle and difficult to evolve. As systems
scale, the need for a more modular and adaptable approach becomes
evident.

The motivation for this work stems from the necessity to decouple
operational concerns from core business logic. Drawing inspiration from
classical design patterns such as the *Chain of Responsibility* and
*Strategy*, as well as modern declarative paradigms, we propose a
framework —**Extensible Orchestrated Interceptor Workflows (EOIW)**—that
facilitates the construction of high-performance, maintainable, and
resilient command processing pipelines. EOIW models command execution as
a functional pipeline $`\mathcal{P}`$, where each stage $`s \in S`$
(e.g., routing, middleware invocation, handler execution,
post-processing) is orchestrated through dynamically extensible
interceptor chains $`I`$. The framework aims to provide predictable
latency, high throughput, and strong type safety , all while ensuring
idempotency guarantees.

This paper focuses on the command side of CQRS, specifically addressing
synchronous/asynchronous/disruptor processing, middleware orchestration,
routing, and handler execution. By abstracting execution concerns and
enabling dynamic extension of interceptor chains, our approach ensures
cleaner separation of responsibilities and better runtime adaptability.
The scope is confined to command-side processing, leaving the query side
for future exploration .

## Problem Statement

Command processing in modern enterprise systems, particularly in CQRS
architectures, often suffers from complexity, latency, and
maintainability issues. Traditional implementations tend to muddle
business logic with cross-cutting concerns such as logging, retry
handling, and audit propagation. This tight coupling makes pipelines
brittle and difficult to evolve as the system scales. Formally, let a
command pipeline be represented as
$`\mathcal{P} = \{s_1, s_2, \dots, s_n\}`$, where $`s_i \in S`$ denotes
a stage of execution. Without proper abstraction, each $`s_i`$ mixes
business logic $`B(s_i)`$ with operational concerns $`O(s_i)`$, leading
to high coupling and reduced maintainability :
``` math
\begin{equation}
    s_i = B(s_i) + O(s_i), \quad \forall i = 1, \dots, n
\end{equation}
```

## Motivation

There is a clear need for scalable, reliable, and pattern-driven designs
that allow for modular, high-performance command processing. By
decoupling operational concerns from business logic and leveraging
classical design patterns (e.g., Chain of Responsibility, Strategy,
Template Method) along with modern declarative and functional paradigms,
systems can achieve predictable latency, strong type safety, and
idempotency guarantees. EOIW introduces a separation such that:
``` math
\begin{equation}
s_i = B(s_i), \quad I_i = O(s_i), \quad \forall i
\end{equation}
```
where $`I_i`$ represents the interceptor chain handling operational
concerns independently of business logic .

## Scope

This work focuses on the command side of CQRS pipelines (excluding
query-side concerns). Specifically, it addresses
synchronous,asynchronous, disruptor processing, middleware
orchestration, routing, and handler execution. The proposed framework
abstracts execution concerns from business logic, enabling clean
separation of responsibilities and dynamic extension of command
workflows.

### Contributions

The paper makes several key contributions:

- Introduction of **Extensible Orchestrated Interceptor Workflows
  (EOIW)**, a composable and maintainable architecture for command
  processing pipelines.

- Systematic mapping of classical design patterns to CQRS command
  pipelines, highlighting performance and maintainability trade-offs.

- Formal temporal model $`\tau`$ for command execution and
  post-processing, ensuring deterministic ordering and resilience
  semantics under transient system failures.

- Practical implementation using Jersey(JAX-RS) Spring Boot and
  high-performance command queues, demonstrating measurable improvements
  in throughput and latency over conventional synchronous CQRS handlers.

# Background and Related Work

## Command Query Responsibility Segregation (CQRS)

Command Query Responsibility Segregation (CQRS) is an architectural
pattern that separates the handling of data modification (commands) from
data retrieval (queries) within an application. This segregation allows
for optimization of each operation independently, leading to improved
scalability, performance, and maintainability. The CQRS pattern is
particularly beneficial in scenarios where read and write operations
have distinct performance, scalability, or security requirements.

In traditional CRUD (Create, Read, Update, Delete) architectures, a
single data model is often used for both reading and writing operations.
However, as applications scale, this approach can lead to challenges
such as data mismatch, lock contention, and performance bottlenecks. By
adopting CQRS, these issues can be mitigated, as the read and write
models can be optimized separately to meet their specific needs.

Despite its advantages, implementing CQRS introduces complexity,
especially in maintaining consistency between the command and query
models. Techniques like eventual consistency or two-phase commit are
often employed to address these challenges.

## Event-Driven Architecture (EDA)

Event-Driven Architecture (EDA) is a design paradigm where system
components communicate through the production, detection, and reaction
to events. This approach enables decoupled, asynchronous communication
between components, facilitating scalability and responsiveness in
distributed systems .

In the context of CQRS, EDA plays a crucial role by enabling the
decoupling of command processing from query handling. Events generated
by command handlers can be asynchronously processed by other components,
allowing for real-time updates to the query models. This decoupling
enhances system flexibility and scalability, as components can evolve
independently without affecting the overall system.

However, EDA introduces challenges related to event ordering, message
delivery guarantees, and system observability. These challenges
necessitate the design of robust event processing mechanisms to ensure
system reliability and maintainability.

## Middleware and Interceptor Patterns

Middleware and interceptor patterns are design strategies that allow for
the insertion of additional processing logic into the execution flow of
an application. These patterns enable the separation of cross-cutting
concerns such as logging, authentication, and error handling from the
core business logic, promoting cleaner and more maintainable codebases.

In the context of CQRS and EDA, middleware and interceptors can be
utilized to manage concerns like retry mechanisms, resilience, logging,
and audit propagation. By abstracting these concerns into separate
components, the core business logic remains focused and decoupled from
operational concerns, facilitating easier maintenance and evolution of
the system.

## Temporal Models and Deterministic Execution

Ensuring deterministic execution and predictable latency is critical in
high-performance systems. Temporal models provide a framework for
reasoning about the timing and ordering of events within a system,
enabling the design of systems that exhibit predictable behavior even
under varying load conditions.

In the context of command processing pipelines, temporal models can be
employed to define the sequencing and timing constraints of command
execution and post-processing stages. This approach ensures that
commands are processed in a consistent and predictable manner,
facilitating the design of resilient systems that can gracefully handle
transient failures and maintain system stability.

## Related Frameworks and Implementations

Several frameworks and implementations have explored the integration of
CQRS, EDA, and interceptor patterns to build scalable and maintainable
systems:

Axon Framework: A Java framework that supports the development of
event-driven, CQRS-based applications, providing infrastructure for
event sourcing, aggregates, and sagas .

Spring Cloud Stream: A framework for building event-driven microservices
with Spring Boot, offering abstractions and support for integrating with
message brokers .

Triggerflow: An extensible trigger-based orchestration architecture for
serverless workflows, capable of constructing various reactive
schedulers and supporting high-volume event processing workloads .

Apache Camel: An integration framework that provides a rule-based
routing and mediation engine, supporting numerous protocols and data
formats. It allows the construction of complex, event-driven workflows
using Enterprise Integration Patterns (EIPs) .

Apache Fineract is an open-source, cloud-ready core banking platform
developed under the Apache Software Foundation. Designed to support
digital financial services, it offers scalable and secure solutions for
managing client data, loans, savings, and accounting operations. With a
modular architecture and comprehensive RESTful APIs, Fineract enables
financial institutions to deliver inclusive and customizable banking
services, fostering financial inclusion worldwide .

These frameworks demonstrate the practical application of combining
CQRS, EDA, and interceptor patterns to build robust and scalable
systems. However, they often focus on specific aspects of the
architecture and may not provide a comprehensive solution that
integrates all these elements seamlessly.

## Research Gaps and Opportunities

While existing research and frameworks have addressed various aspects of
command processing in distributed systems, several gaps remain:

Composability and Extensibility: Many existing solutions lack the
flexibility to dynamically compose and extend command processing
pipelines without significant refactoring.

Deterministic Execution: Achieving predictable latency and deterministic
execution in high-throughput systems remains a challenging problem.

Separation of Concerns: Effectively decoupling operational concerns from
business logic without introducing excessive complexity is an ongoing
challenge.

The proposed Extensible Orchestrated Interceptor Workflows (EOIW)
framework aims to address these gaps by providing a composable,
maintainable, and resilient architecture for command processing
pipelines. By integrating classical design patterns with modern
declarative and functional paradigms, EOIW seeks to achieve a balance
between performance and maintainability, facilitating the development of
high-performance, enterprise-grade systems.

# Functional Composition of the Command Pipeline

## Formal Definitions

Let:

- $`C`$ — the set of all **commands**.

- $`R`$ — the set of all **results**.

- $`E`$ — the set of all **exceptions**.

- $`\mathcal{S}`$ — the set of all **suppliers**, where
  $`s \in \mathcal{S}`$ is a function $`s: \varnothing \to R \cup E`$.

## Core Mappings

``` math
\begin{aligned}
\text{route} &: C \to H && \text{(selects the appropriate handler)} \\
\text{handle}_h &: C \to R \cup E && \text{(handler processes the command)} \\
\text{exec} &: C \to \mathcal{S} && \text{(executor creates deferred computation)} \\
\text{pre}_m &: C \to C && \text{(middleware transformation)} \\
\text{post}_p &: C \times (R \cup \{\bot\}) \times (E \cup \{\bot\}) \to (R \cup \{\bot\}) \times (E \cup \{\bot\}) && \text{(post-processor)}
\end{aligned}
```

## Composed Pipeline Function

``` math
\begin{equation}
\mathrm{pipeline} = \mathrm{wrapPost} \circ \mathrm{exec}
\end{equation}
```

For a command $`c \in C`$:
``` math
\begin{equation}
s = \mathrm{exec}(c)
\end{equation}
```
and
``` math
\begin{equation}
\mathrm{wrapPost}(s)() =
\mathrm{finalize}\big(
    \mathrm{fold}_{p \in P}(\mathrm{post}_p,\, \mathrm{tryExec}(s))
\big)
\end{equation}
```

## Execution Semantics

``` math
\begin{equation}
\mathrm{tryExec}(s) =
\begin{cases}
    (s(), \bot) & \text{if handler succeeds}\\[4pt]
    (\bot, e) & \text{if handler throws exception } e
\end{cases}
\end{equation}
```

## Temporal Property

A more precise formulation of the temporal property, separating
orchestration from execution, is:
``` math
\begin{align}
\forall c \in C,\, \exists! r \in R, e \in E : \quad
& \underbrace{\mathrm{pre}_{m_1} \circ \dots \circ \mathrm{pre}_{m_n}}_{\text{middleware orchestration}}(c) \notag\\
& \;\to\; 
\underbrace{\mathrm{exec}_h(c)}_{\text{command execution}} \notag\\
& \;\to\; 
\underbrace{\mathrm{post}_{p_1} \circ \dots \circ \mathrm{post}_{p_m}}_{\text{post-processing orchestration}}(c,r,e)
\end{align}
```

Where:

- $`\mathrm{pre}_{m_i}`$ – middleware transformation and side-effects
  (validation, logging, etc.) before command execution.

- $`\mathrm{exec}_h`$ – the actual handler invocation performing the
  command’s business logic, producing $`r \in R`$ or $`e \in E`$.

- $`\mathrm{post}_{p_j}`$ – post-processors applied sequentially after
  successful or failed execution for auditing, retries, notifications,
  etc.

This separation makes it explicit that **pipeline orchestration
(middleware and post-processing sequencing) is distinct from the core
command execution**, even though the temporal property ensures
deterministic and traceable sequencing across the full lifecycle.

#### Resilience Extension.

In the presence of systemic failures (e.g., transient network or
database unavailability), the temporal property is extended with
fallback semantics. If $`\mathrm{exec}(c)`$ cannot complete
successfully, a resilience mechanism (such as Resilience4J retries)
attempts recovery:
``` math
\begin{equation}
\forall c \in C,\ \exists! p \in P :
\big(
    \mathrm{exec}(c) \prec \mathrm{post}_p(c,r,e)
\big)
\ \lor\
\big(
    \mathrm{fail}(c) \Rightarrow \Diamond\, \mathrm{retry}(c)
\big)
\end{equation}
```

If recovery eventually fails, the system propagates an explicit
exception, preserving state consistency and traceability throughout the
command lifecycle.

# Data Structure and Algorithm

## Command

The `Command` class represents the fundamental unit of execution in the
EOIW framework. Each command encapsulates the metadata required for
tracking, auditing, and ensuring idempotency during processing .

<div class="algorithm">

<div class="algorithmic">

CommandT **Fields:** id : UUID idempotencyKey : String status : String
createdAt : DateTime updatedAt : DateTime tenantId : String username :
String payload : T

</div>

</div>

This class serves as a base abstraction for all executable operations .
Each subclass specializes the `payload` field to represent
domain-specific data.

## CommandExecutor

The `CommandExecutor` interface defines the contract for executing a
given `Command` instance. It encapsulates the functional semantics of
deferred execution by returning a `SupplierRES`, allowing commands to be
executed lazily or within transactional contexts. This abstraction
decouples the command dispatch logic from the actual execution strategy,
enabling flexible orchestration, retries, and interceptor-based
workflows.

<div class="algorithm">

<div class="algorithmic">

**Input:** command of type REQ **Output:** Supplier RES **Returns:** A
function (Supplier) that produces a result of type RES when invoked

</div>

</div>

In this interface, each command is associated with a type-safe
request–response pair REQ, RES. Implementations may wrap the returned
`Supplier` with cross-cutting behaviors such as retry logic, audit
propagation, or asynchronous scheduling depending on the configured
interceptor chain.

## CommandHandler

The **CommandHandler** interface defines the contract for processing
commands of type REQ and producing results of type RES . It serves as
the central point of business logic execution in the EOIW pipeline. Each
handler encapsulates domain-specific behavior and ensures type-safe
command resolution through the `matches()` function, which dynamically
determines compatibility based on generic type inspection.

Formally, a *CommandHandler* can be expressed as a mapping:
``` math
\begin{equation}
\mathcal{H}: \mathcal{C}_{REQ} \rightarrow \mathcal{R}_{RES}
\end{equation}
```
where $`\mathcal{C}_{REQ}`$ represents the domain of incoming commands
and $`\mathcal{R}_{RES}`$ represents the resulting output space. To
enable flexible routing and dynamic dispatch, the handler type
$`\tau_H`$ is resolved at runtime via the `matches()` method such that:
``` math
\begin{equation}
\tau_H \sqsubseteq \tau_P \implies \text{handler is eligible for command payload type } \tau_P
\end{equation}
```
This design allows the framework to automatically associate commands
with the appropriate handlers while maintaining strong compile-time type
guarantees.

<div class="algorithm">

<div class="algorithmic">

**Input:** command of type REQ **Output:** result of type RES
**Returns:** the processed result of the command

**Input:** command of any type **Output:** Boolean handlerType $`\gets`$
TypeToken of this handler payloadType $`\gets`$ class of command.payload
**Return:** true if handlerType is assignable from payloadType, else
false

</div>

</div>

## CommandMiddleware

The `CommandMiddleware` interface represents the intermediate layer
between the command dispatching process and handler execution.
Middleware components are responsible for executing cross-cutting
concerns such as validation, logging, authorization, resilience
policies, and telemetry propagation. Each middleware is executed in a
well-defined order within the interceptor chain, forming the
orchestration layer of the EOIW pipeline.

<div class="algorithm">

<div class="algorithmic">

**Input:** command of any generic type **Output:** none **Description:**
Performs pre-processing, validation, logging, or other middleware logic
before or after command handling.

**Input:** command passed to the next middleware in the chain
**Output:** continuation to the next interceptor **Description:**
Delegates command execution to the subsequent middleware or handler in
the chain, forming a composable pipeline:
``` math
\begin{equation}
        \mathcal{M}_1 \circ \mathcal{M}_2 \circ \dots \circ \mathcal{M}_n \Rightarrow \mathcal{H}
\end{equation}
```

</div>

</div>

## CommandPipeline

The `CommandPipeline` interface represents the orchestration layer
responsible for managing the end-to-end flow of a command from
submission to completion. It serves as the central entry point of the
EOIW architecture, coordinating middleware invocation, handler
resolution, and post-processing actions.

Formally, the pipeline $`\mathcal{P}`$ can be defined as:
``` math
\begin{equation}
\mathcal{P}(C) = (\mathcal{P\!P}_1 \circ \dots \circ \mathcal{P\!P}_m \circ \mathcal{H} \circ \mathcal{M}_n \circ \dots \circ \mathcal{M}_1)(C)
\end{equation}
```
where $`\mathcal{M}_i`$ represents middleware stages, $`\mathcal{H}`$
denotes the terminal handler, and $`\mathcal{P\!P}_j`$ represents
post-processing stages (logging, auditing, notifications, retries).

This model explicitly captures the **full lifecycle** of a command:

1.  **Middleware** – pre-processing and cross-cutting concerns.

2.  **Handler** – executes core business logic.

3.  **Post-Processors** – post-execution actions ensuring auditing,
    retry semantics, or additional side effects.

The actual evaluation of the command logic is **lazy**; the pipeline
produces a ‘Supplier‘ (or callable) that encapsulates the execution. The
command is only executed when the API or client explicitly triggers it,
allowing composable, runtime-extensible pipelines with controlled
evaluation timing.

<div class="algorithm">

<div class="algorithmic">

**Input:** command of type REQ **Output:** SupplierRES **Returns:** a
lazily evaluated function that produces the result when invoked
**Description:** Initiates command execution by passing the command
through the configured pipeline:

1.  Each middleware $`\mathcal{M}_i`$ performs pre-processing
    (validation, logging, retry).

2.  The command reaches the terminal handler $`\mathcal{H}`$ for core
    business logic execution.

3.  Post-processing stages $`\mathcal{P\!P}_j`$ execute in sequence,
    performing actions like audit logging, notifications, or retries.

``` math
\begin{equation}
        \mathcal{P} : \mathcal{C}_{REQ} \rightarrow \mathcal{S}_{RES}, \quad
        \mathcal{P}(C) = \mathcal{P\!P}_m(\dots \mathcal{P\!P}_1(\mathcal{H}(\mathcal{M}_n(\dots \mathcal{M}_1(C) \dots))))
\end{equation}
```

</div>

</div>

## CommandPostProcessor

The `CommandPostProcessor` interface represents the final stage of the
EOIW execution lifecycle. It is responsible for performing auxiliary
operations after the successful (or failed) execution of a command.
Formally, it defines a mapping from an executed command $`C_T`$ and its
outcome $`R_T`$ to a set of side effects or compensating actions
$`\Phi(C_T, R_T)`$ such that:

``` math
\begin{equation}
\Phi : (\mathcal{C}_T, \mathcal{R}_T) \rightarrow \mathcal{E}
\end{equation}
```

where $`\mathcal{E}`$ denotes the set of post-execution effects (e.g.,
audit logs, retries, notifications). This design isolates non-functional
concerns from the command handler, improving modularity and resilience.

<div class="algorithm">

<div class="algorithmic">

**Input:** command of type T **Output:** none **Description:** Executes
post-processing logic after the main command has completed. Typical
actions include:

- $`\Phi_1`$: persisting audit logs or metrics,

- $`\Phi_2`$: triggering retries for transient failures,

- $`\Phi_3`$: sending notifications or domain events,

- $`\Phi_4`$: cleaning up temporary state or caches.

The post-processor may be composed as a continuation $`\mathcal{P}^+`$
to the main pipeline $`\mathcal{P}`$:
``` math
\begin{equation}
            \mathcal{P}^+(C) = \Phi(C, \mathcal{P}(C))
\end{equation}
```

</div>

</div>

## CommandRouter

The `CommandRouter` interface defines the routing mechanism that maps
each command to a corresponding handler. It ensures that a command of
type $`\tau_P`$ is dispatched only to a handler capable of processing
that payload type $`\tau_H`$, following the eligibility constraint:

``` math
\begin{equation}
\tau_H \sqsubseteq \tau_P \implies \text{handler is eligible for payload type } \tau_P
\end{equation}
```

Formally, the router defines a partial function:

``` math
\begin{equation}
\rho : \mathcal{C}_{\tau_P} \rightarrow \mathcal{H}_{\tau_H}
\end{equation}
```

where $`\mathcal{C}_{\tau_P}`$ is the set of all command instances and
$`\mathcal{H}_{\tau_H}`$ is the set of valid handlers. If no suitable
handler exists, $`\rho`$ may yield $`\bot`$ (undefined), triggering a
`CommandNotRoutableException` or an equivalent recovery mechanism.

<div class="algorithm">

<div class="algorithmic">

**Input:** command of type REQ **Output:** CommandHandler of type REQ,
RES **Returns:** the first matching handler responsible for processing
the command **Description:** Determines the appropriate handler based on
the types of the request and expected response objects.

- The router scans the list of registered handlers and selects the first
  handler whose type matches the command payload.

- If multiple handlers are eligible, only the first match is returned.

- If no handler matches, a runtime exception or fallback mechanism is
  triggered.

</div>

</div>

## DefaultCommandPipelineImplementation

The `DefaultCommandPipelineImplementation` represents the concrete
realization of the `CommandPipeline` interface. It coordinates the
execution of commands through a series of middleware, handlers, and
post-processors, ensuring type-safe dispatch and lazy evaluation. The
pipeline encapsulates the complete command lifecycle:

``` math
\begin{equation}
\text{Command} \xrightarrow{\text{Executor}} \text{Handler} 
\xrightarrow{\text{Result}} \text{PostProcessor}^{*}
\end{equation}
```

Formally, given a command $`c_{\tau_P}`$ of type $`\tau_P`$, the
pipeline defines:
``` math
\begin{equation}
\pi(c_{\tau_P}) = \lambda () \rightarrow h_{\tau_H}(c_{\tau_P})
\end{equation}
```
where $`h_{\tau_H}`$ is the matching handler, and the returned function
is a lazy supplier of the eventual result.

<div class="algorithm">

<div class="algorithmic">

**Input:** command of type REQ **Output:** Supplier of type RES
**Precondition:** command must not be null baseSupplier $`\gets`$
executor.Execute(command)

**Return** a Supplier that, when invoked, performs: result $`\gets`$
null exception $`\gets`$ null **Try** result $`\gets`$
baseSupplier.Get() **Catch** ex exception $`\gets`$ ex **End Try**

**For each** postProcess **in** postProcesses **do** typedProcessor
$`\gets`$ (cast) postProcess to CommandPostProcessorREQ
typedProcessor.Run(command) **End For**

**If** exception $`\neq`$ null **then** throw
RuntimeException(`"Handler execution failed"`, exception) **End If**

**Return** result

</div>

</div>

The returned supplier enforces **lazy evaluation**: execution occurs
only when the supplier is invoked. This pattern allows deferred
computation, resilience policies (e.g., retries or circuit breakers),
and consistent post-processing even under handler exceptions.

## SynchronousCommandExecutorImplementation

The `SynchronousCommandExecutorImplementation` represents one of
multiple interchangeable executor variants (e.g., `Sync`, `Async`,
`Disruptor-based`) selectable via configuration. This executor performs
*synchronous, blocking* command execution by sequentially invoking
middleware and dispatching the command to its handler.

At runtime, the executor is conditionally activated based on
configuration properties such as:

``` math
\begin{equation}
\texttt{command.executor} \in \{ \texttt{sync}, \texttt{async}, \texttt{disruptor}, \ldots \}
\end{equation}
```

Formally, for a command $`c_{\tau_P}`$, the executor defines:
``` math
\begin{equation}
E_{\text{sync}}(c_{\tau_P}) = \lambda() \rightarrow h_{\tau_H}(M(c_{\tau_P}))
\end{equation}
```
where $`M`$ represents the ordered composition of middlewares and
$`h_{\tau_H}`$ is the routed handler.

<div class="algorithm">

<div class="algorithmic">

**Input:** command of type REQ **Output:** Supplier of type RES
**Description:** Executes the command synchronously by invoking all
middlewares and routing to the appropriate handler. *Note:* This
implementation is conditionally activated based on application
configuration (e.g., `command.executor = sync, async, disruptor, etc.`),
allowing multiple executor implementations to coexist and be selected at
runtime. middleware.Invoke(command) handler $`\gets`$
router.Route(command) **Return:** a Supplier that, when invoked,
executes `handler.Handle(command)` and returns the result

</div>

</div>

This synchronous executor enforces immediate evaluation — the handler
logic is executed in the same thread of invocation. It is best suited
for low-latency, deterministic workflows where blocking behavior is
acceptable. Alternative implementations (e.g., `AsyncCommandExecutor`,
`DisruptorCommandExecutor`) can extend this pattern to achieve
non-blocking or event-driven semantics.

# Command Processing Infrastructure

## Class Diagram

<figure id="fig:class-diagram-main" data-latex-placement="H">
<figure data-latex-placement="H">
<div class="center">

</div>
</figure>
<figcaption>Class diagram for the main system modules</figcaption>
</figure>

## Architecture

While the UML diagrams illustrate the structural composition of command
pipelines, it is instructive to consider the underlying architectural
principles that make such designs effective. At first glance, highly
concurrent, low-latency architectures like LMAX Disruptor may appear
suited for niche, high-throughput applications. However, their design
choices reveal broader benefits. By moving the working set into main
memory, adopting a single-threaded, in-memory event processor, and
separating cross-cutting concerns from core business logic, much of the
traditional complexity of enterprise applications is eliminated. Event
sourcing ensures durability without relying on synchronous database
coordination, while structured, deterministic processing simplifies
error handling and concurrency management, even when executed in a
synchronous pipeline.

This architectural philosophy resonates with the command-side of CQRS:
operations can be orchestrated through a synchronous pipeline where
middleware, handlers, and post-processors interact deterministically and
efficiently, without being tightly coupled to the underlying
infrastructure. It encourages clearer separation of responsibilities,
type safey, facilitates maintainable code, and provides predictable
performance. In essence, modern enterprise systems can borrow these
principles to achieve high throughput, resilience, and simplicity while
relying on synchronous execution.

# Best Practices

High-performance command processing pipelines require disciplined
architectural practices to ensure maintainability, resilience, and
predictable throughput. Based on the principles of Extensible
Orchestrated Interceptor Workflows (EOIW), the following best practices
are recommended:

## Consistent Use of the Command Pattern

The Command pattern encapsulates a request as an object, separating the
responsibility of issuing a command from the execution logic. Using the
Command pattern consistently across all operations ensures:

- **Decoupling of senders and receivers:** Pipeline orchestration stages
  can operate independently without tight coupling to business logic.

- **Enhanced testability:** Each command can be independently tested,
  mocked, or replayed.

- **Support for cross-cutting concerns:** Features such as logging,
  auditing, retries, and undo functionality can be applied uniformly
  across all commands without modifying core business logic.

Standardizing command representation ensures that interceptor chains and
orchestration layers can process all commands uniformly, improving
composability and predictability (Gamma et al. 1994).

## Lightweight and Composable Middleware

Middleware in command pipelines should be small, modular, and
composable, avoiding heavy monolithic components. This enables:

- **Dynamic orchestration:** Interceptor chains can be extended or
  reordered without affecting other components.

- **Independent evolution:** Middleware handling logging, retry, or
  security can evolve separately from the command handlers.

- **Minimal performance overhead:** Lightweight middleware ensures that
  cross-cutting concerns do not introduce latency bottlenecks.

Examples include logging interceptors, audit propagators, and resilience
wrappers. Composability ensures that these components can be reused
across pipelines and dynamically injected into the workflow (Fowler,
n.d.).

## High-Throughput Event-Driven Infrastructure

For high-performance command processing, adopting event-driven
infrastructures such as LMAX Disruptor or equivalent low-latency message
queues provides:

- **Lock-free inter-thread communication:** Minimizes contention in
  high-throughput systems.

- **Deterministic latency:** Consistent timing ensures commands are
  processed predictably.

- **Backpressure handling:** Event-driven frameworks allow the system to
  gracefully handle surges in command volume.

EOIW leverages these mechanisms to orchestrate commands efficiently,
ensuring that high volumes of requests are processed without bottlenecks
(Thompson et al. 2011).

## Standardized Command Execution

Applying Strategy or Template Method patterns for command execution
provides a consistent framework for handling:

- **Retries and failure handling:** Retry policies and exception
  management can be standardized across commands.

- **Pre- and post-processing:** Hooks for validation, logging, or
  notifications can be applied consistently.

- **Customizable behavior:** Specific business logic variations can be
  implemented by extending templates or strategies without altering the
  pipeline core.

This pattern-based approach ensures maintainable, predictable, and
extensible execution flows (Gamma et al. 1994).

## Monitoring, Observability, and Exception Handling

Continuous monitoring of command pipelines is essential to maintain
performance and reliability:

- **Performance metrics:** Track throughput, latency, and queue length
  at each pipeline stage.

- **Exception logging and handling:** Centralized logging of errors and
  propagation policies prevent silent failures.

- **Observability:** Distributed tracing and audit logs ensure
  visibility into command execution sequences and bottlenecks.

By instrumenting interceptor chains and middleware, EOIW provides
comprehensive insights into operational behavior, facilitating proactive
maintenance and optimization (Liu et al. 2013).

# Conclusion and Future Work

## Conclusion

In this work, we presented **Extensible Orchestrated Interceptor
Workflows (EOIW)**, a framework designed to address the performance,
maintainability, and resiliency challenges inherent in command-driven
enterprise systems. The key contributions of this study are threefold:

1.  **Pattern Mapping for Command Pipelines:** By systematically
    applying classical design patterns such as Chain of Responsibility,
    Strategy, and Template Method to command processing workflows, we
    demonstrate how these established techniques can be leveraged to
    improve modularity, extensibility, and predictability of command
    execution. This mapping provides practitioners with concrete
    guidelines for integrating pattern-based design in modern CQRS
    pipelines (Gamma et al. 1994).

2.  **Analysis of Performance and Maintainability Trade-offs:** EOIW
    provides a structured approach for decoupling operational
    concerns—such as retry handling, logging, audit propagation, and
    exception management—from business logic. Our analysis highlights
    the trade-offs between achieving low-latency, high-throughput
    command execution and maintaining clean, extensible architecture.
    The framework emphasizes lightweight, composable middleware and
    high-performance event-driven infrastructures, ensuring both
    operational efficiency and maintainability.

3.  **Practical Implementation Guide:** The framework is validated
    through an implementation leveraging Spring Boot and a
    high-throughput command queue, illustrating concrete techniques to
    orchestrate command execution with deterministic temporal behavior.
    The implementation demonstrates measurable improvements in
    throughput and latency compared to conventional synchronous CQRS
    handlers, providing a practical reference for developers aiming to
    deploy EOIW in enterprise environments (Dailey 2010).

Overall, EOIW advances the state of the art by offering a unified
methodology that integrates classical and modern design paradigms,
enabling developers to construct robust, high-performance command
processing pipelines while preserving system flexibility and
observability.

## Future Directions

While EOIW establishes a solid foundation for orchestrated command
pipelines, several avenues for future research and extension remain:

- **Distributed CQRS with Multiple Nodes:** Extending EOIW to fully
  distributed environments can address scalability demands of
  large-scale enterprise systems. Future work could explore
  synchronization and consistency mechanisms, dynamic load balancing,
  and fault-tolerant orchestration across geographically distributed
  nodes.

- **Event Sourcing Integration:** Integrating event sourcing with EOIW
  could further enhance system resilience and auditability. By
  persisting state transitions as immutable events, the framework could
  enable advanced recovery mechanisms, historical replay of command
  pipelines, and enhanced debugging capabilities.

- **AI-Assisted Command Routing and Pattern Detection:** Leveraging
  machine learning techniques to analyze command execution patterns
  could enable predictive routing, dynamic optimization of interceptor
  chains, and anomaly detection in real time. AI-assisted orchestration
  could optimize resource utilization and reduce latency under variable
  workloads, pushing EOIW toward adaptive, self-tuning command
  processing pipelines.

These future directions aim to expand EOIW’s applicability to complex,
high-volume, and highly dynamic enterprise systems, fostering further
research in high-performance, pattern-driven command processing
architectures. By combining deterministic execution guarantees with
extensibility and adaptability, EOIW can serve as a robust foundation
for next-generation enterprise applications.

### Practical Recommendations

Based on our study and implementation experience, we recommend:

- Designing interceptor chains with clear separation between core
  business logic and operational concerns.

- Using declarative pipeline configuration rather than hard-coded
  sequences to facilitate runtime extensibility.

- Implementing idempotent handlers and well-defined retry policies to
  improve resilience in distributed environments.

- Evaluating trade-offs between synchronous and asynchronous execution
  depending on latency requirements and throughput targets.

- Integrating observability mechanisms (logging, metrics , tracing) at
  the interceptor level to enable monitoring without polluting business
  logic.

# Appendix

## Command.Java <a href="#alg:commandAlgorithm" data-reference-type="ref"
data-reference="alg:commandAlgorithm">[alg:commandAlgorithm]</a>

``` java
package com.java.workflow.infrastructure.core;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class Command<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;
  private String idempotencyKey;
  private String status;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private String tenantId;
  private String username;
  private T payload;
}
```

## CommandConstants.Java

``` java
package com.java.workflow.infrastructure.core;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class CommandConstants {

  public static final String COMMAND_REQUEST_ID = "x-helloworld-request-id";
}
```

## CommandExecutor.Java <a href="#alg:commandExecutor" data-reference-type="ref"
data-reference="alg:commandExecutor">[alg:commandExecutor]</a>

``` java
package com.java.workflow.infrastructure.core;

import java.util.function.Supplier;

public interface CommandExecutor {
  <REQ, RES> Supplier<RES> execute(Command<REQ> command);
}
```

## CommandHandler.Java <a href="#alg:commandHandler" data-reference-type="ref"
data-reference="alg:commandHandler">[alg:commandHandler]</a>

``` java
package com.java.workflow.infrastructure.core;

import com.google.common.reflect.TypeToken;

public interface CommandHandler<REQ, RES> {
  RES handle(Command<REQ> command);

  default boolean matches(Command<?> command) {
    TypeToken<REQ> handlerType = new TypeToken<>(getClass()) {};
    return handlerType
            .getRawType()
            .isAssignableFrom(command.getPayload().getClass());
  }
}
```

## CommandMiddleware.Java <a href="#alg:commandMiddleware" data-reference-type="ref"
data-reference="alg:commandMiddleware">[alg:commandMiddleware]</a>

``` java
package com.java.workflow.infrastructure.core;

public interface CommandMiddleware {
  void invoke(Command<?> command);
}
```

## CommandPipeline.Java <a href="#alg:commandPipeline" data-reference-type="ref"
data-reference="alg:commandPipeline">[alg:commandPipeline]</a>

``` java
package com.java.workflow.infrastructure.core;

import java.util.function.Supplier;

public interface CommandPipeline {
  <REQ, RES>Supplier<RES> send(Command<REQ> command);
}
```

## CommandPostProcessor.Java <a href="#alg:commandPostProcessor" data-reference-type="ref"
data-reference="alg:commandPostProcessor">[alg:commandPostProcessor]</a>

``` java
package com.java.workflow.infrastructure.core;

@FunctionalInterface
public interface CommandPostProcessor<T> {
  void run(Command<T> command);
}
```

## CommandProperties.Java

``` java
package com.java.workflow.infrastructure.core;

import com.lmax.disruptor.dsl.ProducerType;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@ConfigurationProperties(prefix = "helloworld.command")
public final class CommandProperties implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Builder.Default
  private Boolean enabled = true;

  @Builder.Default
  private CommandExecutorType executor = CommandExecutorType.sync;

  @Builder.Default
  private Integer ringBufferSize = 1024;

  @Builder.Default
  private ProducerType producerType = ProducerType.SINGLE;

  public enum CommandExecutorType {
    sync,
    async,
    disruptor
  }
}
```

## CommandRouter.Java <a href="#alg:commandRouter" data-reference-type="ref"
data-reference="alg:commandRouter">[alg:commandRouter]</a>

``` java
package com.java.workflow.infrastructure.core;

@FunctionalInterface
public interface CommandRouter {

  <REQ,RES> CommandHandler<REQ,RES> route(Command<REQ> command);
}
```

## DefaultCommandPipeline.Java <a href="#alg:commandPipelineImpl" data-reference-type="ref"
data-reference="alg:commandPipelineImpl">[alg:commandPipelineImpl]</a> <a href="#fig:class-diagram-main" data-reference-type="ref"
data-reference="fig:class-diagram-main">1</a>

``` java
package com.java.workflow.infrastructure.implementation;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandExecutor;
import com.java.workflow.infrastructure.core.CommandPipeline;
import com.java.workflow.infrastructure.core.CommandPostProcessor;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnBean(CommandPipeline.class)
public class DefaultCommandPipeline implements CommandPipeline {
  private final CommandExecutor executor;
  private final List<CommandPostProcessor<?>> postProcesses;

  @Override
  public <REQ, RES> Supplier<RES> send(Command<REQ> command) {
    Objects.requireNonNull(command, "Command Must Not Be Null.");
    // Dependency Inject - PostProcessing
    // Base supplier executes handler + middleware
    Supplier<RES> baseSupplier = executor.execute(command);

    // Wrap the base supplier with post-processing
    return () -> {
      RES result = null;
      Throwable exception = null;
      try {
        // Execute the handler logic
        result = baseSupplier.get();
      } catch (Throwable ex) {
        exception = ex;
      }

      // Execute post-processing with both result and exception
      for(CommandPostProcessor<?> postProcess : postProcesses) {
        CommandPostProcessor<REQ> typedProcessor = (CommandPostProcessor<REQ>) postProcess;
        typedProcessor.run(command);
      }

      // If exception occurred, propagate it
      if (exception != null) {
        throw new RuntimeException("Handler execution failed", exception);
      }
      return result;
    };
  }
}
```

## DefaultCommandRouter.Java

``` java
package com.java.workflow.infrastructure.implementation;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandHandler;
import com.java.workflow.infrastructure.core.CommandRouter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnBean(CommandRouter.class)
public class DefaultCommandRouter implements CommandRouter {

  private final List<CommandHandler<?,?>> commandHandlers;

  @Override
  public <REQ, RES> CommandHandler<REQ, RES> route(Command<REQ> command) {

    if(command == null) {
      throw new RuntimeException("Command Handler Not Found!");
    }

    return (CommandHandler<REQ, RES>) commandHandlers
            .stream()
            .filter(handler -> handler.matches(command))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(command.getId().toString()));
  }
}
```

## DisruptorCommandExecutor.Java <a href="#alg:commandExecutorImpl" data-reference-type="ref"
data-reference="alg:commandExecutorImpl">[alg:commandExecutorImpl]</a>

``` java
package com.java.workflow.infrastructure.implementation;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandExecutor;
import com.java.workflow.infrastructure.core.CommandMiddleware;
import com.java.workflow.infrastructure.core.CommandRouter;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(value = "helloworld.command.executor", havingValue = "disruptor")
public class DisruptorCommandExecutor implements CommandExecutor, Closeable {

  private final Disruptor<CommandEvent> disruptor;

  @Override
  public <REQ, RES> Supplier<RES> execute(Command<REQ> command) {
    CommandEvent<REQ, RES> processedEvent = next(command);
    return processedEvent.getFuture()::join;
  }

  private <RES, REQ> CommandEvent<REQ,RES> next(Command<REQ> command) {
    var ringBuffer = disruptor.getRingBuffer();
    var sequenceId = ringBuffer.next();

    CommandEvent<REQ, RES> event = ringBuffer.get(sequenceId);
    event.setCommand(command);
    ringBuffer.publish(sequenceId);
    return event;
  }

  @Override
  public void close() throws IOException {
    disruptor.shutdown();
  }

  @Slf4j
  @Getter
  @Setter
  public static class CommandEvent<REQ, RES> {
    private Command<REQ> command;
    private CompletableFuture<RES> future = new CompletableFuture<>();
  }

  @Slf4j
  @RequiredArgsConstructor
  public static class CompletableCommandEventHandler implements EventHandler<CommandEvent>{
    private final List<CommandMiddleware> commandMiddlewares;
    private final CommandRouter router;

    @Override
    public void onEvent(CommandEvent event, long sequence, boolean endOfBatch) throws Exception {
      log.info("Inside CompletableCommandEventHandler - onEvent");
      try {
        for(CommandMiddleware middleware : commandMiddlewares) {
          middleware.invoke(event.getCommand());
        }

        var handler = router.route(event.getCommand());
        event.getFuture().complete(handler.handle(event.getCommand()));
      } catch(Exception e) {
        event.getFuture().completeExceptionally(e);
      }
    }
  }
}
```

## SynchronousCommandExecutor.Java <a href="#alg:commandExecutorImpl" data-reference-type="ref"
data-reference="alg:commandExecutorImpl">[alg:commandExecutorImpl]</a>

``` java
package com.java.workflow.infrastructure.implementation;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandExecutor;
import com.java.workflow.infrastructure.core.CommandHandler;
import com.java.workflow.infrastructure.core.CommandMiddleware;
import com.java.workflow.infrastructure.core.CommandRouter;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(value = "helloworld.command.executor", havingValue = "sync")
public class SynchronousCommandExecutor  implements CommandExecutor {

  private final List<CommandMiddleware> middlewares;
  private final CommandRouter router;

  @Override
  public <REQ, RES> Supplier<RES> execute(Command<REQ> command) {
    for(CommandMiddleware middleware : middlewares) {
      middleware.invoke(command);
    }
    CommandHandler<REQ, RES> handler = router.route(command);
    return (() -> handler.handle(command));
  }
}
```

## CommandPersistanceMiddleware.Java

``` java
package com.java.workflow.infrastructure.persistence.middlewares;

import com.fasterxml.jackson.databind.JsonNode;
import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandMiddleware;
import com.java.workflow.infrastructure.persistence.domain.CommandEntity;
import com.java.workflow.infrastructure.persistence.domain.CommandRepository;
import com.java.workflow.infrastructure.persistence.mappers.CommandJsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Order(value = 2)
@Component
public class CommandPersistanceMiddleware implements CommandMiddleware {

  private final CommandRepository commandRepository;
  private final CommandJsonMapper commandJsonMapper;

  @Override
  public void invoke(Command<?> command) {
    if (command == null) {
      return;
    }

    try {
      JsonNode payloadJson = commandJsonMapper.map(command.getPayload());
      CommandEntity entity = new CommandEntity();

      entity.setCommandId(command.getId());
      entity.setPayload(payloadJson);
      entity.setCreatedAt(command.getCreatedAt());

      commandRepository.save(entity);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
```

## ResiliencePostProcessor.Java

``` java
package com.java.workflow.infrastructure.postprocess;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandPostProcessor;
import com.java.workflow.infrastructure.persistence.domain.CommandEntity;
import com.java.workflow.infrastructure.persistence.domain.CommandRepository;
import io.github.resilience4j.retry.annotation.Retry;
import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Order(value = 1)
@Component
public class ResiliencePostProcessor<T> implements CommandPostProcessor<T> {

  private final CommandRepository repository;
  private final AtomicInteger counter = new AtomicInteger(0);

  @Transactional
  @Retry(name = "run", fallbackMethod = "updateCommandStatusFallback")
  @Override
  public void run(Command<T> command) {

    log.info("Command PostProcess Resilience 4J");

    // Fetch the DB entity
    CommandEntity commandEntity = repository.findByCommandId(command.getId());
    if (commandEntity == null) {
      throw new RuntimeException("CommandEntity not found for ID: " + command.getId());
    }
    log.info("CommandEntity fetched: {}", commandEntity);

    try {
      // Update the status to CLOSED
      commandEntity.setStatus("CLOSED");
      commandEntity.setUpdatedAt(OffsetDateTime.now());
      repository.save(commandEntity);
      log.info("Command status updated to CLOSED for ID: {}", command.getId());
    } catch (DataAccessException ex) {
      // Only retry for actual DB access errors
      log.warn("Transient DB issue encountered for command ID {}: {}", command.getId(), ex.getMessage());
      throw new RuntimeException("Transient DB error, retrying...", ex);
    }
  }

  // Fallback method when all retries fail
  public void updateCommandStatusFallback(Long id, Throwable e) {
    log.error("Retries exhausted for command {}, marking as failed in memory only. Root cause: {}", id, e.getMessage());
    throw new RuntimeException("Critical failure: Unable to update command status; DB unreachable", e);
  }
}
```

## CommandConfiguration.Java

``` java
package com.java.workflow.infrastructure.starter;

import com.java.workflow.infrastructure.core.CommandMiddleware;
import com.java.workflow.infrastructure.core.CommandProperties;
import com.java.workflow.infrastructure.core.CommandRouter;
import com.java.workflow.infrastructure.implementation.DisruptorCommandExecutor;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(CommandProperties.class)
@ComponentScan("com.java.command.core")
@ComponentScan("com.java.command.implementation")
public class CommandConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(value = "helloworld.command.executor", havingValue = "disruptor")
  WaitStrategy waitStrategy() {
    log.info("Inside CommandConfiguration - waitStrategy");
    return new YieldingWaitStrategy();
  }

  @Bean
  @ConditionalOnProperty(value = "helloworld.command.executor", havingValue = "disruptor")
  Disruptor<?> disruptor (CommandProperties properties, WaitStrategy waitStrategy, List<CommandMiddleware> middlewares, CommandRouter router) {
    Disruptor<DisruptorCommandExecutor.CommandEvent> disruptor =
            new Disruptor<>(DisruptorCommandExecutor.CommandEvent::new,
                    properties.getRingBufferSize(),
                    DaemonThreadFactory.INSTANCE,
                    properties.getProducerType(),
                    waitStrategy);

    log.info("Inside CommandConfiguration - disruptor");

    disruptor.handleEventsWith(new DisruptorCommandExecutor.CompletableCommandEventHandler(middlewares, router));
    disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
    disruptor.start();

    return disruptor;
  }
}
```

<div id="refs" class="references csl-bib-body hanging-indent">

<div id="ref-fineract" class="csl-entry">

Dailey, James. 2010. *ApacheFineract*.

</div>

<div id="ref-martinFowlerInterceptor" class="csl-entry">

Fowler, Martin. n.d. *Event Interception Patterns*.
<https://martinfowler.com/articles/patterns-legacy-displacement/event-interception.html>.

</div>

<div id="ref-gamma1994design" class="csl-entry">

Gamma, Erich, Richard Helm, Ralph Johnson, and John Vlissides. 1994.
*Design Patterns, Software Engineering, Object-Oriented Programming*.
Addison-Wesley Boston.

</div>

<div id="ref-liu2013observability" class="csl-entry">

Liu, Yang-Yu, Jean-Jacques Slotine, and Albert-László Barabási. 2013.
“Observability of Complex Systems.” *Proceedings of the National Academy
of Sciences* 110 (7): 2460–65.

</div>

<div id="ref-thompson2011disruptor" class="csl-entry">

Thompson, Martin, Dave Farley, Michael Barker, Patricia Gee, and Andrew
Stewart. 2011. *Disruptor: High Performance Alternative to Bounded
Queues for*.

</div>

</div>

[^1]: Email: kapil.panchal.development@gmail.com
