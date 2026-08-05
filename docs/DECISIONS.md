# BasicRotor Design Decisions

This document records major architectural decisions made during the development of BasicRotor.

The purpose of this document is to preserve the reasoning behind important design choices, making the project easier to understand and maintain over time.

---

# ADR-001

## Title

Virtual Block Rendering

## Status

Accepted

## Decision

Animated assemblies are rendered as client-side virtual blocks instead of physically moving Minecraft blocks.

## Reason

Minecraft is not designed to move large numbers of blocks every tick.

Keeping the original blocks in place while rendering virtual copies:

* Reduces world updates
* Improves performance
* Preserves block states
* Simplifies multiplayer synchronization
* Avoids unnecessary chunk updates

## Alternatives Considered

### Move real blocks

Rejected because it requires continuous block updates, neighbor updates, lighting recalculations, and significantly increases server workload.

### Convert assemblies into entities

Rejected because it complicates rendering, persistence, and future interaction with vanilla blocks.

---

# ADR-002

## Title

Separate Rendering from Gameplay

## Status

Accepted

## Decision

Rendering is purely visual.

Gameplay logic must never depend on rendering.

## Reason

The server remains authoritative.

Clients are free to interpolate and animate without affecting gameplay.

This also allows multiplayer support without synchronizing every animation frame.

---

# ADR-003

## Title

Assembly as the Core Data Model

## Status

Accepted

## Decision

Every animated structure is represented as a LinkedAssembly.

## Reason

Using a single data model makes the framework reusable.

Future machines should reuse the same assembly system instead of implementing their own structure management.

---

# ADR-004

## Title

Movement is Independent

## Status

Accepted

## Decision

Movement is implemented separately from rendering.

Movement generates transforms.

Rendering consumes transforms.

## Reason

This separation allows multiple movement implementations to reuse the same rendering pipeline.

Future movement types can be introduced without modifying renderer code.

---

# ADR-005

## Title

Assembly Stores Data Only

## Status

Accepted

## Decision

Assemblies are responsible only for storing data.

They do not render or calculate movement.

## Reason

Following the Single Responsibility Principle keeps the architecture modular and easier to maintain.

---

# ADR-006

## Title

Documentation Before Implementation

## Status

Accepted

## Decision

Major architectural changes should be documented before implementation begins.

## Reason

Writing documentation first forces architectural decisions to be considered carefully and reduces unnecessary refactoring later.

---

# Future Decisions

This file will continue to grow as the framework evolves.

Examples of future decision records include:

* Save format
* Networking strategy
* Assembly serialization
* Transform pipeline
* Animation interpolation
* Runtime optimization
* Multithreading strategy
* Chunk loading behavior
