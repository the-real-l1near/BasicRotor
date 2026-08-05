# Basic Rotor

A Minecraft Fabric mod focused on creating advanced mechanical structures and a flexible block movement system.

The project starts with a simple rotating rotor, but the long-term goal is to build a reusable **Linked Assembly System** that allows players to create complex moving machines, mechanical structures, and multiblock animations.

---

# Overview

Minecraft blocks are normally static. Moving large structures by physically changing blocks causes many problems:

* excessive world updates
* lighting recalculation
* neighbor updates
* block entity synchronization
* collision issues
* performance problems

Basic Rotor takes a different approach.

Instead of moving real blocks, the mod creates a **virtual moving assembly**.

Real blocks remain in the world as the source of truth, while the client renders a temporary animated version of linked blocks.

---

# Core Concept

## Real Blocks + Virtual Assembly

A linked structure consists of two parts:

### Real Structure

The actual Minecraft blocks:

```
[Blade]
   |
[Rotor]---[Gear]
```

These blocks:

* stay in their original positions
* keep their block data
* can be saved normally
* can be restored at any time

---

### Virtual Assembly

When activated, the client creates a rendered copy:

```
       Blade

Gear -- Rotor -- Blade

          ↻
```

The virtual assembly handles:

* rotation
* translation
* animation
* mechanical movement

The original blocks are temporarily hidden and locked while the assembly is active.

---

# Features

## Rotor System

Current development focus:

* Custom block entity rendering
* Direction-based rotation
* Smooth client-side animation
* Facing-aware rotation axis

The rotor system is the first test case for the future assembly framework.

---

## Linked Assembly System (Planned)

Players will be able to combine multiple blocks into one moving structure.

Example:

```
        Blade

Gear --- Rotor --- Blade

        Shaft
```

A linked assembly stores:

* block type
* block state
* facing direction
* relative position
* block entity data

The structure is controlled from an origin point.

Example:

```
Origin:
(100,64,100)

Rotor:
(0,0,0)

Blade:
(1,0,0)

Gear:
(-1,0,0)
```

---

# Movement System

The goal is not to create only windmills.

The movement engine is designed for multiple mechanical systems.

## Rotation

Examples:

* Windmills
* Turbines
* Wheels
* Gears
* Rotating machines

```
Movement Type:
ROTATION

Axis:
Z
```

---

## Translation

Examples:

* Pistons
* Sliding doors
* Moving platforms

```
Movement Type:
TRANSLATION

Axis:
X
```

---

## Future Possibilities

* Mechanical arms
* Multi-axis machines
* Complex animations
* Large moving structures

---

# Assembly States

Moving structures use a state system:

```
STOPPED
RUNNING
BRAKING
RETURNING
```

## STOPPED

Normal world state:

* Real blocks are visible
* Player interaction is enabled
* Structure can be edited

---

## RUNNING

When powered:

* Virtual assembly appears
* Original blocks are hidden
* Movement begins

---

## BRAKING

When power is removed:

* The structure does not instantly stop
* Speed gradually decreases
* Mechanical inertia is preserved

---

## RETURNING

After stopping:

* The assembly smoothly returns to its original position
* No sudden snapping
* Real blocks are restored after movement ends

---

# Interaction System

While an assembly is moving:

* Linked blocks cannot be modified
* Link tools cannot edit the structure
* Blocks cannot be broken or interacted with

The player must wait until the machine fully stops.

Example:

```
Turn off the redstone and wait until the assembly stops.
```

This prevents conflicts between real blocks and virtual rendering.

---

# Technical Direction

Basic Rotor follows a strict separation between:

## Server

Responsible for:

* assembly data
* linked blocks
* machine state
* movement rules
* player interaction

---

## Client

Responsible for:

* rendering
* interpolation
* animation
* visual transformations

Animation is not synchronized every frame.

Instead:

Server synchronizes the important state:

```
running
speed
movement type
assembly data
```

The client calculates smooth visual movement locally.

---

# Architecture

Planned structure:

```
com.l1near.basicrotor

assembly/
 ├── Assembly.java
 ├── LinkedBlockData.java
 ├── AssemblyManager.java
 ├── AssemblyTransform.java
 ├── MovementType.java
 └── AssemblyState.java


block/
 ├── AssemblyControllerBlock.java
 └── LinkableBlock.java


item/
 └── LinkTool.java


client/
 └── AssemblyRenderer.java
```

---

# Development Roadmap

## Phase 1 - Rotor Prototype

* [x] Custom block entity renderer
* [x] Rotation animation
* [x] Facing-based rotation
* [x] Client-side rendering system

---

## Phase 2 - Assembly Foundation

* [ ] Create assembly data system
* [ ] Link blocks together
* [ ] Save and load assemblies
* [ ] Manage linked structures

---

## Phase 3 - Virtual Rendering

* [ ] Render multiple blocks as one object
* [ ] Hide original linked blocks
* [ ] Apply shared transformations

---

## Phase 4 - Mechanical Movement

* [ ] Rotation system
* [ ] Translation system
* [ ] Acceleration
* [ ] Braking
* [ ] Returning animation

---

## Phase 5 - Expansion

* [ ] Energy systems
* [ ] Advanced machines
* [ ] Sounds
* [ ] Particles
* [ ] Complex mechanical structures

---

# Long-Term Goal

Basic Rotor aims to become a foundation for creating mechanical systems in Minecraft Fabric.

The first implementation is a rotating rotor, but the final goal is a general-purpose movement framework capable of powering:

* windmills
* turbines
* factories
* mechanical machines
* animated structures
* custom multiblock systems

---

# License

License information will be added later.
