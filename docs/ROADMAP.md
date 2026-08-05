# BasicRotor Roadmap

This document describes the long-term development plan for BasicRotor.

The roadmap focuses on building a reusable framework rather than implementing individual machines.

---

# Phase 1 — Rotor Prototype ✅

**Status:** Completed

Goal:

Build the first working animated block renderer.

Completed:

* Custom BlockEntity renderer
* Client-side animation
* Facing-aware rotation
* Lighting support
* Multiplayer-compatible rendering
* Rotor prototype

---

# Phase 2 — Framework Foundation

**Status:** In Progress

Goal:

Build the core framework that every future machine will use.

Planned:

* Assembly data layer
* Movement framework
* Assembly manager
* Common transform system
* Assembly renderer

---

# Phase 3 — Linking System

Goal:

Allow players to build custom assemblies.

Planned:

* Link Tool
* Unlink Tool
* Assembly validation
* Persistent linked data

---

# Phase 4 — Runtime Assembly

Goal:

Turn linked blocks into animated virtual structures.

Planned:

* Hide original blocks
* Render virtual blocks
* Runtime assembly lifecycle
* Smooth braking
* Return to origin
* Restore hidden blocks

---

# Phase 5 — Redstone Integration

Goal:

Allow assemblies to react to gameplay.

Planned:

* Redstone activation
* Start / Stop control
* Runtime state management

---

# Phase 6 — Framework Expansion

Goal:

Support multiple movement types.

Planned:

* Rotation movement
* Translation movement
* Oscillation movement
* Combined movements

---

# Phase 7 — Example Machines

The framework should be capable of powering many different structures.

Examples:

* Windmill
* Water Wheel
* Gear Train
* Steam Turbine
* Conveyor
* Crane
* Elevator
* Rotating Bridge
* Decorative Mechanical Structures

---

# Long-Term Vision

BasicRotor should become a reusable animation framework for Minecraft mechanical structures.

New machines should primarily be created by combining existing framework components instead of introducing machine-specific implementations.
