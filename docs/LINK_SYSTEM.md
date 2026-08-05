# Link System

This document specifies how linked assemblies are created, managed, and executed within BasicRotor.

The link system is responsible for defining relationships between blocks. It does **not** handle rendering or movement directly.

---

# Overview

A linked assembly is a collection of blocks that behave as a single animated structure.

Every assembly has exactly one origin block, called the **Rotor**.

All linked blocks store their position relative to the rotor.

---

# Terminology

| Term             | Description                                        |
| ---------------- | -------------------------------------------------- |
| Rotor            | The origin block of an assembly.                   |
| Linked Block     | A block attached to the rotor.                     |
| LinkedAssembly   | The complete collection of linked blocks.          |
| Runtime Assembly | A temporary animated version of a linked assembly. |
| Link Tool        | The item used to create or remove links.           |

---

# Assembly Lifecycle

```text
Stopped
    │
    ▼
Powered
    │
    ▼
Starting
    │
    ▼
Running
    │
    ▼
Braking
    │
    ▼
Returning
    │
    ▼
Stopped
```

---

# Linking Workflow

```text
Place Rotor
      │
      ▼
Place Blocks
      │
      ▼
Use Link Tool
      │
      ▼
Validate Structure
      │
      ▼
Store LinkedAssembly
      │
      ▼
Ready
```

No animation occurs during the linking process.

---

# Runtime Workflow

```text
Receive Redstone
        │
        ▼
Hide Original Blocks
        │
        ▼
Create Runtime Assembly
        │
        ▼
Movement Updates Transform
        │
        ▼
Renderer Draws Virtual Blocks
        │
        ▼
Power Removed
        │
        ▼
Brake
        │
        ▼
Return To Origin
        │
        ▼
Restore Original Blocks
```

---

# Linked Block Data

Each linked block stores only the information required to reconstruct the structure.

Typical data includes:

* Relative position
* Block state
* Facing
* Additional block properties (if required)

Absolute world positions are never stored inside the assembly.

---

# Runtime Assembly

The runtime assembly exists only while the structure is animated.

Responsibilities:

* Read linked block data
* Generate render transforms
* Provide render data to the renderer

The runtime assembly does not permanently modify the world.

---

# Editing Rules

Assemblies may only be modified while stopped.

When an assembly is running:

* Linking is disabled.
* Unlinking is disabled.
* The Link Tool should display a message informing the player to stop the assembly before editing.

---

# Hidden Blocks

When an assembly starts:

* Original linked blocks become inactive.
* They behave as if they are not present for interaction.
* Virtual blocks become the visible representation.

When the assembly stops:

* Virtual blocks are removed.
* Original blocks become active again.
* Existing links remain unchanged.

---

# Validation Rules

Before an assembly can be created:

* A rotor must exist.
* Every linked block must belong to the same assembly.
* Duplicate links are not allowed.
* Invalid references are rejected.

---

# Design Invariants

The following rules must always remain true:

* One rotor owns exactly one linked assembly.
* One block may belong to only one assembly at a time.
* Rendering never changes linked data.
* Movement never modifies link relationships.
* Link relationships survive power cycles.
* Links are removed only when explicitly unlinked or when a linked block is destroyed.

---

# Future Extensions

The link system is designed to support future features without changing its core architecture.

Possible extensions include:

* Multiple movement controllers
* Hierarchical assemblies
* Dynamic attachment points
* Serialization improvements
* Blueprint export/import
* Network synchronization
