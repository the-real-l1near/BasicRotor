# BasicRotor Architecture

This document describes the current high-level structure of BasicRotor.

BasicRotor uses a server-authoritative design. The server owns gameplay state and assembly data, while the client is responsible for interpolation and rendering.

## Overview

The mod is built around three main systems:

- Rotor movement
- Linked block assemblies
- Client-side rendering

These systems are intentionally kept separate so movement logic, assembly data, and rendering do not depend too heavily on each other.

## Rotor Movement

Rotor movement is controlled by:

- `MovementData`
- `MovementInput`
- `MovementRuntime`
- `MovementState`

`MovementData` stores the current movement values such as rotation and speed.

`MovementInput` contains temporary inputs for the current tick, such as redstone power or a return request.

`MovementRuntime` updates the movement state every tick.

The current movement flow is:

```text
STOPPED
   ↓
STARTING
   ↓
RUNNING
   ↓
BRAKING
   ↓
IDLE
   ↓
RETURNING
   ↓
STOPPED