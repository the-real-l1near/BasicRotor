# BasicRotor Architecture

## Vision

BasicRotor is a framework for creating animated linked block assemblies in Minecraft.

Instead of physically moving blocks through the world, BasicRotor renders virtual assemblies while preserving the original world state. This approach minimizes world updates, improves performance, and allows complex mechanical structures to be animated without affecting gameplay.

Although the project begins with a simple rotor block, its long-term goal is to become a reusable framework for building many different mechanical structures.

---

# Project Goals

BasicRotor aims to:

* Build a reusable framework instead of a single-purpose machine.
* Separate game logic from rendering.
* Support multiplayer environments.
* Minimize unnecessary world updates.
* Keep every system modular and extensible.
* Follow clean software architecture principles.

---

# Non-Goals

BasicRotor intentionally does **not** attempt to:

* Move real Minecraft blocks every tick.
* Replace structures with entities.
* Implement a custom physics engine.
* Simulate collisions for moving structures.
* Make rendering responsible for gameplay.

---

# Core Principles

## Everything is an Assembly

Every animated structure is represented as an Assembly.

Examples include:

* Windmills
* Water Wheels
* Gear Systems
* Turbines
* Conveyors
* Cranes

The framework should solve general problems instead of machine-specific problems.

---

## Data is Independent

Assemblies only store data.

They never render themselves.

They never calculate movement.

---

## Movement is Independent

Movement systems generate transforms.

They never render anything.

They never modify world data directly.

---

## Rendering is Independent

Rendering consumes transforms.

Rendering never changes gameplay.

Rendering only visualizes the current state.

---

## Server Controls Gameplay

Gameplay is always determined by server-side logic.

Client-side rendering exists only to display animation.

---

# High-Level Architecture

```text
           World
             │
             ▼
      LinkedAssembly
             │
             ▼
   Movement Controller
             │
             ▼
      Transform Data
             │
             ▼
    Client-side Renderer
```

Each layer has exactly one responsibility.

---

# Package Responsibilities

## assembly

Stores assembly-related data.

Contains:

* LinkedAssembly
* LinkedBlockData
* AssemblyState

Does not:

* Render
* Animate
* Access client-only classes

---

## movement

Calculates movement.

Examples:

* RotationMovement
* TranslationMovement
* OscillationMovement

Does not:

* Render
* Store world data
* Access rendering APIs

---

## client.render

Responsible for rendering virtual structures.

Responsibilities:

* Read transform data
* Submit block models
* Apply visual animation

Does not:

* Change gameplay
* Store assemblies
* Calculate movement

---

# Runtime Flow

```text
Place Rotor
      │
      ▼
Link Blocks
      │
      ▼
Create Assembly
      │
      ▼
Power On
      │
      ▼
Movement Updates Transform
      │
      ▼
Renderer Draws Virtual Blocks
      │
      ▼
Power Off
      │
      ▼
Brake
      │
      ▼
Return To Origin
      │
      ▼
Restore Hidden Blocks
```

---

# Design Rules

* One class should have one primary responsibility.
* Prefer composition over inheritance.
* Keep systems loosely coupled.
* Keep rendering independent from gameplay.
* Keep movement independent from rendering.
* Never duplicate movement logic.
* Prefer reusable systems over machine-specific implementations.
* Documentation should be updated before major architectural changes.

---

# Future Expansion

The architecture is designed to support many future mechanical systems without major redesign.

Examples:

* Windmills
* Water Wheels
* Gear Trains
* Steam Turbines
* Conveyors
* Rotating Bridges
* Cranes
* Elevators
* Decorative Rotating Structures

---

# Glossary

| Term             | Description                                                                  |
| ---------------- | ---------------------------------------------------------------------------- |
| Assembly         | A collection of linked blocks treated as one animated structure.             |
| Linked Block     | A block belonging to an assembly.                                            |
| Origin           | The reference position of an assembly.                                       |
| Transform        | Position and rotation data used for rendering.                               |
| Movement         | A system responsible for generating transforms over time.                    |
| Virtual Block    | A client-rendered block that replaces a hidden world block during animation. |
| Runtime Assembly | The animated representation of an assembly while it is active.               |
